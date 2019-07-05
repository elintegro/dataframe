/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.register

import com.sun.istack.internal.Nullable
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.oauth2.SpringSecurityOauth2BaseService
import grails.plugin.springsecurity.oauth2.exception.OAuth2Exception
import grails.plugin.springsecurity.oauth2.token.OAuth2SpringToken
import grails.plugin.springsecurity.userdetails.GrailsUser
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.servlet.ModelAndView

abstract class SpringSecurityOAuth2Controller extends grails.plugin.springsecurity.oauth2.SpringSecurityOAuth2Controller{

    public static final String SPRING_SECURITY_OAUTH_TOKEN = 'springSecurityOAuthToken'
    SpringSecurityOauth2BaseService springSecurityOauth2BaseService
    SpringSecurityService springSecurityService

    def onSuccess() {
        String provider = params.provider
        Map parameters =["reloadPage": true, "success": true, "msg": provider.capitalize()+" Authentication Successful"]
        String formattedParams = formatParams(parameters)
        if (!provider) {
            log.warn "The Spring Security OAuth callback URL must include the 'provider' URL parameter"
            throw new OAuth2Exception("The Spring Security OAuth callback URL must include the 'provider' URL parameter")
        }
        def sessionKey = springSecurityOauth2BaseService.sessionKeyForAccessToken(provider)
        if (!session[sessionKey]) {
            log.warn "No OAuth token in the session for provider '${provider}'"
            throw new OAuth2Exception("Authentication error for provider '${provider}'")
        }
        // Create the relevant authentication token and attempt to log in.
        OAuth2SpringToken oAuthToken = springSecurityOauth2BaseService.createAuthToken(provider, session[sessionKey])

        if (oAuthToken.principal instanceof GrailsUser) {
            authenticateAndRedirect(oAuthToken, getDefaultTargetUrl(formattedParams))
        } else {
            // This OAuth account hasn't been registered against an internal
            // account yet. Give the oAuthID the opportunity to create a new
            // internal account or link to an existing one.
            session[SPRING_SECURITY_OAUTH_TOKEN] = oAuthToken

            def redirectUrl = springSecurityOauth2BaseService.getAskToLinkOrCreateAccountUri()
            if (!redirectUrl) {
                log.warn "grails.plugin.springsecurity.oauth.registration.askToLinkOrCreateAccountUri configuration option must be set"
                throw new OAuth2Exception('Internal error')
            }
            log.debug "Redirecting to askToLinkOrCreateAccountUri: ${redirectUrl}"
            redirect(redirectUrl instanceof Map ? redirectUrl : [uri: redirectUrl + formattedParams])
        }
    }

    def ask() {
        if (springSecurityService.isLoggedIn()) {
            def currentUser = springSecurityService.currentUser
            OAuth2SpringToken oAuth2SpringToken = session[SPRING_SECURITY_OAUTH_TOKEN] as OAuth2SpringToken
            // Check for token in session
            if (!oAuth2SpringToken) {
                log.warn("ask: OAuthToken not found in session")
                throw new OAuth2Exception('Authentication error')
            }
            // Try to add the token to the OAuthID's
            currentUser.addTooAuthIDs(
                    provider: oAuth2SpringToken.providerName,
                    accessToken: oAuth2SpringToken.socialId,
                    user: currentUser
            )
            if (currentUser.validate() && currentUser.save()) {
                // Could assign the token to the OAuthIDs. Login and redirect
                oAuth2SpringToken = springSecurityOauth2BaseService.updateOAuthToken(oAuth2SpringToken, currentUser)
                authenticateAndRedirect(oAuth2SpringToken, getDefaultTargetUrl())
                return
            }
        }
        // There seems to be a new one in the town aka 'There is no one logged in'
        // Ask to create a new account or link an existing user to it
        return new ModelAndView("/springSecurityOAuth2/ask", [:])
    }

    protected void authenticateAndRedirect(@Nullable OAuth2SpringToken oAuthToken, redirectUrl) {
        session.removeAttribute SPRING_SECURITY_OAUTH_TOKEN
        SecurityContextHolder.context.authentication = oAuthToken
        redirect(redirectUrl instanceof Map ? redirectUrl : [uri: redirectUrl])
    }

    protected Map getDefaultTargetUrl(String params) {
        def config = SpringSecurityUtils.securityConfig
        def savedRequest = SpringSecurityUtils.getSavedRequest(session)
        def defaultUrlOnNull = '/'
        if (savedRequest && !config.successHandler.alwaysUseDefault) {
            def url = (savedRequest.redirectUrl ?: defaultUrlOnNull)
            return [url: url+params]
        }
        String uri = (config.successHandler.defaultTargetUrl ?: defaultUrlOnNull)
        return [uri: uri+params]
    }

    protected String formatParams(Map params){
        if(params.isEmpty()){
            return ""
        }
        StringBuilder builtParams = new StringBuilder()
        Set keys = params.keySet()
        String lastIndex = keys.getAt(keys.size()-1).toString()
        builtParams.append("?")
        keys.each{
            String separator = (lastIndex.equalsIgnoreCase(it.toString()))?"":"&"
            builtParams.append(it.toString()+"="+params.get(it.toString())+separator)
        }

        return builtParams
    }
}

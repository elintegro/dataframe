package com.elintegro.register

import com.sun.istack.internal.Nullable
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.oauth2.SpringSecurityOauth2BaseService
import grails.plugin.springsecurity.oauth2.exception.OAuth2Exception
import grails.plugin.springsecurity.oauth2.token.OAuth2SpringToken
import org.springframework.security.core.context.SecurityContextHolder

class LoginWithGoogleController {
    SpringSecurityOauth2BaseService springSecurityOauth2BaseService
    SpringSecurityService springSecurityService

    public static final String SPRING_SECURITY_OAUTH_TOKEN = 'springSecurityOAuthToken'

    def linkYourElintegroAccountWithGoogle(){
        render(view:  "/auth/ask")
    }
    def linkAccount(OAuth2LinkAccountCommand command) {
        OAuth2SpringToken oAuth2SpringToken = session[SPRING_SECURITY_OAUTH_TOKEN] as OAuth2SpringToken
        if (!oAuth2SpringToken) {
            log.warn "linkAccount: OAuthToken not found in session"
            throw new OAuth2Exception('Authentication error')
        }
        if (request.post) {
            if (!springSecurityOauth2BaseService.authenticationIsValid(command.username, command.password)) {
                log.info "Authentication error for use ${command.username}"
                command.errors.rejectValue("username", "OAuthLinkAccountCommand.authentication.error")
                render view: 'ask', model: [linkAccountCommand: command]
                return
            }
            def commandValid = command.validate()
            def User = springSecurityOauth2BaseService.lookupUserClass()
            boolean linked = commandValid && User.withTransaction { status ->
                def user = User.findByUsername(command.username)
                if (user) {
                    user.addTooAuthIDs(provider: oAuth2SpringToken.providerName, accessToken: oAuth2SpringToken.socialId, user: user)
                    if (user.validate() && user.save()) {
                        oAuth2SpringToken = springSecurityOauth2BaseService.updateOAuthToken(oAuth2SpringToken, user)
                        return true
                    } else {
                        return false
                    }
                } else {
                    command.errors.rejectValue("username", "OAuthLinkAccountCommand.username.not.exists")
                }
                status.setRollbackOnly()
                return false
            }
            if (linked) {
                authenticateAndRedirect(oAuth2SpringToken, getDefaultTargetUrl())
                return
            }
        }
        render view: 'ask', model: [linkAccountCommand: command]
    }
    protected void authenticateAndRedirect(@Nullable OAuth2SpringToken oAuthToken, redirectUrl) {
        session.removeAttribute SPRING_SECURITY_OAUTH_TOKEN
        SecurityContextHolder.context.authentication = oAuthToken
        redirect(redirectUrl instanceof Map ? redirectUrl : [uri: redirectUrl])
    }
    protected Map getDefaultTargetUrl() {
        def config = SpringSecurityUtils.securityConfig
        def savedRequest = SpringSecurityUtils.getSavedRequest(session)
        def defaultUrlOnNull = '/'
        if (savedRequest && !config.successHandler.alwaysUseDefault) {
            return [url: (savedRequest.redirectUrl ?: defaultUrlOnNull)]
        }
        return [uri: (config.successHandler.defaultTargetUrl ?: defaultUrlOnNull)]
    }

}
class OAuth2LinkAccountCommand {
    String username
    String password

    static constraints = {
        username blank: false
        password blank: false
    }
}
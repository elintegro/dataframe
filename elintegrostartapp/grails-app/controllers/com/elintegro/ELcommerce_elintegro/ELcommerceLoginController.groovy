package com.elintegro.ELcommerce_elintegro

import com.elintegro.accessToken.AccessTokenCode
import com.elintegro.auth.User
import com.elintegro.auth.UserRole
import grails.converters.JSON
import grails.util.Environment
import grails.util.Holders

import java.nio.charset.Charset

class ELcommerceLoginController {
    def springSecurityService

    def elCommerceLoginFromElintegro(){
        def currentUser = springSecurityService.currentUser
        def userName = currentUser.username
        def firstName = currentUser.firstName
        def lastName = currentUser.lastName
        def email = currentUser.email
        UserRole userRole = UserRole.findByUser(User.findByUsername(userName))
        def serverUrl
        def quizzableUrl
        if(Environment.current == Environment.DEVELOPMENT) {
            serverUrl = "http://localhost:9264/"
            quizzableUrl = serverUrl+"Elintegro_Ecommerce/userDetailsFromElintegro"
        }
        else
        {
            serverUrl = Holders.grailsApplication.config.quizzableUrl
            quizzableUrl = serverUrl+"Elintegro_Ecommerce/userDetailsFromElintegro"
        }
        def accessToken
        AccessTokenCode accessTokenCode = AccessTokenCode.findByUsername(userName)
        if(!accessTokenCode) {
            AccessTokenCode newAccessTokenCode = new AccessTokenCode()
            newAccessTokenCode.username = currentUser.username
            newAccessTokenCode.token = UUID.randomUUID().toString().replaceAll('-', '')
            newAccessTokenCode.save(flush: true)
            accessToken = newAccessTokenCode.token
        }
        else{
            accessToken = accessTokenCode.token
        }
        String urlParameters  = "userName=${userName}&firstName=${firstName}&lastName=${lastName}&email=${email}&userRole=${userRole.role}&token=${accessToken}";
        byte[] postData       = urlParameters.getBytes(Charset.forName("UTF-8"))
        int    postDataLength = postData.length;
        String request        = quizzableUrl;
        URL    url            = new URL( request );
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setDoOutput( true );
        conn.setInstanceFollowRedirects( false );
        conn.setRequestMethod( "POST" );
        conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty( "charset", "utf-8");
        conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
        conn.setUseCaches( false );
        conn.connect()
        try{
            DataOutputStream wr = new DataOutputStream( conn.getOutputStream())
            wr.write( postData );
            String out = conn.inputStream.text
            //Integer out = conn.getResponseCode()//(You can do this instead of above line)
        }catch(e){
            log.error("Cann't send request to the quizzable."+e)
        }
        def resultData = [success: true, accessToken: accessToken,serverUrl:serverUrl]
        render(resultData as JSON)
    }


}

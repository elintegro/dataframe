/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.notification.impl.gateway.fb

import com.elintegro.erf.notification.impl.gateway.fb.helperClass.Button
import com.elintegro.erf.notification.impl.gateway.fb.helperClass.Message
import com.elintegro.erf.notification.impl.gateway.fb.helperClass.Messaging
import com.elintegro.erf.notification.impl.gateway.fb.helperClass.Recipient
import com.google.gson.Gson
import groovy.util.logging.Slf4j

import javax.servlet.http.HttpServletRequest

/**
 * Created by kchapagain on Aug, 2018.
 */
@Slf4j
class FbHelper {

    public static Button createButton(String url, String title){
        Button button = new Button()
        button.type = "web_url"
        button.url = url
        button.title = title
        return button
    }

    public static Button shareButton(){
        Button button = new Button()
        button.type = "element_share"
        return button
    }

    public static Message createMessage(String msg){
        Message message = new Message()
        message.setText(msg)
        return message
    }

    public static String createFbSendMessage(String senderId, Message message){
        Recipient recipient = createRecipient(senderId)
        Messaging reply = createReplyMessaging(recipient, message)
        String replyJson = new Gson().toJson(reply)
        log.info("replyJson-------------"+replyJson)
        return replyJson
    }

    public static Recipient createRecipient(String senderId){
        Recipient recipient = new Recipient()
        recipient.setId(senderId)
        return recipient
    }

    public static Messaging createReplyMessaging(Recipient recipient, Message message){
        Messaging reply = new Messaging()
        reply.setRecipient(recipient)
        reply.setMessage(message)
        return reply
    }

    public static read(HttpServletRequest request){
        StringBuffer stringBuffer = new StringBuffer()
        String line = null
        try {
            BufferedReader reader = request.getReader()
            while ((line = reader.readLine()) != null)
                stringBuffer.append(line)
            return stringBuffer.toString()
        }catch (Exception e){
            log.error(e)
        }
    }

    public static String createGetStartedButton(String payload, String fburl){
          return sendMessage(payload, fburl)
    }

    public static String sendMessage(String payload, fburl){

        StringBuffer jsonString
        URL url = new URL(fburl)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection()

        connection.setDoInput(true)
        connection.setDoOutput(true)
        connection.setRequestMethod("POST")
        connection.setRequestProperty("Accept", "application/json")
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8")
        writer.write(payload)
        writer.close()
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))
        jsonString = new StringBuffer()
        String line
        while ((line = br.readLine()) != null) {
            jsonString.append(line)
        }
        br.close()
        connection.disconnect()
        return jsonString.toString()

    }
}

/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.payment.utils

import javax.servlet.http.HttpServletRequest

class PaypalUtil {


    public static Map<String, String> getHeadersInfo(HttpServletRequest request) {

        Map<String, String> map = new HashMap<String, String>()

        @SuppressWarnings("rawtypes")
        Enumeration headerNames = request.getHeaderNames()
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement()
            String value = request.getHeader(key)
            map.put(key, value)
        }

        return map
    }

    public static String getBody(HttpServletRequest request) throws IOException {

        String body
        StringBuilder stringBuilder = new StringBuilder()
        BufferedReader bufferedReader = null

        try {
            InputStream inputStream = request.getInputStream()
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream))
                char[] charBuffer = new char[128]
                int bytesRead = -1
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead)
                }
            } else {
                stringBuilder.append("")
            }
        } catch (IOException ex) {
            throw ex
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close()
                } catch (IOException ex) {
                    throw ex
                }
            }
        }

        body = stringBuilder.toString()
        return body
    }
    
}

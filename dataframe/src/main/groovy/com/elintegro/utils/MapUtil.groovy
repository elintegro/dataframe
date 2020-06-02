/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.utils

import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j

@Slf4j
class MapUtil {

    public static def findDeep(Map m, String key) {
        if (m.containsKey(key)) return m[key]
        m.findResult { k, v -> v instanceof Map ? findDeep(v, key) : null }
    }

    public static def putDeepValue(Map m, String key, def value, String keyToInsert){
        if (m.containsKey(key)) {
            Map values = m.get(key)
            values.put(keyToInsert, value)
            return
        }
        m.findResult { k, v -> v instanceof Map ? putDeepValue(v, key, value, keyToInsert) : null }
    }

    public static def putDeepValue(Map m, String key, def value){
        if (m.containsKey(key)) {
            m.put(key, value)
            return
        }
        m.findResult { k, v -> v instanceof Map ? putDeepValue(v, key, value) : null }
    }
    public static String convertMapToJSONString(Map map){

        /*
        ObjectMapper mapper = new ObjectMapper();
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, map);
        final byte[] data = out.toByteArray();
        String dataJsonString_ = new String(data)
        */
        try {
            JsonBuilder bld = new JsonBuilder(map)
            String dataJsonString = bld.toPrettyString()
            return dataJsonString
        }catch(Exception e){
            log.error("Failing to convert map to ${map.toString()}JSON error = ${e}")
        }
    }

    public static String convertMapToJSONString(Object obj){

        /*
        ObjectMapper mapper = new ObjectMapper();
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, map);
        final byte[] data = out.toByteArray();
        String dataJsonString_ = new String(data)
        */
        try {
            JsonBuilder bld = new JsonBuilder(obj)
            String dataJsonString = bld.toPrettyString()
            return dataJsonString
        }catch(Exception e){
            log.error("Failing to convert map to ${obj.toString()}JSON error = ${e}")
        }
    }


}

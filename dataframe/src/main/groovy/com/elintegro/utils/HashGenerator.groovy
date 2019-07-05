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

import org.apache.commons.lang.StringUtils
import org.hashids.Hashids

class HashGenerator {

    private static final SALT = "ERF-Elintegro-SALT";
    private static Hashids hashids = new Hashids(SALT,15)

    public static String encodeId(long input) {
        return  hashids.encode(input)
    }

    public static String encodeId(String input) {
        return  hashids.encodeHex(input)
    }


    public static def decodeHashCode(String hash){
        return hashids.decode(hash)
    }

    public static def decodeHex(String hash){
        return hashids.decodeHex(hash)
    }


    //TODO write something better!
    public static def generateUniqueCode(){
        Long time = System.currentTimeMillis()
        long code = (long) Math.floor(Math.random() * 1000000)+time
        return hashids.encode(code);
    }

    public static String mask(final String maskValue, start) {
        String s = maskValue.replaceAll("\\D", "");
        int end = s.length() - start;
        String overlay = StringUtils.repeat("X", end - start);
        return StringUtils.overlay(s, overlay, start, end);

    }

    public static String maskNumber(String number, String mask) {

        int index = 0
        StringBuilder masked = new StringBuilder()
        for (int i = 0; i < mask.length(); i++) {
            char c = mask.charAt(i)
            if (c == '#') {
                masked.append(number.charAt(index))
                index++
            } else if (c == 'X') {
                masked.append(c)
                index++
            } else {
                masked.append(c)
            }
        }
        return masked.toString()
    }

}

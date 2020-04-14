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

import groovy.util.logging.Slf4j

@Slf4j
class DataframeFileUtil {

    public static void readAndReplaceContentsInFile(filePath, stringToReplace, stringToReplaceWith, commentOldValue){
        File file= new File(filePath);
        FileReader fr = new FileReader(file);
        String s;
        try {
            BufferedReader br = new BufferedReader(fr);
            String totalString = ""
            while ((s = br.readLine()) != null) {
                totalString += s
                totalString +="\n"
            }
            def contains = totalString.contains(stringToReplace)
            if(contains){
                String commentedOldHql = ""
                if(commentOldValue){
                    commentedOldHql = "/*hql = \"\"\""+stringToReplace+"\"\"\"*/"
                    stringToReplace = /(?i)hql\s*=\s*(("""|'''|"|')${stringToReplace}("""|'''|"|'))/
                    stringToReplaceWith = commentedOldHql+"\n\n\t\thql = \"\"\""+stringToReplaceWith+"\"\"\""
                }
                totalString = totalString.replaceAll(stringToReplace, stringToReplaceWith);
            }
            FileWriter fw = new FileWriter(file);
            fw.write(totalString);
            fw.close();
        }catch(Exception e){
            log.debug(e.getMessage())
        }
    }

    public static void writeStringIntoFile(String fileName, String fileContent) {
        BufferedWriter writer
        File file = new File(fileName)
        if (file) {
            String pathOnly = fileName.substring(0, fileName.indexOf(file.getName())-1)
            File theDir = new File(pathOnly);
            if(!theDir.exists()) {
                boolean result = false;
                try {
                    theDir.mkdir();
                    result = true;
                }
                catch (SecurityException se) {
                    log.error("File ${fileName} has not been created!")
                    return;//handle it
                }
                if (result) {
                    System.out.println("DIR created");
                }
            }
        }

        try {
            writer = new BufferedWriter(new FileWriter(fileName))
            writer.write(fileContent)
        }catch(Exception e){
            log.info("There is a problem to create dataframe vue file. Exception : "+e)

        }finally {
            writer.close()
        }




    }
}

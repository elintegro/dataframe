/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.dataframe.service

import com.elintegro.annotation.OverridableByEditor
import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.layout.abs.Layout
import grails.gorm.transactions.Transactional
import grails.util.Holders
import org.codehaus.groovy.runtime.InvokerHelper
import org.codehaus.groovy.runtime.NullObject
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation

@Transactional
class DfEditorService {

   public static final dataFrameLookup = ["refDataframe","dataframe","onClick", "detailDataFrame"]

   //Todo: may be needed for editor task to reload bean automatically
   def writeChangedBeanProperties(Dataframe df, String pathname, Map changedPropertyMap){
      String dataframeName = df.dataframeName
      def f = new File(pathname)
      def lines = []
      def declaredFiled = df.class.getDeclaredFields()
      String startBeanRegex = "\\s*def\\s*$dataframeName\\s*=\\s*\\{\\s*bean\\s*->\\s*"
      String endBeanRegex = "\\s*//\\s*end\\s* of\\s* \\s*$dataframeName\\s*"
      if ( f.exists() ) {
         boolean isfileFound = false
         f.eachLine { line, nb ->
            if ( line.find(startBeanRegex) ) {
               isfileFound = true
               lines << line
            }
            if (line.find(endBeanRegex) && isfileFound){
               isfileFound = false
               declaredFiled.each {field ->
                  def annotationClass = field.getDeclaredAnnotation(OverridableByEditor)
                  if (annotationClass){
                     def type = field.type
                     def proKey = field.name
                     if (changedPropertyMap.containsKey(proKey)){
                        def propVal = changedPropertyMap?.get(proKey)
                        if (type.equals(String.class)){
                           lines << "\t\tbean.$proKey = '''$propVal'''"
                        }else if (type.equals(Map.class)){
                           //todo: need to find apropriate solution for converting map to write in file
                           String toMapString = formatMap((Map)propVal, true, -1)
                           println "formatedtoMapString---"+toMapString
                           lines << "\t\tbean.$proKey = ${toMapString}"
                        }else if (type.equals(Boolean.class)){
                           lines << "\t\tbean.$proKey = ${Boolean.parseBoolean(propVal)}"
                        }
                        else{
                           lines << "\t\tbean.$proKey = ${propVal}"
                        }
                     }
                  }
               }
            }
            if (!isfileFound){
               lines << line
            }
         }
      }

      f.withWriter('UTF-8') { writer ->
         lines.each { String line ->
            writer.write "${line}${System.lineSeparator()}"
         }
      }
   }

   def updateBean(Dataframe df, String pathname, Map changedPropertyMap){
      String dataframeName = df.dataframeName
      def f = new File(pathname)
      def lines = []
      def declaredFiled = df.class.getDeclaredFields()
      String startBeanRegex = "\\s*$dataframeName\\s*\\(\\s*(com\\.elintegro\\.erf\\.dataframe\\.Dataframe)\\s*\\)\\s*\\{\\s*bean\\s*->"
      String endBeanRegex = "\\s*//\\s*end\\s* of\\s* \\s*$dataframeName\\s*"
      String endBeansRegex = "\\s*//\\s*end\\s* of\\s* \\s*beans\\s*"
      if ( f.exists() ) {
         boolean isfileFound = false
         boolean createNewBean = true
         f.eachLine { line, nb ->
            if ( line.find(startBeanRegex) ) {
               isfileFound = true
               createNewBean = false
               lines << line
            }
            if (line.find(endBeanRegex) && isfileFound){
               isfileFound = false
               buildBeanToWriteInFile(lines, declaredFiled, changedPropertyMap)
            }
            if (line.find(endBeansRegex) && !isfileFound && createNewBean){
               lines << "\t$dataframeName(com.elintegro.erf.dataframe.Dataframe){ bean ->"
               buildBeanToWriteInFile(lines, declaredFiled, changedPropertyMap)
               lines << "\t} //end of $dataframeName"
            }
            if (!isfileFound){
               lines << line
            }
         }
      }
      f.withWriter('UTF-8') { writer ->
         lines.each { String line ->
            writer.write "${line}${System.lineSeparator()}"
         }
      }
   }

   private static void buildBeanToWriteInFile(lines, declaredFiled, Map changedPropertyMap){
      declaredFiled.each {field ->
         def annotationClass = field.getDeclaredAnnotation(OverridableByEditor)
         if (annotationClass){
            def type = field.type
            def proKey = field.name
            def propVal = changedPropertyMap?.get(proKey)
            if (changedPropertyMap.containsKey(proKey)){
               if (type.equals(String.class)){
                  lines << "\t\t$proKey = '''$propVal'''"
               }else if (type.equals(Map.class)){
                  //todo: need to find apropriate solution for converting map to write in file
                  String toMapString = formatMap((Map)propVal, true, -1)
                  println "formatedtoMapString---"+toMapString
                  lines << "\t\t$proKey = ${toMapString}"
               }
               //Todo: this bellow code may not needed
               else if (propVal instanceof Layout){
                  String layoutName = propVal.layoutBeanName
                  if (layoutName){
                     lines << "\t\t$proKey = ${buildBeanRef(doVerbose(layoutName))}"
                  }
               }
               else{
                  lines << "\t\t$proKey = ${propVal}"
               }
            }
         }
      }
   }


   private static String formatMap(Map map, boolean verbose, int maxSize) {
      if (map.isEmpty()) {
         return "[:]"
      }
      StringBuilder buffer = new StringBuilder("[")
      boolean first = true
      for (Object o : map.entrySet()) {
         if (first) {
            first = false
         } else {
            buffer.append(", ")
         }
         if (maxSize != -1 && buffer.length() > maxSize) {
            buffer.append("...")
            break
         }
         Map.Entry entry = (Map.Entry) o
         buffer.append(format(entry.getKey(), verbose, -1))
         buffer.append(":")
         if (entry.getValue() == map) {
            buffer.append("(this Map)")
         } else {
            if (dataFrameLookup.contains(entry.getKey())){
               def value = doVerbose((entry.getValue())?.beanName)
               if (value){
                  String refDf = buildBeanRef(value)
                  buffer.append(refDf)
               }
            }else {
               buffer.append(format(entry.getValue(), verbose, sizeLeft(maxSize, buffer)))
            }
         }
      }
      buffer.append("]")
      return buffer.toString()
   }


   public static String format(Object arguments, boolean verbose, int maxSize) {
      if (arguments == null) {
         final NullObject nullObject = NullObject.getNullObject()
         return (String) nullObject.getMetaClass().invokeMethod(nullObject, "toString", InvokerHelper.EMPTY_ARGS)
      }
      if (arguments.getClass().isArray()) {
         if (arguments instanceof char[]) {
            return new String((char[]) arguments)
         }
         return format(DefaultTypeTransformation.asCollection(arguments), verbose, maxSize)
      }
      if (arguments instanceof Map) {
         return formatMap((Map) arguments, verbose, maxSize)
      }
      if (arguments instanceof String) {
         if (verbose) {
            return doVerbose(arguments)
         } else {
            return (String) arguments
         }
      }
      return arguments.toString()
   }

   private static int sizeLeft(int maxSize, StringBuilder buffer) {
      return maxSize == -1 ? maxSize : Math.max(0, maxSize - buffer.length())
   }

   private static doVerbose(arguments){
      String arg = InvokerHelper.escapeBackslashes((String) arguments)
              .replaceAll("'", "\\\\'")
      return "\'" + arg + "\'"
   }

   private static String buildBeanRef(String value){
      return "ref("+value+")"
   }

   public void updateExistingBeans(params){
      log.info("Trying to Update the existing bean for params")
      println(params)
      String dataframeName = params.dataframe
      if(dataframeName){
         String baseResourcesPath = "grails-app/conf/spring/"
         String defaultResourceFileName = baseResourcesPath+"resources.groovy"
         File file = new File(defaultResourceFileName)
         String regexForLoadResourceString = "loadResource"
         List<String> resourcesFileNames = new ArrayList<>()
         if(file.exists()) {
            file.eachLine { line ->
               if(line.find(regexForLoadResourceString) && !line.find("(?m)(\\?|:)|(^//)")){
                  String clearedString = line.trim()
                  String resourceFlName = clearedString.split("\\s+")[1]
                  resourcesFileNames.add(resourceFlName.replaceAll("(\\s+)|^(\"|\')|(\"|\')\$", ""))
               }

            }
         }

         boolean isBeanPresent = false
         String fileNameWithBean = ""
         resourcesFileNames.each {it->
            String fullClasspathName = baseResourcesPath + it.toString()

            File rfile = new File(fullClasspathName)
            rfile.eachLine {line ->

               String beanRegex = "\\s*$dataframeName\\s*\\(\\s*(com\\.elintegro\\.erf\\.dataframe\\.Dataframe)\\s*\\)\\s*\\{\\s*bean\\s*->"
               if(line.find(beanRegex)){
                  isBeanPresent = true
                  fileNameWithBean = fullClasspathName
               }
            }
         }
         if(isBeanPresent){
            String oldHql = Holders.applicationContext.getBean(dataframeName).hql
            String newHql= params.hql?:""
            log.info(fileNameWithBean+" contains the inputted bean "+dataframeName)
            if(oldHql){
               boolean commentOldValue = true
               com.elintegro.utils.dataframeFildataframe.readAndReplaceContentsInFile(fileNameWithBean,oldHql,newHql, commentOldValue)

            }else{
               log.debug("No field name hql found inside dataframe"+dataframeName)
            }
         }

      }
   }

}

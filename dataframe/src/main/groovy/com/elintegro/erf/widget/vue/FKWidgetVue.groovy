/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development.
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works.
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.DataframeInstance
import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.layout.abs.LayoutVue

/**
 * This Widget is created to provide saving capabilities to the Foreign Keys fields
 * It is not for display
 */
class FKWidgetVue extends WidgetVue{
    private String vuecomputedMethodScriptString = ""
    String embDDfr = ""

    @Override
    String getHtml(DataframeVue dataframe, Map fields) {
        return ""
    }

    String getVueDataVariable(DataframeVue dataframe, Map field) {
        String dataVariable = dataframe.getDataVariableForVue(field)
        Dataframe refDataframe = getReferenceDataframe(field.parent)
        String refDataframeName = refDataframe.dataframeName
        //todo now the update of store value in save method is not automated. make it automated.
        return """$dataVariable : excon.getFromStore('$refDataframeName','key'),\n"""

    }

    String getVueSaveVariables(DataframeVue dataframe, Map field){
        Dataframe refDataframe = getReferenceDataframe(field.parent)
        String thisFieldName = dataframe.getFieldId(field)
        StringBuilder buildParentAndRefParams = new StringBuilder()
        for(Map.Entry entry: refDataframe.getNamedParameters()){
            String keyField = entry.key;
            String keyFieldName = Dataframe.buildFullFieldNameKeyParam(refDataframe, entry.key);
            String parentFieldName = Dataframe.buildFullFieldNameParentParam(refDataframe, keyField);
            String dataVariable = dataframe.getDataVariableForVue(field)
            buildParentAndRefParams.append("allParams[\"$parentFieldName\"] = \"$thisFieldName\";\n")
            buildParentAndRefParams.append("allParams[\"ref_$dataVariable\"] = \"$keyFieldName\";\n")
            buildParentAndRefParams.append("allParams['$dataVariable'] = this.state.$dataVariable;\n")
        }
        if(refDataframe.saveButton){
            return ""
        }else {
            return buildParentAndRefParams.toString()
        }
    }

    String getValueSetter(DataframeVue dataframe, Map field, String divId, String dataVariable, String key) throws DataframeException{
        Dataframe refDataframe = getReferenceDataframe(field.parent)
        String fldParam = dataframe.getFieldId(field)
        String refDataframeName = refDataframe.dataframeName
        String namedParamKey = "this.\$store.state.${refDataframeName}.key"
        return ""
    }

    String getComputedMethods(DataframeVue dataframe, Map field, String divId, String fldId, String key){
        Dataframe refDataframe = getReferenceDataframe(field.parent)
        return vuecomputedMethodScriptString
    }

    public Map loadAdditionalData(DataframeInstance parentDfInstance, String fieldnameToReload, Map inputData, def session){

        DataframeVue parentDf = parentDfInstance.df
        Map fieldProps = parentDf.fields.get(fieldnameToReload)
        DataframeVue referedDf = getReferenceDataframe(fieldProps.get("dataframe"))

        def paramsForRefDf = [:]

        //validations
        assert(fieldnameToReload)
        def inputFieldValue = inputData.get(fieldnameToReload)

//		assert(inputFieldValue)

        def vMember = fieldProps.valueMember?inputData.get("name"): fieldProps.valueMember
        assert(vMember)


        //This is the value of the reference field to retrieve data from the other \Dataframe
        def value = inputFieldValue?inputFieldValue[vMember]:null

        //All required and optional parameters to form DataframeInstance and call readAndGetJson:
        Set namedParams = parentDf.keysOfNamedParameters
        namedParams.each {namedParamKey ->
            if (inputData.containsKey(namedParamKey)){
                paramsForRefDf.put(namedParamKey, inputData.get(namedParamKey))
            }
        }
        paramsForRefDf.put(vMember, value)
        paramsForRefDf.put('parentDataframe', parentDf.dataframeName)
        paramsForRefDf.put('parentNode', "")
        paramsForRefDf.put('level', "")
        paramsForRefDf.put('parentFieldName', vMember)

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //Actual data, retrieved from Database:
        //
        def dfInstance = new DataframeInstance(referedDf, paramsForRefDf)
        Map jsonMap = [:]
        if (!inputFieldValue){
            dfInstance.isDefault = true
            jsonMap = dfInstance.retrieveAndGetJson()
            return jsonMap
        }
        jsonMap = dfInstance.readAndGetJson()
        return jsonMap
    }

    String getEmbdDfrName(){
        return embDDfr
    }
}

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
import com.elintegro.erf.dataframe.vue.VueJsBuilder
import com.elintegro.erf.dataframe.vue.VueStore
import com.elintegro.erf.layout.abs.LayoutPlaceholder
import com.elintegro.erf.layout.abs.LayoutVue

class DataframeWidgetVue extends WidgetVue{
    private String vuecomputedMethodScriptString = ""
    String embDDfr = ""

    @Override
    String getHtml(DataframeVue dataframe, Map fields) {
        String fldId = dataframe.getFieldId(fields)
        StringBuilder resultPageHtml = new StringBuilder()
        Dataframe refDataframe = getReferenceDataframe(fields.dataframe)
        String refDataframeName = refDataframe.dataframeName
        dataframe.embeddedDataframes.add(refDataframeName)
        dataframe.childrenDataframes.add(refDataframeName)
        LayoutVue reflayoutObj = refDataframe.currentFrameLayout
        resultPageHtml.append(reflayoutObj.layoutPlaceHolder?:"")
        String attr = fields.attr?:""
        Map propPass = fields.propPass
        Map propReturn = fields.propReturn
        StringBuilder propString = new StringBuilder()
        if(propPass && propPass.containsKey("key")){
            String key = propPass.key
            String value = propPass.value
            if(key.trim().indexOf(":") == 0)
                dataframe.getVueJsBuilder().addToDataScript("$value:'',\n")
            propString.append("""$key = '$value' """)
        }
        propString.append(attr)

        String dataframeHtml = refDataframe.getComponentName(propString.toString())
        dataframe.getVueJsBuilder().addToDataScript("${refDataframeName}_data:{key: ''},\n")
        String fieldnameStr = fldId.replace(DataframeVue.DASH, DataframeVue.DOT);
        dataframe.addEmbeddedDataframe(fieldnameStr , refDataframe)
        embDDfr = refDataframeName
        return dataframeHtml
    }

    String getVueDataVariable(DataframeVue dataframe, Map field) {
        String dataVariable = dataframe.getDataVariableForVue(field)
        Dataframe refDataframe = getReferenceDataframe(field.dataframe)
        String refDataframeName = refDataframe.dataframeName
        String valueSetter = ""
        boolean isFKAndHide = field.hideFK

        //todo now the update of store value in save method is not automated. make it automated.
        if(isFKAndHide)
            valueSetter = """$dataVariable : drfExtCont.getFromStore('$refDataframeName','key'),\n"""
        return valueSetter

    }

    String getVueSaveVariables(DataframeVue dataframe, Map field){
        Dataframe refDataframe = getReferenceDataframe(field.dataframe)
        String thisFieldName = dataframe.getFieldId(field)
        StringBuilder buildParentAndRefParams = new StringBuilder()
        for(Map.Entry entry: refDataframe.getNamedParameters()){
            String keyField = entry.key;
            String keyFieldName = Dataframe.buildFullFieldNameKeyParam(refDataframe, entry.key);
            String parentFieldName = Dataframe.buildFullFieldNameParentParam(refDataframe, keyField);
            buildParentAndRefParams.append("allParams[\"$parentFieldName\"] = \"$thisFieldName\";\n")
            buildParentAndRefParams.append("allParams[\"ref-$thisFieldName\"] = \"$keyFieldName\";\n")
            if(field.hideFK) {
                String dataVariable = dataframe.getDataVariableForVue(field)
                buildParentAndRefParams.append("allParams['$thisFieldName'] = this.$dataVariable;\n")
            }
        }
        if(refDataframe.saveButton){
            return ""
        }else {
            return buildParentAndRefParams.toString()
        }
    }

    String getValueSetter(DataframeVue dataframe, Map field, String divId, String dataVariable, String key) throws DataframeException{
        Dataframe refDataframe = getReferenceDataframe(field.dataframe)
        String fldParam = dataframe.getFieldId(field)
        String refDataframeName = refDataframe.dataframeName
//        String namedParamKey = "this.\$store.state.${dataframe.dataframeName}.${refDataframeName}.key"
        String namedParamKey = "this.\$store.state.${refDataframeName}.key"

        return ""
    }

    String getComputedMethods(DataframeVue dataframe, Map field, String divId, String fldId, String key){
        Dataframe refDataframe = getReferenceDataframe(field.dataframe)
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

        def vMember = fieldProps.valueMember
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

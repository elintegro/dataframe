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
import com.elintegro.erf.dataframe.DbResult
import com.elintegro.erf.dataframe.ParsedHql
import com.elintegro.erf.dataframe.ResultPageHtmlBuilder
import com.elintegro.erf.dataframe.db.fields.MetaField
import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.vue.VueJsBuilder
import com.elintegro.erf.dataframe.vue.VueStore
import com.elintegro.utils.MapUtil
import org.springframework.context.i18n.LocaleContextHolder

class BoxListWidgetVue extends WidgetVue {
//    String vueComponentInitScripts = "";
    String embDDfr = "";

    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = dataframe.getDataVariableForVue(field);
        def search = field?.search
        String wdgHql = field?.hql;
        def onClick = field?.onClick
        def onButtonClick = field?.onButtonClick
        List gridDataframeList= []
        StringBuilder methodScriptsBuilder = new StringBuilder();
        StringBuilder requestFieldParams = new StringBuilder()
        StringBuilder fieldParams = new StringBuilder();
        StringBuilder onclickDfrBuilder = new StringBuilder();
        ParsedHql parsedHql = new ParsedHql(wdgHql, dataframe.grailsApplication, dataframe.sessionFactory);
        List<MetaField> fieldMetaData = dataframe.metaFieldService.getMetaDataFromFields(parsedHql, field.name);
        field.put("gridMetaData", fieldMetaData);
        field.put("parsedHql", parsedHql);
        List dataHeader = []
        fieldMetaData.each {metaField ->
            def propItemVal = metaField.alias?:metaField.name
            dataHeader.add(['text':propItemVal.capitalize(), 'value':propItemVal])
            fieldParams.append("\n<td class='text-xs-right'>{{ props.item.$propItemVal }}</td>");
            requestFieldParams.append("\nallParams['").append(metaField["alias"]).append("'] = dataRecord.").append(metaField["alias"]).append(";\n");
        }
        field.put("dataHeader", dataHeader);
        def parentDataframeName = dataframe.dataframeName
        String refDataframeName = ""
        if (onClick){
            DataframeVue refDataframe = DataframeVue.getDataframeBeanFromReference(onClick.refDataframe)
            refDataframeName = refDataframe.dataframeName
//            dataframe.childrenDataframes.add(refDataframeName)
//            embDDfr = refDataframeName
//            gridDataframeList.add(refDataframeName)
            getOnClickScript(onClick, dataframe, methodScriptsBuilder, onclickDfrBuilder, gridDataframeList, fldName)
        }

        if (onButtonClick){
            getOnButtonClickScript(onButtonClick, dataframe, methodScriptsBuilder, onclickDfrBuilder, gridDataframeList, fieldParams, fldName, parentDataframeName, requestFieldParams, dataHeader)

        }
        def fldNm = field.name;
        field.labelCode = field.labelCode?field.labelCode:fldNm
        def fldNameDefault = org.apache.commons.lang.WordUtils.capitalizeFully(fldNm);
//        String title = messageSource.getMessage(field.labelCode, null, fldNameDefault, LocaleContextHolder.getLocale())
        String title = field.name
        field.put("gridDataframeList", gridDataframeList);
        field.put("gridMethodScripts", methodScriptsBuilder);
        return """
       ${search?"""<v-card-title><v-subheader>$title</v-subheader>
            <v-spacer></v-spacer>
            <v-text-field
                    v-model="${fldName}_search"
                    append-icon="search"
                    label="Search"
                    single-line
                    hide-details
            ></v-text-field>
        </v-card-title>""":""}
       <v-data-table
            :headers="${fldName}_headers"
            :items="${fldName}_items"
            ${search?":search='${fldName}_search'":""}
    >
        <template slot="items" slot-scope="props">
          <tr @click="${fldName}_showDetail$refDataframeName(props.item)">
            ${fieldParams.toString()}
          </tr>  
        </template>
    </v-data-table>
        ${onclickDfrBuilder.toString()}
"""
    }

    String getVueDataVariable(DataframeVue dataframe, Map field) {
        String dataVariable = dataframe.getDataVariableForVue(field)
        def search = field?.search
        List gridDataframeNames = field?.gridDataframeList
        String gridDataframeNamesBuilder = ""
        if (gridDataframeNames){
            gridDataframeNamesBuilder = "{\n"
            gridDataframeNames.each {dfName ->
                gridDataframeNamesBuilder = gridDataframeNamesBuilder + "${dfName}_display:false,\n"
            }
            gridDataframeNamesBuilder = gridDataframeNamesBuilder + "}\n"
        }
        return """
           ${search?"${dataVariable}_search:'',":""}
           ${dataVariable}_headers: [],
           ${dataVariable}_items: [],
           ${gridDataframeNamesBuilder?"gridDataframeNames:${gridDataframeNamesBuilder},":""}
           ${dataVariable}_selectedrow:null,
"""

    }

    String getValueSetter(DataframeVue dataframe, Map field, String divId, String dataVariable, String key) throws DataframeException{
        def onClick = field.onClick
        String namedParamKey = ""
        if (onClick){
            if(onClick.refDataframe){
                DataframeVue refDataframe = DataframeVue.getDataframeBeanFromReference(onClick.refDataframe)
                String refDataframeName = refDataframe.dataframeName
//                namedParamKey = "this.\$refs.${refDataframeName.toLowerCase()}_ref.\$data.namedParamKey = \"this.\$store.state.${refDataframeName}.key\";\n"
                namedParamKey = ""
            }
        }
        String fullFieldName = key.replace(Dataframe.DOT,Dataframe.DASH)
        return """
               var fullFieldName = '$fullFieldName'; 
               var dataDessert = response.additionalData[fullFieldName]['data'];
               var dataHeader = response.additionalData[fullFieldName]['dataHeader'];
               this.${dataVariable}_headers = dataHeader;
               this.${dataVariable}_items = dataDessert;
               $namedParamKey 
              """
    }

    String getMethodsScript(DataframeVue dataframe, Map field, String divId, String fldId, String key){
        def gridMethodScripts = field?.gridMethodScripts
        return gridMethodScripts.toString();
    }

    String getEmbdDfrName(){
        return embDDfr
    }

    public Map loadAdditionalData(DataframeInstance dataframeInst, String fieldnameToReload, Map inputData, def dbSession){
        Map result=[:];
        Dataframe dataframe = dataframeInst.df;
        Map fieldProps = dataframe.fields.get(fieldnameToReload);

        //Add fields from the Dataframe as possible input parameters for the additional HQL:
        inputData.putAll(dataframeInst.getFieldValuesAsKeyValueMap());

        String wdgHql = fieldProps?.hql;
        if(wdgHql){
            Map sessionParams = dataframeInst.sessionParams
            if (sessionParams){
                inputData << sessionParams
            }
            List<MetaField> fieldMetaData =  fieldProps.get("gridMetaData");
            ParsedHql parsedHql =  fieldProps.get("parsedHql");
            List dataHeader =  fieldProps.get("dataHeader");
            getNamedParameterValue(dataframeInst,inputData, parsedHql, fieldProps)
            DbResult dbRes = new DbResult(wdgHql, inputData, dbSession, parsedHql);
            List resultList = dbRes.getResultList();
            result.put("data", resultList);
            result.put("dataHeader", dataHeader);
            result.put("defaultData", getDefaultData(fieldMetaData));

            if(!(fieldProps.containsKey("metaData") && fieldProps.get("metaData"))){
                fieldProps.put("metaData", getDataFields(wdgHql, dataframe, fieldMetaData));
            }

            result.put("metaData", fieldProps.get("metaData"));

        }
        return result
    }

    public void getNamedParameterValue(dfInstance,inputData, parsedHql, fieldProps){
        Dataframe dataframe = dfInstance.df
//        Map sessionParams = dfInstance.sessionParams
        if(parsedHql.namedParameters){
            parsedHql.namedParameters.each{
                String key = it.getKey()
                if(fieldProps.containsKey(key)){
                    String hql = fieldProps.get(key)
//                inputData << sessionParams
                    ParsedHql parsedHql1 = new ParsedHql(hql, dataframe.grailsApplication, dataframe.sessionFactory)
                    DbResult dbRes = new DbResult(hql, inputData, dataframe.sessionFactory.openSession(), parsedHql1)
                    List resultList = dbRes.getResultList()
                    resultList.each {Map result->
                        def value = result? MapUtil.findDeep(result, key):""
                        inputData.put(key,value)
                    }
                }
            }
        }
    }

    private static String getGridValuesScript(String parentDataframeName, String fldName
                                              ,StringBuilder fieldParams, DataframeVue refDataframe){
        String refDataframeName = refDataframe.dataframeName
        return """ 
                         ${parentDataframeName}Var.${fldName}_selectedrow = dataRecord;
                   var allParams = {'dataframe':'$refDataframeName'};
                   $fieldParams
                   axios.get('$refDataframe.ajaxUrl', {
                    params: allParams
                }).then(function (responseData) {
                        var response = responseData.data.data;
                        console.log(response);
                        ${parentDataframeName}Var.gridDataframeNames.${refDataframeName}_display = true;
                        var refParams = ${parentDataframeName}Var.\$refs.${refDataframeName.toLowerCase()}_ref.params;
                        var gridRefreshParams = {};
                        gridRefreshParams['isGridRefresh'] = true;
                        gridRefreshParams['fieldName'] = '$fldName';
                        gridRefreshParams['parentDataframe'] = '$parentDataframeName';
                        gridRefreshParams['dataframe'] = '$refDataframeName';
                        refParams['gridRefreshParams'] = gridRefreshParams;
                        
                        ${refDataframe.doAfterRefresh}                        
                    })
                    .catch(function (error) {
                        console.log(error);
                    });

"""
        //                        ${parentDataframeName}Var.\$refs.${refDataframeName.toLowerCase()}_ref.${refDataframeName}_populateJSONData(response);

    }

    private static void getOnButtonClickScript(onButtonClick, DataframeVue dataframe, StringBuilder methodScriptsBuilder, StringBuilder onclickDfrBuilder
                                               , gridDataframeList, StringBuilder fieldParams, String fldName, String parentDataframeName, StringBuilder requestFieldParams, List dataHeader){
        String buttonHoverMessage = ""
        DataframeVue buttonRefDataframe
        onButtonClick.each{Map onButtonClickMaps ->
            String actionName = onButtonClickMaps?.actionName
            dataHeader.add(['text':actionName.capitalize(), 'value':'name', sortable: false])
            fieldParams.append("\n<td class='justify-center layout px-0' @click.stop=''>");
            onButtonClickMaps.buttons.each {Map buttonMaps->
                String text = buttonMaps?.buttonName
                def vIcon = buttonMaps?.vIcon
                String appendCallbackScript
                if(buttonMaps?.callBackParams){
                    appendCallbackScript = buttonMaps?.callBackParams?.appendScript?:""
                }
                buttonHoverMessage = dataframe.messageSource.getMessage(buttonMaps.tooltip.hoverMessage, null, org.apache.commons.lang.WordUtils.capitalizeFully(text), LocaleContextHolder.getLocale())
                buttonRefDataframe = DataframeVue.getDataframeBeanFromReference(buttonMaps?.refDataframe)
                String dataframeHtml = buttonRefDataframe.getComponentName("")
                String buttonRefDataframeName = buttonRefDataframe.dataframeName
//                gridDataframeList.add(buttonRefDataframeName)
//                dataframe.childrenDataframes.add(buttonRefDataframeName)
                def deleteButton = buttonMaps.deleteButton
                if (buttonMaps?.image){
                    String actionImageUrl = buttonMaps?.image?.url?:"https://image.flaticon.com/icons/png/128/66/66720.png";
                    String height = buttonMaps?.image?.height?:"20"
                    String width = buttonMaps?.image?.width?:"25"
                    fieldParams.append("""
                                        <img ${toolTip(buttonMaps)} height="$height" width="$width" style='margin-top: 14px; cursor: pointer;' src="$actionImageUrl" @click="${fldName}_${deleteButton?"delete":"showDetail${buttonRefDataframeName}"}(props.item);"/>
                    """)
                }else if (vIcon){
                    fieldParams.append("""<v-icon small ${toolTip(buttonMaps)} class="mr-2" @click="${fldName}_${deleteButton?"delete":"showDetail${buttonRefDataframeName}"}(props.item);">${vIcon?.iconName}</v-icon>""")
                }
                if (deleteButton){
                    constructGridDeleteScript(methodScriptsBuilder, buttonRefDataframe, requestFieldParams, deleteButton, fldName, parentDataframeName)
                }else {
                    String stateName = fldName + "_button"
                    onclickDfrBuilder.append(getRefDataframeHtml(buttonMaps, dataframe, fldName, gridDataframeList, stateName))
                }
            }
            fieldParams.append("""</td>""")
        }
    }

    private static void getOnClickScript(def onClick, DataframeVue dataframe, StringBuilder methodScriptsBuilder, StringBuilder onclickDfrBuilder, def gridDataframeList
                                         , String fldName){

        String stateName = fldName + "_onClick"
        onclickDfrBuilder.append(getRefDataframeHtml(onClick, dataframe, fldName, gridDataframeList, stateName))

        methodScriptsBuilder.append("""
                    ${fldName}_refreshDataForGrid: function(responseData, refreshParams){
                          var selectedRow = this.${fldName}_selectedrow;
                          var editedIndex = this.${fldName}_items.indexOf(selectedRow);
                          var data = responseData.data;
                          var operation = data.operation;
                          var newData = data.newData;
                          var row = {};
                          jQuery.each(newData, function(key, value) {
                              var dataMap = value;
                              jQuery.each(dataMap, function (key, value) {
                                  if (key in selectedRow) {
                                      row[key] = value;
                                  }
                              });
                          });
                          if (operation==="I") {
                              this.${fldName}_items.push(row)
                          } else {
                              Object.assign(this.${fldName}_items[editedIndex], row)
                          }
                          this.gridDataframeNames[refreshParams.dataframe] = false; 
                },\n

            """)
    }

    private static String getRefDataframeHtml(Map onClickMap, DataframeVue dataframe, String fldName, def gridDataframeList, VueJsBuilder vueJsBuilder){
        StringBuilder resultPageHtml = new StringBuilder()
        String parentDataframeName = dataframe.dataframeName
        DataframeVue refDataframe = null
        if(onClickMap.refDataframe){
            refDataframe = DataframeVue.getDataframeBeanFromReference(onClickMap.refDataframe)
        }
        if(!refDataframe){
            return resultPageHtml.toString()
        }
        String refDataframeName = refDataframe?.dataframeName?:""
        gridDataframeList.add(refDataframeName)
        dataframe.childrenDataframes.add(refDataframeName)
        vueJsBuilder.addToDataScript("${refDataframeName}_display : false,\n")
        VueStore.addToShowHideParamNames("${refDataframeName}_display : true,\n")
        ResultPageHtmlBuilder.globalParametersInStore.append("${refDataframeName}_display : true,\n")

        if(onClickMap.showAsDialog){
            resultPageHtml.append("""<v-dialog v-model="gridDataframeNames.${refDataframeName}_display" max-width="500px">""")
//            resultPageHtml.append(refDataframe.getComponentName())
            resultPageHtml.append("""<component :is='${refDataframeName}_comp' ref='${refDataframeName}_ref'></component>""")
            resultPageHtml.append("""</v-dialog>""")
        }else{
            resultPageHtml.append("""<div v-show="gridDataframeNames.${refDataframeName}_display " max-width="500px">""")
//            resultPageHtml.append(refDataframe.getComponentName())
            resultPageHtml.append("""<component :is='${refDataframeName}_comp' ref='${refDataframeName}_ref'></component>""")
            resultPageHtml.append("""</div>""")
        }

        //Add computed and watch scripts for dialog box
        String computedScript = """check${refDataframeName}CloseButton: function(){return this.\$store.state.vueInitDataframe.${refDataframeName}_display}, \n"""
        String watchScript = """check${refDataframeName}CloseButton:{handler: function(val, oldVal) {
                               this.${refDataframeName}_display = this.\$store.state.vueInitDataframe.${refDataframeName}_display;}}, \n """
//        VueJsBuilder vueJsBuilder = dataframe.vueJsBuilder
        String updateStoreCallScript = ""
        String updateStoreMehtodScript = ""
        if(dataframe.createStore || dataframe.vueStore){
            dataframe.vueStore = ["state":"${fldName}_grid:{},\n"]
            updateStoreCallScript = "this.${refDataframeName}_updateStore(dataRecord);"
            updateStoreMehtodScript = """${refDataframeName}_updateStore: function(data){
                            Vue.set(this.\$store.state.${parentDataframeName}.${fldName}_grid, "key", data.id)
                            Vue.set(this.\$store.state.${refDataframeName}, "key", data.id)
                    },\n """
        }
        vueJsBuilder.addToMethodScript("""
                     ${fldName}_showDetail${refDataframeName}: function(dataRecord){
                              $updateStoreCallScript
                              this.${refDataframeName}_comp = "${refDataframeName.toLowerCase()}";
                              ${parentDataframeName}Var.gridDataframeNames.${refDataframeName}_display = true;
                    },\n 
                    $updateStoreMehtodScript
                                      """)
        vueJsBuilder.addToDataScript("${refDataframeName}_comp: '',\n")
        vueJsBuilder.addToComputedScript(computedScript)
        vueJsBuilder.addToWatchScript(watchScript)

        return resultPageHtml.toString()
    }

    private static void constructGridDeleteScript(StringBuilder methodScriptsBuilder, DataframeVue buttonRefDataframe, StringBuilder requestFieldParams, def deleteButton, String fldName, String parentDataframeName){
        String valueMember = deleteButton.valueMember
        List<String> keyFieldNames = buttonRefDataframe.getKeyFieldNameForNamedParameter(buttonRefDataframe)
        keyFieldNames.each {
            if (it.split('-').collect().contains(valueMember)){
                requestFieldParams.append("\nallParams['").append(it).append("'] = dataRecord.").append(valueMember).append(";\n");
            }

        }
        methodScriptsBuilder.append("""
                ${fldName}_delete: function(dataRecord){
                           var allParams = {'dataframe':'$buttonRefDataframe.dataframeName'};
                           var editedIndex = this.${fldName}_items.indexOf(dataRecord);
                           $requestFieldParams
                            confirm('${deleteButton.message}');
                           axios.get('$deleteButton.ajaxDeleteUrl', {
                             params: allParams
                        }).then(function (responseData) {
                         if (responseData.data.success){
                           ${parentDataframeName}Var.${fldName}_items.splice(editedIndex, 1)
                         }
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
                    },\n 
          """)
    }

    public Map getDefaultData(List<MetaField> fieldMetaData){

        def defaultValues = [:]

        fieldMetaData.each{ metaField ->
            defaultValues.put(metaField.alias?:metaField.name,metaField.defaultValue)
        }

        return defaultValues
    }

    public Map getDataFields(String wdgHql, Dataframe dataframe, List<MetaField> fieldMetaData){

        List datafields = [];
        def dataFieldsName = [:]

        fieldMetaData.each{ metaField ->
            datafields.add([name: metaField.alias, type: metaField.dataType.getjQueryDataType()])
            dataFieldsName.put(metaField.alias,[metaField.alias,metaField.dataType.getjQueryDataType()])
        }


        Map metadata = [:]

        metadata.put("datafields", datafields)
        metadata.put("dataFieldsName", dataFieldsName)

        metadata.put("columns", getColumns(wdgHql, dataframe, fieldMetaData))

        //String pkString = "";
        //(dataframe.metaFieldService.getPk(fieldMetaData)).collect { pkString += ( pkString ? "," : "" ) + "$it"  };

        //metadata.put("pk", pkString);
        metadata.put("pk", dataframe.metaFieldService.getPk(fieldMetaData));


        return metadata;
    }

    private List getColumns(String wdgHql, Dataframe df, List fieldMetaData){

        List result = new ArrayList();

        ParsedHql pHql = new ParsedHql(wdgHql, df.grailsApplication, df.sessionFactory);

        fieldMetaData.each{ metaField ->
            Map columnProp = addColumnMap(df, metaField);
            result.add(columnProp);
        }
        return result;

    }

    private Map addColumnMap(Dataframe df, MetaField mf) {
        Map columnMap = [:]
        String fieldname = mf.name
        columnMap.put("datafield", mf.alias)
        columnMap.put("text",  df.messageSource.getMessage(mf.labelCode, null, org.apache.commons.lang.WordUtils.capitalizeFully(fieldname), LocaleContextHolder.getLocale()));
//		columnMap.put("width", mf.length)
        String defaultFormat = getDefaultFormat(mf.domainDataType);
        if(defaultFormat){
            columnMap.put("cellsformat", defaultFormat)
        }
        columnMap.put("cellsalign", getDefaultAligh(mf.domainDataType)) //conver k into Column Localized title

        return columnMap;
    }


    private String getDefaultFormat(String cellType){
        if("date".equals(cellType)){
            return "dd-MMMM-yyyy"
        }
        return null;
    }

    private String getDefaultAligh(String cellType, String locale){
        if("number".equals(cellType)){
            return "right"
        }
        if(locale.startsWith("he")){
            return "right";
        }else{
            return "left";
        }
    }

    private String getDefaultAligh(String cellType){
        return getDefaultAligh(cellType, "en_ca");
    }
}


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
import com.elintegro.erf.dataframe.ResultPageHtmlBuilder
import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.DbResult
import com.elintegro.erf.dataframe.ParsedHql
import com.elintegro.erf.dataframe.db.fields.MetaField
import com.elintegro.erf.dataframe.vue.VueJsBuilder
import com.elintegro.erf.dataframe.vue.VueStore
import com.elintegro.utils.MapUtil
import grails.converters.JSON
import grails.util.Holders
import org.springframework.context.i18n.LocaleContextHolder
import org.apache.commons.lang.WordUtils

/**
 * Created by kchapagain on Nov, 2018.
 */
class GridWidgetVue extends WidgetVue {

    def contextPath = Holders.grailsApplication.config.rootPath
    public String ajaxDeleteUrl = "${contextPath}/dataframe/ajaxDeleteExpire"
    String embDDfr = "";

    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName     = dataframe.getDataVariableForVue(field);
        def showGridSearch = field?.showGridSearch?:false
        String wdgHql      = field?.hql;
        def onClick        = field?.onClick
        def onButtonClick  = field?.onButtonClick
        String valueMember = field?.valueMember
        boolean internationalize = field.internationalize?true:false
        List gridDataframeList= []
        StringBuilder methodScriptsBuilder = new StringBuilder();
        StringBuilder requestFieldParams   = new StringBuilder()
        StringBuilder fieldParams          = new StringBuilder();
        StringBuilder onclickDfrBuilder    = new StringBuilder();
        ParsedHql parsedHql = new ParsedHql(wdgHql, dataframe.grailsApplication, dataframe.sessionFactory);
        List<MetaField> fieldMetaData      = dataframe.metaFieldService.getMetaDataFromFields(parsedHql, field.name);
        field.put("gridMetaData", fieldMetaData);
        field.put("parsedHql", parsedHql);
        List dataHeader = []
        boolean showRefreshMethod = false
        fieldMetaData.each {metaField ->
            def propItemText = metaField.alias?:metaField.name
            def propItemVal  = metaField.name
            String headerText = propItemText.capitalize()
            if(internationalize){
                headerText = getMessageSource().getMessage(propItemText,null,propItemText,LocaleContextHolder.getLocale())
            }
//            String capitalisedProp = propItemText.capitalize()
            String hiddenClass = ""
            if(metaField.pk || metaField.fk){
                hiddenClass = "hidden"
            }
            dataHeader.add(['text':headerText, 'keys':propItemVal, 'value':headerText, 'class':hiddenClass])
            String propTextLowercase = propItemText.toLowerCase()
            if(propTextLowercase.contains("image") || propTextLowercase.contains("picture") || propTextLowercase.contains("avatar")){
                String defaultImageName = Holders.config.images.defaultImageName
                String imgUrl =  getImageUrl(field)
                fieldParams.append("\n<td class='text-xs-left'><div v-html='props.item.$propItemText'></div></td>");
            }else {
                fieldParams.append("\n<td class='$hiddenClass text-xs-left'>{{ props.item.$propItemText }}</td>");
            }
            requestFieldParams.append("\nallParams['").append(metaField["alias"]).append("'] = dataRecord.").append(metaField["alias"]).append(";\n");
        }
        field.put("dataHeader", dataHeader);
        def parentDataframeName = dataframe.dataframeName
        String onClickMethod    = " "
        String refDataframeName = ""
        if (onClick){
            showRefreshMethod   = true
            if(onClick.script){
                dataframe.getVueJsBuilder().addToMethodScript(""" 
                   ${fldName}_showDetail: function(dataRecord){
                              ${onClick.script}
                    },\n 
            """)
            } else{
                DataframeVue refDataframe = DataframeVue.getDataframeBeanFromReference(onClick.refDataframe)
                refDataframeName = refDataframe.dataframeName
                onClickMethod    = "${fldName}_showDetail$refDataframeName(props.item)"
                getOnClickScript(onClick, dataframe, refDataframeName, onclickDfrBuilder, gridDataframeList, fldName)
            }
        }

        if (onButtonClick){
            showRefreshMethod = true
            getOnButtonClickScript(onButtonClick, dataframe, onclickDfrBuilder, gridDataframeList, fieldParams, fldName, parentDataframeName, requestFieldParams, dataHeader)

        }
        showRefreshMethod = showRefreshMethod || field.showRefreshMethod?true:false
        if(showRefreshMethod){
            dataframe.getVueJsBuilder().addToMethodScript(refreshGrid(fldName, refDataframeName, dataframe))
        }
        String defaultImageName = Holders.config.images.defaultImageName
        def fldNameDefault = WordUtils.capitalizeFully(fldName);
        String labelCode = field.labelCode?:fldName
        String label = getMessageSource().getMessage(labelCode, null, fldNameDefault, LocaleContextHolder.getLocale())
        boolean showGridFooter = field.showGridFooter?true:false
        field.put("gridDataframeList", gridDataframeList);
        field.put("gridMethodScripts", methodScriptsBuilder);
        StringBuilder dataTableAttribbutes = new StringBuilder()
        if(!showGridFooter){
            dataTableAttribbutes.append(""" hide-actions""")
        }
        String searchPlaceholder = getMessageSource().getMessage("Search", null, "Search", LocaleContextHolder.getLocale())
        String draggIndicator = field.draggable?""" <td class="drag" style="max-width:'20px';">::</td>""":""
//        dataSb.append("${fldName}_display:false,\n")
        String gridTitle = label?"""<v-card-title class='title font-weight-light' style='justify-content: space-evenly;'>$label</v-card-title>""":""
        return """<v-card v-show="${fldName}_display"><v-divider/>${gridTitle}

       ${showGridSearch?"""
            <v-spacer></v-spacer>
            <v-text-field
                    v-model="${fldName}_search"
                    append-icon="search"
                    label="$searchPlaceholder"
                    single-line
                    hide-details
                    class='pa-3'
            ></v-text-field>
        """:""}
       <v-data-table
            :headers="${fldName}_headers"
            :items="${fldName}_items"
            ${showGridSearch?":search='${fldName}_search'":""}
            ${dataTableAttribbutes.toString()}
    >
        <template slot="items" slot-scope="props">
          <tr @click="${onClickMethod}" :key="props.item.$valueMember">
            $draggIndicator ${fieldParams.toString()}
          </tr>  
        </template>
    </v-data-table></v-card>
        ${onclickDfrBuilder.toString()}
"""
    }

    String getVueDataVariable(DataframeVue dataframe, Map field) {
        String dataVariable = dataframe.getDataVariableForVue(field)
        def search = field?.showGridSearch
        List gridDataframeNames = field?.gridDataframeList
        String gridDataframeNamesBuilder = ""
        def dataHeader = field.dataHeader
        if (gridDataframeNames){
            gridDataframeNamesBuilder = "{\n"
            gridDataframeNames.each {dfName ->
                gridDataframeNamesBuilder = gridDataframeNamesBuilder + "${dfName}_display:false,\n"
            }
            gridDataframeNamesBuilder = gridDataframeNamesBuilder + "}\n"
        }

        dataframe.getVueJsBuilder().addToMethodScript(""" getDefaultDataHeaders : function(){\n
                             var defaultDataHeaders = ${dataHeader as JSON};
                             this.${dataVariable}_headers = defaultDataHeaders;
                          },\n""")
        return """
            drag:'',
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
                DataframeVue refDataframe = getReferenceDataframe(onClick.refDataframe)
                String refDataframeName = refDataframe.dataframeName
//                namedParamKey = "this.\$refs.${refDataframeName.toLowerCase()}_ref.\$data.namedParamKey = \"this.\$store.state.${refDataframeName}.key\";\n"
                namedParamKey = ""
            }
        }
        String fldName = dataframe.getDataVariableForVue(field);
        String fullFieldName = key.replace(Dataframe.DOT,Dataframe.DASH)
        dataframe.getVueJsBuilder().addToComputedScript(""" ${fldName}_display: function(){if(this.${dataVariable}_items.length){
                  return true;
               }},\n""")
        String hqlLowercase = field.hql?.toLowerCase()
        StringBuilder formatAvatarSb = new StringBuilder()
        String avatar = field.avatarAlias?:'Avatar'
        if(hqlLowercase && hqlLowercase.contains("image") || hqlLowercase.contains("picture") || hqlLowercase.contains("avatar")){
            String imgUrl =  getImageUrl(field)
            String defaultImageName = Holders.config.images.defaultImageName
            formatAvatarSb.append(""" if(dataDessert.length > 0){for(var i=0; i<dataDessert.length; i++){
                                               var avarName = dataDessert[i].$avatar;
                                               var formattedName = avarName?'$imgUrl'+avarName:'$imgUrl'+'$defaultImageName'
                                               dataDessert[i].$avatar = '<v-img height="40px" width="40px" src="'+formattedName+'" />';
                                    }}""");
        }
        return """
               var fullFieldName = '$fullFieldName'; 
               var dataDessert = response.additionalData[fullFieldName]['data'];
               var dataHeader = response.additionalData[fullFieldName]['dataHeader'];
               ${formatAvatarSb.toString()}
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

    }

    private void getOnButtonClickScript(onButtonClick, DataframeVue dataframe,  StringBuilder onclickDfrBuilder
                                        , gridDataframeList, StringBuilder fieldParams, String fldName, String parentDataframeName, StringBuilder requestFieldParams, List dataHeader){
        String buttonHoverMessage = ""
        onButtonClick.each{Map onButtonClickMaps ->
            String actionName = getMessageSource().getMessage(onButtonClickMaps?.actionName?:"", null, onButtonClickMaps?.actionName?:"", LocaleContextHolder.getLocale())
            dataHeader.add(['text':actionName.capitalize(), 'keys':actionName, 'value':'name', sortable: false])
            fieldParams.append("\n<td class='justify-center layout px-0' @click.stop=''>");
            onButtonClickMaps.buttons.each {Map buttonMaps->
                String text = buttonMaps?.buttonName
                String appendCallbackScript
                if(buttonMaps?.callBackParams){
                    appendCallbackScript = buttonMaps?.callBackParams?.appendScript?:""
                }
                buttonHoverMessage = dataframe.messageSource.getMessage(buttonMaps.tooltip.hoverMessage, null, org.apache.commons.lang.WordUtils.capitalizeFully(text), LocaleContextHolder.getLocale())
                boolean deleteButton = buttonMaps.deleteButton
                boolean editButton = buttonMaps.editButton
                boolean showDetail = buttonMaps.showDetail
                def vIcon = buttonMaps?.vuetifyIcon

                String btnName = ""
                String methodScript = ""
                if(deleteButton){
                    methodScript= constructGridDeleteScript(buttonMaps, fldName, dataframe.dataframeName)
                    btnName = "deleteButton"
                    vIcon.name = "delete"

                }else if(editButton){
                    btnName = "editButton"
                    vIcon.name = "edit"
                    methodScript = getEditJavascript(buttonMaps, dataframe, fldName)
                } else if(showDetail){
                    btnName = "detailsButton"
                    methodScript = getShowDetailJavascript(buttonMaps, dataframe, fldName)
                } else {
                    methodScript = buttonMaps.script
                    btnName = buttonMaps.name?:"editButton"
                    if(buttonMaps?.refDataframe){
                        methodScript= getEditJavascript(buttonMaps, dataframe, fldName)
                    }
                    if(!methodScript){
                        methodScript = ""
                    }
                }
                String methodName = """ ${fldName}_${btnName}method(props.item);"""
                if (buttonMaps?.image){
                    String actionImageUrl = buttonMaps?.image?.url?:"https://image.flaticon.com/icons/png/128/66/66720.png";
                    String height = buttonMaps?.image?.height?:"20"
                    String width = buttonMaps?.image?.width?:"25"
                    fieldParams.append("""
                                        <img ${toolTip(buttonMaps)} height="$height" width="$width" style='margin-top: 14px; cursor: pointer;' src="$actionImageUrl" @click="${methodName}"/>
                    """)
                }else if (vIcon){
                    fieldParams.append("""<v-icon small ${toolTip(buttonMaps)} class="mr-2" @click="${methodName}">${vIcon?.name}</v-icon>""")
                }
                if(buttonMaps?.refDataframe && !buttonMaps.deleteButton){
                    onclickDfrBuilder.append(getRefDataframeHtml(buttonMaps, dataframe, fldName, gridDataframeList))
                }
                dataframe.getVueJsBuilder().addToMethodScript(""" ${fldName}_${btnName}method: function(dataRecord){
                                             $methodScript
                                             },\n""")

                /*if (deleteButton){
                    constructGridDeleteScript(methodScriptsBuilder, buttonRefDataframe, requestFieldParams, deleteButton, fldName, parentDataframeName)
                }else {
                    String stateName = fldName + "_button"
                    onclickDfrBuilder.append(getRefDataframeHtml(buttonMaps, dataframe, fldName, gridDataframeList))
                }*/
            }
            fieldParams.append("""</td>""")
        }
    }

    private void getOnClickScript(def onClick, DataframeVue dataframe, String refDataframeName, StringBuilder onclickDfrBuilder, def gridDataframeList
                                  , String fldName){

        String stateName = fldName + "_onClick"

        onclickDfrBuilder.append(getRefDataframeHtml(onClick, dataframe, fldName, gridDataframeList))
        dataframe.getVueJsBuilder().addToMethodScript(""" ${fldName}_showDetail$refDataframeName: function(dataRecord){
                                             ${getShowDetailJavascript(onClick, dataframe, fldName)}
                                             },\n""")

    }

    private String refreshGrid(String fldName, String refDataframeName, Dataframe dataframe){
        String vueStoreKeyName = refDataframeName?:"dataframeBuffer"
        dataframe.getVueJsBuilder().addToComputedScript(""" refresh${fldName}Grid: function(){
                                        var responseData =  drfExtCont.getFromStore("$vueStoreKeyName", "savedResponseData");
                                         if(responseData == null || responseData == undefined || responseData == ""){
                                            return null
                                          } else {
                                            return responseData
                                          }
                                        
                               },\n""")
        dataframe.getVueJsBuilder().addToWatchScript("""refresh${fldName}Grid: {handler: function(val, oldVal) {
                                                this.${fldName}_refreshDataForGrid(val);
                            }},\n""")
        return """
                    ${fldName}_refreshDataForGrid: function(responseData){
                       
                          var selectedRow = this.${fldName}_selectedrow;
                          var editedIndex = this.${fldName}_items.indexOf(selectedRow);
                          var data = responseData.data;
                          var operation = data.operation;
                          var newData = data.newData;
                          var row = {};
                          jQuery.each(newData, function(key, value) {
                              var dataMap = value;
                              jQuery.each(dataMap, function (key, value) {
                                 if(selectedRow){
                                    if (key in selectedRow) {
                                      row[key] = value;
                                    }
                                 } else {
                                    row[key] = value;
                                 }
                                  
                              });
                          });
                          if (operation==="I") {
                              this.${fldName}_items.push(row)
                          } else {
                              Object.assign(this.${fldName}_items[editedIndex], row)
                          }
//                          this.gridDataframeNames[refreshParams.dataframe] = false; 
                },\n

            """
    }

    private String getRefDataframeHtml(Map onClickMap, DataframeVue dataframe, String fldName, def gridDataframeList){
        StringBuilder resultPageHtml = new StringBuilder()
        DataframeVue refDataframe = null
        if(onClickMap.refDataframe){
            refDataframe = getReferenceDataframe(onClickMap.refDataframe)
        }

        if(!refDataframe){
            return resultPageHtml.toString()
        }
        String refDataframeName = refDataframe?.dataframeName?:""
        gridDataframeList.add(refDataframeName)
        dataframe.childrenDataframes.add(refDataframeName)
        VueStore store = dataframe.getVueJsBuilder().getVueStore()
        store.addToShowHideParamNames("${refDataframeName}_display : true,\n")
        dataframe.getVueJsBuilder().addToDataScript("${refDataframeName}_data:{key:'', \nrefreshGrid: true},\n")

        if(onClickMap.showAsDialog){
            resultPageHtml.append("""<v-dialog v-model="gridDataframeNames.${refDataframeName}_display" max-width="800px">""")
//            resultPageHtml.append(refDataframe.getComponentName())
            resultPageHtml.append("""<component :is='${refDataframeName}_comp' ref='${refDataframeName.toLowerCase()}_ref' :${refDataframeName}_prop="${refDataframeName}_data"></component>""")
            resultPageHtml.append("""</v-dialog>""")
        }else{
            resultPageHtml.append("""<div v-show="gridDataframeNames.${refDataframeName}_display " max-width="500px">""")
//            resultPageHtml.append(refDataframe.getComponentName())
            resultPageHtml.append("""<component :is='${refDataframeName}_comp' ref='${refDataframeName.toLowerCase()}_ref' v-bind:refreshGrid="true"></component>""")
            resultPageHtml.append("""</div>""")
        }

        //Add computed and watch scripts for dialog box
        String computedScript = """check${refDataframeName}CloseButton: function(){return this.\$store.state.dataframeShowHideMaps.${refDataframeName}_display}, \n"""
        String watchScript = """check${refDataframeName}CloseButton:{handler: function(val, oldVal) {
                               this.gridDataframeNames.${refDataframeName}_display = this.\$store.state.dataframeShowHideMaps.${refDataframeName}_display;}}, \n """

        dataframe.getVueJsBuilder().addToDataScript("${refDataframeName}_comp: '',\n")
                .addToComputedScript(computedScript)
                .addToWatchScript(watchScript)

        return resultPageHtml.toString()
    }

    private String getEditJavascript(Map onClickMap, DataframeVue dataframe, String fldName){
        return getShowDetailJavascript(onClickMap, dataframe, fldName)
    }

    private String getShowDetailJavascript(Map onClickMap, DataframeVue dataframe,  String fldName){
        String parentDataframeName = dataframe.dataframeName
        String updateStoreCallScript = ""
        String updateStoreMehtodScript = ""
        DataframeVue refDataframe
        if(onClickMap.refDataframe){
            refDataframe = getReferenceDataframe(onClickMap.refDataframe)
        }
        String refDataframeName = refDataframe.dataframeName
        if(dataframe.createStore || dataframe.vueStore){
//            String previousState = dataframe.vueStore.state?:""
//            String newState = previousState + "${fldName}_grid:{},\n";
//            dataframe.vueStore = ["state":newState];
//
//            Vue.set(this.\$store.state.${parentDataframeName}.${fldName}_grid, "key", '')
            VueStore store = dataframe.getVueJsBuilder().getVueStore()
            store.addToState("${fldName}_grid:{},\n")
            updateStoreCallScript = "this.${refDataframeName}_updateStore(dataRecord);"
            dataframe.getVueJsBuilder().addToMethodScript("""${refDataframeName}_updateStore: function(data){
                            var key = data.id?data.id:data.Id;
                            Vue.set(this.\$store.state.${parentDataframeName}.${fldName}_grid, "key", key)
                            Vue.set(this.\$store.state.${refDataframeName}, "key", key)
                    },\n """)
        }
        return """
                              $updateStoreCallScript
                              this.${refDataframeName}_comp = "";
                              this.${refDataframeName}_comp = "${refDataframeName.toLowerCase()}";
                              var key = dataRecord.id?dataRecord.id:(dataRecord.Id|dataRecord.ID);
                              Vue.set(this.${refDataframeName}_data, 'key', key);
                              ${parentDataframeName}Var.gridDataframeNames.${refDataframeName}_display = true;
                              Vue.set(this.\$store.state.dataframeShowHideMaps,'${refDataframeName}_display', true); 
                    \n 
                    """
    }
    private String constructGridDeleteScript(Map buttonMaps,  String fldName, String parentDataframeName){
        DataframeVue buttonRefDataframe = getReferenceDataframe(buttonMaps.refDataframe)
        String valueMember =buttonMaps.valueMember?:"id"
        String doBeforeDelete = buttonMaps.doBeforeDelete?:""
        StringBuilder requestFieldParams = new StringBuilder()
        List<String> keyFieldNames = buttonRefDataframe.getKeyFieldNameForNamedParameter(buttonRefDataframe)

        requestFieldParams.append("allParams['dataframe'] = '$buttonRefDataframe.dataframeName';\n")
        requestFieldParams.append("allParams['parentDataframe'] = '$parentDataframeName';\n")
        requestFieldParams.append("allParams['fieldName'] = '$fldName';\n")
        requestFieldParams.append("allParams['id'] = dataRecord.id?dataRecord.id:dataRecord.Id;")
        keyFieldNames.each {
            if (it.split('-').collect().contains(valueMember)){
                if(valueMember.equalsIgnoreCase("id")){

                    requestFieldParams.append("\nallParams['").append(it).append("'] = allParams['id'];\n");
                }else{

                    requestFieldParams.append("\nallParams['").append(it).append("'] = dataRecord.").append(valueMember).append(";\n");
                }
            }

        }
        String confirmMessage = buttonMaps.message?:"Are you sure ?"
        String url =  buttonMaps.ajaxDeleteUrl?: ajaxDeleteUrl
        return """
                
                           var allParams = {};
                           var editedIndex = this.${fldName}_items.indexOf(dataRecord);
                           ${requestFieldParams.toString()}
                            $doBeforeDelete
                            confirm('${confirmMessage}');
                           axios.get('$url', {
                             params: allParams
                        }).then(function (responseData) {
                         if (responseData.data.success){
                           ${parentDataframeName}Var.${fldName}_items.splice(editedIndex, 1)
                         }
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
                    \n 
          """
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

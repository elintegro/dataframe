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

import com.elintegro.erf.dataframe.*
import com.elintegro.erf.dataframe.db.fields.MetaField
import com.elintegro.erf.dataframe.vue.DataMissingException
import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.vue.VueJsBuilder
import com.elintegro.erf.dataframe.vue.VueStore
import com.elintegro.utils.MapUtil
import grails.converters.JSON
import grails.util.Holders
import org.apache.commons.lang.WordUtils
import org.grails.web.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.i18n.LocaleContextHolder
/**
 * Created by kchapagain on Nov, 2018.
 */
class GridWidgetVue extends WidgetVue {
    private static final Logger log = LoggerFactory.getLogger(GridWidgetVue.class);

    def contextPath = Holders.grailsApplication.config.rootPath
    public String ajaxDeleteUrl = "dataframe/ajaxDeleteExpire"
    String embDDfr = "";
    private static final String headers = "headers"
    private static final String defaultData = "defaultData"

    boolean setPersistedValueToResponse(JSONObject jData, def value, String domainAlias, String fieldName, Map additionalDataRequestParamMap, DataframeInstance dfInstance, Object sessionHibernate, Map fieldProps){
        Map additionalData = loadAdditionalData(dfInstance, fieldProps, fieldName, additionalDataRequestParamMap, sessionHibernate)
        if(additionalData.containsKey(items)){
            jData?.persisters?."${domainAlias}"."${fieldName}"."${items}" = additionalData."${items}"
        }
        if(additionalData.containsKey(headers)){
            jData?.persisters?."${domainAlias}"."${fieldName}"."${headers}" = additionalData."${headers}"
        }
    }

    boolean setTransientValueToResponse(JSONObject jData, def value, String domainAlias, String fieldName, Map additionalDataMap, DataframeInstance dfInstance, Object sessionHibernate, Map fieldProps){
        Map additionalData = loadAdditionalData(dfInstance, fieldProps, fieldName, additionalDataMap, sessionHibernate)
        if(additionalData.containsKey(items)){
            jData?.transits?."${fieldName}"."${items}" = additionalData."${items}"
        }
        if(additionalData.containsKey(headers)){
            jData?.transits?."${fieldName}"."${headers}" = additionalData."${headers}"
        }
    }


    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName     = getFieldName(dataframe, field)
        def showGridSearch = field?.showGridSearch?:false
        StringBuilder onclickDfrBuilder    = new StringBuilder();
        def fldNameDefault = WordUtils.capitalizeFully(fldName);
        String labelCode = field.labelCode?:fldName
        String label = getMessageSource().getMessage(labelCode, null, fldNameDefault, LocaleContextHolder.getLocale())
        boolean showGridFooter = field.showGridFooter?true:false
        StringBuilder dataTableAttribbutes = new StringBuilder()
        if(!showGridFooter){
            dataTableAttribbutes.append("""hide-default-footer""")
        }
        String searchPlaceholder = getMessageSource().getMessage("Search", null, "Search", LocaleContextHolder.getLocale())
        String labelStyle = field.labelStyle?:""
//        String modelString = getModelString(dataframe, field)
        String modelString = getFieldJSONModelNameVue(field)
        String itemsStr = getFieldJSONItems(field)
        String headerString = "${getFieldJSONNameVue(field)}${DOT}${headers}"
        boolean isDynamic = field.isDynamic?:false
        String gridTitle
        if(isDynamic){
            gridTitle = label?"""<v-card-title class='title pt-0 font-weight-light' style='$labelStyle'>$label\t{{state.gridTitleFromState}}</v-card-title>""":""
        }else {
            gridTitle = label?"""<v-card-title class='title pt-0 font-weight-light' style='$labelStyle'>$label</v-card-title>""":""
        }
        String fieldParams = prepareFieldParams(dataframe, field, onclickDfrBuilder)
        String itemKey = field.itemKey?:"id"
        return """<v-card v-show="${fldName}_display"><v-divider/>

       ${showGridSearch?"""
            <v-row><v-col cols='8'  style="margin-left:-30px; align-self:flex-end;">${gridTitle}</v-col>
            <v-col cols="4">
                    <v-text-field
                    v-model="${fldName}_search"
                    append-icon="search"
                    label="$searchPlaceholder"
                    single-line
                    hide-details
                    class='pa-3'
            ></v-text-field>
            </v-col></v-row>
        """:""}
       <v-data-table
            :headers="${headerString}"
            :items="${itemsStr}"
            :items-per-page="-1"
            item-key="${itemKey}"
            ${showGridSearch?":search='${fldName}_search'":""}
            ${dataTableAttribbutes.toString()}
            ${getAttr(field)}
    >
$fieldParams
    </v-data-table></v-card>
        ${onclickDfrBuilder.toString()}
"""
    }

    String prepareFieldParams(Dataframe dataframe, Map field, StringBuilder onclickDfrBuilder ){
        String fldName     = getFieldName(dataframe, field)
        String wdgHql      = field?.hql;
        def onClick        = field?.onClick
        def onButtonClick  = field?.onButtonClick
        String alignment   = field?.textAlign?:'start'
        String headerWidth = field.headerWidth?:''
        String valueMember = field?.valueMember
        boolean internationalize = field.internationalize?true:false
        StringBuilder requestFieldParams   = new StringBuilder()
        StringBuilder fieldParams          = new StringBuilder();

        ParsedHql parsedHql = new ParsedHql(wdgHql, dataframe.grailsApplication, dataframe.sessionFactory, "${dataframe.dataframeName}:${field.name}" );
        List<MetaField> fieldMetaData      = dataframe.metaFieldService.getMetaDataFromFields(parsedHql, field.name);
        field.put("gridMetaData", fieldMetaData);
        field.put("parsedHql", parsedHql);
        List dataHeader = []
        fieldMetaData.each {metaField ->
            def propItemText = metaField.alias?:metaField.name
            def propItemVal  = metaField.name
            String headerText = propItemText.capitalize()
            if(internationalize){
                headerText = getMessageSource().getMessage(propItemText,null,propItemText,LocaleContextHolder.getLocale())
            }
//            String capitalisedProp = propItemText.capitalize()
            StringBuilder headerClass = new StringBuilder()
            if(metaField.pk || metaField.fk){
                headerClass.append("hidden ")
                valueMember = valueMember?: metaField.alias
            }
            addClassesToHeader(field, headerClass, propItemVal)
            headerClass.append("text-$alignment")
            dataHeader.add(['text':headerText, 'keys':propItemVal, 'value':headerText, 'class':"${headerClass.toString()}", 'width':"${headerWidth}"])
            String propTextLowercase = propItemText.toLowerCase()
            if(propTextLowercase.contains("image") || propTextLowercase.contains("picture") || propTextLowercase.contains("avatar") || propTextLowercase.contains("logo")){
                String defaultImageName = Holders.config.images.defaultImageName
                String imgUrl =  getImageUrl(field)
                String avatar = field.avatarAlias?:'Avatar'
                String height = field.avatarHeight?:'auto'
                String width = field.avatarWidth?:'40'
//                fieldParams.append("\n<td class='$headerClass'><div v-html='props.item.$propItemText'></div></td>");
                fieldParams.append("\n<td class='$headerClass'><v-img height='$height' width='$width' :src='props.item.$propItemText' :alt ='props.item.$propItemText'></v-img></td>");
            }else {
                Map manageFields = field.manageFields as Map
                String tdString = "\n<td class='$headerClass'>{{ props.item.$propItemText }}</td>";
                if(manageFields){
                    if(manageFields.containsKey(propItemVal)){
                        if('link' == manageFields[propItemVal].type){
                            tdString = "\n<td class='$headerClass'><a :href='props.item.$propItemText' target='_blank' style ='text-decoration :none !important;' >{{ props.item.$propItemText }} </a></td>";
                        }
                    }
                }
                fieldParams.append(tdString)
            }
            requestFieldParams.append("\nparams['").append(metaField["alias"]).append("'] = dataRecord.").append(metaField["alias"]).append(";\n");
        }
        field.put("dataHeader", dataHeader);
        VueJsBuilder vueJsBuilder = dataframe.getVueJsBuilder()
        VueStore store = vueJsBuilder.getVueStore()
        store.addToState("${field.name}_selectedrow : '',\n")
        def parentDataframeName = dataframe.dataframeName
        String onClickMethod    = " "
        String refDataframeName = ""
        List gridDataframeList= []
        if (onClick){
//            showRefreshMethod   = true
            if(onClick.script){
                vueJsBuilder.addToMethodScript(""" 
                   ${fldName}_showDetail: function(dataRecord){
                              ${onClick.script}
                    },\n 
            """)
            } else{
                DataframeVue refDataframe = DataframeVue.getDataframeBeanFromReference(onClick.refDataframe)
                refDataframeName = refDataframe.dataframeName
                onClickMethod    = "${fldName}_showDetail$refDataframeName(props.item)"
                getOnClickScript(onClick, dataframe, refDataframeName, onclickDfrBuilder, gridDataframeList, fldName, field)
            }
        }

        if (onButtonClick){
//            showRefreshMethod = true
            getOnButtonClickScript(onButtonClick, dataframe, onclickDfrBuilder, gridDataframeList, fieldParams, fldName, dataHeader, field)

        }
        addMethodsToScript(dataframe, field)
//        putPropWatcherForChildDataframes(dataframe)
        field.put("gridDataframeList", gridDataframeList);
        String draggIndicator = field.draggable?""" <td class="drag" style="max-width:'20px';">::</td>""":""
        return """

        <template slot="item" slot-scope="props">
          <tr @click.stop="${onClickMethod}" :key="props.item.$valueMember">
            $draggIndicator ${fieldParams.toString()}
          </tr>  
        </template>
         """
    }

    private void addMethodsToScript(DataframeVue dataframe, Map fieldProps){

        String dataVariable = dataframe.getDataVariableForVue(fieldProps)
        def dataHeader = fieldProps.dataHeader
        String headerString = "${getFieldJSONNameVue(fieldProps)}${DOT}${headers}"
        dataframe.getVueJsBuilder().addToMethodScript(""" getDefaultDataHeaders_${dataVariable} : function(){\n
                             var defaultDataHeaders = ${dataHeader as JSON};
                             this.$headerString = defaultDataHeaders;
                          },\n""")
    }

    String getVueDataVariable(DataframeVue dataframe, Map field) {
        String dataVariable = dataframe.getDataVariableForVue(field)
        VueStore store = dataframe.getVueJsBuilder().getVueStore()
        store.addToState("gridTitleFromState: '',\n")//need to set this title from dataframe(doAfterRefresh) or script bean...
        def search = field?.showGridSearch
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
            drag:'',
           ${search?"${dataVariable}_search:'',":""}
           ${gridDataframeNamesBuilder?"gridDataframes:${gridDataframeNamesBuilder},":""}
"""

    }

    Map getStateDataVariablesMap(DataframeVue dataframe, Map field){
        List gridItems = []
        List gridHeaders = []
        if (isInitBeforePageLoad(field)){
            Map result = getGridData(dataframe, field, [:])
            if (result){
                gridItems = result."${items}"
                gridHeaders = result."${headers}"
            }
        }
        Map fldJSON = getDomainFieldJsonMap(dataframe, field)
        fldJSON?.put(items, gridItems)
        fldJSON?.put(headers, gridHeaders)
        return dataframe.domainFieldMap
    }

    private void putPropWatcherForChildDataframes(DataframeVue dataframe){
       List childDfrs = dataframe.childrenDataframes
        if(childDfrs){
            for(String dfr: childDfrs) {
                if (!dfr) continue
                DataframeVue child = DataframeVue.getDataframeByName(dfr) as DataframeVue
                String dfrName = child.dataframeName

                if(child.putFillInitDataMethod) {
                    VueJsBuilder vueJsBuilder = child.getVueJsBuilder()
                    vueJsBuilder.addToWatchScript(""" ${dfrName}_prop: {
                             deep:true,
                             handler: function(val, oldVal){
                                  if(val.refreshInitialData){
                                     this.${dfrName}_fillInitData();
                                  } else {
                                      console.log("${dfrName}_prop has refreshInitialData as false or undefined. Could not refresh.");
                                  }
                             }
                     },\n""")
                }
            }
        }
    }

    String getStateDataVariable(DataframeVue dataframe, Map field){

        String dataVariable = dataframe.getDataVariableForVue(field)
        def dataHeader = field.dataHeader
        dataframe.getVueJsBuilder().addToMethodScript(""" getDefaultDataHeaders_${dataVariable} : function(){\n
                             var defaultDataHeaders = ${dataHeader as JSON};
                             this.state.${dataVariable}_headers = defaultDataHeaders;
                          },\n""")
        return """
           ${dataVariable}_headers: [],
           ${dataVariable}_items: [],
"""
    }

    String getValueSetter(DataframeVue dataframe, Map field, String divId, String dataVariable, String key) throws DataframeException{
        def onClick = field.onClick
        String namedParamKey = ""
        if (onClick){
            if(onClick.refDataframe){
                DataframeVue refDataframe = getReferenceDataframe(onClick.refDataframe)
                String refDataframeName = refDataframe.dataframeName
//                namedParamKey = "this.\$refs.${refDataframeName}_ref.\$data.namedParamKey = \"this.\$store.state.${refDataframeName}.key\";\n"
                namedParamKey = ""
            }
        }
        String fldName = dataframe.getDataVariableForVue(field);
        dataframe.getVueJsBuilder().addToComputedScript(""" ${fldName}_display: function(){
                                                                    if(this.${getFieldJSONItems(field)}){
                                                                        return true;
                                                                    }
                                                                  },\n""")
        return """
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

    public Map loadAdditionalData(DataframeInstance dataframeInst, Map fieldProps, String fieldnameToReload, Map inputData, def dbSession){
        DataframeVue dataframe = dataframeInst.df;
        //Add fields from the Dataframe as possible input parameters for the additional HQL:
        createInputParamsData(dataframeInst, inputData)
        Map result= getGridData(dataframe, fieldProps, inputData)
        return result
    }

    public static def createInputParamsData(DataframeInstance dataframeInstance, Map inputData){
        inputData.putAll(dataframeInstance.getFieldValuesAsKeyValueMap());
        Map sessionParams = dataframeInstance.sessionParams
        if (sessionParams){
            inputData << sessionParams
        }
    }

    private def getGridData(DataframeVue dataframe, Map fieldProps, Map inputData){
        Map result=[:];
        String wdgHql = fieldProps?.hql;
        if(wdgHql){
            List<MetaField> fieldMetaData =  fieldProps.get("gridMetaData");
            List dataHeader =  fieldProps.get("dataHeader");
            List resultList = getResultList(fieldProps, dataframe, wdgHql, inputData)
            result.put(items, resultList);
            result.put(headers, dataHeader);
            result.put(defaultData, getDefaultData(fieldMetaData));
            if(!(fieldProps.containsKey("metaData") && fieldProps.get("metaData"))){
                fieldProps.put("metaData", getDataFields(wdgHql, dataframe, fieldMetaData));
            }
            result.put("metaData", fieldProps.get("metaData"));
        }
        return result
    }

    public static List getResultList(Map fieldProps, DataframeVue dataframe, String wdgHql, Map inputData){
        ParsedHql parsedHql =  fieldProps.get("parsedHql");
        if(parsedHql == null){
            log.warn("We have to recreate the Parsed Hql since it is null")
            parsedHql = new ParsedHql(fieldProps.hql, dataframe.grailsApplication, dataframe.sessionFactory)
        }
        def dbSession = dataframe.sessionFactory.openSession()
        DbResult dbRes = new DbResult(wdgHql, inputData, dbSession, parsedHql);
        List resultList = dbRes.getResultList()
        return resultList
    }

    public void getNamedParameterValue(dfInstance,inputData, parsedHql, fieldProps){
        Dataframe dataframe = dfInstance.df
        if(parsedHql.namedParameters){
            parsedHql.namedParameters.each{
                String key = it.getKey()
                if(fieldProps.containsKey(key)){
                    String hql = fieldProps.get(key)
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

    private void getOnButtonClickScript(onButtonClick, DataframeVue dataframe,  StringBuilder onclickDfrBuilder
                                        , gridDataframeList, StringBuilder fieldParams, String fldName, List dataHeader, Map fieldProps){
        String buttonHoverMessage = ""
        onButtonClick.each{Map onButtonClickMaps ->
            String actionName = getMessageSource().getMessage(onButtonClickMaps?.actionName?:"", null, onButtonClickMaps?.actionName?:"", LocaleContextHolder.getLocale())
            dataHeader.add(['text':actionName.capitalize(), 'keys':actionName, 'value':'name', sortable: false])
            fieldParams.append("\n<td class='text-start layout' @click.stop=''>");
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
                def vIcon = buttonMaps?.vuetifyIcon?:new HashMap<>()
                def showHide = vIcon.showHide
                String btnName = ""
                String methodScript = ""
                if(deleteButton){
                    methodScript= constructGridDeleteScript(buttonMaps, fldName, dataframe.dataframeName , fieldProps)
                    btnName = "deleteButton"
                    vIcon.name = "delete"

                }else if(editButton){
                    btnName = "editButton"
                    vIcon.name = "edit"
                    methodScript = getEditJavascript(buttonMaps, dataframe, fldName, fieldProps)
                } else if(showDetail){
                    btnName = "detailsButton"
                    methodScript = getShowDetailJavascript(buttonMaps, dataframe, fldName, fieldProps)
                } else {
                    methodScript = buttonMaps.script
                    btnName = buttonMaps.name
                    if(!btnName) throw new DataMissingException("name is required for each action buttons")
                    if(buttonMaps?.refDataframe && !buttonMaps.script){
                        methodScript= getEditJavascript(buttonMaps, dataframe, fldName, fieldProps)
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
                                        <img ${toolTip(buttonMaps)} height="$height" width="$width" class="mr-2" style='margin-top: 14px; cursor: pointer;' src="$actionImageUrl" @click.stop="${methodName}"/>
                    """)
                }else if (vIcon){
                    fieldParams.append("""<v-icon small ${toolTip(buttonMaps)} class="mr-2" ${showHide?.showHide?showHide.script:""} @click.stop="${methodName}">${vIcon?.name}</v-icon>""")
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
                                  , String fldName, Map fieldProps){

        String stateName = fldName + "_onClick"

        onclickDfrBuilder.append(getRefDataframeHtml(onClick, dataframe, fldName, gridDataframeList))
        dataframe.getVueJsBuilder().addToMethodScript(""" ${fldName}_showDetail$refDataframeName: function(dataRecord){
                                             ${getShowDetailJavascript(onClick, dataframe, fldName, fieldProps)}
                                             },\n""")

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
        store.addToDataframeVisibilityMap("${refDataframeName} : false,\n")
        dataframe.getVueJsBuilder().addToDataScript("${refDataframeName}_data:{key:'', \nrefreshGrid: true, \nrefreshInitialData: 8.0,\n parentData:{}},\n")

        if(onClickMap.showAsDialog){
            resultPageHtml.append("""<v-dialog v-model="visibility.${refDataframeName}" width='${getWidth(onClickMap)}' max-width='${getMaxWidth(onClickMap)}' >""")
            resultPageHtml.append("""<component :is='${refDataframeName}_comp' ref='${refDataframeName}_ref' :${refDataframeName}_prop="${refDataframeName}_data"></component>""")
            resultPageHtml.append("""</v-dialog>""")
        }else{
            resultPageHtml.append("""<div v-show="visibility.${refDataframeName}" max-width="${getMaxWidth(onClickMap)}">""")
            resultPageHtml.append("""<component :is='${refDataframeName}_comp' ref='${refDataframeName}_ref' :${refDataframeName}_prop="${refDataframeName}_data" v-bind:refreshGrid="true"></component>""")
            resultPageHtml.append("""</div>""")
        }

        //Add computed and watch scripts for dialog box
        dataframe.getVueJsBuilder().addToDataScript("${refDataframeName}_comp: '',\n")

        return resultPageHtml.toString()
    }

    private String getEditJavascript(Map onClickMap, DataframeVue dataframe, String fldName, Map fieldProps){
        return getShowDetailJavascript(onClickMap, dataframe, fldName, fieldProps)
    }

    private String getShowDetailJavascript(Map onClickMap, DataframeVue dataframe,  String fldName, Map fieldProps){
        String parentDataframeName = dataframe.dataframeName
        String updateStoreCallScript = ""
        if(!onClickMap.refDataframe){
            return ""
        }
        DataframeVue refDataframe = getReferenceDataframe(onClickMap.refDataframe)
        String refDataframeName = refDataframe.dataframeName
        boolean refreshInitialData = onClickMap.refreshInitialData ?:true
        return """
                              this.${refDataframeName}_comp = "${refDataframeName}";
                              var key = dataRecord.id?dataRecord.id:(dataRecord.Id|dataRecord.ID);
                              let gridState = ${excon}.getFromStore('${parentDataframeName}');
                              gridState.${fieldProps.name} = {'selectedRow': dataRecord}; 
                              ${excon}.saveToStore('${parentDataframeName}', gridState);
                              ${excon}.saveToStore('${parentDataframeName}', '${fieldProps.name}_selectedrow', dataRecord);
                              ${excon}.setVisibility("${refDataframeName}", true);
                              let propData = this.${refDataframeName}_data;
                              propData['key']=  key;
                              propData['parentData'] = dataRecord;
                              propData['refreshInitialData'] = ${refreshInitialData?'Math.random()':'8.0'};
                              Vue.set(this.${refDataframeName}_data, propData);
                    \n 
                    """
    }
    /**
     * send 'fieldType'(persisters or transits) from buttonMaps (from descriptor) to delete grid data.
     * for example check delete button used in grid of  'vueGridOfTranslatedTextDataframe' in resourceTranslationAssistantVue.groovy
     * @param buttonMaps
     * @param fldName
     * @param parentDataframeName
     * @param fieldProps
     * @return
     */
    private String constructGridDeleteScript(Map buttonMaps,  String fldName, String parentDataframeName, Map fieldProps){
        DataframeVue buttonRefDataframe = getReferenceDataframe(buttonMaps.refDataframe)
        String valueMember =buttonMaps.valueMember?:"id"
        String doBeforeDelete = buttonMaps.doBeforeDelete?:""
        String doAfterDelete = buttonMaps.doAfterDelete?:""
        String fieldType = getFieldType(fieldProps)
        getFieldJSONNameVue(fieldProps)
        StringBuilder requestFieldParams = new StringBuilder()
        List<String> keyFieldNames = buttonRefDataframe.getKeyFieldNameForNamedParameter(buttonRefDataframe)

        requestFieldParams.append("params['dataframe'] = '$buttonRefDataframe.dataframeName';\n")
        requestFieldParams.append("params['parentDataframe'] = '$parentDataframeName';\n")
        requestFieldParams.append("params['fieldName'] = '${fieldProps.name}';\n")
        requestFieldParams.append("params['id'] = dataRecord.id?dataRecord.id:dataRecord.Id;")
        keyFieldNames.each {
            if (it.split('_').collect().contains(valueMember)){
                if(valueMember.equalsIgnoreCase("id")){

                    requestFieldParams.append("\nparams['").append(it).append("'] = params['id'];\n");
                }else{

                    requestFieldParams.append("\nparams['").append(it).append("'] = dataRecord.").append(valueMember).append(";\n");
                }
            }

        }
        String confirmMessage = buttonMaps.message?:"Are you sure ?"
        String url =  buttonMaps.ajaxDeleteUrl?: ajaxDeleteUrl
        return """
                var params = {};
                var editedIndex = this.state.${fieldType}.${fieldProps.name}.items.indexOf(dataRecord);
                ${requestFieldParams.toString()}
                $doBeforeDelete
                if(dataRecord.$valueMember){
                    if(!confirm("${messageSource.getMessage("delete.confirm.message", null, "delete.confirm.message", LocaleContextHolder.getLocale())}"))return
                    const self = this;
                    excon.callApi('$url', 'post', params).then(function (responseData){
                        if (responseData.data.success){
                            self.state.${fieldType}.${fieldProps.name}.items.splice(editedIndex, 1);
                        }
                        $doAfterDelete
                    })
                        .catch(function (error) {
                            console.log(error);
                        });
                } else {
            
                            this.state.${fieldType}.${fieldProps.name}.items.splice(editedIndex, 1);
                }
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

    private void addClassesToHeader(Map field, StringBuilder headerClass, String headerText){
        Map addClassesToHeader = field.addClassesToHeader
        if(addClassesToHeader){
            String fieldName = field.name
            if(addClassesToHeader.containsKey(headerText)){
                headerClass.append(addClassesToHeader.get(headerText)+" ")
            }
        }
    }
}

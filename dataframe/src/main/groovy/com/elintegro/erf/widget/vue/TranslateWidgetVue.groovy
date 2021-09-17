package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.vue.VueStore
import org.apache.commons.lang.WordUtils
import org.springframework.context.i18n.LocaleContextHolder
/**
 * Use state management for translation
 * Note: Please use OnSelect method of combobox to assign sourceLanguage, sourceText, targetLanguage
 *       to the state of the respective dataframe you are using and also you can do various other custom things yourself here
 *Note: For translation button works by itself i.e it brings translated data back to you
 *      but you need to provide doAfterTranslation script which means what to do after translation.
 *      Also here you can provide your own custom url for translating the data to you.
 *Note: If you want your own script for what happens in translation then please use translateScript section in descriptor.
 *Note: For Undo functionality provide your own script in undoScript  section in descriptor
 *     **/
class TranslateWidgetVue extends CollectionWidgetVue {

    public String translateUrl = "vendor/translateWithGoogle"
    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        def fldNameDefault = WordUtils.capitalizeFully(fldName);
        String labelCombobox = field.labelCombobox?:fldName
        String translateButtonLabel = field.translateButtonLabel?:fldName
        String undoButtonLabel = field.undoButtonLabel?:fldName
        String comboboxLabel = getLabels(labelCombobox,fldNameDefault)
        String translateLabel = getLabels(translateButtonLabel,fldNameDefault)
        String undoLabel = getLabels(undoButtonLabel,fldNameDefault)
        String html = getHtmlStructure(dataframe, field, fldName, comboboxLabel, translateLabel,undoLabel)
        return html
    }

    Map getStateDataVariablesMap(DataframeVue dataframe, Map field){
        Map result = generateInitialData(dataframe, field)

        String valueMember = field.valueMember?:"id"
        List keys=[]
        List res
        Map selMap = [:]
        selMap.put(valueMember,'')
        if(result && result.size() > 0){
            keys = result.keys
            res = result.result
            selMap = result.selectedMap
        }else{
            log.error("This data is empty, please check data is provided in underliing database or enum class or other datasource. Check descriptor of Dataframe ${dataframe.dataframeName}")
        }

        Map domainFieldMap = dataframe.domainFieldMap
        Map fldJSON = getDomainFieldJsonMap(dataframe, field)
        fldJSON?.put("items", res)

        return domainFieldMap
    }

    private String getHtmlStructure(DataframeVue dataframe, Map field, String fldName,String comboboxLabel, String translateLabel,String undoLabel){
        String colSpacing = field.colSpacing?:"pa-0"
        boolean showTranslateButton = field.showTranslateButton?:false
        VueStore store = dataframe.getVueJsBuilder().getVueStore()
        store.addToState("${dataframe.dataframeName}",[
                showTranslateButton:false,
                showUndoButton:false,
                sourceLanguage:'',
                targetLanguage:'',
                sourceText:'',
                translatedText:'',
        ])
        //For Combobox
        String onSelect = ""
        if(field.onSelect && field.onSelect.methodScript ){
            onSelect = " @change='${dataframe.dataframeName}_onSelect_$fldName' "
            dataframe.getVueJsBuilder().addToMethodScript("""${dataframe.dataframeName}_onSelect_$fldName: function(_params){
                            $field.onSelect.methodScript
                            this.state.${dataframe.dataframeName}.showTranslateButton = $showTranslateButton;
             },\n """)
        }
        boolean isReadOnly = dataframe.isReadOnly(field)
        String typeString = ""
        if(!isSearchable(field)){
            typeString = """type="button" """
        }
        String multiple = field.multiple?"multiple":''
        String displayMember = field.displayMember?:'name'
        String valueMember = field.valueMember?:'id'
        String itemsStr = getFieldJSONItems(field)
        String modelString = getFieldJSONModelNameVue(field)

        //For Buttons
        String refHtml = ""
        def onButtonClick = field.onButtonClick
        if(onButtonClick && field.get('onButtonClick')){
            if(onButtonClick.refDataframe){
                refHtml = getRefHtml(onButtonClick as Map, dataframe)
            }
        }
        addMethodsToTranslateScript(dataframe, field)
        addMethodsToUndoScript(dataframe, field)

        return """
            <v-row class='ma-0'>
                <v-col cols='6' class='$colSpacing'>
                    <v-combobox
                          v-model = "${modelString}"  
                          :items="${itemsStr}"
                          ${validate(field)?":rules = '${fldName}_rule'":""}
                          label="$comboboxLabel"
                          ${isDisabled(dataframe, field)?":disabled=true":""}
                          item-text="${displayMember}"
                          item-value="${valueMember}"
                          $multiple
                          hide-no-data
                          hide-selected
                          ${isReadOnly?"readonly":''}
                          ${toolTip(field)}
                          $onSelect
                          $typeString
                          ${getAttrForCombobox(field)}
                          ${fillProps(field)}
                    ></v-combobox>
                </v-col>
                <v-col cols='6' class='$colSpacing'>
                    $refHtml<v-btn ${getAttrForTranslateButton(field)} @click.stop='${fldName}_translate_method' v-show='state.${dataframe.dataframeName}.showTranslateButton'>${translateLabel}</v-btn>
                </v-col>
            </v-row>
            <v-row class='ma-0'>
                <v-col cols='6' class='$colSpacing'>
                    <v-btn ${getAttrForUndoButton(field)} @click.stop='${fldName}_undo_method' v-show='state.${dataframe.dataframeName}.showUndoButton'>${undoLabel}</v-btn>
                </v-col>
            </v-row>
        """
    }
    protected String getAttrForCombobox(Map field){
        return field.getAttrForCombobox?:""
    }
    protected String getAttrForTranslateButton(Map field){
        return field.getAttrForTranslateButton?:""
    }
    protected String getAttrForUndoButton(Map field){
        return field.getAttrForUndoButton?:""
    }
    private String getRefHtml(Map onButtonClick, DataframeVue dataframe){
        StringBuilder sb = new StringBuilder()
        DataframeVue refDataframe = DataframeVue.getDataframeBeanFromReference(onButtonClick.refDataframe)
        String refDataframeName = refDataframe.dataframeName
        String dialogAttrs = onButtonClick.dialogAttrs?:""
        VueStore store = dataframe.getVueJsBuilder().getVueStore()
        store.addToDataframeVisibilityMap("${refDataframeName} : false,\n")
        dataframe.getVueJsBuilder().addToDataScript(" ${refDataframeName}_data:{key:'', refreshGrid:true},\n")
        if(onButtonClick.showAsDialog){
            sb.append("""<v-dialog v-model="visibility.${refDataframeName}" ${dialogAttrs}><v-card>""")
            sb.append(refDataframe.getComponentName("resetForm=true "))
            sb.append("""</v-card></v-dialog>""")
        } else {
            sb.append(refDataframe.getComponentName("resetForm=true"))
        }

        return sb.toString()
    }
    private void addMethodsToTranslateScript(DataframeVue dataframe, Map fieldProps){
        String fldName = getFieldName(dataframe, fieldProps)
        String customTranslatedScript = translatedScript(dataframe, fieldProps)
        String translateScript = fieldProps.translateScript?:customTranslatedScript
        dataframe.getVueJsBuilder().addToMethodScript("""  
                               ${fldName}_translate_method: function(){
                                        $translateScript
                               },\n""")
    }
    private void addMethodsToUndoScript(DataframeVue dataframe, Map fieldProps){
        String fldName = getFieldName(dataframe, fieldProps)
        String undoScript = fieldProps.undoScript?:"console.log('please put script for undo function manually');"
        dataframe.getVueJsBuilder().addToMethodScript("""  
                               ${fldName}_undo_method: function(){
                                        $undoScript
                               },\n""")
    }
    String getVueDataVariable(DataframeVue dataframe, Map field) {
        String dataVariable = dataframe.getDataVariableForVue(field)
        return """ ${dataVariable}_showTranslateButton:false,\n 
                   ${dataVariable}_showUndoButton:false,\n 
               """
    }
    def getLabels(String label, def fldNameDefault){
        String labels =  getMessageSource().getMessage(label, null, fldNameDefault, LocaleContextHolder.getLocale())
        return labels
    }
    private def translatedScript(DataframeVue dataframe, Map field) {
        if (field?.translateUrl){
            translateUrl = field.translateUrl
        }
        String customDoAfterTranslation = """
                                                self.state.${dataframe.dataframeName}.translatedText = response.data.translatedText;
                                                console.log(response.data);\n 
                                                this.state.${dataframe.dataframeName}.showUndoButton = true;\n
                                          """
        String doAfterTranslation = field.doAfterTranslation?:customDoAfterTranslation

        return """
            let self = this;
            let params = this.state;
            params['langFrom'] = this.state.${dataframe.dataframeName}.sourceLanguage;
            params['text'] = this.state.${dataframe.dataframeName}.sourceText;
            params['langTo'] = this.state.${dataframe.dataframeName}.targetLanguage;
            excon.callApi('$translateUrl', 'post', params).then(function(responseData){
                let response = responseData.data;
                if(response.success){
                    self.state.${dataframe.dataframeName}.translatedText = response.data.translatedText;
                    $doAfterTranslation
                }
                else{
                    excon.showAlertMessage(response);
                }
            });
            """
    }
}

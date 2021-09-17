package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.DataframeInstance
import com.elintegro.erf.dataframe.vue.DataframeVue
import org.grails.web.json.JSONObject
import org.springframework.context.i18n.LocaleContextHolder

/**
 * Created by kchapagain on Jun, 2020.
 */
class TimeWidgetVue extends WidgetVue{

    //Todo:this widget needs to modify with new data structure
    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        boolean isReadOnly = dataframe.isReadOnly(field)
        String locale = field.locale?:"he"
        String localeString = locale?"locale='$locale'":""
        String dateFormatPlaceholder = getMessageSource().getMessage("date.format.hint", null, "date.format.hint", LocaleContextHolder.getLocale())
        String menuAttr = field.menuAttr?:""

//        String modelString = getModelString(dataframe, field)
        String modelString = getFieldJSONModelNameVue(field)
        return """
                    <v-dialog
                                        ref="${fldName}_dialog"
                                        v-model="${fldName}_dialog"
                                        :return-value.sync="$modelString"
                                        persistent
                                        width="290px"
                                        $menuAttr
                                >
                                    <template v-slot:activator="{ on }">
                                        <v-text-field
                                                v-model="$modelString"
                                                label="${getLabel(field)}"
                                                prepend-icon="access_time"
                                                readonly
                                                v-on="on"
                                                ${fillProps(field)}
                                        ></v-text-field>
                                    </template>
                                    <v-time-picker
                                            v-if="${fldName}_dialog"
                                            v-model="$modelString"
                                            full-width
                                            $localeString
                                            ${isReadOnly?"readonly":''}
                                    >
                                        <v-spacer></v-spacer>
                                        <v-btn text color="primary" @click="${fldName}_dialog = false">Cancel</v-btn>
                                        <v-btn text color="primary" @click="\$refs.${fldName}_dialog.save($modelString)">Yes</v-btn>
                                    </v-time-picker>
                     </v-dialog>
                """
    }

    String getVueDataVariable(DataframeVue dataframe, Map field){

        String dataVariable = dataframe.getDataVariableForVue(field)
        return """${dataVariable}_dialog:false,\n"""
    }

    boolean setPersistedValueToResponse(JSONObject jData, def value, String domainAlias, String fieldName, Map additionalDataRequestParamMap, DataframeInstance dfInstance, Object sessionHibernate, Map fieldProps){
        if(value.hour && value.minute)
            jData?.persisters?."${domainAlias}"."${fieldName}".value = value.hour + ":"+value.minute
        else
            super.setPersistedValueToResponse(jData, value, domainAlias, fieldName, additionalDataRequestParamMap, dfInstance, sessionHibernate, fieldProps)
    }

    boolean setTransientValueToResponse(JSONObject jData, def value, String domainAlias, String fieldName, Map additionalDataRequestParamMap, DataframeInstance dfInstance, Object sessionHibernate, Map fieldProps){
        if(value.hour && value.minute)
            jData?.persisters?."${domainAlias}"."${fieldName}".value = value.hour + ":"+value.minute
        else
            super.setTransientValueToResponse(jData, value, domainAlias, fieldName, additionalDataRequestParamMap, dfInstance, sessionHibernate, fieldProps)
    }
}
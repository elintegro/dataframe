package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.vue.DataframeVue

/**
 * Created by kchapagain on Jun, 2020.
 */
class TimeWidgetVue extends WidgetVue{

    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        boolean isReadOnly = dataframe.isReadOnly(field)
        String locale = field.locale?:"he"
        String localeString = locale?"locale='$locale'":""
        String menuAttr = field.menuAttr?:""

        String modelString = getModelString(dataframe, field)
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
                                                :label="${getLabel(field)}"
                                                prepend-icon="access_time"
                                                readonly
                                                v-on="on"
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
}
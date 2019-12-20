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
import com.elintegro.erf.dataframe.DbResult
import com.elintegro.erf.dataframe.ParsedHql

/**
 * Created by kchapagain on Nov, 2018.
 */
class MultiSelectComboboxVue extends WidgetVue {

    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = dataframe.getDataVariableForVue(field)
        boolean isReadOnly = dataframe.isReadOnly(field)
        String label = field.label
        String validate = field?.validate
        def disabled = field.disabled == null? false : field.disabled
        def search = field?.search
        String displayMember = field.displayMember
        return """
            <v-combobox
          v-model="$fldName"
          :items="${fldName}_items"
          ${validate?":rules = '${fldName}_rule'":""}
          label="$label"
          ${disabled?":readonly":""}
          ${search?"::search-input.sync= '${fldName}_search'":""}
          multiple
          item-text="${displayMember}"
          small-chips
          hide-selected
          ${isReadOnly?"readonly":''}
          ${toolTip(field)}  
        ></v-combobox>
        """
    }

    String getVueDataVariable(DataframeVue dataframe, Map field) {
        String dataVariable = dataframe.getDataVariableForVue(field)
        def search = field?.search
        return """$dataVariable:\"\",\n
                  ${dataVariable}_items:[],\n
                  ${search?"${dataVariable}_search:null,\n":""}"""

    }

    String getValueSetter(DataframeVue dataframe, Map field, String divId, String dataVariable, String key) throws DataframeException{
        String fullFieldName = key.replace(Dataframe.DOT,Dataframe.DASH)
        if(!field){
            throw new DataframeException(" Fields are empty for the Dataframe :${dataframe.dataframeName}")
        }
        if(!(field.hql)){
            throw new DataframeException(" Hql field is empty for the Dataframe :${dataframe.dataframeName}")
        }
        return """
               var fullFieldName = '$fullFieldName';
               var data = response.additionalData[fullFieldName]['dictionary'];
               var selectedData = response.additionalData[fullFieldName]['selectedData'];
               this.$dataVariable = selectedData;
               this.${dataVariable}_items = data;
              """
    }

    @Override
    String getValueScript(DataframeVue dataframe, Map field, String divId, String fldId, String key) {
        String valueMember = field.valueMember
        String dataVariable = dataframe.getDataVariableForVue(field)
        String thisFieldName = dataframe.getFieldId(field)
        return """allParams["$thisFieldName"] = drfExtCont.mapStringify(this.$dataVariable, '$valueMember'),\n"""
    }

    String getVueSaveVariables(DataframeVue dataframe, Map field) {
        String valueMember = field.valueMember?:"id"
        String dataVariable = dataframe.getDataVariableForVue(field)
        String thisFieldName = dataframe.getFieldId(field)
        return """allParams['$thisFieldName'] = drfExtCont.mapStringify(this.$dataVariable, '$valueMember'); \n"""
    }

    public Map loadAdditionalData(DataframeInstance dfInst, String fieldnameToReload, Map inputData, def session){
        Map result=[:]
        Dataframe df = dfInst.df;
        Map fieldProps = df.fields.get(fieldnameToReload)

        String wdgHql = fieldProps?.hql
        ParsedHql parsedHql = new ParsedHql(wdgHql, df.grailsApplication, df.sessionFactory);

        if(wdgHql){
            DbResult dbRes = new DbResult(wdgHql, inputData, session, parsedHql);
            List resultList = dbRes.getResultList()
            fieldProps["dictionary"] = resultList
            result = buildData(dfInst, inputData, fieldProps, fieldnameToReload)
        }
        return result
    }

    private static def buildData(DataframeInstance dfInst, Map inputData, Map fieldProps, String fieldnameToReload){
        def dictionary = fieldProps.dictionary
        def selectedValue = null
        Dataframe df = dfInst.df
        String simpleFieldName = ""
        String domainAlias= Dataframe.getDataFrameDomainAlias(fieldnameToReload)
        def domainInstance = null
        boolean isParameterRelatedToDomain = false
        def queryDomain = null
        if (domainAlias && df.writableDomains) {
            Map domain = df.writableDomains.get(domainAlias)
            queryDomain = dfInst.getPersistentEntityFromDomainMap(domain)
            Map keysNamesAndValue = [:];
            dfInst.getKeysNamesAndValueForPk(keysNamesAndValue, domain, inputData);
//            keysNamesAndValue = dfInst.getKeysAndValues(domain);
            if (!keysNamesAndValue.isEmpty()){
                domainInstance = dfInst.retrieveDomainInstanceForUpdate(keysNamesAndValue, queryDomain);
            }
            simpleFieldName = Dataframe.extractSimpleFieldName(df.dataframeName,fieldnameToReload,domainAlias)
            isParameterRelatedToDomain = dfInst.isParameterRelatedToDomain(queryDomain, domainAlias, simpleFieldName)
        }
        if (simpleFieldName && isParameterRelatedToDomain){
            def prop = queryDomain.getPropertyByName(simpleFieldName)
            def refFieldValues = domainInstance?domainInstance."$simpleFieldName":null
            if (refFieldValues && df.metaFieldService.isAssociation(prop)){
                String valueMember = fieldProps.valueMember
                String displayMember = fieldProps.displayMember
                def selectedValList = []
                refFieldValues.each{obj ->
                    def selMap= [:]
                    selMap.put(valueMember, obj."$valueMember")
                    selMap.put(displayMember, obj."$displayMember")
                    selectedValList.add(selMap)
                }

                selectedValue = selectedValList
            }
        }
        return [dictionary:dictionary, selectedData:selectedValue]
    }


}

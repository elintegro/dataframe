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
import com.elintegro.erf.dataframe.vue.DataframeVue

/**
 * Created by kchapagain on Jul, 2019.
 */
class CountDisplayVue extends WidgetVue{

    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        if(field.hide && field.hide == true){
            return ""
        }
        String fldName = getFieldName(dataframe, field)
        boolean isReadOnly = dataframe.isReadOnly(field)
        String autoComplete = field.autoComplete?:'off'

        String html = """<v-text-field
            label="${getLabel(field)}"
            v-model = "${getModelString(dataframe, field)}" 
            ${isDisabled(dataframe, field)?":disabled = true":""}
            ${isReadOnly?"readonly":''}
            ${toolTip(field)}
            style="width:${getWidth(field)}; height:${getHeight(field, "30px")};"
            background-color="white"
            autocomplete = $autoComplete
            ${getAttr(field)}
          ></v-text-field>"""
        if(field?.layout){
            html = applyLayout(dataframe, field, html)
        }
        return html
    }

    String getValueSetter(DataframeVue dataframe, Map field, String divId, String dataVariable, String key) throws DataframeException{
        String fullFieldName = key.replace(Dataframe.DOT,Dataframe.DASH)
        return """
               var fullFieldName = '$fullFieldName';
               var countData = response.additionalData[fullFieldName]['count'];
               this.$dataVariable = countData;
              """
    }

    public Map loadAdditionalData(DataframeInstance dfInst, String fieldnameToReload, Map inputData, def session){
        Map result=[:]
        Dataframe df = dfInst.df;
        Map fieldProps = df.fields.get(fieldnameToReload)

        String wdgHql = fieldProps?.hql
        ParsedHql parsedHql = new ParsedHql(wdgHql, df.grailsApplication, df.sessionFactory);
        int count = 0
        if(wdgHql){
            DbResult dbRes = new DbResult(wdgHql, inputData, session, parsedHql);
            List resultList = dbRes.getResultList()
            count = resultList.size()
        }
        return ['count': count]
    }
}

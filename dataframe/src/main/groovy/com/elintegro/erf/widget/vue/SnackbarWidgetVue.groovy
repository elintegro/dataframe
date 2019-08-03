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

import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.vue.VueJsBuilder
import com.elintegro.erf.dataframe.vue.VueStore

/**
 * Created by kchapagain on Jan, 2019.
 */
class SnackbarWidgetVue extends WidgetVue{

    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        return """
      <v-snackbar
        v-model="alertProp.snackbar"
        :bottom="y === 'bottom'"
        :left="x === 'left'"
        :multi-line="mode === 'multi-line'"
        :right="x === 'right'"
        :timeout="timeout"
        :top="y === 'top'"
        :color="alertProp.alert_type"
        :vertical="mode === 'vertical'"
      >
        {{ alertProp.alert_message }}
        <v-spacer></v-spacer>
        <v-btn
          dark
          flat
          @click="alertProp.snackbar = false"
        >
          <v-icon medium >close</v-icon>
        </v-btn>
      </v-snackbar>
                """
    }

    String getVueDataVariable(DataframeVue dataframe, Map field) {
        String y = field?.y?:"top"
        String x = field?.x?:"right"
        def timeout = field?.timeout?:6000
        dataframe.getVueJsBuilder().addToComputedScript("""
              alertProp(){
               return this.\$store.state.${dataframe.dataframeName}.alertProp;
           },\n
        """)
        VueStore store = dataframe.getVueJsBuilder().getVueStore()
        store.addToState("""
             alertProp:{
                snackbar: false,
                alert_type: '',
                alert_message: ''
            },\n
        """)
        store.addToMutation("""
            alertMessage(state, payload) {  
                state.${dataframe.dataframeName}.alertProp.snackbar = payload.snackbar;
                state.${dataframe.dataframeName}.alertProp.alert_type = payload.alert_type;
                state.${dataframe.dataframeName}.alertProp.alert_message = payload.alert_message;
            },\n
        """)
        return """
            y: '$y',
            x: '$x',
            mode: '',
            timeout: $timeout,
                """

    }
}

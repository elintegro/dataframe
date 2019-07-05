/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.dataframe.vue

class VueStore {

    private StringBuilder state = null
    private StringBuilder mutation = null
    private StringBuilder showHideParamNames = new StringBuilder()

    VueStore(){
        state = new StringBuilder()
        mutation = new StringBuilder()
    }

//    StringBuilder state = new StringBuilder()
//    StringBuilder mutation = new StringBuilder()
    StringBuilder getter = new StringBuilder()

    String getState(){
        return state.toString()
    }

    void addToState(String value){
        state.append(value)
    }

    String getShowHideParamNames(){

        return showHideParamNames.toString()
    }

    void addToShowHideParamNames(String value){
        showHideParamNames.append(value)
    }

    public String getGlobalState(){
        StringBuilder sbb = new StringBuilder()
        sbb.append(showHideParamNames.toString())

        return sbb.toString()
    }

    String buildState(dataframeName){
        if(state.length() == 0){
            return ""
        }
        StringBuilder sbb = new StringBuilder()
        sbb.append("""$dataframeName: {\n""")
        sbb.append(state.toString())
        sbb.append("""},\n""")

        return sbb.toString()
    }

    String getMutation(){
        return mutation.toString()
    }

    void addToMutation(String value){
        mutation.append(value)
    }

    /*String buildMutation(dataframeName){
        if(mutation.length() == 0){
            return ""
        }
        StringBuilder sbb = new StringBuilder()
        sbb.append("""$dataframeName: {\n""")
        sbb.append(mutation.toString())
        sbb.append("""},\n""")

        return sbb.toString()
    }*/

}

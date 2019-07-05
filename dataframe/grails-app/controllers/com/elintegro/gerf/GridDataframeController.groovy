/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.gerf

import com.elintegro.erf.dataframe.service.GridDataFrame
import grails.converters.JSON
import grails.core.GrailsApplication
import grails.util.Holders

//import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.springframework.context.ApplicationContext

class GridDataframeController {
    GrailsApplication grailsApplication
    ApplicationContext ctx
    private void initCtx(){
        if(!ctx) ctx = (ApplicationContext)/*ApplicationHolder.getApplication()*/Holders.grailsApplication.getMainContext();
    }


    private GridDataFrame getGridDataframe(params){
        initCtx()
        GridDataFrame gridDataframe = (GridDataFrame) ctx.getBean(params.dataframe)
        return gridDataframe
    }


    /**
     * This method uses the parameter in the given hql of Dataframe object and retrieve the corresponding
     * values from database. If there is no match for the parameter then a blank list will be return other
     * wise the result list will return and render as json on this following method.
     * @return
     */
    def ajaxValues(){
        def dataframe = params.dataframe
        def paramz = params
        def keys = params.keys
        if (dataframe=="participantsGrid"){
            GridDataFrame gridDataframe = getGridDataframe(params)
            // TODO get the hql from gridDataframe
            // TODO fetch the data set using the keys
            // TODO convert them to a jsonMap in a format like the following example:
            /*
            *  def jsonMap = ["data": [
                                [firstName:"Shai", lastName:"Lipkovich"] ,
                                [firstName:"Abani", lastName:"Patra"]
                               ]
            * */
        }
        def jsonMap = ["data": [
                                [firstName:"Shai", lastName:"Lipkovich"] ,
                                [firstName:"Abani", lastName:"Patra"]
                               ]

        ]

        //= dataframe.readAndGetJson(params)

        render jsonMap as JSON
    }



}

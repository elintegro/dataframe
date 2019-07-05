/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro

import org.joda.time.DateTimeZone

class GenerateBillJob {

    def billService

    //for server time configuration
    static {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        DateTimeZone.setDefault(DateTimeZone.forID("GMT"));
    }

    static triggers = {
//        simple repeatInterval: 5000l // execute job once in 5 seconds
//        simple repeatInterval: 5*60*1000 // execute job once in 5 minute
        simple repeatInterval: 12*60*60*1000 // execute job once in 12 hrs
    }

    def execute() {
        // execute job
        println "generate bill job executed"
        billService.generateContractBill()
    }
}

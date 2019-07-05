/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.elintegrostartapp.property

import com.elintegro.gc.commonfield.ControlField
import com.elintegro.ref.Address
import com.elintegro.elintegrostartapp.Facility
import com.elintegro.elintegrostartapp.ref.PropertyType

class Property extends ControlField{

    //Facility owner
    String description
    String mainPicture
    static 	hasMany = [units:Unit, images:com.elintegro.elintegrostartapp.Image]
    //static belongsTo = [facility: Facility]
    int numberUnits
    PropertyType propertyType
    Address address
    Date expDate
    String zillow
    boolean isActiveProperty = true





    static constraints = {
        mainPicture(nullable:true)
        //numberUnits(nullable:true)
        propertyType(	nullable:true)
        //address(nullable:true)
        expDate(nullable:true)
        isActiveProperty (nullable: true)
        zillow(nullable: true)
    }

    String toString(){
        return description //TODO: put more meaningfull expression here
    }
}


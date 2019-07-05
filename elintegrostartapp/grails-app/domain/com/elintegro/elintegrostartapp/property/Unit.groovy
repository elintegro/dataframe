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
import com.elintegro.elintegrostartapp.client.Client

class Unit extends ControlField{

    String name
    String description
    String mainPicture
    Short bedrooms = 1
    Short bathrooms = 1
    Short kitchens = 1
    Short balconies = 0
    Short floor = 1
    Double area = 0.0D
    Date expDate
    Date lastUpdated

    static hasMany = [client:Client]
    static belongsTo = [property: Property]

    static constraints = {
        mainPicture(nullable:true)
        name(nullable:true)
        bedrooms(nullable:true, size : 0..2, scale : 0)
        bathrooms(nullable:true, size : 0..2, scale : 0)
        kitchens(nullable:true, size : 0..2, scale : 0, maxSize : 2)
        balconies(nullable:true, size : 0..2, scale : 0)
        floor(nullable:true, size : 0..3, scale : 0)
        expDate(nullable:true)
        area(nullable: true)
    }

    String toString(){
        return description //TODO: put more meaningfull expression here
    }
}

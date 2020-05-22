/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.crm

import com.elintegro.auth.User
import com.elintegro.gc.commonfield.ControlField
import com.elintegro.ref.Address
import com.elintegro.ref.Language

class Person extends ControlField{
    String email
	String firstName
	String lastName
	String salutation
	String midName
	String description
	Date bday // TODO NOTE: Date object appear as a datetime type in mysql, which makes creates a strange error when retrieving an SQL (something about not being able to convert the field to a TIMESTAMP). The TODO is to investiagte and fix.
	String phone
//	Gender gender
	Address mainAddress
	Person mainContactPerson
	String mainPicture
    static 	hasMany = [addresses:Address, phones:Phone, languages:Language]
	static belongsTo = [user:User]
	
	static mapping = {
		addresses lazy: false
		phones lazy: false
		languages lazy: false
	}

    static constraints = {
		email(nullable:false)
		firstName(nullable: false, size: 2..30)
        lastName (nullable: false, size: 2..30)
		salutation(nullable:true, size: 2..6)
		midName(nullable:true, size: 2..30)
		description(nullable:true)
		bday(nullable:true)
		phone(nullable:true, size: 10..15)
        mainAddress(nullable:true)
		mainContactPerson(nullable:true)
		mainPicture(nullable:true)
		phones(nullable:true)
		addresses(nullable:true)		
    }

    String getEmail(){
        //return user?.username?:email
        email
    }

	@Override
	String toString(){
		return firstName + " " + lastName+" email: "+email
	}
}
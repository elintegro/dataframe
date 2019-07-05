/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.auth

import com.elintegro.crm.Person
import com.elintegro.gc.commonfield.ControlField
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class User extends ControlField implements Serializable {

    private static final long serialVersionUID = 1

    String username
    String password
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    String  email
    String 	firstName
    String 	lastName
    boolean guestUser = false
    boolean admin = false

    Set<Role> getAuthorities() {
        (UserRole.findAllByUser(this) as List<UserRole>)*.role as Set<Role>
    }

    static constraints = {
        password nullable: false, blank: false, password: true
        username blank: false, unique: true
    }

    static mapping = {
	    password column: '`password`'
    }

    def beforeInsert() {
        if(username==email || !username){
        setUsername(email)
        }
    }

    def beforeUpdate() {

    }

    public User(){
        accountLocked = true
        enabled = true
    }

    String getFullname(){
        return this.firstName?:""+this.lastName?:""
    }
    static hasOne = [person:Person]
    static hasMany = [oAuthIDs: OAuthID]
    /**
     * Retrieves the person to which this user belongs
     * */
    Person getPerson(){
        return Person.findByUser(this)
    }

    @Override
    String toString(){
        return this.username
    }
}

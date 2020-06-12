/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.elintegrostartapp.client

import com.elintegro.auth.User
import com.elintegro.crm.Person
import com.elintegro.elintegrostartapp.hr.Images
import com.elintegro.elintegrostartapp.hr.Position
import com.elintegro.elintegrostartapp.hr.Skills
import com.elintegro.elintegrostartapp.property.Unit
import com.elintegro.gc.commonfield.ControlField
import com.elintegro.elintegrostartapp.Facility
import com.elintegro.elintegrostartapp.ref.ApplicationStatus

class Application extends ControlField{

    User user
    Date applicationDate
    String signedBy
    ApplicationStatus status
    Date waitingExpirationDate
    String referredByPerson
    String referredByOrganisation
    String linkedin
    String resume


    Person applicant
    MedicalRecord medicalRecord

    Facility facility

    String question1
    String question2

    String comments
    String lastComment

    static 	hasMany = [contacts: Person, availablePositions: Position, skills: Skills,avatar: Images]

    static constraints = {
        applicant (nullable: true)
        user (nullable: true)
        applicationDate (nullable: true)
        signedBy (nullable: true)
        status (nullable: true)
        waitingExpirationDate (nullable: true)
        referredByPerson (nullable: true)
        referredByOrganisation(nullable: true)
        facility(nullable: true)
        linkedin(nullable: true)
        resume(nullable: true)
        avatar(nullable: true)
        question1(nullable: true,length: 4096)
        question2(nullable: true,length: 4096)
        comments(nullable: true,length: 4096)
        lastComment(nullable: true,length:4096)
    }
}

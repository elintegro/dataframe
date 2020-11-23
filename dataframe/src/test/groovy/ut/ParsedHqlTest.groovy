/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package ut

import com.elintegro.erf.dataframe.ParsedHql
import org.junit.Test

import java.util.regex.Pattern


class ParsedHqlTest extends GroovyTestCase{

    void testGetReplacedJoinString(){
        String hql1 = "Contract as contract where contract.id=:id"
        String hql2 = "Tenant tenant, Contract contract left join tenant.units unit where unit.id=:id  and contract.tenant = tenant.id"
        String hql3 ="Tenant tenant join Contract contract ON contract.id = tenat.contract join Contract1 contract1 ON contract1.id = contract.id left join tenant.units unit where unit.id=:id and contract.tenant = tenant.id"
        String hql4 ="Owner owner inner join owner.users as user where user.id =:session_userid and (owner.expDate is null or owner.expDate > date(:now))"
        String hql5 ="Tenant tenant join Contract contract ON contract.id = tenat.contract join Contract1 contract1 ON contract1.id = contract.id"
        String hql6 ="Tenant tenant inner join Contract contract with contract.id = tenat.contract"

        ParsedHql parsedHql = new ParsedHql()
        String returnedString1 = parsedHql.getReplacedJoinString(hql1)
        String returnedString2 = parsedHql.getReplacedJoinString(hql2)
        String returnedString3 = parsedHql.getReplacedJoinString(hql3)
        String returnedString4 = parsedHql.getReplacedJoinString(hql4)
        String returnedString5 = parsedHql.getReplacedJoinString(hql5)
        String returnedString6 = parsedHql.getReplacedJoinString(hql6)
        assertEquals("Unit test for hql string 1",returnedString1, "Contract as contract")
        assertEquals("Unit test for hql string 2",returnedString2, "Tenant tenant, Contract contract, tenant.units unit")
        assertEquals("Unit test for hql string 3",returnedString3, "Tenant tenant, Contract contract, Contract1 contract1, tenant.units unit")
        assertEquals("Unit test for hql string 4",returnedString4, "Owner owner, owner.users as user")
        assertEquals("Unit test for hql string 5",returnedString5, "Tenant tenant, Contract contract, Contract1 contract1")
        assertEquals("Unit test for hql string 6",returnedString6, "Tenant tenant, Contract contract")
    }
}

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

import com.elintegro.erf.dataframe.DomainClassInfo
import com.elintegro.erf.dataframe.db.fields.MetaField
import com.elintegro.erf.payment.base.response.Response
import com.elintegro.utils.MapUtil
import org.grails.testing.GrailsUnitTest
import org.grails.web.json.JSONObject
import org.hibernate.persister.entity.SingleTableEntityPersister
import spock.lang.Shared
import spock.lang.Specification

class NewStructureTest extends Specification implements GrailsUnitTest {

    //@Shared JSONObject apiSampleParameters
    //@Shared DataframeVue dfr
    @Shared Map testMap = [:]
    @Shared DataframeVue dfr

    def setupSpec() {
    }

    def setup() {
        dfr = new DataframeVue()
        dfr.hql = "select person.firstName, person.lastName, person.email,person.phone,application.linkedin from Application application inner join application.applicant person where application.id=:id"


    }

    def cleanup() {
    }

    def "test build State JSON"(){
        given:

        testMap = ["dfrName": [ "namedParams":["id":["domain":"person", "field":"id"]],"domains":["person":["first_name":null, "last_name":null], "application":["linkedIn":""]]] ]
        dfr.init()

        when:

        dfr.parsedHql.namedParameters?.forEach { namedParamKey, namedParamValue ->
            DomainClassInfo dom = parsedHql.hqlDomains.get(namedParamValue[0])
            SingleTableEntityPersister pers =(SingleTableEntityPersister)dom.persister.properties.get("entityPersister")
            String[] propNames = dom.persister.getPropertyNames()


            propNames.forEach{name ->
                String col = dom.persister.getPropertyColumnNames(name)
                println col
            }

            String dbColumnName = dom.persister.getPropertyColumnNames(namedParamValue[1])

            pers.properties
            pers.attributes
            pers.keyColumnNames
            pers.metaPropertyValues
            pers.tableName
            pers.fromTableFragment(pers.tableName)
            pers.entityMetamodel.properties
            def pN1 = pers.entityMetamodel.propertyNames
            def pN2 = pers.entityMetamodel.getPropertyNames()
            dom.persister.getPropertyNames()
        }

        dfr.addNamedParameters()

        String testRes = MapUtil.convertMapToJSONString(dfr.domainFieldMap)

        then:

        testRes == "???"

    }

}
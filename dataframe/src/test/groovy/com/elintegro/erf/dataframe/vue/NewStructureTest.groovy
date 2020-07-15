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

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.DataframeInstance
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
    @Shared DataframeInstance dfInstance

    @Shared DataframeInstance dfrInst
    @Shared JSONObject params_
    @Shared Map params
    boolean result = true

    def setupSpec() {
    }

    def setup() {
        dfr = new DataframeVue()
        dfr.init()
        dfr.hql = "select person.firstName, person.lastName, person.email,person.phone,application.linkedin from Application application inner join application.applicant person where application.id=:id"
        params = [
                "dataframe": "vueNewEmployeeBasicInformationDataframe",
                "vueNewEmployeeBasicInformationDataframe":
                        [
                                "persisters"     : [
                                        "Person"     : [
                                                "firstName": [
                                                        "value": null
                                                ],
                                                "lastName" : [
                                                        "value": null
                                                ],
                                                "phone"    : [
                                                        "value": null
                                                ],
                                                "email"    : [
                                                        "value": null
                                                ]
                                        ],
                                        "application": [
                                                "availablePositions": [
                                                        "value": null,
                                                        "items": [
                                                                [
                                                                        "id"  : 1,
                                                                        "name": "Back-end Java Developer"
                                                                ],
                                                                [
                                                                        "id"  : 2,
                                                                        "name": "Front-end Developer"
                                                                ],
                                                                [
                                                                        "id"  : 3,
                                                                        "name": "Product Owner"
                                                                ],
                                                                [
                                                                        "id"  : 4,
                                                                        "name": "Scrum Master"
                                                                ],
                                                                [
                                                                        "id"  : 5,
                                                                        "name": "Site Adminstrator"
                                                                ],
                                                                [
                                                                        "id"  : 6,
                                                                        "name": "Developer"
                                                                ]
                                                        ]
                                                ],
                                                "linkedin"          : [
                                                        "value": null
                                                ]
                                        ]
                                ],
                                "domain_keys"    : [
                                        "Person"     : [
                                                "id": null
                                        ],
                                        "application": [
                                                "id": null
                                        ]
                                ],
                                "namedParameters": [
                                        "id": [
                                                "domain": "application",
                                                "field" : "id",
                                                "value" : null
                                        ]
                                ]
                        ]
        ]

    }

    def cleanup() {
    }

    def "test build State JSON"(){
        given:

        params_ = new JSONObject(params)
        dfInstance = new DataframeInstance(dfr, params_)

        when:

        result = dfInstance.save(false)

        then:

        result == true

        //testRes == "???"

    }

}
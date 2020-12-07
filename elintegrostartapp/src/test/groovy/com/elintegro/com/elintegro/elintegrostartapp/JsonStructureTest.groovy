/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.com.elintegro.elintegrostartapp

import com.elintegro.crm.Person
import com.elintegro.school.Child
import com.elintegro.school.Parent
import com.elintegro.utils.MapUtil
import grails.converters.JSON
import grails.gorm.transactions.Rollback
import grails.test.mixin.integration.Integration
import org.grails.testing.GrailsUnitTest
import spock.lang.Shared
import spock.lang.Specification

@Integration
@Rollback
class JsonStructureTest extends Specification implements GrailsUnitTest {

    static final String DOMAIN_KEYS = "keys"

    //@Shared JSONObject apiSampleParameters
    //@Shared DataframeVue dfr
    @Shared def personsJson
    @Shared def parentJson


    def setupSpec() {
    }

    def setup() {
        //dfr = new DataframeVue()
        //dfr.hql = "select person.firstName, person.lastName, person.email,person.phone,application.linkedin from Application application inner join application.applicant person where application.id=:id"


    }

    def cleanup() {
    }

    def "test build Map as JSON"(){
        given:
        Map domainFieldMap = [:]
        domainFieldMap.put( DOMAIN_KEYS, [:])


        when:
        Map keys = domainFieldMap.get(DOMAIN_KEYS)
        keys.put("id",[:])

        then:
        assert keys

    }

    def "test build State JSON"(){
        given:

        testDomains()
        def parents = Parent.list()
        def persons = Person.list()

        personsJson = persons as JSON //convert the list into JSON using default method
        parentJson = parents as JSON //convert the list into JSON using default method

        when:

        String testPerson = personsJson.toString(true)
        String testParent = parentJson.toString(true)
        println testParent
        println testPerson

        then:

        testParent == """[
   {
      "children": [],
      "name": "Eugene",
      "id": 1
   },
   {
      "children": [],
      "name": "Eli",
      "id": 2
   },
   {
      "children": [],
      "name": "Elina",
      "id": 3
   }
]"""
        testPerson == """[
   {
      "lastName": "Lipkovich",
      "addresses": [],
      "mainContactPerson": null,
      "languages": [],
      "midName": null,
      "phones": [],
      "description": null,
      "mainPicture": null,
      "updateTime": null,
      "mainAddress": null,
      "expDate": null,
      "firstName": "Eugene",
      "bday": null,
      "phone": null,
      "createTime": "2020-05-18T23:08:04Z",
      "id": 1,
      "salutation": null,
      "user": {"id": 1},
      "email": "eugenelip@gmail.com"
   },
   {
      "lastName": "Lipkovich",
      "addresses": [],
      "mainContactPerson": null,
      "languages": [],
      "midName": null,
      "phones": [],
      "description": null,
      "mainPicture": null,
      "updateTime": null,
      "mainAddress": null,
      "expDate": null,
      "firstName": "Daniel",
      "bday": null,
      "phone": null,
      "createTime": "2020-05-18T23:08:04Z",
      "id": 2,
      "salutation": null,
      "user": null,
      "email": "daniel.lipkovich@gmail.com"
   },
   {
      "lastName": "subedi",
      "addresses": [],
      "mainContactPerson": null,
      "languages": [],
      "midName": null,
      "phones": [],
      "description": null,
      "mainPicture": null,
      "updateTime": null,
      "mainAddress": null,
      "expDate": null,
      "firstName": "Prakash",
      "bday": null,
      "phone": null,
      "createTime": "2020-05-18T23:08:04Z",
      "id": 3,
      "salutation": null,
      "user": {"id": 3},
      "email": "pnlwlust@gmail.com"
   }
]"""

    }

//create test data in db!
    def testDomains(){
        List children = [];
        if (!Child.count()) {
            Child ch = new Child(name: "Daniel")
            children.add(ch)
            ch.save(failOnError: true)

            ch = new Child(name: "Shai")
            children.add(ch)
            ch.save(failOnError: true)


            ch = new Child(name: "Shoham")
            children.add(ch)
            ch.save(failOnError: true)

            ch = new Child(name: "Dafna")
            children.add(ch)
            ch.save(failOnError: true)

            ch = new Child(name: "Rina")
            children.add(ch)
            ch.save(failOnError: true)

            ch = new Child(name: "Gabi")
            children.add(ch)
            ch.save(failOnError: true)

            ch = new Child(name: "Oshi")
            children.add(ch)
            ch.save(failOnError: true)

        }
        if (!Parent.count()) {
            Parent p = new Parent(name:"Eugene")
            p.addToChildren(children.get(0))
            p.addToChildren(children.get(1))
            p.save(failOnError: true)

            p = new Parent(name:"Eli")
            p.addToChildren(children.get(2))
            p.addToChildren(children.get(3))
            p.addToChildren(children.get(4))
            p.save(failOnError: true)

            new Parent(name:"Elina").save(failOnError: true)
            p.addToChildren(children.get(4))
            p.addToChildren(children.get(5))
            p.addToChildren(children.get(6))
            p.save(failOnError: true)
        }
    }

}
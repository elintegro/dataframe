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

import com.elintegro.erf.dataframe.DataframeInstance
import com.elintegro.erf.payment.base.gateway.PaymentGateway
import com.elintegro.erf.payment.base.gateway.PaymentGatewayFactory
import com.elintegro.erf.payment.base.parameter.RequestBuilder
import com.elintegro.erf.payment.base.response.Response
import com.elintegro.erf.payment.enums.AvailableGateways
import com.elintegro.erf.payment.enums.paypal.AutoBillAmount
import com.elintegro.erf.payment.enums.paypal.InitialFailAmountAction
import com.elintegro.erf.payment.enums.paypal.Intent
import com.elintegro.erf.payment.enums.paypal.PaymentDefinitionType
import com.elintegro.erf.payment.enums.paypal.RecipientType
import com.elintegro.erf.payment.enums.paypal.RecurringFrequency
import com.elintegro.erf.payment.enums.paypal.RecurringType
import com.elintegro.erf.payment.impl.gateway.paypal.PaypalGateway
import com.elintegro.erf.payment.impl.gateway.paypal.PaypalServiceOperation
import com.elintegro.erf.payment.impl.parameter.paypal.PaypalPayParams
import com.elintegro.erf.payment.impl.parameter.paypal.PaypalPayoutParams
import com.elintegro.erf.payment.impl.parameter.paypal.PaypalRecurringPaymentParams
import com.elintegro.erf.payment.impl.parameter.paypal.PaypalRefundParams
import com.elintegro.erf.payment.impl.response.paypal.PaypalPayResponse
import com.elintegro.erf.payment.impl.response.paypal.PaypalRecurringResponse
import com.paypal.api.payments.Amount
import com.paypal.api.payments.Currency
import com.paypal.api.payments.PaymentDefinition
import com.paypal.api.payments.PayoutItem
import com.paypal.api.payments.Transaction
import org.grails.testing.GrailsUnitTest
import org.grails.web.json.JSONObject
import org.joda.time.DateTime
import spock.lang.Shared
import spock.lang.Specification

import java.text.SimpleDateFormat

class SaveDataframeInstanceSpec extends Specification implements GrailsUnitTest {

        //@Shared String paypalClientId = "AbWoxLeyrjlKjbYMUb2F8_MvMa_MXW90Ff-HqIBOesadKW115iLgWQXlgaQOZhuN_sFntpTDVuCYZPsj"
        //@Shared String secret = "EDHzoptZCglHaOQ-yUBBNl7_KzOzL61gxU4V9ArqWrE7eiDrt8wJqGr6pq-s62nJi_i2CSuFLnlt7ssi"
        //@Shared PaymentGateway paymentGateway
        //@Shared JSONObject apiSampleParameters
        //@Shared Amount amount = PaypalServiceOperation.createAmount([
//                currency: "USD", total:"10"
  //      ])

        @Shared DataframeVue dfr
        @Shared DataframeInstance dfInstance

        @Shared DataframeInstance dfrInst
        @Shared JSONObject params_
        @Shared Map params



    def setupSpec() {
        }

        def setup() {
            //paymentGateway = PaymentGatewayFactory.getGateway(AvailableGateways.PAYPAL)
            //paymentGateway.setTestMode(true)
            //apiSampleParameters = paymentGateway.getApiSampleParameters()
            //apiSampleParameters.put("clientId", paypalClientId)
            //apiSampleParameters.put("secret", secret)
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

        def "Testing DataInstance save"(){

            when:

            params_ = new JSONObject(params)
            dfInstance = new DataframeInstance(dfr, params_)
            boolean result = dfInstance.save(false)

            then:

            assert result, "Cannot save this dbinstance"
        }
    }

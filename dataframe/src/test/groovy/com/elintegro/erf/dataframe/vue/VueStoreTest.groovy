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

import com.elintegro.erf.payment.base.gateway.PaymentGateway
import com.elintegro.erf.payment.base.gateway.PaymentGatewayFactory
import com.elintegro.erf.payment.base.parameter.RequestBuilder
import com.elintegro.erf.payment.base.response.Response
import com.elintegro.erf.payment.enums.AvailableGateways
import com.elintegro.erf.payment.enums.paypal.*
import com.elintegro.erf.payment.impl.gateway.paypal.PaypalGateway
import com.elintegro.erf.payment.impl.gateway.paypal.PaypalServiceOperation
import com.elintegro.erf.payment.impl.parameter.paypal.PaypalPayParams
import com.elintegro.erf.payment.impl.parameter.paypal.PaypalPayoutParams
import com.elintegro.erf.payment.impl.parameter.paypal.PaypalRecurringPaymentParams
import com.elintegro.erf.payment.impl.parameter.paypal.PaypalRefundParams
import com.elintegro.erf.payment.impl.response.paypal.PaypalPayResponse
import com.elintegro.erf.payment.impl.response.paypal.PaypalRecurringResponse
import com.paypal.api.payments.*
import org.grails.testing.GrailsUnitTest
import org.grails.web.json.JSONObject
import org.joda.time.DateTime
import spock.lang.Shared
import spock.lang.Specification

import java.text.SimpleDateFormat

class VueStoreTest extends Specification implements GrailsUnitTest {

    @Shared JSONObject apiSampleParameters
    @Shared DataframeVue dfr
    def setupSpec() {
    }

    def setup() {
        dfr = new DataframeVue()
        //dfr.hql = "select person.firstName, person.lastName, person.email,person.phone,application.linkedin from Application application inner join application.applicant person where application.id=:id"

    }

    def cleanup() {
    }

    def "test build State JSON"(){
        given:

        dfr = new DataframeVue()
        dfr.hql = "select person.firstName, person.lastName, person.email,person.phone,application.linkedin from Application application inner join application.applicant person where application.id=:id"
        dfr.domainFieldMap



        when:
        Response response = paymentGateway.refund(apiSampleParameters, refundParams)

        then:
        response.success == true

    }

}
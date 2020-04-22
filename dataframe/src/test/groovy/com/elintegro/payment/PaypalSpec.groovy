/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.payment

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
import com.elintegro.erf.payment.impl.response.paypal.PaypalPayoutResponse
import com.elintegro.erf.payment.impl.response.paypal.PaypalRecurringResponse
import com.paypal.api.payments.Amount
import com.paypal.api.payments.Currency
import com.paypal.api.payments.Item
import com.paypal.api.payments.PaymentDefinition
import com.paypal.api.payments.PayoutItem
import com.paypal.api.payments.Transaction
import org.grails.testing.GrailsUnitTest
import org.grails.web.json.JSONObject
import org.joda.time.DateTime
import spock.lang.Shared
import spock.lang.Specification

import java.text.SimpleDateFormat

class PaypalSpec extends Specification implements GrailsUnitTest {

    @Shared String paypalClientId = "AbWoxLeyrjlKjbYMUb2F8_MvMa_MXW90Ff-HqIBOesadKW115iLgWQXlgaQOZhuN_sFntpTDVuCYZPsj"
    @Shared String secret = "EDHzoptZCglHaOQ-yUBBNl7_KzOzL61gxU4V9ArqWrE7eiDrt8wJqGr6pq-s62nJi_i2CSuFLnlt7ssi"
    @Shared PaymentGateway paymentGateway
    @Shared JSONObject apiSampleParameters
    @Shared Amount amount = PaypalServiceOperation.createAmount([
            currency: "USD", total:"10"
    ])
    def setupSpec() {
    }

    def setup() {
        paymentGateway = PaymentGatewayFactory.getGateway(AvailableGateways.PAYPAL)
        paymentGateway.setTestMode(true)
        apiSampleParameters = paymentGateway.getApiSampleParameters()
        apiSampleParameters.put("clientId", paypalClientId)
        apiSampleParameters.put("secret", secret)
    }

    def cleanup() {
    }

    def "payment design testing"(){
        when:
        paymentGateway = PaymentGatewayFactory.getGateway(AvailableGateways.PAYPAL)

        then:
        paymentGateway instanceof PaypalGateway

        when:
        JSONObject apiSampleParameters = paymentGateway.getApiSampleParameters()

        then:
        assert apiSampleParameters.get("clientId"), "Paypal client id"
        assert apiSampleParameters.get("secret"), "Paypal Secret"

    }

    def "pay response testing"(){
        given:
        String jsonStr = '{"id": "111"}'
        Response paypalResponse = new PaypalPayResponse()

        when:
        Response response = paypalResponse.convertJsonStringResponse(jsonStr, PaypalPayResponse.class)

        then:
        assert response.id, "111"
    }

    def "testing pay"(){
        given:
        RequestBuilder paypalPayRequestParams = new PaypalPayParams()
        paypalPayRequestParams.intent(Intent.SALE.desc)
        String returnUrl, cancelUrl
		// TODO: init ${contextPath}
        returnUrl = "http://localhost:9091/${contextPath}/paypalSuccess"
        cancelUrl = "http://localhost:9091/${contextPath}/paypalCancel"
        paypalPayRequestParams.redirectUrls(PaypalServiceOperation.createRedirectUrls(cancelUrl, returnUrl))
        List<Transaction> transactionList = []
        transactionList.add(PaypalServiceOperation.createTransaction(amount: amount, description: "description"))
        paypalPayRequestParams.transactionList(transactionList)
        paypalPayRequestParams.payer(PaypalServiceOperation.createPayer(paymentMethod: 'paypal'))

        when:
        Response response = paymentGateway.pay(apiSampleParameters, paypalPayRequestParams)

        then:
        println "successUrl---"+response?.approvalUrl
        response.success == true
    }

    def "test pay approval"(){
        given:
        String payerID = "2NDQLSFXCYTEL" //need to add dynamic id from callback url
        String paymentId = "PAY-0YE996998J333482VLNK3U7Q" //need to add dynamic id from callback url
        RequestBuilder paypalPayRequestParams = new PaypalPayParams()
        paypalPayRequestParams.payerId(payerID)
        paypalPayRequestParams.paymentId(paymentId)

        when:
        Response response = paymentGateway.paymentApproval(apiSampleParameters, paypalPayRequestParams)

        then:
        println "sale id---"+response.id
        response.success == true
        response.state == "approved"
    }

    def "test recurring payment"(){
        given:
        def dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = new Date(new DateTime().plusDays(2).getMillis())
        Currency currency = new Currency().setCurrency('USD').setValue("10")
        String returnUrl, cancelUrl
        returnUrl = "http://localhost:9091/seniara/paypalSuccess"
        cancelUrl = "http://localhost:9091/seniara/paypalCancel"
        RequestBuilder recurringPaymentParams = new PaypalRecurringPaymentParams()
        PaymentDefinition paymentDefinition = PaypalServiceOperation.createPaymentDefination("payment defination", PaymentDefinitionType.REGULAR.desc
                , RecurringFrequency.DAY.desc, "1", "0", currency)
        recurringPaymentParams.planName("plan Name").planDescription("description")
                .type(RecurringType.INFINITE.desc)
        recurringPaymentParams.paymentDefinitions([paymentDefinition]).agreementName("agreementName")
                .agreementDescription("agreementDescription").startDate(dateFormat.format(date))
                .currency(currency).returnUrl(returnUrl).cancelUrl(cancelUrl)
        recurringPaymentParams.maxFailAttempts('0').autoBillAmount(AutoBillAmount.YES.desc)
                .initialFailAmountAction(InitialFailAmountAction.CONTINUE.desc)

        when:
        PaypalRecurringResponse response = (PaypalRecurringResponse)paymentGateway.recurringPayment(apiSampleParameters, recurringPaymentParams)

        then:
        response.success == true
        response.agreementName == "agreementName"
        println "success url----"+response.approvalUrl

    }

    def "recurring payment approval"(){
        given:
        String token = "EC-6AF34148CD281583T" //token from call back url
        RequestBuilder recurringPaymentParams = new PaypalRecurringPaymentParams()
        recurringPaymentParams.token(token)

        when:
        Response response = paymentGateway.recurringPaymentApproval(apiSampleParameters, recurringPaymentParams)

        then:
        response.success == true
        response.agreementState == "Active"

    }

    def "recurring payment transaction lists"(){
        given:
        String agreementId = "I-7CG67YYVCRV5"
        Date startDate = new Date(new DateTime().minusDays(2).millis)
        Date endDate = new Date(new DateTime().plusDays(2).millis)
        RequestBuilder recurringPaymentParams = new PaypalRecurringPaymentParams()
        recurringPaymentParams.agreementId(agreementId).transactionStartDate(startDate).transactionEndDate(endDate)

        when:
        Response response = paymentGateway.recurringPaymentTransactionLists(apiSampleParameters, recurringPaymentParams)

        then:
        response.success == true
        println "agreementTransactionList "+response.agreementTransactionList
    }

    def "recurring payment cancel"(){
        given:
        String agreementId = "I-7CG67YYVCRV5"
        RequestBuilder recurringPaymentParams = new PaypalRecurringPaymentParams()
        recurringPaymentParams.agreementId(agreementId).note("recurring payment cancelled")

        when:
        Response response = paymentGateway.recurringPaymentCancel(apiSampleParameters, recurringPaymentParams)

        then:
        response.success == true
    }

    def "recurring payment re-active"(){
        given:
        String agreementId = "I-7CG67YYVCRV5"
        RequestBuilder recurringPaymentParams = new PaypalRecurringPaymentParams()
        recurringPaymentParams.agreementId(agreementId).note("recurring payment re-active")

        when:
        Response response = paymentGateway.recurringPaymentReactive(apiSampleParameters, recurringPaymentParams)

        then:
        response.success == true
    }

    def "payout test"(){
        given:
        RequestBuilder payoutParams = new PaypalPayoutParams()
        List<PayoutItem> items = []
        Currency amount = new Currency().setCurrency('USD').setValue("10")
        PayoutItem item = PaypalServiceOperation.createPayoutItems(RecipientType.EMAIL.desc, "Thanks for your patronage"
                , "kumarchapagain03sc-buyer@gmail.com", "201404324236", amount)
        items.add(item)
        payoutParams.senderBatchHeader(PaypalServiceOperation.createSenderBatchHeader("You have a Payment!")).items(items)

        when:
        Response response = paymentGateway.payout(apiSampleParameters, payoutParams)

        then:
        response.success == true
    }

    def "test refund"(){
        given:
        String saleId = "4P291533PL986803Y" //dynamic sale id here
        RequestBuilder refundParams = new PaypalRefundParams()
        amount.setTotal("5")
        refundParams.amount(amount).saleId(saleId).isPartiallyRefund(true)

        when:
        Response response = paymentGateway.refund(apiSampleParameters, refundParams)

        then:
        response.success == true

    }

}
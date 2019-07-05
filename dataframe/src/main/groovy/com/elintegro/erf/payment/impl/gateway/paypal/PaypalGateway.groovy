/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.payment.impl.gateway.paypal

import com.elintegro.erf.payment.base.gateway.PaymentGateway
import com.elintegro.erf.payment.base.parameter.RequestBuilder
import com.elintegro.erf.payment.base.response.Response
import com.elintegro.erf.payment.impl.parameter.paypal.PaypalPayParams
import com.elintegro.erf.payment.impl.response.paypal.PaypalPayResponse
import com.elintegro.erf.payment.impl.response.paypal.PaypalPayoutResponse
import com.elintegro.erf.payment.impl.response.paypal.PaypalRecurringResponse
import com.elintegro.erf.payment.impl.response.paypal.PaypalRefundResponse
import com.elintegro.erf.payment.impl.response.paypal.PaypalWebhookResponse
import com.elintegro.erf.payment.utils.PaypalUtil
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.paypal.api.payments.Agreement
import com.paypal.api.payments.AgreementTransactions
import com.paypal.api.payments.DetailedRefund
import com.paypal.api.payments.Links
import com.paypal.api.payments.MerchantPreferences
import com.paypal.api.payments.Payment
import com.paypal.api.payments.PayoutBatch
import com.paypal.api.payments.Plan
import com.paypal.api.payments.RefundRequest
import com.paypal.api.payments.Webhook
import com.paypal.base.rest.APIContext
import grails.converters.JSON
import groovy.json.JsonParser
import org.grails.web.json.JSONObject
import org.json.simple.parser.JSONParser

class PaypalGateway extends PaymentGateway<RequestBuilder> {


    @Override
    Response pay(JSONObject apiSampleParameters, RequestBuilder requestParamObject) {
        Map properties = requestParamObject.asMap()
        Response payResponse = new PaypalPayResponse()
        Payment payment
        try {
            APIContext apiContext = getApiContext(apiSampleParameters)
            payment = PaypalServiceOperation.createPayment(properties, apiContext)

        }catch (e){
            payResponse.setSuccess(false)
            payResponse.setErrorMessage(e.getMessage())
            return payResponse
        }
        payResponse
                .createTime(payment.createTime)
                .updateTime(payment.updateTime)
                .id(payment.id)
                .state(payment.state)
                .failedTransactions(payment.failedTransactions)
                .failureReason(payment.failureReason)
                .intent(payment.intent)
                .payer(payment.payer)
                .transactionList(payment.transactions)

        for (Links links in payment?.links) {
            if (links?.rel == 'approval_url') {
                payResponse.approvalUrl(links.href)
            }
            if (links?.rel == 'return_url') {
                payResponse.retUrl(links.href)
            }
        }
        return payResponse
    }

    @Override
    Response refund(JSONObject apiSampleParameters, RequestBuilder requestParamObject) {
        Map properties = requestParamObject.asMap()
        Response refundResponse = new PaypalRefundResponse()
        DetailedRefund detailedRefund
        try {
            APIContext apiContext = getApiContext(apiSampleParameters)
            RefundRequest refundRequest = PaypalServiceOperation.createRefundRequest(properties)
            detailedRefund = PaypalServiceOperation.refundSale(properties, apiContext, refundRequest)

        }catch (e){
            refundResponse.setSuccess(false)
            refundResponse.setErrorMessage(e.getMessage())
            return refundResponse
        }
        refundResponse.refundId(detailedRefund.id).amount(detailedRefund.amount).createTime(detailedRefund.createTime).updateTime(detailedRefund.updateTime)
                .state(detailedRefund.state).saleId(detailedRefund.saleId).parentPayment(detailedRefund.parentPayment).invoiceNumber(detailedRefund.invoiceNumber)
        return refundResponse
    }

    @Override
    Response recurringPayment(JSONObject apiSampleParameters, RequestBuilder requestParamObject) {
        Map properties = requestParamObject.asMap()
        Response recurringResponse = new PaypalRecurringResponse()
        Plan plan
        Agreement agreement
        try {
            APIContext apiContext = getApiContext(apiSampleParameters)
            MerchantPreferences merchantPreferences = PaypalServiceOperation.createMerchantPreferences(properties)
            plan = PaypalServiceOperation.createPlan(properties, merchantPreferences, apiContext)
            PaypalServiceOperation.updatePlan(plan, apiContext)
            plan = PaypalServiceOperation.retrivePlan(plan, apiContext)
            agreement = PaypalServiceOperation.createAgreement(properties, plan, apiContext)
        }catch (e){
            recurringResponse.setSuccess(false)
            recurringResponse.setErrorMessage(e.getMessage())
            return recurringResponse
        }
        recurringResponse.agreementName(agreement.name).agreementDescription(agreement.description).agreementId(agreement.id)
                .plan(agreement.plan).payer(agreement.payer).token(agreement.token).startDate(agreement.startDate)

        for (Links links in agreement?.links) {
            if (links?.rel == 'approval_url') {
                recurringResponse.approvalUrl(links.href)
            }
            if (links?.rel == 'return_url') {
                recurringResponse.retUrl(links.href)
            }
        }
        return recurringResponse
    }

    @Override
    Response recurringPaymentApproval(JSONObject apiSampleParameters, RequestBuilder requestParamObject) {
        Map properties = requestParamObject.asMap()
        Response recurringApprovalResponse = new PaypalRecurringResponse()
        Agreement agreement
        try {
            APIContext apiContext = getApiContext(apiSampleParameters)
            agreement = PaypalServiceOperation.createAgreementExecution(properties, apiContext)

        }catch (e){
            recurringApprovalResponse.setSuccess(false)
            recurringApprovalResponse.setErrorMessage(e.getMessage())
            return recurringApprovalResponse
        }
        recurringApprovalResponse.agreementId(agreement.id).agreementState(agreement.state).agreementName(agreement.name)
                .agreementDescription(agreement.description).plan(agreement.plan).payer(agreement.payer).agreementDetails(agreement.agreementDetails)
                .startDate(agreement.startDate)
        return recurringApprovalResponse
    }

    @Override
    Response recurringPaymentUpdate(JSONObject apiSampleParameters, RequestBuilder requestParamObject) {
        Map properties = requestParamObject.asMap()
        Response recurringUpdateResponse = new PaypalRecurringResponse()
        Agreement agreement
        try {
            APIContext apiContext = getApiContext(apiSampleParameters)
            agreement = PaypalServiceOperation.updateAgreement(properties, apiContext)

        }catch (e){
            recurringUpdateResponse.setSuccess(false)
            recurringUpdateResponse.setErrorMessage(e.getMessage())
            return recurringUpdateResponse
        }
        return recurringUpdateResponse
    }

    @Override
    Response recurringPaymentCancel(JSONObject apiSampleParameters, RequestBuilder requestParamObject) {
        Map properties = requestParamObject.asMap()
        Response recurringCancelResponse = new PaypalRecurringResponse()
        try {
            APIContext apiContext = getApiContext(apiSampleParameters)
            PaypalServiceOperation.cancelAgreement(properties, apiContext)
        }catch (e){
            recurringCancelResponse.setSuccess(false)
            recurringCancelResponse.setErrorMessage(e.getMessage())
            return recurringCancelResponse
        }
        return recurringCancelResponse
    }

    @Override
    Response recurringPaymentReactive(JSONObject apiSampleParameters, RequestBuilder requestParamObject) {
        Map properties = requestParamObject.asMap()
        Response recurringReactiveResponse = new PaypalRecurringResponse()
        try {
            APIContext apiContext = getApiContext(apiSampleParameters)
            PaypalServiceOperation.reactiveAgreement(properties, apiContext)
        }catch (e){
            recurringReactiveResponse.setSuccess(false)
            recurringReactiveResponse.setErrorMessage(e.getMessage())
            return recurringReactiveResponse
        }
        return recurringReactiveResponse
    }

    @Override
    Response recurringPaymentTransactionLists(JSONObject apiSampleParameters, RequestBuilder requestParamObject) {
        Map properties = requestParamObject.asMap()
        Response recurringTransactionListResponse = new PaypalRecurringResponse()
        AgreementTransactions agreementTransactions
        try {
            APIContext apiContext = getApiContext(apiSampleParameters)
            agreementTransactions = PaypalServiceOperation.transactions(properties, apiContext)
        }catch (e){
            recurringTransactionListResponse.setSuccess(false)
            recurringTransactionListResponse.setErrorMessage(e.getMessage())
            return recurringTransactionListResponse
        }
        recurringTransactionListResponse.agreementTransactionList(agreementTransactions.agreementTransactionList)
        return recurringTransactionListResponse
    }

    @Override
    Response voidTransaction(JSONObject apiSampleParameters, RequestBuilder requestParamObject) {
        return null
    }

    @Override
    Response bankPaymentOperation(JSONObject apiSampleParameters, RequestBuilder requestParamObject) {
        return null
    }

    @Override
    Response cardPaymentOperation(JSONObject apiSampleParameters, RequestBuilder requestParamObject) {
        return null
    }

    @Override
    Response webHook(JSONObject apiSampleParameters, RequestBuilder requestParamObject) {
        Map properties = requestParamObject.asMap()
        Response webhookResponse = new PaypalWebhookResponse()
        Webhook webhook
        try {
            APIContext apiContext = getApiContext(apiSampleParameters)
            webhook = PaypalServiceOperation.createWebhook(properties, apiContext)

        }catch (e){
            webhookResponse.setSuccess(false)
            webhookResponse.setErrorMessage(e.getMessage())
            return webhookResponse
        }
        webhookResponse.webHookId(webhook.id).eventTypes(webhook.eventTypes).url(webhook.url)
        return webhookResponse
    }

    @Override
    Response webHookOperation(JSONObject apiSampleParameters, RequestBuilder requestParamObject) {


        return null
    }

    @Override
    Response paymentApproval(JSONObject apiSampleParameters, RequestBuilder requestParamObject) {
        Map properties = requestParamObject.asMap()
        Response payResponse = new PaypalPayResponse()
        Payment payment
        try {
            APIContext apiContext = getApiContext(apiSampleParameters)
            payment = PaypalServiceOperation.createPaymentExecution(properties, apiContext)

        }catch (e){
            payResponse.setSuccess(false)
            payResponse.setErrorMessage(e.getMessage())
            return payResponse
        }
        payResponse
                .createTime(payment.createTime)
                .updateTime(payment.updateTime)
                .id(payment.id)
                .state(payment.state)
                .intent(payment.intent)
                .payer(payment.payer)
                .transactionList(payment.transactions)
        return payResponse
    }

    @Override
    Response payout(JSONObject apiSampleParameters, RequestBuilder requestParamObject) {
        Map properties = requestParamObject.asMap()
        Response payoutResponse = new PaypalPayoutResponse()
        PayoutBatch payout
        try {
            APIContext apiContext = getApiContext(apiSampleParameters)
            payout = PaypalServiceOperation.createBatchPayout(properties, apiContext)

        }catch (e){
            payoutResponse.setSuccess(false)
            payoutResponse.setErrorMessage(e.getMessage())
            return payoutResponse
        }
        payoutResponse
                .batchHeader(payout.batchHeader)
        return payoutResponse
    }

    @Override
    JSONObject getApiSampleParameters() {
        return  new JSONObject()
                .put("clientId", "Paypal client id")
                .put("secret", "Paypal Secret");
    }

    @Override
    JSONObject getPaySampleParameters() {
        return null
    }

    @Override
    JSONObject getRefundSampleParameters() {
        return null
    }

    @Override
    JSONObject getRecurringPaymentSampleParameters() {
        return null
    }

    @Override
    JSONObject getVoidTransactionSampleParameters() {
        return null
    }

    private String getApiURL(){
        if (isTestMode()) {
            return "https://api.sandbox.paypal.com";
        } else {
            return "https://api.paypal.com";
        }
    }

    private APIContext getApiContext(JSONObject apiSampleParameters){
        String endPoint = getApiURL()
        String clientId = (String)apiSampleParameters.get("clientId")
        String secret = (String)apiSampleParameters.get("secret")
        Map sdkConfigParameter = PaypalServiceOperation.getSdkConfigParameter(clientId, secret, endPoint)
        String accessToken = PaypalServiceOperation.getAccessToken(clientId, secret, sdkConfigParameter)
        APIContext apiContext = PaypalServiceOperation.getAPIContext(accessToken, sdkConfigParameter)
        return apiContext
    }

    public Response getPayout(JSONObject apiSampleParameters, String payoutBatchId){
        PayoutBatch payout
        Response payoutResponse = new PaypalPayoutResponse()
        try {
            APIContext apiContext = getApiContext(apiSampleParameters)
            payout = PaypalServiceOperation.getPayout(apiContext, payoutBatchId)

        }catch (e){
            payoutResponse.setSuccess(false)
            payoutResponse.setErrorMessage(e.getMessage())
            return payoutResponse
        }
        payoutResponse
                .batchHeader(payout.batchHeader)
        return payoutResponse
    }

    public Response getRecurringPaymentDetails(JSONObject apiSampleParameters, String agreementId){
        Agreement agreement
        Response recurringDetailResponse = new PaypalRecurringResponse()
        try {
            APIContext apiContext = getApiContext(apiSampleParameters)
            agreement = PaypalServiceOperation.getRecurringPaymentDetails(apiContext, agreementId)

        }catch (e){
            recurringDetailResponse.setSuccess(false)
            recurringDetailResponse.setErrorMessage(e.getMessage())
            return recurringDetailResponse
        }
        recurringDetailResponse.agreementId(agreement.id).agreementState(agreement.state).agreementName(agreement.name)
                .agreementDescription(agreement.description).plan(agreement.plan).payer(agreement.payer).agreementDetails(agreement.agreementDetails)
                .startDate(agreement.startDate)
        return recurringDetailResponse
    }

    public Response validateWebhook(JSONObject apiSampleParameters, request, webhookId){
        Response webhookResponse = new PaypalWebhookResponse()
        Boolean isValidate = false
        Map<String, String> headerInfo = PaypalUtil.getHeadersInfo(request)
        println "headerInfos----"+headerInfo
        String body = PaypalUtil.getBody(request)
        JSONParser jsonParser = new JSONParser()
        JSONObject requestJson = (JSONObject) jsonParser.parse(body)
        try {
            APIContext apiContext = getApiContext(apiSampleParameters)
            isValidate = PaypalServiceOperation.validateWebhook(apiContext, webhookId, headerInfo, body)
        }catch (e){
            webhookResponse.setSuccess(false)
            webhookResponse.setErrorMessage(e.getMessage())
            return webhookResponse
        }
        if (!isValidate){
            webhookResponse.setSuccess(false)
            webhookResponse.setErrorMessage("Unable to validate request.")
        }
        webhookResponse.setJsonObject(requestJson)
        return webhookResponse
    }
}

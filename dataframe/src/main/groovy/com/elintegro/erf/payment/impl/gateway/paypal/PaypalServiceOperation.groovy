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

import com.elintegro.erf.payment.base.gateway.GatewayServiceOperations
import com.elintegro.erf.payment.utils.PaypalUtil
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.paypal.api.payments.Address
import com.paypal.api.payments.Agreement
import com.paypal.api.payments.AgreementStateDescriptor
import com.paypal.api.payments.AgreementTransactions
import com.paypal.api.payments.Amount
import com.paypal.api.payments.Currency
import com.paypal.api.payments.DetailedRefund
import com.paypal.api.payments.Details
import com.paypal.api.payments.Event
import com.paypal.api.payments.EventType
import com.paypal.api.payments.MerchantPreferences
import com.paypal.api.payments.Patch
import com.paypal.api.payments.Payer
import com.paypal.api.payments.Payment
import com.paypal.api.payments.PaymentDefinition
import com.paypal.api.payments.PaymentExecution
import com.paypal.api.payments.Payout
import com.paypal.api.payments.PayoutBatch
import com.paypal.api.payments.PayoutItem
import com.paypal.api.payments.PayoutSenderBatchHeader
import com.paypal.api.payments.Plan
import com.paypal.api.payments.RedirectUrls
import com.paypal.api.payments.RefundRequest
import com.paypal.api.payments.Sale
import com.paypal.api.payments.Transaction
import com.paypal.api.payments.Webhook
import com.paypal.api.payments.WebhookList
import com.paypal.base.Constants
import com.paypal.base.rest.APIContext
import com.paypal.base.rest.OAuthTokenCredential
import com.paypal.base.rest.PayPalRESTException
import grails.util.Holders


class PaypalServiceOperation extends GatewayServiceOperations{


    public static String getAccessToken(String clientID, String clientSecret, Map configurationMap) throws PayPalRESTException {

        return new OAuthTokenCredential(clientID, clientSecret, configurationMap).getAccessToken()

    }

    public static Map getSdkConfigParameter(String clientId, String clientSecret, String endpoint){

        Map sdkConfig = [(Constants.CLIENT_ID)    : clientId,
                         (Constants.CLIENT_SECRET): clientSecret,
                         (Constants.ENDPOINT)     : endpoint]
        return sdkConfig
    }

    static APIContext getAPIContext(String accessToken, Map sdkConfig){
        APIContext apiContext = new APIContext(accessToken)
        apiContext.setConfigurationMap(sdkConfig)
        return apiContext
    }


    static APIContext createAPIContext(String accessToken,String requestId){
        return new APIContext(accessToken, requestId)

    }

    public static Address createAddress(Map props){
        Address address = new Address()
        address.setLine1(props['line1'])
        address.setCity(props['city'])
        address.setCountryCode(props['countryCode'])
        address.setPostalCode(props['postalCode'])
        address.setState(props['state'])
        return address
    }


    public static Details createDetails(Map props){
        Details details = new Details()
        details.setShipping(props['shipping'])
        details.setSubtotal(props['subTotal'])
        details.setTax(props['tax'])
        return details

    }

    public static Amount createAmount(Map props){
        Amount amount = new Amount()
        amount.setCurrency(props['currency'])
        amount.setTotal(props['total'])
        amount.setDetails(props['details'])
        return amount
    }

    public static Transaction createTransaction(Map props){
        Transaction transaction = new Transaction()
        transaction.setAmount(props['amount'])
        transaction.setDescription(props['description'])
        return transaction
    }

    public static Payer createPayer(Map props){
        Payer payer = new Payer()
        payer.setPaymentMethod(props['paymentMethod'])
        payer.setFundingInstruments(props['fundingInstrumentList'])//List
        return payer
    }

    public static Payment createPayment(Map props, APIContext apiContext){
        Payment payment = new Payment()
        payment.setIntent(props['intent'])
        payment.setPayer(props['payer'])
        payment.setTransactions(props['transactions'])// list
        payment.setRedirectUrls(props['redirectUrls'])
        Payment createdPayment = payment.create(apiContext)
        return createdPayment
    }

    public static RedirectUrls createRedirectUrls(String cancelUrl, String returnUrl){
        RedirectUrls redirectUrls = new RedirectUrls()
        redirectUrls.setCancelUrl(cancelUrl)
        redirectUrls.setReturnUrl(returnUrl)
        return redirectUrls

    }

    public static Payment createPaymentExecution(Map props, APIContext apiContext){
        Payment payment = Payment.get(apiContext, props['paymentId'])
        PaymentExecution paymentExecute = new PaymentExecution()
        paymentExecute.setPayerId(props['payerId'])
        return payment.execute(apiContext, paymentExecute)
    }

    public static Plan createPlan(Map props, MerchantPreferences merchantPreferences, APIContext apiContext){
        Plan plan = new Plan()
        plan.setName(props['planName'])
        plan.setDescription(props['planDescription'])
        plan.setType(props['type'])
        plan.setPaymentDefinitions(props['paymentDefinitions'])
        plan.setMerchantPreferences(merchantPreferences)
        Plan createPlan = plan.create(apiContext)
        return createPlan
    }

    public static void updatePlan(Plan createPlan, APIContext apiContext){
        Map<String, String> value = new HashMap<String, String>()
        value.put('state','ACTIVE')
        List<Patch> patchList = createPatchRequestList(value, 'replace')
        //update plan
        createPlan.update(apiContext, patchList)
    }

    public static List<Patch> createPatchRequestList(Map value, String op){
        Patch patch = new Patch()
        patch.setPath('/')
        patch.setValue(value)
        patch.setOp(op)
        List<Patch> patchList = new ArrayList<Patch>()
        patchList.add(patch)
        return patchList
    }

    public static Plan retrivePlan(Plan createPlan, APIContext apiContext){
        Plan retrivePlan =  Plan.get(apiContext, createPlan.getId())
        return retrivePlan
    }

    public static Agreement createAgreement(Map props, Plan retrivePlan, APIContext apiContext){
        Agreement agreement = new Agreement()
        agreement.setName(props['agreementName'])
        agreement.setDescription(props['agreementDescription'])
        agreement.setStartDate(props['startDate'])
        agreement.setShippingAddress(props['shippingAddress'])
        agreement.setPlan(new Plan().setId(retrivePlan.getId()))
        agreement.setPayer(new Payer().setPaymentMethod('paypal'))
        Agreement createdAgreement = agreement.create(apiContext)
        return  createdAgreement
    }

    public static Agreement createAgreementExecution(Map props, APIContext apiContext){
        return Agreement.execute(apiContext, props['token'])
    }

    public static Agreement updateAgreement(Map props, APIContext apiContext){
        //documentation unclear for this
        Agreement agreement = new Agreement()
        agreement.setId(props['agreementId'])
        Map<String, Object> value = new HashMap<String, Object>()
        value = props['valueToUpdate']
        List<Patch> patchList = createPatchRequestList(value, 'replace')
        return agreement.update(apiContext, patchList)
    }

    public static Agreement getRecurringPaymentDetails(APIContext apiContext, String agreementId){
        return Agreement.get(apiContext, agreementId)
    }

    public static void cancelAgreement(Map props, APIContext apiContext){
        Agreement agreement = new Agreement()
        agreement.setId(props['agreementId'])
        AgreementStateDescriptor agreementStateDescriptor = new AgreementStateDescriptor()
        agreementStateDescriptor.setNote(props['note'])
        agreement.cancel(apiContext,agreementStateDescriptor)
    }

    public static void reactiveAgreement(Map props, APIContext apiContext){
        Agreement agreement = new Agreement()
        agreement.setId(props['agreementId'])
        AgreementStateDescriptor agreementStateDescriptor = new AgreementStateDescriptor()
        agreementStateDescriptor.setNote(props['note'])
        agreement.reActivate(apiContext,agreementStateDescriptor)
    }

    public static AgreementTransactions transactions(Map props, APIContext apiContext){
        String agreementId = props['agreementId']
        Date transactionStartDate = props['transactionStartDate']
        Date transactionEndDate = props['transactionEndDate']
        return Agreement.transactions(apiContext, agreementId
                ,transactionStartDate, transactionEndDate)
    }

    public static Currency createCurrency(String currency, String amount){
        Currency currency1 = new Currency()
        currency1.setCurrency(currency)
        currency1.setValue(amount)
        return currency1
    }

    public static PaymentDefinition createPaymentDefination(String name, String type, String frequency
                                                            , String frequencyInterval, String cycles, Currency currency){
        PaymentDefinition paymentDefinition = new PaymentDefinition()
        paymentDefinition.setName(name)
        paymentDefinition.setType(type)
        paymentDefinition.setFrequency(frequency)
        paymentDefinition.setFrequencyInterval(frequencyInterval)
        paymentDefinition.setCycles(cycles)
        paymentDefinition.setAmount(currency)
        return paymentDefinition
    }

    public static MerchantPreferences createMerchantPreferences(Map props){
        MerchantPreferences merchantPreferences = new MerchantPreferences()
        merchantPreferences.setSetupFee(props['currency'])
        merchantPreferences.setReturnUrl(props['returnUrl'])
        merchantPreferences.setCancelUrl(props['cancelUrl'])
        merchantPreferences.setMaxFailAttempts(props['maxFailAttempts'])
        merchantPreferences.setAutoBillAmount(props['autoBillAmount'])
        merchantPreferences.setInitialFailAmountAction(props['initialFailAmountAction'])
        return merchantPreferences
    }

    public static RefundRequest createRefundRequest(Map props){
        RefundRequest refund = new RefundRequest()
        refund.setAmount(props['amount'])
        return refund
    }

    public static DetailedRefund refundSale(Map props, APIContext apiContext, RefundRequest refundRequest){
        Sale sale = new Sale()
        sale.setId(props['saleId'])
        DetailedRefund detailedRefund = sale.refund(apiContext, refundRequest)
        return detailedRefund
    }

    public static PayoutBatch createBatchPayout(Map props, APIContext apiContext){
        Payout payout = new Payout()
        payout.setSenderBatchHeader(props['senderBatchHeader'])
        payout.setItems(props['items'])
        PayoutBatch batch = payout.create(apiContext, new HashMap<String, String>())
        return batch
    }

    public static PayoutBatch getPayout(APIContext apiContext, String payoutBatchId){
        return Payout.get(apiContext, payoutBatchId)
    }

    public static PayoutSenderBatchHeader createSenderBatchHeader(String emailSubject){
        PayoutSenderBatchHeader senderBatchHeader = new PayoutSenderBatchHeader()
        Random random = new Random()
        senderBatchHeader.setSenderBatchId(
                new Double(random.nextDouble()).toString())
                .setEmailSubject(emailSubject)
        return senderBatchHeader
    }

    public static PayoutItem createPayoutItems(String recipientType, String note, String receiver
                                               , String senderItemId, Currency amount){
        PayoutItem senderItem = new PayoutItem()
        senderItem.setRecipientType(recipientType)
                .setNote(note)
                .setReceiver(receiver)
                .setSenderItemId(senderItemId)
                .setAmount(amount)
        return senderItem
    }

    public static EventType createEventType(String eventName, String description = null){
        EventType eventType = new EventType()
        eventType.setName(eventName)
        if (description)
            eventType.setDescription(description)
        return eventType
    }

    public static Webhook createWebhook(Map props, APIContext apiContext) {
        Webhook webhook = new Webhook()
        webhook.setUrl(props['webHookUrl'])
        webhook.setEventTypes(props['eventTypes'])
        Webhook createdWebhook = webhook.create(apiContext, webhook)
        return createdWebhook
    }

    public static deleteWebhook(APIContext apiContext, String webhookId){
        boolean isExists=false
        if(webhookList(apiContext).get(webhookId)!=null){
            isExists=true
        }
        if(isExists){
            Webhook webhook = new Webhook()
            webhook.delete(apiContext,webhookId)
        }
    }

    public static Map webhookList(APIContext apiContext){
        Map<String,String> webhookList = new HashMap()
        JsonParser jsonParser = new JsonParser()
        JsonObject jsonObject = (JsonObject) jsonParser.parse(listALLWebHook(apiContext).toString())
        String jsonArrayString = jsonObject.get("webhooks")
        JsonArray arrayFromString = jsonParser.parse(jsonArrayString).getAsJsonArray()
        for(int i=0;i<arrayFromString.size();i++){
            JsonObject innerJsonObject = (JsonObject) jsonParser.parse(arrayFromString.get(i).toString())
            String  id = innerJsonObject.get("id").toString().replace("\"","")
            println ("This is id" + id)
            webhookList.put(id, "VALID")
        }
        return webhookList
    }

    public static WebhookList listALLWebHook(APIContext apiContext){
        WebhookList webhookList = new WebhookList()
        return webhookList.getAll(apiContext)
    }

    public static boolean validateWebhook(APIContext apiContext, String WebhookId, Map<String, String> headerInfo, String body){
        apiContext.addConfiguration(Constants.PAYPAL_WEBHOOK_ID, WebhookId)
        Boolean result
        try {
            result = Event.validateReceivedEvent(apiContext, headerInfo, body);
        }catch (e){
            result = false
        }
        return result
    }

    public static List listTransactionFees(Payment payment){
        List fees = []

        payment['transactions']?.each{ val ->
            val['relatedResources']?.each{ resource ->
                fees.add(resource['sale']['transactionFee'])

            }
        }

        return fees
    }
}

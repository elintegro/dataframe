/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.payment.impl.parameter.paypal

import com.paypal.api.payments.Address
import com.paypal.api.payments.Currency
import com.paypal.api.payments.PaymentDefinition

class PaypalRecurringPaymentParams extends PaypalParams{

    //for plan
    String planName
    String planDescription
    String type

    //for agreement
    String agreementName
    String agreementDescription
    String startDate
    Address shippingAddress

    //for merchantpreference
    Currency currency
    String returnUrl
    String cancelUrl
    String maxFailAttempts
    String autoBillAmount
    String initialFailAmountAction

    List<PaymentDefinition> paymentDefinitions

    //for recurring payment approval
    String token

    // for recurring payment approval/update/cancel/reactive/transactionList
    String agreementId

    //cancel or reactive note
    String note

    //for recurring transaction lists
    Date transactionStartDate
    Date transactionEndDate

    public PaypalRecurringPaymentParams planName(String planName){
        this.planName = planName
        return this.with("planName", planName);
    }

    public PaypalRecurringPaymentParams planDescription(String planDescription){
        this.planDescription = planDescription
        return this.with("planDescription", planDescription);
    }

    public PaypalRecurringPaymentParams type(String type){
        this.type = type
        return this.with("type", type);
    }

    public PaypalRecurringPaymentParams agreementName(String agreementName){
        this.agreementName = agreementName
        return this.with("agreementName", agreementName);
    }

    public PaypalRecurringPaymentParams agreementDescription(String agreementDescription){
        this.agreementDescription = agreementDescription
        return this.with("agreementDescription", agreementDescription);
    }

    public PaypalRecurringPaymentParams startDate(String startDate){
        this.startDate = startDate
        return this.with("startDate", startDate);
    }

    public PaypalRecurringPaymentParams shippingAddress(Address shippingAddress){
        this.shippingAddress = shippingAddress
        return this.with("shippingAddress", shippingAddress);
    }

    public PaypalRecurringPaymentParams currency(Currency currency){
        this.currency = currency
        return this.with("currency", currency);
    }

    public PaypalRecurringPaymentParams returnUrl(String returnUrl){
        this.returnUrl = returnUrl
        return this.with("returnUrl", returnUrl);
    }

    public PaypalRecurringPaymentParams cancelUrl(String cancelUrl){
        this.cancelUrl = cancelUrl
        return this.with("cancelUrl", cancelUrl);
    }

    public PaypalRecurringPaymentParams maxFailAttempts(String maxFailAttempts){
        this.maxFailAttempts = maxFailAttempts
        return this.with("maxFailAttempts", maxFailAttempts);
    }

    public PaypalRecurringPaymentParams autoBillAmount(String autoBillAmount){
        this.autoBillAmount = autoBillAmount
        return this.with("autoBillAmount", autoBillAmount);
    }

    public PaypalRecurringPaymentParams initialFailAmountAction(String initialFailAmountAction){
        this.initialFailAmountAction = initialFailAmountAction
        return this.with("initialFailAmountAction", initialFailAmountAction);
    }

    public PaypalRecurringPaymentParams paymentDefinitions(List<PaymentDefinition> paymentDefinitions){
        this.paymentDefinitions = paymentDefinitions
        return this.with("paymentDefinitions", paymentDefinitions);
    }

    public PaypalRecurringPaymentParams agreementId(String agreementId){
        this.agreementId = agreementId
        return this.with("agreementId", agreementId);
    }

    public PaypalRecurringPaymentParams note(String note){
        this.note = note
        return this.with("note", note);
    }

    public PaypalRecurringPaymentParams token(String token){
        this.token = token
        return this.with("token", token);
    }

    public PaypalRecurringPaymentParams transactionStartDate(Date transactionStartDate){
        this.transactionStartDate = transactionStartDate
        return this.with("transactionStartDate", transactionStartDate);
    }

    public PaypalRecurringPaymentParams transactionEndDate(Date transactionEndDate){
        this.transactionEndDate = transactionEndDate
        return this.with("transactionEndDate", transactionEndDate);
    }
}

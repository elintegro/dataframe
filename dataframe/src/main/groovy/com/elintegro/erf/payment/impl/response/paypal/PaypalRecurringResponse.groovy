/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.payment.impl.response.paypal

import com.elintegro.erf.payment.base.parameter.RequestBuilder
import com.elintegro.erf.payment.base.response.Response
import com.paypal.api.payments.AgreementDetails
import com.paypal.api.payments.AgreementTransaction
import com.paypal.api.payments.Payer
import com.paypal.api.payments.Plan

class PaypalRecurringResponse implements Response, RequestBuilder {

    String agreementId;
    String agreementState;
    String agreementName;
    String agreementDescription;
    String startDate;
    Payer payer;
    Plan plan;
    String approvalUrl
    String retUrl
    String token

    AgreementDetails agreementDetails;

    //for transaction list
    List<AgreementTransaction> agreementTransactionList;


    public PaypalRecurringResponse agreementId(String agreementId){
        this.agreementId = agreementId
        return this.with("agreementId", agreementId);
    }

    public PaypalRecurringResponse agreementState(String agreementState){
        this.agreementState = agreementState
        return this.with("agreementState", agreementState);
    }

    public PaypalRecurringResponse agreementName(String agreementName){
        this.agreementName = agreementName
        return this.with("agreementName", agreementName);
    }

    public PaypalRecurringResponse agreementDescription(String agreementDescription){
        this.agreementDescription = agreementDescription
        return this.with("agreementDescription", agreementDescription);
    }

    public PaypalRecurringResponse startDate(String startDate){
        this.startDate = startDate
        return this.with("startDate", startDate);
    }

    public PaypalRecurringResponse payer(Payer payer){
        this.payer = payer
        return this.with("payer", payer);
    }

    public PaypalRecurringResponse plan(Plan plan){
        this.plan = plan
        return this.with("plan", plan);
    }

    public PaypalRecurringResponse approvalUrl(String approvalUrl){
        this.approvalUrl = approvalUrl
        return this.with("approvalUrl", approvalUrl);
    }

    public PaypalRecurringResponse retUrl(String retUrl){
        this.retUrl = retUrl
        return this.with("retUrl", retUrl);
    }

    public PaypalRecurringResponse agreementTransactionList(List<AgreementTransaction> agreementTransactionList){
        this.agreementTransactionList = agreementTransactionList
        return this.with("agreementTransactionList", agreementTransactionList);
    }

    public PaypalRecurringResponse token(String token){
        this.token = token
        return this.with("token", token);
    }

    public PaypalRecurringResponse agreementDetails(AgreementDetails agreementDetails){
        this.agreementDetails = agreementDetails
        return this.with("agreementDetails", agreementDetails);
    }
}

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

import com.paypal.api.payments.Payer
import com.paypal.api.payments.RedirectUrls
import com.paypal.api.payments.Transaction


class PaypalPayParams extends PaypalParams {

    //for creating normal payment
    String intent
    RedirectUrls redirectUrls
    List<Transaction> transactions
    Payer payer

    //for payment approval
    String paymentId
    String payerId

    public PaypalPayParams intent(String intent){
        this.intent = intent
        return this.with("intent", intent);
    }

    public PaypalPayParams redirectUrls(RedirectUrls redirectUrls){
        this.redirectUrls = redirectUrls
        return this.with("redirectUrls", redirectUrls);
    }

    public PaypalPayParams transactionList(List<Transaction> transactionList){
        this.transactions = transactionList
        return this.with("transactions", transactionList);
    }

    public PaypalPayParams payer(Payer payer){
        this.payer = payer
        return this.with("payer", payer);
    }

    public PaypalPayParams payerId(String payerId){
        this.payerId = payerId
        return this.with("payerId", payerId);
    }

    public PaypalPayParams paymentId(String paymentId){
        this.paymentId = paymentId
        return this.with("paymentId", paymentId);
    }

}

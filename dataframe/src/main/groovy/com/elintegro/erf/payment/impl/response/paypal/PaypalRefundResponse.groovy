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
import com.paypal.api.payments.Amount

class PaypalRefundResponse implements Response, RequestBuilder {

    String refundId
    Amount amount
    String createTime
    String updateTime
    String state
    String saleId
    String parentPayment
    String invoiceNumber

    public PaypalRefundResponse refundId(String refundId){
        this.refundId = refundId
        return this.with("refundId", refundId);
    }

    public PaypalRefundResponse amount(Amount amount){
        this.amount = amount
        return this.with("amount", amount);
    }

    public PaypalRefundResponse createTime(String createTime){
        this.createTime = createTime
        return this.with("createTime", createTime);
    }

    public PaypalRefundResponse updateTime(String updateTime){
        this.updateTime = updateTime
        return this.with("updateTime", updateTime);
    }

    public PaypalRefundResponse saleId(String saleId){
        this.saleId = saleId
        return this.with("saleId", saleId);
    }

    public PaypalRefundResponse state(String state){
        this.state = state
        return this.with("state", state);
    }

    public PaypalRefundResponse parentPayment(String parentPayment){
        this.parentPayment = parentPayment
        return this.with("parentPayment", parentPayment);
    }

    public PaypalRefundResponse invoiceNumber(String invoiceNumber){
        this.invoiceNumber = invoiceNumber
        return this.with("invoiceNumber", invoiceNumber);
    }
}

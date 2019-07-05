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

import com.elintegro.erf.payment.base.response.Response
import com.elintegro.erf.payment.impl.parameter.paypal.PaypalPayParams
import com.paypal.api.payments.Error

class PaypalPayResponse extends PaypalPayParams implements Response{

    String id
    String createTime
    String updateTime
    String approvalUrl
    String retUrl
    String state
    List<Error> failedTransactions
    String failureReason

    PaypalPayResponse(){
        //set common field here
    }

    public PaypalPayResponse id(String id){
        this.id = id
        return this.with("id", id);
    }

    public PaypalPayResponse createTime(String createTime){
        this.createTime = createTime
        return this.with("createTime", createTime);
    }

    public PaypalPayResponse updateTime(String updateTime){
        this.updateTime = updateTime
        return this.with("updateTime", updateTime);
    }

    public PaypalPayResponse approvalUrl(String approvalUrl){
        this.approvalUrl = approvalUrl
        return this.with("approvalUrl", approvalUrl);
    }

    public PaypalPayResponse retUrl(String retUrl){
        this.retUrl = retUrl
        return this.with("retUrl", retUrl);
    }

    public PaypalPayResponse state(String state){
        this.state = state
        return this.with("state", state);
    }

    public PaypalPayResponse failedTransactions(List<Error> failedTransactions){
        this.failedTransactions = failedTransactions
        return this.with("failedTransactions", failedTransactions);
    }

    public PaypalPayResponse failureReason(String failureReason){
        this.failureReason = failureReason
        return this.with("failureReason", failureReason);
    }

}

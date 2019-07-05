/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.gc

import com.elintegro.billing.Charge
import com.elintegro.billing.PaymentFrequencyUnit
import com.elintegro.payment.paymentEnums.ChargeType

class Contract {

    //Contract Management Field
    String description
    Date startDate
    Date endDate
    Double rentalPrice = 0.0
    String paymentFrequency
    String paymentMethod
    Date firstPaymentDate
    Date lastIssuedBillDate
    short noOfPayments = 1
    short noOfPaymentFrequency = 1
    PaymentFrequencyUnit paymentFrequencyUnit
    String specialConditions
    String otherNotes
    String status
    String ownerSignature
    Date signDate
    Date offerStartDate
    Date offerEndDate
    Date cancelOperationDate
    ChargeType chargeType

    String paymentStatus  //Pending, Active, Suspended, Cancelled, Expired
    String paymentProfileId // unique id of payment for recurring payment
    int noOfPaymentCompleted = 0

    static constraints = {
        tenant nullable: false, blank: false
        application nullable: false, blank: false
        unit nullable: false, blank: false
    }
}

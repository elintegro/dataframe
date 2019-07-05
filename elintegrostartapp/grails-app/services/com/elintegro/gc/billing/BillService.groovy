/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.gc.billing

import com.elintegro.billing.Bill
import com.elintegro.billing.BillBusinessRules
import com.elintegro.billing.Charge
import com.elintegro.gc.Contract
import grails.gorm.transactions.Transactional
import org.joda.time.DateTime

@Transactional
class BillService {
    def generateCurrentBill(List<Charge> charges) {
        def currentDate = new Date()
        Double totalAmount = 0.0D
        def bill = new Bill()
        Boolean isGenerateBillFlag = false
        List<Date> chargesBillDates = []
        charges.each { Charge charge->
            def currentBillDate = charge.currentBillDate
            Date nextCurrentBillingDate = getNextCurrentBillDate(charge, currentBillDate)
            if (BillBusinessRules.isValidBillDate(nextCurrentBillingDate, currentDate, currentBillDate)){
                totalAmount = totalAmount + charge.currentPaymentAmount
                charge.currentBillDate = nextCurrentBillingDate
                charge.currentInstallmantNumber = charge.currentInstallmantNumber + 1
                charge.save()
                bill.addToCharges(charge)
                isGenerateBillFlag = true
                chargesBillDates.add(nextCurrentBillingDate)
            }
        }
        if (isGenerateBillFlag){
            try {
                bill.amount = totalAmount
                bill.dueDate = BillBusinessRules.getBillDueDate(chargesBillDates)
                bill.billDate = new Date()
                bill.tenant = charges.first().tenant
                bill.owner = charges.first().owner
                bill.balance = BillBusinessRules.calculateBalance(totalAmount)
                bill.save()
                return bill
            }catch (e){
                log.info("error saving due to ...."+e.getMessage())
            }
        }
    }

    def getNextCurrentBillDate(Charge charge, Date currentBillDate){
        Date nextCurrentBillingDate
        def contract = charge.contract
        def firstPaymentDate = contract?.firstPaymentDate
        if (!currentBillDate){
            if (firstPaymentDate){
                nextCurrentBillingDate = firstPaymentDate
            }else {
                nextCurrentBillingDate = contract.startDate
            }
        }else {
            nextCurrentBillingDate = calculateNextBillingDate(charge)
        }
        return nextCurrentBillingDate
    }

    def calculateNextBillingDate(Charge charge){
        def contract = charge.contract
        def currentBillDate = charge.currentBillDate
        def nextBillDate = getFrequencyUnitDate(currentBillDate, contract)
        return nextBillDate
    }

    def generateContractBill(){
        def tenantOwnersCombination = Charge.withCriteria {
            projections {
//                distinct(["tenant", "owner"])
            }
        }
        tenantOwnersCombination.each { tenantOwner ->
//            Tenant tenant = tenantOwner[0]
//            Owner owner = tenantOwner[1]
            def charges = Charge.executeQuery("select ch from Charge as ch join ch.contract contract where ((ch.tenant=:tenant and ch.owner=:owner) and ch.currentInstallmantNumber < cast(contract.noOfPayments as int)) order by ch.createTime desc", [tenant: tenant, owner: owner])
            generateCurrentBill(charges)
        }
    }

    private static Date getFrequencyUnitDate(Date currentBillDate, Contract contract){
        def noOfFrequencyUnit = contract.noOfPaymentFrequency
        def frequencyUnitName = contract.paymentFrequencyUnit.name
        Date nextDate = null
        if (frequencyUnitName.equals("day")){
            nextDate = new Date(new DateTime(currentBillDate).plusDays(noOfFrequencyUnit).getMillis())
        }else if (frequencyUnitName.equals("week")){
            nextDate = new Date(new DateTime(currentBillDate).plusWeeks(noOfFrequencyUnit).getMillis())
        }else if (frequencyUnitName.equals("month")){
            nextDate = new Date(new DateTime(currentBillDate).plusMonths(noOfFrequencyUnit).getMillis())
        }else if (frequencyUnitName.equals("year")){
            nextDate = new Date(new DateTime(currentBillDate).plusYears(noOfFrequencyUnit).getMillis())
        }
        return nextDate
    }

}

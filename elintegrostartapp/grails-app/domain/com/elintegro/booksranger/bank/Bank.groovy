/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.booksranger.bank

class Bank {

	String accountName //2nd element after _ in the file name
	Date receiptDate
	String transactionName = ""
	Double debitAmount = 0.0D
	Double creditAmount = 0.0D
	Double balanceAmount = 0.0D
	
	String accountPeriod //1nd element after _ in the file name
	String filePeriod    //1nd element after _ in the file name
	
	Date crDate
	Date upDate
	String user
	Date expDate
	
	//01/05/2016
	def static dateFormat = new java.text.SimpleDateFormat("MM/dd/yyyy")
	
	public Bank(){		
	}
	
	public Bank(
            String accountName,
            Date receiptDate,
            String transactionName,
            Double debitAmount,
            Double creditAmount,
            Double balanceAmount
				){
				this.accountName = accountName
				this.receiptDate = receiptDate
				this.transactionName = transactionName
				this.debitAmount = debitAmount
				this.creditAmount = creditAmount
				this.balanceAmount = balanceAmount
	}

				
	public Bank(
            String accountName,
            String receiptDate,
            String transactionName,
            String debitAmount,
            String creditAmount,
            String balanceAmount
				){
				this.accountName = accountName
				this.receiptDate = dateFormat.receiptDate //
				this.transactionName = transactionName
				this.debitAmount = debitAmount //
				this.creditAmount = creditAmount //
				this.balanceAmount = balanceAmount //
	}

				
    static constraints = {
		receiptDate(nullable: true)
		debitAmount(nullable: true)
		creditAmount(nullable: true)
		balanceAmount(nullable: true)
		accountPeriod(nullable: true)
		filePeriod(nullable: true)
		
		crDate(nullable: true)
		upDate(nullable: true)
		user(nullable: true)
	

    }
	
}

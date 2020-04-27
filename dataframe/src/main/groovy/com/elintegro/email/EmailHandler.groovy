/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.email


//import grails.plugin.mail.MailService

class EmailHandler implements Runnable{
	
	public void run(){
		
	}
	/*private MailService mailService
	String email
	String emailFrom
	String emailSubject
	String emailBody
	int maxTimesToTry = 20
	int delayBetweenAttempts = 600000 //10*60*1000 mls
	
	
	public EmailHandler(MailService mailService){
		this.mailService = mailService
	}
	
	public void run() {
		int currentAttempt = 0
		while(true){
			try{
				mailService.sendMail {
					to email
					from emailFrom
					subject emailSubject
					html emailBody
				}
			}catch(javax.mail.internet.AddressException ea){
				
				if(currentAttempt > maxTimesToTry){
					break
				}
				
				maxTimesToTry++
				//Delay and try again
				this.sleep(this.delayBetweenAttempts)			
		}
		}
	}
	
	*//**
	 * 
	 * @return
	 * @throws EmailDataException
	 *//*
	boolean validateEmailData() throws EmailDataException{
		boolean ret = true
		if(StringUtilsErf.isEmpty(email)) //TODO: use regex!!!!
		
		if(StringUtilsErf.isEmpty(emailFrom)) {
			throw new EmailDataException("From field is empty")		
		}
		
		if(StringUtilsErf.isEmpty(emailSubject)) {
			throw new EmailDataException("Subject field is empty")
		}
		
		if(StringUtilsErf.isEmpty(emailBody)) {
			throw new EmailDataException("email body field is empty")
		}
		
		return ret		
	} 	
	
	*//**
	 *
	 *//*
	def void sendEmail(){
		
		validateEmailData()
		
		try{
			mailService.sendMail {
				to email
				from emailFrom
				subject emailSubject
				html emailBody
			}
		}catch(javax.mail.internet.AddressException ea){
			//If there is an exception, make sure the email will attempt to be sent until exhaustion
			EmailHandler eh = new EmailHandler(mailService)
			def teh = new Thread(eh)
			teh.start()
		}
	}

	
	*//**
	 * Inner class for Exception
	 * @author euge
	 *
	 *//*
	class EmailDataException extends Throwable{
				
		EmailDataException(String msg){
			this.message = msg
		}
		
	}*/
	
}

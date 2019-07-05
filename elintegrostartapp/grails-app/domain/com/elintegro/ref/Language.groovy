/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.ref


/**
 * This domain keeps ref table for diff languages
 * @author Eugenel
 *
 */
class Language {

	String code		//Code from Locale standad
	String name    //Name printed in the language 
	String ename  //Name printed in English
	String description
	boolean inuse = false
	
	
	static mapping = {
		cache usage:'read-only'
	 }
	
	
    static constraints = {
		
		code(blank:false, nullable:false)
		name(blank:true, nullable:true)
		ename(blank:false, nullable:false)
		description(blank:true, nullable:true)
		
    }
	
	String toString(){
		"${code} - ${name}"
	}

	static void callMe(Language l){
				
		String language_code = l.getCode();
		String language_name = l.getName();
		String language_ename = l.getEname();
		String language_descr = l.getDescription();
		String language_inuse = l.getInuse();
		
				System.out.println("Eu!!!- from Language   language_code = " + language_code);
	}
		
}

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

class Country {

	String name
	String FIPS104
	String ISO2
	String ISO3
	String ISON
	String internet
	String capital
	String mapReference
	String nationalitySingular
	String nationalityPlural
	String currency
	String currencyCode
	Long population
	String title
	String COMMENT
	
    static constraints = {
		name(maxsize:50)
		FIPS104(maxsize:2, nullable:true)
		ISO2(maxsize:2, nullable:true)
		ISO3(maxsize:3, nullable:true)
		ISON(maxsize:4, nullable:true)
		internet(maxsize:2, nullable:true)
		capital(maxsize:25, nullable:true)
		mapReference(maxsize:50, nullable:true)
		nationalitySingular(maxsize:35, nullable:true)
		nationalityPlural(maxsize:50, nullable:true)
		currency(maxsize:30, nullable:true)
		currencyCode(maxsize:3, nullable:true)
		population(maxsize:20, nullable:true)
		title(maxsize:50, nullable:true)
		COMMENT(maxsize:255, nullable:true)
    }
	
	def equals(Country country){
		return this.name.equals(country.name); 
	}

	/**
	 * 
	 * @param countryCodeStr
	 * @param countryName
	 * @return
	 */
	def static getCountryByCode(String countryCodeStr, String countryName){
		
		Country ret = Country.find("from Country as c where c.FIPS104=:countryCode", [countryCode: countryCodeStr], [cache: true])
		
		//Country ret = Country.findByFIPS104(countryCodeStr)
		if(!ret){
			ret	= Country.find("from Country as c where c.name=:name", [name: countryName], [cache: true])
		}
		if(!ret){
			ret = new Country(countryCodeStr, countryName)
			ret.save(failOnError: false);	
		}				
		return ret
	}
	
	/**
	 * 
	 * @param countryCodeStr
	 * @param countryName
	 */
	def Country (String countryCodeStr, String countryName){
		this.FIPS104 = countryCodeStr
		this.name = countryName
	}
	
	def Country (){
	}

	
}

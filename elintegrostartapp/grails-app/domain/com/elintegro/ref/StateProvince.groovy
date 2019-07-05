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

class StateProvince {

	String name
	Country country	
	String CODE
	String ADM1Code
	
    static constraints = {
		name(maxsize:45)
		country()
		CODE(maxsize:8, nullable:true)
		ADM1Code(maxsize:4, nullable:true)
    }
	
	/**
	 * 
	 * @param sp
	 * @return
	 */
	def equals(StateProvince sp){
		return this.CODE.equals(sp.CODE) && this.country.equals(sp.country);
	}

	/**
	 * 
	 * @param spCodeStr
	 * @param spStr
	 * @param country
	 * @return
	 */
	def static getStateProvinceByCodeAndCountry(String spCode, String sp, Country country){
		
		//StateProvince ret = StateProvince.find("from state_province as s where s.code=:countryCode and s.country_id=:countryId", [countryCode: spCode, countryId: country.id], [cache: true])
		def cntrId = country.id
		StateProvince ret = StateProvince.find("from StateProvince as s where s.CODE=:spCode and s.country = :countryId", [spCode: spCode, countryId: country], [cache: true])
		if(!ret){			
			ret = StateProvince.find("from StateProvince as s where s.name=:countryName and s.country=:countryId", [countryName: sp, countryId: country], [cache: true])
		}
		if(!ret){
			ret = new StateProvince(spCode, sp, country)
			ret.save(failOnError: false)
		}
		return ret
	}
	
	/**
	 * 
	 * @param spCodeStr
	 * @param spStr
	 * @param country
	 */
	def StateProvince(String spCodeStr, String spStr, Country country){
		this.name = spStr
		this.CODE = spCodeStr
		this.country = country
	}

	def StateProvince(){
	}

}

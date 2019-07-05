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

class City {

	String name
	Country country
	StateProvince stateProvince
	String CODE
	//Region region
	double latitude
	double longitude
	String timeZone
	Dma dma
		
    static constraints = {
		name(maxsize:45)
		stateProvince(nullable:true)
		CODE(maxsize:8, nullable:true)
		latitude(nullable:true)
		longitude(nullable:true)
		timeZone(nullable:true)
		dma(nullable:true)

    }
	
	def equals(City city){
		return this.CODE.equals(city.CODE) && this.country.equals(city.country) 
	}
	
		
	def static getCityByNameAndProvince(String cityName, Country country, StateProvince stateProvince){
		
		def spId = stateProvince.id
		def cntryCode = stateProvince.country.id
		
		def spClause = stateProvince?" and c.stateProvince=:stateProvince":""
		def countryClause = country?" and c.country=:country":""
		
		def hqlQuery = "from City as c where c.name=:cityName $spClause $countryClause"
		
		City ret = City.find( hqlQuery, [cityName:cityName, country: country, stateProvince: stateProvince], [cache: true])
		if(!ret){
			ret = new City(cityName, stateProvince, country)
			ret.save(failOnError: true)
		}
		return ret
	}
	
	def City(String cityName, StateProvince stateProvince, Country country){
		this.name = cityName
		this.stateProvince = stateProvince
		this.country = country
	}
	
	
	def City(){
	}

}


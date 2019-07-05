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

import groovy.util.slurpersupport.NodeChild
import groovy.util.slurpersupport.NodeChildren
import org.springframework.util.StringUtils

class Address  implements Serializable{
	
	static final int ADDRESS_UPDATE_CURRENT = 1;
	static final int ADDRESS_INSERT_NEW = 2;
	static final int ADDRESS_EQUALS = 0;
	
	String addressText
	
	String streetNbr
	String street
	String postalZip

	City city
	String area
	StateProvince stateProvince
	Country country
	String countryString
	String cityString

	
	
	String direction
	String addressLine
	String addressLine2
	String POBox
	String comments
	double longitude
	double latitude
	String apartment
	
	
	static mapping = {
		city fetch: 'join' //makes the retrieve method to use join to retrieve the city for this address
		stateProvince fetch: 'join'
		country fetch: 'join'
	}
	
    static constraints = {
		addressText(nullable:true)
		streetNbr(maxsize:20, nullable:true)
		street(maxsize:50, nullable:true)
		direction(maxsize:30, nullable:true)
		addressLine(maxsize:70, nullable:true)
		addressLine2(maxsize:70, nullable:true)
		postalZip(maxsize:15, nullable:true)
		latitude(nullable:true)
		longitude(nullable:true)
		city(nullable:true)
		stateProvince(nullable:true)
		country(nullable:true)
		POBox (nullable:true)
		comments (nullable:true)
		apartment (nullable:true)
		area(nullable:true)
    }
	
	/*
	 * 
	 */
	String toString(){
		//"${message(code: 'local.addressFormat', args: ['1', default: 'Property')])}"
		//local.addressFormat = {0} {1}, {2}, {3}, {4}, {5}, {6}
		
		//def msg = message(code:"local.addressFormat", args:[streetNbr, street, city?.getName(),stateProvince?.getCODE(), country?.getName(), postalZip, apartment])
		return "${streetNbr}, ${street}, ${city?.getName()},${stateProvince?.getCODE()}, ${country?.getName()}, ${postalZip}, ${apartment}" 				
	}

	
	/**
	 * 
	 * @param address
	 * @return
	 */
	def compareForUpdate(Address address){
		def ret = ADDRESS_EQUALS		
		def tt = StringUtils.hasLength(this.streetNbr)
		
		if(    (!this.streetNbr.equals(address.streetNbr) && StringUtils.hasLength(this.streetNbr))
			|| (!this.street.equals(address.street) && StringUtils.hasLength(this.street))
			|| (!this.postalZip.equals(address.postalZip) && StringUtils.hasLength(this.postalZip))
			|| (!this.city.equals(address.city) && this.city!=null)
			|| (!this.stateProvince.equals(address.stateProvince) && this.stateProvince!=null)
			|| (!this.country.equals(address.country) && this.country!=null)
			|| (!this.apartment.equals(address.apartment) && StringUtils.hasLength(this.apartment))
			){			
			ret = ADDRESS_INSERT_NEW			 
		}  else if(!StringUtils.hasLength(this.streetNbr)
				|| !StringUtils.hasLength(this.street)
				|| !StringUtils.hasLength(this.postalZip)
				|| !StringUtils.hasLength(this.city?.name)
				|| !StringUtils.hasLength(this.stateProvince?.name)
				|| !StringUtils.hasLength(this.country?.name)
				|| !StringUtils.hasLength(this.apartment)
				|| !this.addressText.equals(address.addressText)
				|| !this.direction.equals(address.direction)
				|| !this.addressLine.equals(address.addressLine)
				|| !this.addressLine2.equals(address.addressLine2)
				|| !this.POBox.equals(address.POBox)
				|| !this.latitude.equals(address.latitude)
				|| !this.longitude.equals(address.longitude)
				|| !this.comments.equals(address.comments)
				|| !this.addressLine.equals(address.addressLine)
			){
			ret = ADDRESS_UPDATE_CURRENT
		}	
		return ret
	}	
	
	def updateAddress(Address address){
			if(address.streetNbr) this.streetNbr = address.streetNbr
			if(address.street) this.street = address.street
			if(address.postalZip) this.postalZip = address.postalZip
			if(address.city) this.city = address.city
			if(address.stateProvince) this.stateProvince = address.stateProvince
			if(address.country) this.country = address.country
	        if(address.apartment) this.apartment = address.apartment
			
			this.addressText = address.addressText
			this.direction = address.direction
			this.addressLine = address.addressLine
			this.addressLine2 = address.addressLine2
			this.POBox = address.POBox
			this.latitude = address.latitude
			this.longitude = address.longitude
			this.comments = address.comments
			this.addressLine = address.addressLine
	}
	
	def isFullAddress(){		
		return (this.addressText  
				&& this.latitude == this.latitude 
				&& this.longitude == this.longitude
				&& this.streetNbr
				&& this.street
				&& this.postalZip
				&& this.city			
				)		
	}
	
	/**
	 * 
	 * @param geoCodeResults
	 * @return
	 */
	public populateFromGeoCode(NodeChildren geoCodeResults){
		
		Map<String, NodeChild> geoCodeHash = new HashMap<String, NodeChild>()
		
		//Turn into the hash map
		geoCodeResults.address_component.collect{
			
			def itt = it
			
			itt.type.collect{
				geoCodeHash.put(it.text(), itt)
			}
		}

		this.addressText = geoCodeResults?.formatted_address?.text()
		
		def lat = geoCodeResults.geometry?.location?.lat
		def lng = geoCodeResults.geometry?.location?.lng
		
		this.latitude = lat?.toDouble() 
		this.longitude = lng?.toDouble()
		
		this.streetNbr = geoCodeHash.get("street_number")?.long_name?.text()		
		this.street = geoCodeHash.get("route")?.long_name?.text()
		this.postalZip = geoCodeHash.get("postal_code")?.long_name?.text()
		this.area = geoCodeHash.get("administrative_area_level_2")?.long_name?.text()

		String countryStr = geoCodeHash.get("country")?.long_name?.text()
		String countryCodeStr = geoCodeHash.get("country")?.short_name?.text()
		this.country = Country.getCountryByCode(countryCodeStr, countryStr)
				
		String spStr = geoCodeHash.get("administrative_area_level_1").long_name.text()
		String spCodeStr = geoCodeHash.get("administrative_area_level_1").short_name.text()
		this.stateProvince = StateProvince.getStateProvinceByCodeAndCountry(spCodeStr, spStr, country)
		
		String cityName = geoCodeHash.get("locality").long_name.text()
		this.city = City.getCityByNameAndProvince(cityName, country, stateProvince)
	}	
}
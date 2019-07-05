/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.dataframe

/**
 * 
 * This class uses two field a map and a list to put all data in map and the keys in list.
 * @author Abani
 *
 */

class OrderedMap {
	private Map dataMap = [:]
	private List orderList = new ArrayList()
	private List dbOrderList = new ArrayList()

	
	/**
	 * 
	 * This method uses key and an object put the object on that key to the dataMap and add the key to the orderList.
	 * @param key
	 * @param object
	 */
	public void put(String key, Object object){
		boolean isFieldExists = dataMap.containsKey(key);
		dataMap.put(key, object)
		if(!isFieldExists){		
			orderList.add(key)
			dbOrderList.add(key)
		}
	}

	/**
	 * 
	 * This method uses key to retrieve the object from the dataMap for that key.
	 * @param key
	 * @return
	 */
	public Map get(String key){
		return  dataMap.get(key)
	}

	/**
	 * 
	 * This method uses key, object and beforeKey and put the object for the key in dataMap and insert the key 
	 * before the object position of beforeKey in orderList.
	 * @param key
	 * @param object
	 * @param beforeKey
	 */
	public void insert(String key, Object object, String beforeKey){
		dataMap.put(key, object)
		int indx = orderList.indexOf(beforeKey)
		int total = orderList.size()
		orderList.add(orderList.get(total-1))
		for(int i = total-1; i > indx; i-- ){
			def data = orderList.get(i-1)
			orderList.set(i, data)
		}
		orderList.set(indx, key)
	}

	/**
	 *
	 * This method uses key, object and insertAfterKey and put the object for the key in dataMap and insert the key
	 * after the object position of insertAfterKey in orderList.
	 * @param key
	 * @param object
	 * @param insertAfterKey
	 */
	public void insertAfter(String key, Object object, String insertAfterKey){
		dataMap.put(key, object)
		int indx = orderList.indexOf(insertAfterKey)
		int total = orderList.size()
		orderList.add(orderList.get(total-1))
		for(int i = total-1; i > indx+1; i-- ){
			def data = orderList.get(i-1)
			orderList.set(i, data)
		}
		orderList.set(indx+1, key)
	}
	
	/**
	 * This method returns the orderList
	 * @return
	 */
	public List getList(){
		return orderList
	}
	
	public List getDbList(){
		return dbOrderList
	}
		
	public int size(){
		return dataMap.size()
	}
}

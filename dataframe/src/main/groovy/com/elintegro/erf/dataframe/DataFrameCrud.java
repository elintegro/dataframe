/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.dataframe;

import com.elintegro.erf.dataframe.DataManipulationException;

import java.util.List;
import java.util.Map;

public interface DataFrameCrud {
	
	/**
	 * This method saves dataframe, assuming that parameters contains all user input 
	 * @param params - user input
	 * @throws DataManipulationException
	 */
	void  save(Map params) throws DataManipulationException;
	
	/**
	 * Read data using underlying dataframe HQL
	 * @param params - input parameters (in the where clause of HQL), they are matching by parameter names
	 * @return
	 * @throws DataManipulationException
	 */
	List  read(Map params) throws DataManipulationException;
	
	/**
	 * Deletes the record, defined by parameters 
	 * @param params - should contain map of parameters, required to identify the record
	 * @throws DataManipulationException
	 */
	void  delete(Map params) throws DataManipulationException;
	
	/**
	 * getting the result of the read method and converts it to the standard Json array
	 * @param result
	 * @return
	 */
	Map getJsonResult(List result);	
	
}

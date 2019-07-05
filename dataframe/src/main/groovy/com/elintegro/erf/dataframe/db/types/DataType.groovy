/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.dataframe.db.types



/**
 * 
 * @author Eugenel
 *
 */
public enum DataType {
	BIGINT(1), VARCHAR(2), DOUBLE(3),  BOOLEAN(4), DATE(5), DATETIME(6), INT(7), LONGTEXT(8), FLOAT(9), SHORT(10), ASSOCIATION(11)
	private int id;

	private DataType(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
	
	
	//http://www.jqwidgets.com/jquery-widgets-documentation/documentation/jqxdataadapter/jquery-data-adapter.htm
	//Possible values:  Possible values: 'string', 'date', 'number', 'float', 'int' and 'bool'
	public String getjQueryDataType(){
		switch (this.id) {
			case 1:
				return "number";
			case 2:
				return "string";
			case 3:
				return "number";
			case 4:
				return "bool";
			case 5:
				return "date";
			case 6:
				return "date";
			case 7:
				return "int";
			case 8:
				return "LONGTEXT";
			case 9:
				return "float";
			case 10:
				return "short";
			default :
				return "ASSOCIATION";
		}
	}


	public static DataType valueOf(int id) {
		switch (id) {
			case 1:
				return BIGINT;
			case 2:
				return VARCHAR;
			case 3:
				return DOUBLE;
			case 4:
				return BOOLEAN;
			case 5:
				return DATE;
			case 6:
				return DATETIME;
			case 7:
				return INT;
			case 8:
				return LONGTEXT;
			case 9:
				return FLOAT;
			case 10:
				return SHORT;
			default :
				return ASSOCIATION;
		}
	}

	public  String toString(){
		switch (this.id) {
			case 1:
				return "BIGINT";
			case 2:
				return "VARCHAR";
			case 3:
				return "DOUBLE";
			case 4:
				return "BOOLEAN";
			case 5:
				return "DATE";
			case 6:
				return "DATETIME";
			case 7:
				return "INT";
			case 8:
				return "LONGTEXT";
			case 10:
				return "SHORT";
			default :
				return "ASSOCIATION";
		}
	}
}

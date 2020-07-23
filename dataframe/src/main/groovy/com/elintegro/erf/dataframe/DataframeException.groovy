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

import groovy.util.logging.Slf4j

@Slf4j
class DataframeException extends Exception {
	Dataframe dfr

	public DataframeException (String message) {		
		super (message);
		log.error("DataframeException:" + message)
	}

	public DataframeException (Throwable cause) {
		super (cause);
		log.error("DataframeException:" + cause)
	}

	public DataframeException (String message, Throwable cause) {
		super (message, cause);
		log.error("DataframeException:" + message + " Original Exception: " + cause)
	}

	public DataframeException (Dataframe dfr, String message) {
		super (message);
		this.dfr = dfr
		log.error("DataframeException for dataframe: ${dfr.dataframeName}" + message)
	}

	public DataframeException (Dataframe dfr, Throwable cause) {
		super (cause);
		this.dfr = dfr
		log.error("DataframeException for dataframe: ${dfr.dataframeName}:" + cause)
	}

	public DataframeException (Dataframe dfr, String message, Throwable cause) {
		super (message, cause);
		this.dfr = dfr
		log.error("DataframeException for dataframe: ${dfr.dataframeName}:" + message + " Original Exception: " + cause)
	}
	public DataframeException (String dfrName, String message) {
		super (message);
		log.error("DataframeException for dataframe: ${dfrName}" + message)
	}

	public DataframeException (Throwable cause, String dfrName) {
		super (cause);
		log.error("DataframeException for dataframe: ${dfrName}:" + cause)
	}

	public DataframeException (String dfrName, String message, Throwable cause) {
		super (message, cause);
		this.dfr = dfr
		log.error("DataframeException for dataframe: ${dfrName}:" + message + " Original Exception: " + cause)
	}

}

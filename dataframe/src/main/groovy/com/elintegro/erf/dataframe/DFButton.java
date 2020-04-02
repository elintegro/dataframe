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

import java.util.List;
import java.util.Map;

public class DFButton {
	public String name;
	public String url;
	String type;
	String script="";
	String divForResults; // this field if filled with div to show the results of the action, if not provided, the dialog box will be popping up by default
	public org.springframework.beans.factory.config.RuntimeBeanReference refDataframe;
	boolean active = true;
	String refreshField;
	String buttonLayoutPlaceholder="";
	public String refField; // field to which the button is to be attached to
	public boolean refInDivId;
	Map dialogBoxActionParams; // For dialogbox parameters
	String hoverMessage="";
	Map image;
	Map callBackParams; // For callback successScripts, failureScripts etc
	String style;
	public String doBeforeSave; // Script just before Ajax save
	public boolean showAsDialog = false; //Show the refDataframe as Dialog??
	public boolean route = false; // Show as Route??
	public Map tooltip;
	public String attr; //add extra attribbubtes from descripter
	public  String layout=""; // layout for buttons
	public String doBeforeAjax; // Script just bbefore ajax call
	public String routeIdScript; //Id for the dataframe when route is true
	public List flexGridValues; //grid values for v-flex
	public Map showAsMenu;
	public String attachTo; //Attch the menu to another field
	public String roles; //"ROLE_ADMIN,ROLE_CUSTOMER"
	public String accessType; //ifAllGranted, ifAnyGranted (deafualt)
	public String classNames;
	public boolean scrollable;
	public boolean persistent;

}

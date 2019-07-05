/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.payment.base.response

import com.google.gson.Gson
import org.grails.web.json.JSONObject


trait Response {

    public boolean success = true;
    public String errorMessage = null;
    public JSONObject jsonObject = null;

    public static <T> T convertJsonStringResponse(String jsonString, Class<T> classOfT){
        return new Gson().fromJson(jsonString, classOfT);
    }

    public <T> T setSuccess(boolean isSuccess){
        this.success = isSuccess
        return (T)this
    }

    public <T> T setErrorMessage(String message){
        this.errorMessage = message
        return (T)this
    }

    public <T> T setJsonObject(JSONObject jsonObject){
        this.jsonObject = jsonObject
        return (T)this
    }

    public boolean getSuccess(){
        return this.success
    }

    public String getErrorMessage(){
        return  this.errorMessage
    }

    public JSONObject getJsonObject(){
        return  this.jsonObject
    }

}

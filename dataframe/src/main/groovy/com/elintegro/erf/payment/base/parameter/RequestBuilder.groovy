/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.payment.base.parameter


trait RequestBuilder {

    private Map<String, Object> parameters = new HashMap<String, Object>();

    /**
     * Add all the required parameters for different payment gate for request
     * @param jsonParam
     * @param obj
     * @return the same object
     */
    public <T> T with(final String jsonParam, final Object obj) {
        this.parameters.put(jsonParam, obj);
        return (T)this;
    }
    /**
     *
     * @param jsonParam
     * Remove the given param from request
     */
    public void without(final String jsonParam) {
        this.parameters.remove(jsonParam);
    }

    public Map<String, Object> asMap() {
        return Collections.unmodifiableMap(this.parameters);
    }

    public Map<String, Object> map() {
        return this.parameters
    }


}

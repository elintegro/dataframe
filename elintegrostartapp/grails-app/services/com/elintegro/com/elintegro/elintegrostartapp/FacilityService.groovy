/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.com.elintegro.elintegrostartapp

import com.elintegro.auth.User
import com.elintegro.gerf.DataframeController
import com.elintegro.elintegrostartapp.Facility
import grails.gorm.transactions.Transactional
import grails.util.Holders
import groovy.util.logging.Slf4j

import javax.servlet.http.HttpSession

@Slf4j
@Transactional
class FacilityService {
    def metaFieldService
    Facility getFacilityByUser(HttpSession session) {

        Facility facility = null

        String userId_ = session.getAttribute("userid")
        if ( userId_ == null ) {
            session.setAttribute("userid", (long) Holders.grailsApplication.config.guestUserId)
        }
        Long userId  = Long.valueOf(userId_)

        String hql = "select facility from Facility facility inner join facility.users as user where user.id=?"
        List<Facility> results = Facility.findAll(hql, [userId])
        if(results && results.size() > 0){
            facility = results.get(0)
        }
        return facility
    }

    void populateFacility(List<Object> domains, HttpSession session, Class... domainClasses){
        Facility facility = getFacilityByUser(session)
        populateFacility(domains, facility, domainClasses)
    }

    void populateFacility(List<Object> domains, Facility facility, Class... domainClasses){

        for(Object domainInstance: domains){
            for(Class entityClass: domainClasses){
                if (entityClass.isInstance(domainInstance)) {
                    try {
                       /* String facilityPropertyName = domainInstance.properties.entrySet().find {
                            return (it.key == 'facility' | it.key == 'facilities')
                        }?.key*/
                        def property = domainInstance.metaClass.properties.find{it.name=='facility'| it.name=='facilities'}
                        if(property.getType() == Set){
                            String facilityPropertyName = property.field.name.capitalize()
                            domainInstance."addTo$facilityPropertyName"(facility)
                        }else {
                            domainInstance.facility = facility
                        }
                        log.debug("Assigned!")
                    }catch(Throwable e){
                        log.debug("Error to ssign facility to the instance ! "+ entityClass.getName()+" Exception: " + e)
                    }
                }
            }
        }
    }
}

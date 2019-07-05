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

import grails.util.Holders
import groovy.util.logging.Slf4j
import org.grails.orm.hibernate.cfg.HibernatePersistentEntity
import org.hibernate.persister.entity.AbstractEntityPersister
import org.hibernate.persister.entity.SingleTableEntityPersister

/**
 * This class contents different objects that describes domain classes and helps to retrieve
 * various information about persistent entity and its underlying Database meta data
 */
@Slf4j
class DomainClassInfo {

    def  grailsApplication
    String key  //domain Simple name like "User"
    HibernatePersistentEntity value // One of the Doamin class representation
    Class clazz; //java domain class
    SingleTableEntityPersister persister// One of the Doamin class representation
    String tablename
    String domainAlias //Alias as it was defined in the HQL by a developer

    public DomainClassInfo(Class clazz, String domainAlias, String tableName, SingleTableEntityPersister persister) {
        this.tablename = tableName
        this.domainAlias = domainAlias
        this.clazz = clazz
        this.value = Holders.grailsApplication.mappingContext.getPersistentEntity(clazz.name)
        this.persister = persister
        key = clazz.getSimpleName()
    }

    public String getSimpleDomainName(){
        return key
    }

    public HibernatePersistentEntity getHpersistentEntity(){
        return value
    }

    public AbstractEntityPersister getPersister(){
        return persister
    }

    @Override
    public String toString(){
        return "Domain simple name:" + key + " Domain alias: " + domainAlias + " Domain class: " + clazz.name;
    }

    public String getDBFieldName(String fieldHibernetName){
        String[] fieldDbNameArr = persister.getPropertyColumnNames(fieldHibernetName)
        String fieldDbName = fieldHibernetName;
        if (fieldDbNameArr != null && fieldDbNameArr.size() > 0) {
            fieldDbName = fieldDbNameArr[0]
            if(fieldDbNameArr.size() > 1){
                log.debug("getPropertyColumnNames returns more then 1 value!: " + org.springframework.util.StringUtils.arrayToCommaDelimitedString(fieldDbNameArr))
            }
        }
        return fieldDbName.replaceAll('`',"")
    }
}

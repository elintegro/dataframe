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
import org.grails.datastore.mapping.model.PersistentProperty
import org.grails.datastore.mapping.model.types.Association
import org.grails.datastore.mapping.model.types.Basic
import org.grails.datastore.mapping.model.types.Custom
import org.grails.datastore.mapping.model.types.Embedded
import org.grails.datastore.mapping.model.types.EmbeddedCollection
import org.grails.datastore.mapping.model.types.Identity
import org.grails.datastore.mapping.model.types.ManyToMany
import org.grails.datastore.mapping.model.types.ManyToOne
import org.grails.datastore.mapping.model.types.OneToMany
import org.grails.datastore.mapping.model.types.OneToOne
import org.grails.datastore.mapping.model.types.Simple
import org.grails.datastore.mapping.model.types.TenantId
import org.grails.datastore.mapping.model.types.ToMany
import org.grails.datastore.mapping.model.types.ToOne
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
    String key  //domain Simple name like "User" TODO: this field should be renamed to "name" to reflect its real meaning
    HibernatePersistentEntity value // One of the Domain class representation
    Class clazz; //java domain class
    SingleTableEntityPersister persister// One of the Domain class representation
    String tablename
    String domainAlias //Alias as it was defined in the HQL by a developer or by deafault should be equal to the Domain class name

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

    public PersistentProperty getPropertyByName(String fieldName) {
        return value.getPropertyByName(fieldName)
    }

    public getRefDomainClass(String fieldName){
        if (isAssociation(fieldName)) {
            return getPropertyByName(fieldName).associatedEntity.getJavaClass()
        }
        return null
    }

    public boolean isAssociation(String fieldName){
        if (getPropertyByName(fieldName) instanceof Association){return true}
        return false
    }

    public boolean isBasic(String fieldName){
        if (getPropertyByName(fieldName) instanceof Basic){return true}
        return false
    }

    public boolean isCustom(String fieldName){
        if (getPropertyByName(fieldName) instanceof Custom){return true}
        return false
    }

    public boolean isEmbedded(String fieldName){
        if (getPropertyByName(fieldName) instanceof Embedded){return true}
        return false
    }

    public boolean isEmbeddedCollection(String fieldName){
        if (getPropertyByName(fieldName) instanceof EmbeddedCollection){return true}
        return false
    }

    public boolean isIdentity(String fieldName){
        if (getPropertyByName(fieldName) instanceof Identity){return true}
        return false
    }


    public boolean isManyToMany(String fieldName){
        if (getPropertyByName(fieldName) instanceof ManyToMany){return true}
        return false
    }

    public boolean isManyToOne(String fieldName){
        if (getPropertyByName(fieldName) instanceof ManyToOne){return true}
        return false
    }

    public boolean isOneToMany(String fieldName){
        if (getPropertyByName(fieldName) instanceof OneToMany){return true}
        return false
    }


    public boolean isOneToOne(String fieldName){
        if (getPropertyByName(fieldName) instanceof OneToOne){return true}
        return false
    }

    public boolean isSimple(String fieldName){
        if (getPropertyByName(fieldName) instanceof Simple){return true}
        return false
    }

    public boolean isTenantId(String fieldName){
        if (getPropertyByName(fieldName) instanceof TenantId){return true}
        return false
    }

    public boolean isToMany(String fieldName){
        if (getPropertyByName(fieldName) instanceof ToMany){return true}
        return false
    }

    public boolean isToOne(String fieldName){
        if (getPropertyByName(fieldName) instanceof ToOne){return true}
        return false
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


import com.elintegro.translator.FileValidationService
import com.elintegro.translator.Text
import grails.plugin.springsecurity.SpringSecurityService
import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import org.spockframework.compiler.model.Spec
import spock.lang.Shared
import spock.lang.Specification

import java.util.regex.Matcher
import java.util.regex.Pattern

class FileValidationServiceSpec extends Specification implements ServiceUnitTest<FileValidationService> {

    def setup() {
    }
    def setupSpec(){
    }
    void "test regular Expressions"() {
        given: "when key is in non-English alphabets"
            def key = "जन्मदिन"//this should fail
            def key1 = "vueUserProfileDataframe.person.firstName"//this should pass
            def key2 = " день рождения"// this should fail
            def key3 =" vueUserProfileDataframe.जन्मदिन.firstName"// this should fail
        when: "Key  are in Latin Alphabets"
            Pattern regExPattern = Pattern.compile(/^[a-zA-Z0-9\u0024@\u0024!%*?&#^-_. +]+\u0024/, Pattern.CASE_INSENSITIVE);
            Matcher matcher = regExPattern.matcher(key3);

        then: "the key should be saved into the database"
            matcher
    }


}


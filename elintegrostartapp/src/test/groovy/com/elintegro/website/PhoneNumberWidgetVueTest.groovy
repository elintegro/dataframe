package com.elintegro.website

import com.elintegro.erf.widget.vue.PhoneNumberWidgetVue
import grails.util.Holders
import org.grails.testing.GrailsUnitTest
import org.springframework.context.i18n.LocaleContextHolder
import spock.lang.Specification

class PhoneNumberWidgetVueTest extends Specification implements GrailsUnitTest {
    void unitTestForPhoneRegex(){
        given:

        def usRegex = /^(([(]?(\\d{2,4})[)]?)|(\\d{2,4})|([+1-9]+\\d{1,2}))?[-\\s]?(\\d{2,3})?[-\\s]?((\\d{7,8})|(\\d{3,4}[-\\s]\\d{3,4}))\$/

        when:
        PhoneNumberWidgetVue phoneNumberWidget = new PhoneNumberWidgetVue()
        def locale = LocaleContextHolder.getLocale()
        def regex2 = phoneNumberWidget.unitTestForPhoneRegex(locale )
        println(regex2)

        then:
        usRegex == regex2



    }
}

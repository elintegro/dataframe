package spring.globech.elintegroWebsite

import com.elintegro.erf.dataframe.vue.DataframeVue

beans {
    vueRefactorDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueRefactorDataframe']
        saveButton = true
        wrapInForm = false
        initOnPageLoad = true
        //Vue parameters
        isGlobal = true
        route = true
        vueStore = ["state": "loggedIn: false,\n"]

        hql = "select person.id, person.mainPicture,person.email, person.firstName, person.lastName, person.bday,  person.phone from Person as person where person.id=:id"
/*
        addFieldDef = [
                "inputText": [
                        "widget"      : "InputWidgetVue"
                ],
                "inputNumber": [
                        "widget"      : "NumberInputWidgetVue"
                ],
                "checkbox": [
                        "widget"      : "CheckboxWidgetVue"

                ],
                "datewidget": [
                        "widget"      : "DateWidgetVue",
                ],
                "emailWIdget": [
                        "widget"      : "EmailWidgetVue",
                ],
                "phonenumber": [
                        "widget"      : "PhoneNumberWidgetVue",
                        validate:true
                ],
                "passwordWidget": [
                        "widget"      : "PasswordWidgetVue",
                ],
                "PictureDisplay": [
                        "widget"      : "PictureDisplayWidgetVue",
                ],
                "TextArea": [
                        "widget"      : "TextAreaWidgetVue",
                ],
                "TextDisplay": [
                        "widget"      : "TextDisplayWidgetVue",
                ],
                "comboboxwidget": [
                        "widget"      : "ComboboxVue",
                        hql:"select language.ename from Language language"
                ],
                "mapWidget": [

                        "widget"      : "MapWidgetVue",
                ]

        ]
*/
        putFillInitDataMethod = false
        currentFrameLayout = ref("defaultDataframeLayout")
    }
}

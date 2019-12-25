package spring.globech.elintegroWebsite

import com.elintegro.erf.dataframe.vue.DataframeVue
import grails.util.Holders

beans {
    def contextPath = Holders.grailsApplication.config.rootPath

    vueInitDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueInitDataframe']
        saveButton = false
        wrapInForm = false

        initOnPageLoad = false
//        componentsToRegister = ["vueLoginDataframe"]
        //Vue parameters
        isGlobal = true
        vueStore = ["state": "loggedIn: false,\n"]

        putFillInitDataMethod = false
        currentFrameLayout = ref("emptyDataframeLayout")
    }
    vueElintegroNavigationButtonDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroNavigationButtonDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = false
        dataframeButtons = [home           : [name: "home", type: "link","flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            clientsProjects: [name: "clientsProjects", type: "link",showAsDialog:true, refDataframe: ref("vueClientProjectDataframe"),"flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            technologies   : [name: "techonologies", type: "link",url:"${contextPath}/ElintegroWebsite/renderUrlData", callBackParams:[successScript:""" let url = response.url;
                                                                                                                                                                              window.open("${contextPath}"+url, '_blank');
                                                                                                                                                                 """], "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            gettingStarted : [name: "gettingStarted", type: "link", "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            carrers        : [name: "carrers", type: "link", "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            contactUs      : [name: "contactUs", type: "link", "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            login          : [name: "login", type: "link", "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            register       : [name: "register", type: "link", "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']]]
        wrapButtons = false


        currentFrameLayout = ref("elintegroNavigationButtonLayout")
    }
    vueSubContainerDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueSubContainerDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = false
        currentFrameLayout = ref("subContainerLayout")

    }

    vueElintegroLogoDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroLogoDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = false
        addFieldDef = [
                "logo": [
                        "widget"      : "PictureDisplayWidgetVue",
                        "url"         : "${contextPath}/assets/elintegro_logo.png",
                        flexGridValues: ['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                        "attr"        : " contain ",
                        "height"      : "auto",
                        "width"       : "200",
                        //"min-width"   : "40"

                ]

        ]
        currentFrameLayout = ref("appNameDataframeLayout")

    }
    vueElintegroBannerDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroBannerDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = false
        addFieldDef = [
                "banner": [
                        "widget"      : "PictureDisplayWidgetVue",
                        "url"         : "${contextPath}/assets/elintegro_banner.jpg",
                        flexGridValues: ['xs12', 'sm12', 'md12', 'lg12', 'xl12'],


                ]
//                "person.firstName":[
//                        widget: "InputWidgetVue",
//                        "required": "required"
//                        ,"validate":["rule":["v => !!v || 'FirstName is required'", "v => (v && v.length <= 10) || 'FirstName must be less than 10'"]]
//                        ,"flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl4']],
//
//                "person.lastName":[
//                        widget: "InputWidgetVue",
//                        "required": "required"
//                        ,"flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl4']
//                        ,"validate":["rule":["v => !!v || 'LastName is required'", "v => (v && v.length <= 10) || 'LastName must be less than 10'"]]
//                ],
//                "person.bday":[
//                        widget: "DateWidgetVue",
//                        "required": "required"
//                        ,"flexGridValues":['xs12', 'sm6', 'md6', 'lg12', 'xl4']],
//                "person.contactEmail":[
//                        widget: "EmailWidgetVue",
//                        "required": "required"
//                        ,readOnly: true
//                        ,"flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']
//                        ,"validate":["rule":["v => !!v || 'E-mail is required'"]]
//                ],
//                "person.phone":[
//                        widget: "PhoneNumberWidgetVue",
//                        "required": "required"
//                        ,"validate":["rule":["v => !!v || 'Phone Number is required'"]]
//                ],
//                "person.languages":[
//                        widget: "MultiSelectComboboxVue"
//                        ,"flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl4']
//                        , hql: """select language.id as id, language.ename as ename from Language as language"""
//                        ,"displayMember":"ename"
//                        ,"valueMember":"id"
//                        , search:true
//                ]
// yesko dataframe ma arko kei use garera background image ma banner dina paryo vane layout ma gayera background image dine
//                or class banayera class ko properties css ma lekhne
        ]
        currentFrameLayout = ref("emptyDataframeLayout")

    }
    vueTechnologiesDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueTechnologiesDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = false
        //route : true
        addFieldDef = [
                "java"      : ["widget": "PictureDisplayWidgetVue", "url": "/elintegrostartapp/assets/java.PNG"],
                "javascript": ["widget": "PictureDisplayWidgetVue", "url": "/elintegrostartapp/assets/javascript.PNG"],
                "grails"    : ["widget": "PictureDisplayWidgetVue", "url": "/elintegrostartapp/assets/grailsphoto.PNG"],
                "vuejs"     : ["widget": "PictureDisplayWidgetVue", "url": "/elintegrostartapp/assets/vuejs.PNG"],
                "kafka"     : ["widget": "PictureDisplayWidgetVue", "url": "/elintegrostartapp/assets/kafka.PNG"],
                "oracle"    : ["widget": "PictureDisplayWidgetVue", "url": "/elintegrostartapp/assets/oracle.PNG"],
                "nodejs"    : ["widget": "PictureDisplayWidgetVue", "url": "/elintegrostartapp/assets/nodejs.PNG"],
                "kubernetes": ["widget": "PictureDisplayWidgetVue", "url": "/elintegrostartapp/assets/kubernetes.PNG"],
                "mysql"     : ["widget": "PictureDisplayWidgetVue", "url": "/elintegrostartapp/assets/mysql.PNG"],

        ]
        currentFrameLayout = ref("defaultDialogBoxLayout")


    }
    vueClientProjectDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueClientProjectDataframe']
        dataframeLabelCode = "Clients & Projects Details"
        isGlobal = true
        saveButton = false
        initOnPageLoad = true
        addFieldDef =[
                "clientProject": [
                        widget            : "GridWidgetVue"
                        , name            : "clientProject"



                        , hql             : """select clientProject.clientName as Clientname,
                                                clientProject.projectName as Projectname, clientProject.logo as Logo, 
                                                clientProject.description as Description,clientProject.linkToWebsite as LinkToWebsite from ClientProject clientProject"""
                        , gridWidth       : 820
                        , search          : true
                        , "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ]
                ]
        currentFrameLayout = ref("defaultDialogBoxLayout")

    }
    vueContactUsPageDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueContactUsPageDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = false


        currentFrameLayout = ref("defaultDialogBoxLayout")
    }
}

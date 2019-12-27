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
        isGlobal = true
        putFillInitDataMethod = false
        currentFrameLayout = ref("emptyDataframeLayout")
    }
    vueNavigationDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNavigationDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = false
        currentFrameLayout = ref("navigationLayout")
    }
    vueNavigationButtonDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNavigationButtonDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = true
        dataframeButtons = [home           : [name: "home", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueElintegroBannerDataframe"),"flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            clientsProjects: [name: "clientsProjects", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueClientProjectDataframe"),"flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            technologies   : [name: "techonologies", type: "link",route:true, routeIdScript: "0", refDataframe: ref("vueTechnologiesDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            gettingStarted : [name: "gettingStarted", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueGettingStartedDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            carrers        : [name: "carrers", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueCareersDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            contactUs      : [name: "contactUs", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueContactUsDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            login          : [name: "login", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueLoginDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            register       : [name: "register", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueRegisterDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']]]
        wrapButtons = false


        currentFrameLayout = ref("elintegroNavigationButtonLayout")
    }
    vueLogoDataframe(DataframeVue) {bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueLogoDataframe']
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
                ]
        ]
        currentFrameLayout = ref("appNameDataframeLayout")
    }
    vueElintegroBannerDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroBannerDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = false
        route = true
        addFieldDef = [
                "banner": [
                        "widget"      : "PictureDisplayWidgetVue",
                        "url"         : "${contextPath}/assets/elintegro_banner.jpg",
                        flexGridValues: ['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                ]
        ]
        currentFrameLayout = ref("appNameDataframeLayout")
    }
    vueClientProjectDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueClientProjectDataframe']
        dataframeLabelCode = "Clients & Projects Details"
        isGlobal = true
        saveButton = false
        initOnPageLoad = true
        route = true
        addFieldDef =[
                "clientProject": [
                        widget            : "GridWidgetVue"
                        , name            : "clientProject"
                        , hql             : """select clientProject.clientName as Clientname,
                                                clientProject.projectName as Projectname, clientProject.logo as Logo, 
                                                clientProject.description as Description,clientProject.linkToWebsite as LinkToWebsite from ClientProject clientProject"""
                        , gridWidth       : 820
                        , search          : true
                        ,avatarAlias      :'Logo'
                        ,url:'/assets'
                        , "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ]
        ]
        currentFrameLayout = ref("defaultRouteDataframeLayout")
    }
    vueTechnologiesDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueTechnologiesDataframe']
        dataframeLabelCode = "Technologies"
        isGlobal = true
        saveButton = false
        initOnPageLoad = false
        route = true
        addFieldDef = [
                "java"      : ["widget": "PictureDisplayWidgetVue", "url": "${contextPath}/assets/java.PNG"],
                "javascript": ["widget": "PictureDisplayWidgetVue", "url": "${contextPath}/assets/javascript.PNG"],
                "grails"    : ["widget": "PictureDisplayWidgetVue", "url": "${contextPath}/assets/grailsphoto.PNG"],
                "vuejs"     : ["widget": "PictureDisplayWidgetVue", "url": "${contextPath}/assets/vuejs.PNG"],
                "kafka"     : ["widget": "PictureDisplayWidgetVue", "url": "${contextPath}/assets/kafka.PNG"],
                "oracle"    : ["widget": "PictureDisplayWidgetVue", "url": "${contextPath}/assets/oracle.PNG"],
                "nodejs"    : ["widget": "PictureDisplayWidgetVue", "url": "${contextPath}/assets/nodejs.PNG"],
                "kubernetes": ["widget": "PictureDisplayWidgetVue", "url": "${contextPath}/assets/kubernetes.PNG"],
                "mysql"     : ["widget": "PictureDisplayWidgetVue", "url": "${contextPath}/assets/mysql.PNG"],
        ]
        currentFrameLayout = ref("defaultRouteDataframeLayout")
    }
    vueGettingStartedDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueGettingStartedDataframe']
        dataframeLabelCode = "Getting Started Page"
        isGlobal = true
        saveButton = false
        initOnPageLoad = false
        route = true
        currentFrameLayout = ref("defaultRouteDataframeLayout")
    }
    vueCareersDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueCareersDataframe']
        dataframeLabelCode = "Careers Page"
        isGlobal = true
        saveButton = false
        initOnPageLoad = false
        route = true
        currentFrameLayout = ref("defaultRouteDataframeLayout")
    }

    vueContactUsDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueContactUsPageDataframe']
        dataframeLabelCode = "Contact Us  Page"
        isGlobal = true
        saveButton = false
        initOnPageLoad = false
        route = true
        currentFrameLayout = ref("defaultRouteDataframeLayout")
    }
    vueLoginDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueLoginDataframe']
        dataframeLabelCode = "Login Page"
        isGlobal = true
        saveButton = false
        initOnPageLoad = false
        route = true
        currentFrameLayout = ref("defaultRouteDataframeLayout")
    }
    vueRegisterDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueRegisterDataframe']
        dataframeLabelCode = "Register Page"
        isGlobal = true
        saveButton = false
        initOnPageLoad = false
        route = true
        currentFrameLayout = ref("defaultRouteDataframeLayout")
    }
}

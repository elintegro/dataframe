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
    vueLogoDataframe(DataframeVue) { bean ->
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
    vueNavigationButtonDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNavigationButtonDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = false
        dataframeButtons = [home           : [name: "home", type: "link","flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            clientsProjects: [name: "clientsProjects", type: "link","flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            technologies   : [name: "techonologies", type: "link",url:"${contextPath}/ElintegroWebsite/renderUrlData",
                                              callBackParams:[successScript:""" let url = response.url;window.open("${contextPath}"+url, '_blank');"""],
                                              "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            gettingStarted : [name: "gettingStarted", type: "link", "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            carrers        : [name: "carrers", type: "link", "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            contactUs      : [name: "contactUs", type: "link", "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            login          : [name: "login", type: "link", "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            register       : [name: "register", type: "link", "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']]]
        wrapButtons = false
        currentFrameLayout = ref("navigationButtonLayout")
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
                     ]
        currentFrameLayout = ref("emptyDataframeLayout")
    }
}


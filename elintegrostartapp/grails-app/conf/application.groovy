import grails.util.Environment

// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
garils.hql.test.maxSize=10
grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
        all:           '*/*',
        atom:          'application/atom+xml',
        css:           'text/css',
        csv:           'text/csv',
        form:          'application/x-www-form-urlencoded',
        html:          ['text/html','application/xhtml+xml'],
        js:            'text/javascript',
        json:          ['application/json', 'text/json'],
        multipartForm: 'multipart/form-data',
        rss:           'application/rss+xml',
        text:          'text/plain',
        xml:           ['text/xml', 'application/xml'],
        doc:           ['application/msword'],
        dot:           ['application/msword'],
        docx:          'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
        dotx:          ['application/vnd.openxmlformats-officedocument.wordprocessingml.template'],
        docm:           'application/vnd.ms-word.document.macroEnabled.12',
        dotm:          'application/vnd.ms-word.template.macroEnabled.12',
        xls:           'application/vnd.ms-excel',
        xlt:           'application/vnd.ms-excel',
        xla:           'application/vnd.ms-excel',
        xlsx:          'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
        xltx:          'application/vnd.openxmlformats-officedocument.spreadsheetml.template',
        xlsm:          'application/vnd.ms-excel.sheet.macroEnabled.12',
        xltm:          'application/vnd.ms-excel.template.macroEnabled.12',
        xlam:          'application/vnd.ms-excel.addin.macroEnabled.12',
        xlsb:          'application/vnd.ms-excel.sheet.binary.macroEnabled.12',
        ppt:           'application/vnd.ms-powerpoint',
        pot:           'application/vnd.ms-powerpoint',
        pps:           'application/vnd.ms-powerpoint',
        ppa:           'application/vnd.ms-powerpoint',
        pptx:          'application/vnd.openxmlformats-officedocument.presentationml.presentation',
        potx:          'application/vnd.openxmlformats-officedocument.presentationml.template',
        ppsx:          'application/vnd.openxmlformats-officedocument.presentationml.slideshow',
        ppam:          'application/vnd.ms-powerpoint.addin.macroEnabled.12',
        pptm:          'application/vnd.ms-powerpoint.presentation.macroEnabled.12',
        potm:          'application/vnd.ms-powerpoint.template.macroEnabled.12',
        ppsm:          'application/vnd.ms-powerpoint.slideshow.macroEnabled.12',
        mdb:           'application/vnd.ms-access',
]

grails.resources.pattern = '/**'

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// Make all properties nullable by default
grails.gorm.default.constraints = {
    '*'(nullable: true)
}

grails.gorm.failOnError = true

vue.flexGridValues.Default = ['xs12', 'sm12', 'md4', 'lg4', 'xl4']




environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}


//Guest user
guestUserId=1
adminUsername = "eugenelip@gmail.com"
guestUsername = "elitegro.himalaya@gmail.com"
guestPassword = "pasSw1rd"

myconfig {
    myvariable {
        workdir = 0
    }
}


// google Maps Api key
googleMapsApi{
    apiKey='AIzaSyBAAxHUhrxxl_2ZSpVtGeMX-1Fs83tunNU'
}

// Images and files storage location
images {
    saveLocation {
        local=true
        s3 = false
    }
//	storageLocation = "/opt/tomcat-8/webapps"
    storageLocation = "/usr/local/opt/apache-tomcat-9.0.33/webapps"
    environments{
        development {
            storageLocation = "${userHome}/.$appName"
        }
    }
    imageDirectory = "images"
    defaultImageName = "default_profile.jpg"
    defaultImagePath = "assets/$defaultImageName"
    defaultImageForPdf = "defaultPdfIcon.PNG"
    defaultImagePathForPdf = "assets/$defaultImageForPdf"
    defaultImageForExcel = 'defaultExcelFileIcon.PNG'
    defaultImagePathForExcel = "assets/$defaultImageForExcel"
    defaultImageForDocFile = 'defaultDocFileIcon.PNG'
    defaultImagePathForDocFile = "assets/$defaultImageForDocFile"
    defaultImageForCsvFile = 'defaultCsvIcon.PNG'
    defaultImagePathForCsvFile = "assets/$defaultImageForCsvFile"



}
grails {
    controllers {
        upload {
            maxFileSize= 10485760 //10MB limit for file upload
            maxRequestSize= 10485760
        }
    }
}
aws {
    s3 {
        //credentials goes here
        /*aws_access_key_id = "AKIAJFG4RGQDAU6KO3ZQ"
        aws_secret_access_key = "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"*/
        aws_access_key_id = "AKIAJBKWRVALZJ63VITQ"
        aws_secret_access_key = "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"
//		defaultS3Url = "https://elintegro.s3.amazonaws.com"
        defaultS3Url = "https://s3.us-east-2.amazonaws.com"
//		defaultImageValue = "bernadette-gatsby-11348-unsplash.jpg"
        //buckets go here
        buckets {
            imageUpload = "elintegro1"
        }

    }
}
//mail config
grails{
    mail {
        host= "smtp.gmail.com"
        port= 465
        username= "elintegro.himalaya@gmail.com"
        password= "himalaya123"
        props=[
                "mail.smtp.auth": "true",
                "mail.smtp.socketFactory.port": "465",
                "mail.smtp.socketFactory.class": "javax.net.ssl.SSLSocketFactory",
                "mail.smtp.socketFactory.fallback": "false"]
    }
}

ui{
    register{
        environments{
            development {
                emailSubject = "Elintegro@localhost"
            }
            qa {

                emailSubject = "Elintegro@Qa"
            }
            production{

                emailSubject = "Elintegro@Live"
            }
        }
    }
}
//regex for validation
regex{
    email="^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))\$"
    phone ="^(([(]?(\\d{2,4})[)]?)|(\\d{2,4})|([+1-9]+\\d{1,2}))?[-\\s]?(\\d{2,3})?[-\\s]?((\\d{7,8})|(\\d{3,4}[-\\s]\\d{3,4}))\$"
}
// Added by the JQuery Validation UI plugin:
jqueryValidationUi {
    errorClass = 'error'
    validClass = 'valid'
    onsubmit = true
    renderErrorsOnTop = false

    qTip {
        packed = true
        classes = 'ui-tooltip-red ui-tooltip-shadow ui-tooltip-rounded'
    }

    /*
      Grails constraints to JQuery Validation rules mapping for client side validation.
      Constraint not found in the ConstraintsMap will trigger remote AJAX validation.
    */
    StringConstraintsMap = [
            blank:'required', // inverse: blank=false, required=true
            creditCard:'creditcard',
            email:'email',
            inList:'inList',
            minSize:'minlength',
            maxSize:'maxlength',
            size:'rangelength',
            matches:'matches',
            notEqual:'notEqual',
            url:'url',
            nullable:'required',
            unique:'unique',
            validator:'validator'
    ]

    // Long, Integer, Short, Float, Double, BigInteger, BigDecimal
    NumberConstraintsMap = [
            min:'min',
            max:'max',
            range:'range',
            notEqual:'notEqual',
            nullable:'required',
            inList:'inList',
            unique:'unique',
            validator:'validator'
    ]

    CollectionConstraintsMap = [
            minSize:'minlength',
            maxSize:'maxlength',
            size:'rangelength',
            nullable:'required',
            validator:'validator'
    ]

    DateConstraintsMap = [
            min:'minDate',
            max:'maxDate',
            range:'rangeDate',
            notEqual:'notEqual',
            nullable:'required',
            inList:'inList',
            unique:'unique',
            validator:'validator'
    ]

    ObjectConstraintsMap = [
            nullable:'required',
            validator:'validator'
    ]

    CustomConstraintsMap = [
            phone:'true', // International phone number validation
            phoneUS:'true',
            alphanumeric:'true',
            letterswithbasicpunc:'true',
            lettersonly:'true'
    ]
}

dataSource {
    pooled = false
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
    jmxExport = true
//	username = "developer"
//	password = "java11"
}

grails.reload.enabled = true

// environment specific settings
environments {jdbc:mysql://localhost:3308/ elintegro_website_db_dev
development {
    server.contextPath = "/"
    rootPath = ""
    server.port = 8099
    grails.serverURL = "http://localhost:${server.port}"
    grails.plugin.springsecurity.ui.register.emailFrom='elintegro@localhost'
    dataSource {
        logSql = true
//	  	 	dbCreate = 'create-drop' //"update" // one of 'create', 'create-drop','update'
        dbCreate = 'update' //"update" // one of 'create', 'create-drop','update'
        url = "jdbc:mysql://localhost:3306/elintegro_website_db_dev?useUnicode=true&characterEncoding=UTF-8"
//			username = "root"
//			password = "qbohfoj"
        username = "developer"
        password = "java11"

    }
}
test {
    dataSource {
        logSql = true
        dbCreate = 'create-drop'
        url = "jdbc:mysql://localhost:3306/elintegrostartapp_db"
        username = "root"
        password = "root"
    }
}

develop {
    dataSource {
        //CHANGE DATASOURCE HERE
        dbCreate = 'create-drop' //"update" // one of 'create', 'create-drop','update'
//			dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
        url = "jdbc:mysql://localhost:3306/elintegrostartapp_db" // todo:change after fix the server
        username = "root"
        password = "root"
        logSql = true
        properties {
            jmxEnabled = true
            initialSize = 5
            maxActive = 50
            minIdle = 5
            maxIdle = 25
            maxWait = 10000
            maxAge = 10 * 60000
            timeBetweenEvictionRunsMillis = 5000
            minEvictableIdleTimeMillis = 60000
            validationQuery = "SELECT 1"
            validationQueryTimeout = 3
            validationInterval = 15000
            testOnBorrow = true
            testWhileIdle = true
            testOnReturn = false
            jdbcInterceptors = "ConnectionState"
            defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED
        }
    }
}

qa {
    server.contextPath = "/"
    rootPath = ""
    grails.serverURL = "https://qa.elintegro.com"
    grails.plugin.springsecurity.ui.register.emailFrom='elintegro@QA'
    dataSource {
        logSql = true
//			dbCreate = 'create-drop' //"update" // one of 'create', 'create-drop','update'
        dbCreate = 'update' //"update" // one of 'create', 'create-drop','update'
//			url = " jdbc:mysql://0.tcp.ngrok.io:15905/gcerf"
        url = "jdbc:mysql://95.217.159.62:3316/elintegro_website_db_qa?useUnicode=true&characterEncoding=UTF-8"
        username = "developer"
        password = "java1177"
        logSql = true
//            pooled = true
//            jmxExport = true
//            driverClassName = "com.mysql.jdbc.Driver"
        properties {
            jmxEnabled = true
            initialSize = 5
            maxActive = 50
            minIdle = 5
            maxIdle = 25
            maxWait = 10000
            maxAge = 10 * 60000
            timeBetweenEvictionRunsMillis = 5000
            minEvictableIdleTimeMillis = 60000
            validationQuery = "SELECT 1"
            validationQueryTimeout = 3
            validationInterval = 15000
            testOnBorrow = true
            testWhileIdle = true
            testOnReturn = false
            jdbcInterceptors = "ConnectionState"
            defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED
        }
    }
}

production {
    server.contextPath = "/"
    rootPath = ""
    grails.serverURL = "https://appfactory.elintegro.com"
    grails.plugin.springsecurity.ui.register.emailFrom='elintegro.himalaya'
    dataSource {
        logSql = true
//			dbCreate = 'create-drop' //"update" // one of 'create', 'create-drop','update'
        dbCreate = 'update' //"update" // one of 'create', 'create-drop','update'
        url = "jdbc:mysql://95.217.159.62:3316/elintegro_website_db_prod?useUnicode=true&characterEncoding=UTF-8"
        username = "developer"
        password = "java1177"

        logSql = true

        properties {
            jmxEnabled = true
            initialSize = 5
            maxActive = 50
            minIdle = 5
            maxIdle = 25
            maxWait = 10000
            maxAge = 10 * 60000
            timeBetweenEvictionRunsMillis = 5000
            minEvictableIdleTimeMillis = 60000
            validationQuery = "SELECT 1"
            validationQueryTimeout = 3
            validationInterval = 15000
            testOnBorrow = true
            testWhileIdle = true
            testOnReturn = false
            jdbcInterceptors = "ConnectionState"
            defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED
        }
    }
}
docker {
    server.contextPath = "/"
    rootPath = ""
    grails.serverURL = "https://web.elintegro.com"
    grails.plugin.springsecurity.ui.register.emailFrom='elintegro@QA'
    dataSource {
        logSql = true
//		dbCreate = 'create-drop' //"update" // one of 'create', 'create-drop','update'
        dbCreate = 'update' //"update" // one of 'create', 'create-drop','update'
        url = "jdbc:mysql://95.217.159.62:3316/elintegro_website_db_dev?useUnicode=true&characterEncoding=UTF-8"
        username = "developer"
        password = "java1177"
        logSql = true
//            pooled = true
//            jmxExport = true
//            driverClassName = "com.mysql.jdbc.Driver"
        properties {
            jmxEnabled = true
            initialSize = 5
            maxActive = 50
            minIdle = 5
            maxIdle = 25
            maxWait = 10000
            maxAge = 10 * 60000
            timeBetweenEvictionRunsMillis = 5000
            minEvictableIdleTimeMillis = 60000
            validationQuery = "SELECT 1"
            validationQueryTimeout = 3
            validationInterval = 15000
            testOnBorrow = true
            testWhileIdle = true
            testOnReturn = false
            jdbcInterceptors = "ConnectionState"
            defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED
        }
    }
}
}
//grails.server.port.https = 9090
loginWithSpringSecurity = true

grails{
    cors{
        enabled = true
    }
    plugin {
        springsecurity{
            oauth2{
                providers{
                    google {
                        api_key = '252139291193-dv3ekugm57up532d0e0g1lg1jp4fpir4.apps.googleusercontent.com'
                        api_secret= '9Iqwxj02b3F4c86FCle8z8w0'
                        successUri= "/springSecurityOAuth2/onSuccess?provider=google"
                        failureUri= "/oauth2/google/error"
                        callback= "/oauth2/google/callback"
                    }
                    facebook {
                        api_key = '3331166770333939'
                        api_secret= '6b4892fbb53f6cce09c2ad83d1d5f44c'
                        successUri= "/springSecurityOAuth2/onSuccess?provider=facebook"
                        failureUri= "/oauth2/facebook/error"
                        callback= "/oauth2/facebook/callback"
                    }
                }
                registration {
                    askToLinkOrCreateAccountUri = "/loginWithGoogle/linkYourElintegroAccountWithGoogle"
                }

            }
            successHandler {
                defaultTargetUrl = "${grails.serverURL}/login/success"
                //defaultTargetUrl = "${grails.serverURL}/authSuccessHandler/success"
            }

            password {
                algorithm = 'bcrypt'
                encodeHashAsBase64 = false
                if (Environment.current == Environment.TEST) {
                    bcrypt {
                        logrounds = 4
                    }
                    hash {
                        iterations = 1
                    }
                } else {
                    bcrypt {
                        logrounds = 10
                    }
                    hash {
                        iterations = 10000
                    }
                }
            }
        }
    }
}



grails.plugin.springsecurity.providerNames = ['daoAuthenticationProvider', 'anonymousAuthenticationProvider', 'rememberMeAuthenticationProvider']
//tag::filterChain[]
String ANONYMOUS_FILTERS = 'anonymousAuthenticationFilter,restTokenValidationFilter,restExceptionTranslationFilter,filterInvocationInterceptor' // <1>
grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/assets/**',      filters: 'none'],
        [pattern: '/**/js/**',       filters: 'none'],
        [pattern: '/**/css/**',      filters: 'none'],
        [pattern: '/**/images/**',   filters: 'none'],
        [pattern: '/**/favicon.ico', filters: 'none'],
//		[pattern: '/', filters: ANONYMOUS_FILTERS], // <1>
//		[pattern: '/auth/success', filters: ANONYMOUS_FILTERS], // <1>
//		[pattern: '/oauth/authenticate/google', filters: ANONYMOUS_FILTERS], // <1>
//		[pattern: '/oauth/callback/google', filters: ANONYMOUS_FILTERS], // <1>
//		[pattern: '/api/**', filters:'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter'],
//		[pattern: '/elintegrostartapp/**', filters:'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter'],
//
        [pattern: '/**', filters:'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter']
]
//end::filterChain[]

//tag::logoutHandlers[]
grails.plugin.springsecurity.logout.handlerNames = ['rememberMeServices', 'securityContextLogoutHandler', 'cookieClearingLogoutHandler']
//end::logoutHandlers[]
/*def appName = grails.util.Metadata.current.'app.name'
def baseURL = grails.serverURL ?: "http://127.0.0.1:8080/${appName}"*/
//grails.plugin.springsecurity.ui.register.postRegisterUrl="/"
//grails.plugin.springsecurity.ui.register.defaultRoleNames = ['ROLE_ADMIN']
//grails.plugin.springsecurity.userLookup.userDomainClassName="com.elintegro.auth.User"

//for springsecurity mail
grails.plugin.springsecurity.ui.register.emailBody = '''\
Hi $user.firstName,<br/>
<br/>
Thank you for choosing us. Your registration is almost complete.
click&nbsp;<a href="$url">here</a> to finish your registration.
<br/><br/>
'''
grails.plugin.springsecurity.ui.forgotPassword.emailBody = '''\
Hi $user.firstName,<br/>
<br/>
Please, click
&nbsp;<a href="$url">here</a> to reset your password.
<br/><br/>
'''
emailService.emailWithPassword = '''\
Hi $user.firstName,<br/>
<br/>
Thank you for choosing us. Your registration is almost complete.
click&nbsp;<a href="$url">here</a> to finish your registration.
<br/><br/>You can login using the temporary password: $password<br/><br/>
'''
contactUsEmailService.emailWithInformation = '''Hello $name,<br/>
This is the test for email sending service.
Thank you for choosing us. Your registration is almost complete.
'''

registerService.emailInfoAfterSignUp = '''Hi ${name}, <br/>
We created a temporary password for you so you can start defining your app and making it reality! <br/>

Username:\t ${currentUser} <br/>
Password:\t ${password} <br/>
Click <a href="$url">here</a> to auto-login. <br/>
Click <a href="$urlToChangePassword">here</a> to change your password.
'''
registerService.emailSubjectAfterSignUp = ''' ${name},
welcome. Next steps to build your app.
'''
elintegro.contuctus.email.sendto = "elintegroinc@gmail.com, pangenirabindra5@gmail.com,sumanneupane19982055@gmail.com, shai@gmail.com,rabindra@gmail.com"

application.languages = """'fr','en','es'"""

//elintegro.contuctus.email.interval = 20000     //send email once in 20 seconds
//elintegro.contuctus.email.interval = 5*60*1000 //send email once in 5 minutes.
elintegro.contuctus.email.interval = 12*60*60*1000//send email once in 12 hours.


grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.rejectIfNoRule = false
grails.plugin.springsecurity.fii.rejectPublicInvocations =false
grails.plugin.springsecurity.ui.register.defaultRoleNames = []
//grails.plugin.springsecurity.ui.register.defaultRoleNames = ['ROLE_ADMIN']
//Make sure the default ROLE is one of the roles in ROLE table!
//Otherwise SPRING Security Plugin will fail to verify a new user and the exception will be totally mis-leading:
//No such property: transactionStatus for class: grails.plugin.springsecurity.ui.SpringSecurityUiService
//The real error is that there is no such a role and as a foreign ke

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.elintegro.auth.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.elintegro.auth.UserRole'
grails.plugin.springsecurity.authority.className = 'com.elintegro.auth.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        [pattern: '/',               access: ['permitAll']],
        [pattern: '/error',          access: ['permitAll']],
        [pattern: '/index',          access: ['permitAll']],
        [pattern: '/index.gsp',      access: ['permitAll']],
        [pattern: '/shutdown',       access: ['permitAll']],
        [pattern: '/assets/**',      access: ['permitAll']],
        [pattern: '/**/js/**',       access: ['permitAll']],
        [pattern: '/**/css/**',      access: ['permitAll']],
        [pattern: '/**/images/**',   access: ['permitAll']],
        [pattern: '/**/favicon.ico', access: ['permitAll']]
]

//grails.plugin.springsecurity.filterChain.chainMap = [
//	[pattern: '/assets/**',      filters: 'none'],
//	[pattern: '/**/js/**',       filters: 'none'],
//	[pattern: '/**/css/**',      filters: 'none'],
//	[pattern: '/**/images/**',   filters: 'none'],
//	[pattern: '/**/favicon.ico', filters: 'none'],
//	[pattern: '/**',             filters: 'JOINED_FILTERS']
//]
//
//grails.plugin.springsecurity.filterChain.chainMap = [
//		[pattern: '/api**', filters:'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter'],
//		[pattern: '/**', filters:'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter']
//]

billing.billGracePeriod = 5

environments {
    development {
        paypal.clientID="AbWoxLeyrjlKjbYMUb2F8_MvMa_MXW90Ff-HqIBOesadKW115iLgWQXlgaQOZhuN_sFntpTDVuCYZPsj"
        paypal.clientSecret="EDHzoptZCglHaOQ-yUBBNl7_KzOzL61gxU4V9ArqWrE7eiDrt8wJqGr6pq-s62nJi_i2CSuFLnlt7ssi"

        notification{
            facebook.pageId = "1828091517465599"
            facebook.accessToken = "EAAEOyZADSRZBIBAJisdG6TIntmW2tpIJTwqtvcL3jCtBF0UeUkr630Ua6v6cZBd5NerurYy1TBhYv9YPXiiRbpsIAvpwmgtkYs15MCGwXzZC94nX3SY0dlKFdZB2QTiDRy1VesV7PaLIRQJtkAyb3PSZClnhtwKf0U1afjuPFYzEcS0ZC88CD49"
        }
    }
    qa{
        paypal.clientID="AbWoxLeyrjlKjbYMUb2F8_MvMa_MXW90Ff-HqIBOesadKW115iLgWQXlgaQOZhuN_sFntpTDVuCYZPsj"
        paypal.clientSecret="EDHzoptZCglHaOQ-yUBBNl7_KzOzL61gxU4V9ArqWrE7eiDrt8wJqGr6pq-s62nJi_i2CSuFLnlt7ssi"

        paypal.webhookCustomUrl = "https://0e5bf8a1.ngrok.io/elintegrostartapp/paypal/webHookListener"

        notification{
            facebook.pageId = "2131969000353027"
            facebook.accessToken = "EAAFGa6RClrYBALJs9oCux43G6vO4JgPMaYmsPUZB9DPjt595oxAP2iZBZCymjOJqxgLXxhMPMZAeb2LUQmpbd3se0LFpilp0kFGa1qfZA67TyU8Bzdm3P50d854DWyfc69QRGboHf4N2Pm1SkIpmX2MLseprIN2mSkIciZApsGvJpHMGkIGsUCiSU7pCKGjcMZD"
        }
    }
    production {
        paypal.clientID="AbWoxLeyrjlKjbYMUb2F8_MvMa_MXW90Ff-HqIBOesadKW115iLgWQXlgaQOZhuN_sFntpTDVuCYZPsj"
        paypal.clientSecret="EDHzoptZCglHaOQ-yUBBNl7_KzOzL61gxU4V9ArqWrE7eiDrt8wJqGr6pq-s62nJi_i2CSuFLnlt7ssi"
        paypal.webhookCustomUrl = "https://ec2-18-219-203-245.us-east-2.compute.amazonaws.com/elintegrostartapp/paypal/webHookListener"

        notification{
            facebook.pageId = "1828091517465599"
            facebook.accessToken = "EAAEOyZADSRZBIBAJisdG6TIntmW2tpIJTwqtvcL3jCtBF0UeUkr630Ua6v6cZBd5NerurYy1TBhYv9YPXiiRbpsIAvpwmgtkYs15MCGwXzZC94nX3SY0dlKFdZB2QTiDRy1VesV7PaLIRQJtkAyb3PSZClnhtwKf0U1afjuPFYzEcS0ZC88CD49"
        }
    }
}

environments {
    development {
        static_home_page_url = "http://localhost:8099/#/"
    }
    qa{
        static_home_page_url = "https://qa.elintegro.com"

    }
    production {
        static_home_page_url = "https://www.elintegro.com"
    }

}

// Added by the Spring Security OAuth2 Google Plugin:
grails.plugin.springsecurity.oauth2.domainClass = 'com.elintegro.auth.OAuthID'

dataframe.right_to_left_language = false
quizzableUrl = "https://quizzable.elintegro.com/"

application.languages = """'fr','en','es'"""


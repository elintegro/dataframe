<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Elintegro Start App</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/assets/vuejs/tooltip.css?compile=false"/>
    <script type="text/javascript" src="/assets/jquery/jquery-1.11.2.js?compile=false"></script>
    <script type="text/javascript" src="/assets/jquery/dateformat.js?compile=false"></script>
    <link rel="stylesheet" href="/assets/vuejs/vue-material.css?compile=false"/>
    <link rel="stylesheet" href="/assets/vuejs/vuetify.min.css?compile=false"/>
    <link rel="stylesheet" href="//cdn.materialdesignicons.com/3.9.97/css/materialdesignicons.min.css">
    <link rel="stylesheet" href="/assets/vuejs/gc-vue.css?compile=false"/>
</head>
<body>
<style>
.viewer-canvas {
    background-color: black;
}

.v-table__overflow {
    /*overflow-x: inherit;*/
    overflow-y: inherit;
}

.hidden {
    display: none;
}
</style>
<div id="dfr"></div>
<div id='app'>
    <v-app class="app" style="background-color:#fff;">
        <sectionLayout/>
    </v-app>
</div>
<script type="text/javascript" src="/assets/vuejs/vue.js?compile=false"></script>
<script type="text/javascript" src="/assets/vuejs/vuetify.js?compile=false"></script>
<script type="text/javascript" src="/assets/vuejs/vue-router.js?compile=false"></script>
<script type="text/javascript" src="/assets/vuejs/vuex.js?compile=false"></script>
<!--asset:javascript src="vuejs/vue-spring-security.min.js"/-->
<!--script type="text/javascript" src="https://unpkg.com/vue-spring-security"></script-->
<script src="//cdn.jsdelivr.net/npm/sortablejs@1.8.4/Sortable.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.18.0/axios.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue-resource@1.5.1"></script>
<script src="https://cdn.jsdelivr.net/npm/es6-promise@4/dist/es6-promise.auto.js"></script>
<script src="https://unpkg.com/popper.js"></script>
<script src="https://unpkg.com/v-tooltip"></script>
<script type="text/javascript" src="/assets/vuejs/v-eutil.min.js?compile=false"></script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBAAxHUhrxxl_2ZSpVtGeMX-1Fs83tunNU"></script>
<script type="text/javascript" src="/assets/erf/erfVueController.js?compile=false"></script>
<script type="text/javascript" src="/assets/vuejs/vuex.js?compile=false"></script>
<script>
    let store = new Vuex.Store({
        state: {
            dataframeShowHideMaps: {},
            vueTechnologiesDataframe: {
                key: '',
                doRefresh: '',
            },
            vueElintegroNavigationButtonDataframe: {
                key: '',
                doRefresh: '',
            },
            vueElintegroLogoDataframe: {
                key: '',
                doRefresh: '',
            },
            vueElintegroBannerDataframe: {
                key: '',
                doRefresh: '',
            },
            dataframeBuffer: {},
        },
        mutations: {},
    })
    Vue.component('vueTechnologiesDataframe', {
        name: 'vueTechnologiesDataframe',
        template: `<v-flex xs12 sm12 md12 lg12 xl12><v-card round class='rounded-card' ><v-toolbar dark color="light-blue darken-2"><v-toolbar-title></v-toolbar-title>
                                <v-spacer></v-spacer><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-toolbar><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueTechnologiesDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm6 md6 lg4 xl4  ><v-img
           id = "vueTechnologiesDataframe_java"
          :src="vueTechnologiesDataframe_java"

          :alt = "vueTechnologiesDataframe_java_alt"
          aspect-ratio=""



           ></v-img></v-flex>

						<v-flex xs12 sm6 md6 lg4 xl4  ><v-img
           id = "vueTechnologiesDataframe_javascript"
          :src="vueTechnologiesDataframe_javascript"

          :alt = "vueTechnologiesDataframe_javascript_alt"
          aspect-ratio=""



           ></v-img></v-flex>

						<v-flex xs12 sm6 md6 lg4 xl4  ><v-img
           id = "vueTechnologiesDataframe_grails"
          :src="vueTechnologiesDataframe_grails"

          :alt = "vueTechnologiesDataframe_grails_alt"
          aspect-ratio=""



           ></v-img></v-flex>

						<v-flex xs12 sm6 md6 lg4 xl4  ><v-img
           id = "vueTechnologiesDataframe_vuejs"
          :src="vueTechnologiesDataframe_vuejs"

          :alt = "vueTechnologiesDataframe_vuejs_alt"
          aspect-ratio=""



           ></v-img></v-flex>

						<v-flex xs12 sm6 md6 lg4 xl4  ><v-img
           id = "vueTechnologiesDataframe_kafka"
          :src="vueTechnologiesDataframe_kafka"

          :alt = "vueTechnologiesDataframe_kafka_alt"
          aspect-ratio=""



           ></v-img></v-flex>

						<v-flex xs12 sm6 md6 lg4 xl4  ><v-img
           id = "vueTechnologiesDataframe_oracle"
          :src="vueTechnologiesDataframe_oracle"

          :alt = "vueTechnologiesDataframe_oracle_alt"
          aspect-ratio=""



           ></v-img></v-flex>

						<v-flex xs12 sm6 md6 lg4 xl4  ><v-img
           id = "vueTechnologiesDataframe_nodejs"
          :src="vueTechnologiesDataframe_nodejs"

          :alt = "vueTechnologiesDataframe_nodejs_alt"
          aspect-ratio=""



           ></v-img></v-flex>

						<v-flex xs12 sm6 md6 lg4 xl4  ><v-img
           id = "vueTechnologiesDataframe_kubernetes"
          :src="vueTechnologiesDataframe_kubernetes"

          :alt = "vueTechnologiesDataframe_kubernetes_alt"
          aspect-ratio=""



           ></v-img></v-flex>

						<v-flex xs12 sm6 md6 lg4 xl4  ><v-img
           id = "vueTechnologiesDataframe_mysql"
          :src="vueTechnologiesDataframe_mysql"

          :alt = "vueTechnologiesDataframe_mysql_alt"
          aspect-ratio=""



           ></v-img></v-flex>
	 <div id='vueTechnologiesDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
</v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueTechnologiesDataframe-errorContainer'></div></font>
</v-card></v-flex>`,
        data: function() {
            return {
                vueTechnologiesDataframe_java: '/elintegrostartapp/assets/java.PNG',

                vueTechnologiesDataframe_java_alt: '',
                vueTechnologiesDataframe_javascript: '/elintegrostartapp/assets/javascript.PNG',

                vueTechnologiesDataframe_javascript_alt: '',
                vueTechnologiesDataframe_grails: '/elintegrostartapp/assets/grailsphoto.PNG',

                vueTechnologiesDataframe_grails_alt: '',
                vueTechnologiesDataframe_vuejs: '/elintegrostartapp/assets/vuejs.PNG',

                vueTechnologiesDataframe_vuejs_alt: '',
                vueTechnologiesDataframe_kafka: '/elintegrostartapp/assets/kafka.PNG',

                vueTechnologiesDataframe_kafka_alt: '',
                vueTechnologiesDataframe_oracle: '/elintegrostartapp/assets/oracle.PNG',

                vueTechnologiesDataframe_oracle_alt: '',
                vueTechnologiesDataframe_nodejs: '/elintegrostartapp/assets/nodejs.PNG',

                vueTechnologiesDataframe_nodejs_alt: '',
                vueTechnologiesDataframe_kubernetes: '/elintegrostartapp/assets/kubernetes.PNG',

                vueTechnologiesDataframe_kubernetes_alt: '',
                vueTechnologiesDataframe_mysql: '/elintegrostartapp/assets/mysql.PNG',

                vueTechnologiesDataframe_mysql_alt: '',
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueTechnologiesDataframe_prop', ],
        created() {
            vueTechnologiesDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueTechnologiesDataframe_form.reset()
                }
            },

        },
        methods: {

            closeDataframe: function() {
                console.log("vueTechnologiesDataframe dataframe close button.");
                if (this.$store.state.dataframeShowHideMaps) {
                    Vue.set(this.$store.state.dataframeShowHideMaps, "vueTechnologiesDataframe_display", false);
                }
            },
        },
    })

    Vue.component('vueElintegroNavigationButtonDataframe', {
        name: 'vueElintegroNavigationButtonDataframe',
        template: `<v-flex><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueElintegroNavigationButtonDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>
 <div id='vueElintegroNavigationButtonDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center  pa-2>
<v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize  hidden-md-and-down ' text id='vueElintegroNavigationButtonDataframe-home' @click.prevent='vueElintegroNavigationButtonDataframe_home' >home</v-btn>
</v-flex><v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize  hidden-md-and-down  ' text id='vueElintegroNavigationButtonDataframe-clientsProjects' @click.prevent='vueElintegroNavigationButtonDataframe_clientsProjects' >Clients & Projects</v-btn>
</v-flex><v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='/ElintegroWebsite/renderUrlData' class='text-capitalize hidden-md-and-down  ' text id='vueElintegroNavigationButtonDataframe-technologies' @click.prevent='vueElintegroNavigationButtonDataframe_technologies' >technologies</v-btn>
</v-flex><v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize  hidden-md-and-down  ' text id='vueElintegroNavigationButtonDataframe-gettingStarted' @click.prevent='vueElintegroNavigationButtonDataframe_gettingStarted' >Getting Started</v-btn>
</v-flex><v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize  hidden-md-and-down  ' text id='vueElintegroNavigationButtonDataframe-carrers' @click.prevent='vueElintegroNavigationButtonDataframe_carrers' >Careers</v-btn>
</v-flex><v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize  hidden-md-and-down  ' text id='vueElintegroNavigationButtonDataframe-contactUs' @click.prevent='vueElintegroNavigationButtonDataframe_contactUs' >Contact Us</v-btn>
</v-flex><v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize  hidden-md-and-down  ' text id='vueElintegroNavigationButtonDataframe-login' @click.prevent='vueElintegroNavigationButtonDataframe_login' >Login</v-btn>
</v-flex><v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize  hidden-md-and-down  ' text id='vueElintegroNavigationButtonDataframe-register' @click.prevent='vueElintegroNavigationButtonDataframe_register' >Register</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueElintegroNavigationButtonDataframe-errorContainer'></div></font>
</v-flex> `,
        data: function() {
            return {
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueElintegroNavigationButtonDataframe_prop', ],
        created() {
            vueElintegroNavigationButtonDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroNavigationButtonDataframe_form.reset()
                }
            },

        },
        methods: {

            vueElintegroNavigationButtonDataframe_home: function() {
                var params = {
                    'dataframe': 'vueElintegroNavigationButtonDataframe'
                };

            },

            vueElintegroNavigationButtonDataframe_clientsProjects: function() {
                var params = {
                    'dataframe': 'vueElintegroNavigationButtonDataframe'
                };

            },

            vueElintegroNavigationButtonDataframe_technologies: function() {
                var params = {
                    'dataframe': 'vueElintegroNavigationButtonDataframe'
                };

                params['id'] = 1;

                if (this.$refs.vueElintegroNavigationButtonDataframe_form.validate()) {
                    axios({
                        method: 'post',
                        url: '/ElintegroWebsite/renderUrlData',
                        params: params
                    }).then(function(responseData) {
                        var response = responseData.data
                        if (response.success) {
                            if (response.msg) {
                                store.commit('alertMessage', {
                                    'snackbar': true,
                                    'alert_type': 'success',
                                    'alert_message': response.msg
                                });
                            }

                            let url = response.url;
                            window.open("" + url, '_blank');

                        } else {
                            if (!response.error) {
                            }
                            if (response.msg) {
                                store.commit('alertMessage', {
                                    'snackbar': true,
                                    'alert_type': 'error',
                                    'alert_message': response.msg
                                })
                            }
                        }
                    }).catch(function(error) {
                        console.log(error);
                    });
                }

            },

            vueElintegroNavigationButtonDataframe_gettingStarted: function() {
                var params = {
                    'dataframe': 'vueElintegroNavigationButtonDataframe'
                };

            },

            vueElintegroNavigationButtonDataframe_carrers: function() {
                var params = {
                    'dataframe': 'vueElintegroNavigationButtonDataframe'
                };

            },

            vueElintegroNavigationButtonDataframe_contactUs: function() {
                var params = {
                    'dataframe': 'vueElintegroNavigationButtonDataframe'
                };

            },

            vueElintegroNavigationButtonDataframe_login: function() {
                var params = {
                    'dataframe': 'vueElintegroNavigationButtonDataframe'
                };

            },

            vueElintegroNavigationButtonDataframe_register: function() {
                var params = {
                    'dataframe': 'vueElintegroNavigationButtonDataframe'
                };

            },

            closeDataframe: function() {
                console.log("vueElintegroNavigationButtonDataframe dataframe close button.");
                if (this.$store.state.dataframeShowHideMaps) {
                    Vue.set(this.$store.state.dataframeShowHideMaps, "vueElintegroNavigationButtonDataframe_display", false);
                }
            },
        },
    })

    Vue.component('vueElintegroLogoDataframe', {
        name: 'vueElintegroLogoDataframe',
        template: `<span><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueElintegroLogoDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-img
           id = "vueElintegroLogoDataframe_logo"
          :src="vueElintegroLogoDataframe_logo"

          :alt = "vueElintegroLogoDataframe_logo_alt"
          aspect-ratio=""

           height=auto
           width=200
           contain  ></v-img></v-flex>
	 <div id='vueElintegroLogoDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
</span>`,
        data: function() {
            return {
                vueElintegroLogoDataframe_logo: '/assets/elintegro_logo.png',

                vueElintegroLogoDataframe_logo_alt: '',
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueElintegroLogoDataframe_prop', ],
        created() {
            vueElintegroLogoDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroLogoDataframe_form.reset()
                }
            },

        },
        methods: {

            closeDataframe: function() {
                console.log("vueElintegroLogoDataframe dataframe close button.");
                if (this.$store.state.dataframeShowHideMaps) {
                    Vue.set(this.$store.state.dataframeShowHideMaps, "vueElintegroLogoDataframe_display", false);
                }
            },
        },
    })

    Vue.component('vueElintegroBannerDataframe', {
        name: 'vueElintegroBannerDataframe',
        template: `<div><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueElintegroBannerDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-img
           id = "vueElintegroBannerDataframe_banner"
          :src="vueElintegroBannerDataframe_banner"

          :alt = "vueElintegroBannerDataframe_banner_alt"
          aspect-ratio=""



           ></v-img></v-flex>
	 <div id='vueElintegroBannerDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
</v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueElintegroBannerDataframe-errorContainer'></div></font>
</div>`,
        data: function() {
            return {
                vueElintegroBannerDataframe_banner: '/assets/elintegro_banner.jpg',

                vueElintegroBannerDataframe_banner_alt: '',
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueElintegroBannerDataframe_prop', ],
        created() {
            vueElintegroBannerDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroBannerDataframe_form.reset()
                }
            },

        },
        methods: {

            closeDataframe: function() {
                console.log("vueElintegroBannerDataframe dataframe close button.");
                if (this.$store.state.dataframeShowHideMaps) {
                    Vue.set(this.$store.state.dataframeShowHideMaps, "vueElintegroBannerDataframe_display", false);
                }
            },
        },
    })

    Vue.component('navigationLayout', {
        name: 'navigationLayout',
        template: `<v-card  height="400">
<v-app-bar flat color="white accent-4"  tabs style="z-index:99;">
                                   <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>


                                   <v-toolbar-title style="position:relative;"  ><vueElintegroLogoDataframe/></v-toolbar-title>

                                   <v-spacer></v-spacer>
                                   <vueElintegroNavigationButtonDataframe/>



                               </v-app-bar>
<v-navigation-drawer
     mini-variant="mini"
        v-model="drawer"
         temporary
    app
    hide-overlay
        white
      >
        <v-list
          nav
          dense
        >
          <v-list-item-group
            v-model="group"
            active-class="deep-purple--text text--accent-4"
          >
            <v-list-item>
              <v-list-item-title>Home</v-list-item-title>
            </v-list-item>

            <v-list-item>
              <v-list-item-title>Clients & Projects</v-list-item-title>
            </v-list-item>

            <v-list-item>
              <v-list-item-title>Technologies</v-list-item-title>
            </v-list-item>

            <v-list-item>
              <v-list-item-title>Getting Started</v-list-item-title>
            </v-list-item>
            <v-list-item>
              <v-list-item-title>Careers</v-list-item-title>
            </v-list-item>
            <v-list-item>
              <v-list-item-title>Contact Us</v-list-item-title>
            </v-list-item>
            <v-list-item>
              <v-list-item-title>Login</v-list-item-title>
            </v-list-item>
            <v-list-item>
              <v-list-item-title>Register</v-list-item-title>
            </v-list-item>
          </v-list-item-group>
        </v-list>
      </v-navigation-drawer>
<v-card-text>
                                    <midSectionLayout/>

</v-card-text>

</v-card>`,

        components: {},
        data: () => ({
            drawer: false,
            group: null,
        })
    })
    Vue.component('midSectionLayout', {
        name: 'midSectionLayout',
        template: `<div class="first-banner" style="min-width:1084px"><vueElintegroBannerDataframe/></div>`,
        components: {},
    })
    var sectionLayoutComp = {
        template: `<v-content>
                                  <v-container fluid pa-0>
                                     <navigationLayout/>
                                   </v-container>
                               </v-content>`,
        components: {},
    }
    const router = new VueRouter({
        routes: []
    })
    var app = new Vue({
        el: '#app',
        router,
        store,
        vuetify: new Vuetify(),
        data() {
            return {
                drawer: null,
            }
        },
        components: {
            sectionlayout: sectionLayoutComp,

        },
    })
</script>
<script>

    // import Vuetify from 'vuetify'
    // Vue.use(Vuetify,{
    //     rtl:true
    // })
</script>
</body>
</html>

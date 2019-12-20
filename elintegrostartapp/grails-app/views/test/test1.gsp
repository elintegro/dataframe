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
    <link rel="stylesheet" href="/elintegrostartapp/assets/vuejs/tooltip.css?compile=false"/>
    <script type="text/javascript" src="/elintegrostartapp/assets/jquery/jquery-1.11.2.js?compile=false"></script>
    <script type="text/javascript" src="/elintegrostartapp/assets/jquery/dateformat.js?compile=false"></script>
    <link rel="stylesheet" href="/elintegrostartapp/assets/vuejs/vue-material.css?compile=false"/>
    <link rel="stylesheet" href="/elintegrostartapp/assets/vuejs/vuetify.min.css?compile=false"/>
    <link rel="stylesheet" href="/elintegrostartapp/assets/vuejs/gc-vue.css?compile=false"/>
</head>
<body>
<style>
.viewer-canvas {
    background-color: black;
}

.v-table__overflow {
    overflow-x: inherit;
    overflow-y: inherit;
}
</style>
<div id="dfr"></div>
<div id='app'>
    <v-app class="app" style="background-color:#fff;">
        <sectionLayout/>
    </v-app>
</div>
<script type="text/javascript" src="/elintegrostartapp/assets/vuejs/vue.js?compile=false"></script>
<script type="text/javascript" src="/elintegrostartapp/assets/vuejs/vuetify.js?compile=false"></script>
<script type="text/javascript" src="/elintegrostartapp/assets/vuejs/vue-router.js?compile=false"></script>
<!--asset:javascript src="vuejs/vue-spring-security.min.js"/-->
<!--script type="text/javascript" src="https://unpkg.com/vue-spring-security"></script-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.18.0/axios.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue-resource@1.5.1"></script>
<script src="https://cdn.jsdelivr.net/npm/es6-promise@4/dist/es6-promise.auto.js"></script>
<script src="https://unpkg.com/popper.js"></script>
<script src="https://unpkg.com/v-tooltip"></script>
<script type="text/javascript" src="/elintegrostartapp/assets/vuejs/v-dataframe.min.js?compile=false"></script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBAAxHUhrxxl_2ZSpVtGeMX-1Fs83tunNU"></script>
<script type="text/javascript" src="/elintegrostartapp/assets/erf/erfVueController.js?compile=false"></script>
<script type="text/javascript" src="/elintegrostartapp/assets/vuejs/vuex.js?compile=false"></script>
<script>
    let store = new Vuex.Store({
        state: {
            dataframeShowHideMaps: {},
            vueInitDataframe: {
                loggedIn: false,
                key: '',
            },
            vueTechnologiesDataframe: {
                key: '',
            },
            vueElintegroNavigationButtonDataframe: {
                key: '',
            },
            vueElintegroLogoDataframe: {
                key: '',
            },
            vueElintegroBannerDataframe: {
                key: '',
            },
            dataframeBuffer: {},
        },
        mutations: {},
    })
    Vue.component('vueInitDataframe', {
        name: 'vueInitDataframe',
        template: `<div><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueInitDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>
 <div id='vueInitDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
</v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueInitDataframe-errorContainer'></div></font>
</div>`,
        data: function() {
            return {
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueInitDataframe_prop', ],
        created() {
            vueInitDataframeVar = this;
            this.checkIfPopupWindow();
            this.setInitPageValues();
        },
        methods: {

            verifyInitDfr: function() {
            },
            closeDataframe: function() {
                console.log("vueInitDataframe dataframe close button.");
                if (this.$store.state.dataframeShowHideMaps) {
                    Vue.set(this.$store.state.dataframeShowHideMaps, "vueInitDataframe_display", false);
                }
            },
            checkIfPopupWindow: function() {
                var url = window.location.href;
                //                           var t = url.searchParams.get("reloadPage");
                //                           if(url){
                //                               window.opener.location.reload();
                //                               close();
                //                           }
            },
            setInitPageValues: function(data) {

                axios.get('/elintegrostartapp/login/getUserInfo').then(function(responseData) {
                    drfExtCont.saveToStore("vueInitDataframe", "key", '');
                    drfExtCont.saveToStore("vueProfileMenuDataframe", "key", '');
                    drfExtCont.saveToStore("vueInitDataframe", "loggedIn", responseData.data.loggedIn);
                    drfExtCont.saveToStore("loggedIn", responseData.data.loggedIn);
                    //                                                     vueInitDataframeVar.$store.state.vueInitDataframe = responseData.data;
                    //                                                     Vue.set(vueInitDataframeVar.$store.state.vueInitDataframe, "key", '');
                    //                                                     Vue.set(vueInitDataframeVar.$store.state.vueProfileMenuDataframe, "key", '');
                    var loggedIn = responseData.data.loggedIn
                    //                                                     vueInitDataframeVar.$store.state.loggedIn = loggedIn;
                    var urlLocation = window.location.href;
                    if (loggedIn == false) {//                                                        vueInitDataframeVar.$router.push("/");this.location.reload();
                    }

                }).catch(function(error) {
                    console.log(error);
                });
            },

        },
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
        methods: {

            verifyInitDfr: function() {
            },
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
        template: `<v-flex><v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center  pa-2>
<v-flex xs12 sm12 md4 lg4 xl4 > <v-btn href='null' class='text-capitalize ' flat id='vueElintegroNavigationButtonDataframe-home' @click.prevent='vueElintegroNavigationButtonDataframe_home' >home</v-btn>
</v-flex><v-flex xs12 sm12 md4 lg4 xl4 > <v-btn href='null' class='text-capitalize ' flat id='vueElintegroNavigationButtonDataframe-clientsProjects' @click.prevent='vueElintegroNavigationButtonDataframe_clientsProjects' >Clients & Projects</v-btn>
</v-flex><v-flex xs6 sm6 md12 lg12 xl12 > <v-btn href='null' class='text-capitalize ' flat id='vueElintegroNavigationButtonDataframe-gettingStarted' @click.prevent='vueElintegroNavigationButtonDataframe_gettingStarted' >Getting Started</v-btn>
</v-flex><v-flex xs3 sm3 md12 lg12 xl12 > <v-btn href='null' class='text-capitalize ' flat id='vueElintegroNavigationButtonDataframe-carrers' @click.prevent='vueElintegroNavigationButtonDataframe_carrers' >Carrers</v-btn>
</v-flex><v-flex xs3 sm3 md12 lg12 xl12 > <v-btn href='null' class='text-capitalize ' flat id='vueElintegroNavigationButtonDataframe-contactUs' @click.prevent='vueElintegroNavigationButtonDataframe_contactUs' >Contact Us</v-btn>
</v-flex><v-flex xs3 sm3 md12 lg12 xl12 > <v-btn href='null' class='text-capitalize ' flat id='vueElintegroNavigationButtonDataframe-login' @click.prevent='vueElintegroNavigationButtonDataframe_login' >Login</v-btn>
</v-flex><v-flex xs3 sm3 md12 lg12 xl12 > <v-btn href='null' class='text-capitalize ' flat id='vueElintegroNavigationButtonDataframe-register' @click.prevent='vueElintegroNavigationButtonDataframe_register' >Register</v-btn>
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
        methods: {

            vueElintegroNavigationButtonDataframe_home: function() {
                var allParams = {
                    'dataframe': 'vueElintegroNavigationButtonDataframe'
                };

            },

            vueElintegroNavigationButtonDataframe_clientsProjects: function() {
                var allParams = {
                    'dataframe': 'vueElintegroNavigationButtonDataframe'
                };

            },

            vueElintegroNavigationButtonDataframe_gettingStarted: function() {
                var allParams = {
                    'dataframe': 'vueElintegroNavigationButtonDataframe'
                };

            },

            vueElintegroNavigationButtonDataframe_carrers: function() {
                var allParams = {
                    'dataframe': 'vueElintegroNavigationButtonDataframe'
                };

            },

            vueElintegroNavigationButtonDataframe_contactUs: function() {
                var allParams = {
                    'dataframe': 'vueElintegroNavigationButtonDataframe'
                };

            },

            vueElintegroNavigationButtonDataframe_login: function() {
                var allParams = {
                    'dataframe': 'vueElintegroNavigationButtonDataframe'
                };

            },

            vueElintegroNavigationButtonDataframe_register: function() {
                var allParams = {
                    'dataframe': 'vueElintegroNavigationButtonDataframe'
                };

            },

            verifyInitDfr: function() {
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
                vueElintegroLogoDataframe_logo: '/elintegrostartapp/assets/elintegro_logo.png',

                vueElintegroLogoDataframe_logo_alt: '',
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueElintegroLogoDataframe_prop', ],
        created() {
            vueElintegroLogoDataframeVar = this;
        },
        methods: {

            verifyInitDfr: function() {
            },
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
	 <div id='vueInitDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
</v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueElintegroBannerDataframe-errorContainer'></div></font>
</div>`,
        data: function() {
            return {
                vueElintegroBannerDataframe_banner: '/elintegrostartapp/assets/elintegro_banner.jpg',

                vueElintegroBannerDataframe_banner_alt: '',
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueElintegroBannerDataframe_prop', ],
        created() {
            vueElintegroBannerDataframeVar = this;
        },
        methods: {

            verifyInitDfr: function() {
            },
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
        template: `<v-toolbar flat color="white"  tabs style="z-index:99;">

                                   <v-toolbar-side-icon @click.stop="drawer = !drawer" ></v-toolbar-side-icon>

<v-layout  wrap
      style="height: 200px;">
<v-navigation-drawer
           v-model="drawer"
        absolute
        bottom
        temporary
       style = " width:250px;"




      >


        <v-list dense>


          <v-list-tile
            v-for="item in items"
            :key="item.title"
            @click=""
          >
            <v-list-tile-action>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-tile-action>

            <v-list-tile-content>
              <v-list-tile-title>{{ item.title }}</v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
        </v-list>
      </v-navigation-drawer>
</v-layout>


                                   <v-toolbar-title ><vueElintegroLogoDataframe/></v-toolbar-title>

                                   <v-spacer></v-spacer>
                                   <vueElintegroNavigationButtonDataframe/>
                                   <vueInitDataframe/>
                               </v-toolbar>`,
        data: () => ({
            drawer: false,
            items: [
                { title: 'Home' },
                { title: 'About' },
                { title: 'About' },
                { title: 'About' },
                { title: 'About' },
                { title: 'About' },
                { title: 'About' },
                { title: 'About' }
                ]

        }),

        components: {
        },
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
                                     <midSectionLayout/>
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
</body>
</html>

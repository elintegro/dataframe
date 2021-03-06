<%@ page import="grails.util.Environment" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <g:if test="${Environment.current == Environment.PRODUCTION}">
        <!-- Global site tag (gtag.js) - Google Analytics -->
        <script async src="https://www.googletagmanager.com/gtag/js?id=UA-179643845-1"></script>
        <script>
            window.dataLayer = window.dataLayer || [];
            function gtag(){dataLayer.push(arguments);}
            gtag('js', new Date());

            gtag('config', 'UA-179643845-1');
        </script>
        <!-- Hotjar Tracking Code for www.elintegro.com -->
        <script>
            (function(h,o,t,j,a,r){
                h.hj=h.hj||function(){(h.hj.q=h.hj.q||[]).push(arguments)};
                h._hjSettings={hjid:2033161,hjsv:6};
                a=o.getElementsByTagName('head')[0];
                r=o.createElement('script');r.async=1;
                r.src=t+h._hjSettings.hjid+j+h._hjSettings.hjsv;
                a.appendChild(r);
            })(window,document,'https://static.hotjar.com/c/hotjar-','.js?sv=');
        </script>
    </g:if>
    <g:else>
        <!-- Global site tag (gtag.js) - Google Analytics -->
        <script async src="https://www.googletagmanager.com/gtag/js?id=UA-179643845-2"></script>
        <script>
            window.dataLayer = window.dataLayer || [];
            function gtag(){dataLayer.push(arguments);}
            gtag('js', new Date());

            gtag('config', 'UA-179643845-2');
        </script>

    </g:else>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <g:if test="${Environment.current != Environment.DEVELOPMENT}">
        <meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
    </g:if>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Elintegro App Factory</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="/assets/home/newDesignLogoWithoutText.png" type="image/x-icon">
    <meta name="google-signin-client_id" content="482906574403-seedi2p2ae3s9obm2ohb8bevq693jl3n.apps.googleusercontent.com">
    <asset:stylesheet href="/vuejs/tooltip.css"/>
    <asset:javascript src="jquery/jquery-1.11.2.js"/>
    <asset:javascript src="jquery/dateformat.js"/>

    <g:if test="${Environment.current == Environment.DEVELOPMENT}">
        <asset:stylesheet href="/vuejs/vue-material.css"/>
    </g:if>
    <g:else>
        <link href='https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons' rel="stylesheet">
    </g:else>
%{--    <link rel="stylesheet" href="https://cdn.materialdesignicons.com/3.9.97/css/materialdesignicons.min.css">--}%
    <link rel="stylesheet" href="//cdn.materialdesignicons.com/5.4.55/css/materialdesignicons.min.css">
    <asset:stylesheet href="/vuejs/multiple-image-upload.css"/>
    <asset:stylesheet href="/vuejs/vuetify-v2.0.5.css"/>
    <asset:stylesheet href="/erf/gc-vue.css"/>
    <asset:stylesheet href="/erf/homePageLayout.css"/>
    <asset:stylesheet href="/erf/homePageResponsive.css"/>
    <asset:stylesheet href="/erf/translatorAssistantLayout.css"/>
    <asset:stylesheet href="/erf/owlCarousel.css"/>
    <link rel="stylesheet" href="https://unpkg.com/aos@2.3.0/dist/aos.css" />
%{--    <asset:stylesheet href="/erf/font.css"/>--}%

    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,400;0,500;1,500&display=swap" rel="stylesheet">
%{--    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;400&display=swap" rel="stylesheet">--}%
%{--    <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet" />--}%

    %{--    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" />--}%
    <g:if test="${Environment.CUSTOM.name == "qa"}">
        <link rel="manifest" href="./manifest.json"/>
        <script>
            if('serviceWorker' in navigator){
                navigator.serviceWorker.register('./sw.js')
                    .then((reg) => console.log("Service worker registered", reg))
                    .catch((err)=>console.log("Service worker not registered", err))
            }
        </script>
    </g:if>
</head>
<body>
<style>
.viewer-canvas{
    background-color: black;
}
.v-table__overflow{
    /*overflow-x: inherit;*/
    overflow-y: inherit;
}
.hidden{
    display: none;
}
</style>
<div id="dfr"></div>
${constructedPageHtml}

<g:if test="${Environment.current == Environment.DEVELOPMENT}">
    <asset:javascript src="vuejs/vue.js"/>
</g:if>
<g:else>
    <asset:javascript src="vuejs/vue.min.js"/>

</g:else>
<asset:javascript src="vuejs/vue-router.min.js"/>
<asset:javascript src="vuejs/vuex.js"/>
<asset:javascript src="vuejs/vue-i18n.js"/>
<asset:javascript src="vuejs/vuetify-v2.0.5.js"/>
%{--<script src="//cdn.jsdelivr.net/npm/sortablejs@1.8.4/Sortable.min.js"></script>--}%
%{--<script src="//cdnjs.cloudflare.com/ajax/libs/Vue.Draggable/2.20.0/vuedraggable.umd.min.js"></script>--}%
<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.18.0/axios.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue-resource@1.5.1"></script>
<script src="https://cdn.jsdelivr.net/npm/es6-promise@4/dist/es6-promise.auto.js"></script>
<script src="https://unpkg.com/popper.js"></script>
<script src="https://unpkg.com/v-tooltip"></script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=${grailsApplication.config.googleMapsApi.apiKey}">
</script>
<asset:javascript src="/erf/i18Messages.js"/>
<script src="https://apis.google.com/js/platform.js" async defer></script>
<asset:javascript src="/erf/erfVueController.js"/>
<asset:javascript src="/erf/owlCarousel.js"/>
<asset:javascript src="/vuejs/vuex.js"/>
<asset:javascript src="/vuejs/multiple-image-upload.umd.min.js"/>
<script src="https://unpkg.com/aos@2.3.0/dist/aos.js"></script>
<script src="https://cdn.rawgit.com/michalsnik/aos/2.1.1/dist/aos.js"></script>
<script>
    AOS.init({
        duration: 1200,
    });
</script>
${constructedPageScript}
%{--<link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">--}%


<script>

    $('.owl-carousel').owlCarousel({
        loop: true,
        margin: 10,
        dots: false,
        nav: true,
        autoplay: true,
        autoplaySpeed: 100,
        responsiveClass: true,
        responsive: {
            0: {
                items: 1,
            },
            414: {
                items: 2,
            },
            700: {
                items: 3,
            },
            991: {
                items: 5,
            },
            1280: {
                items: 7,
            }
        }
    })


    $(document).on('scroll', function() {
        if ($(document).scrollTop() > 86) {
            $('#banner').addClass('shrink');
        } else {
            $('#banner').removeClass('shrink');
        }
    });

</script>
</body>
</html>

<%@ page import="grails.util.Environment" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Elintegro Start App</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="http://34.71.77.187/assets/elintegro_logo.png" type="image/x-icon">

    <asset:stylesheet href="/vuejs/tooltip.css"/>
    <asset:javascript src="jquery/jquery-1.11.2.js"/>
    <asset:javascript src="jquery/dateformat.js"/>

    <g:if test="${Environment.current == Environment.DEVELOPMENT}">
        <asset:stylesheet href="/vuejs/vue-material.css"/>
    </g:if>
    <g:else>
        <link href='https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons' rel="stylesheet">
    </g:else>
    <link rel="stylesheet" href="//cdn.materialdesignicons.com/3.9.97/css/materialdesignicons.min.css">
    <asset:stylesheet href="/vuejs/vuetify-v2.0.5.css"/>
    <asset:stylesheet href="/vuejs/gc-vue.css"/>

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
<asset:javascript src="/vuejs/v-eutil.min.js"/>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=${grailsApplication.config.googleMapsApi.apiKey}">
</script>
<asset:javascript src="/erf/i18Messages.js"/>
<asset:javascript src="/erf/erfVueController.js"/>
<asset:javascript src="/vuejs/vuex.js"/>
${constructedPageScript}
<script>

    // import Vuetify from 'vuetify'
    // Vue.use(Vuetify,{
    //     rtl:true
    // })
</script>
</body>
</html>

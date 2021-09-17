package com.elintegro.erf.dataframe.vue

import com.elintegro.utils.MapUtil

class ViewRoutes {
    String routes = ""

    public static String constructRoute(ViewRoutes vueRoutes){
       String routes = vueRoutes.routes
//        String toJsonString = MapUtil.convertMapToJSONString(routes)
        return routes
    }
    public static constructRoute(Map route, String componentName){
        String childRoutes = constructChildrenRoutes(route, componentName)
        return  """{ path: '/$route.name',name:'${componentName}' , component: ${componentName}Comp,
                                  children:[$childRoutes]
                           },\n"""
    }

    private static String getRouteRegistrationString(Map route){
        String componentName = route.component
        return  """ path: '/$route.name',name:'${componentName}' , component: ${componentName}Comp """
    }

    private static String constructChildrenRoutes(Map route, String componentName){
        if(!route.childRoutes){
            return ""
        }
        StringBuilder sb = new StringBuilder()
        List children = route.childRoutes
        for(Object child: children){
            if(!(child instanceof Map)){
                throw new RuntimeException("while defining route, children should be an array of Map with name and layout or dataframe")
            }
            sb.append("{")
            sb.append(getRouteRegistrationString(child))
            sb.append("},")
        }
        return sb.toString()
    }
}

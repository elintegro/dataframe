package com.elintegro.model

import com.elintegro.erf.dataframe.DataframeInstance
import org.grails.web.json.JSONObject
import org.springframework.context.i18n.LocaleContextHolder

class DataframeResponse {

    /**
     * 		if(result) {
     String msg = messageSource.getMessage("data.save.success", null, "save.success", LocaleContextHolder.getLocale())
     responseData = ['success': true, 'msg': msg, operation: operation, data: requestParams, params: requestParams]
     } else {
     String msg = messageSource.getMessage("data.save.not.valid", null, "data.not.valid", LocaleContextHolder.getLocale())
     responseData = ['msg': msg, 'success': false]
     }

     */

    boolean result = true
    String message = ""
    String operation = "I"//TODO switch to enum!!!
    JSONObject data
    DataframeInstance dataframeInstance

    static transients = ["dataframeInstance"]
}

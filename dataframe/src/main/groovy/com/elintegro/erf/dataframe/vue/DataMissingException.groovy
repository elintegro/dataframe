package com.elintegro.erf.dataframe.vue

import groovy.util.logging.Slf4j

@Slf4j
class DataMissingException extends RuntimeException {

    public DataMissingException (String message) {
        super (message);
        log.error("DataMissingException:" + message)
    }

    public DataMissingException (Throwable cause) {
        super (cause);
        log.error("DataMissingException:" + cause)
    }

    public DataMissingException (String message, Throwable cause) {
        super (message, cause);
        log.error("DataMissingException:" + message + " Original Exception: " + cause)
    }

}



package com.elintegro.erf.dataframe

class JoinParsed {
    String joinElement
    String joinClause
    String sourceDomain
    String targetDomain = ""
    String sourceField = ""
    String targetField = ""
    String onCondition = ""
    String joinType = ""


    JoinParsed(String sourceDomain, String joinElement, String joinClause) {
        this.joinElement = joinElement
        this.joinClause = joinClause
        this.sourceDomain = sourceDomain
    }
}

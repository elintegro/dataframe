package com.elintegro.erf.dataframe

import java.util.regex.Matcher
import java.util.regex.Pattern

class JoinParsed {
    String joinElement
    String joinClause
    String sourceDomain
    String targetDomain = ""
    String sourceField = ""
    String targetField = ""
    String onCondition = ""
    String joinType = ""
    final static String JOIN_PART_SOURCE_DOMAIN_GROUP = "sourceDomain"
    final static String JOIN_PART_SOURCE_FIELD_GROUP = "sourceField"
    final static String JOIN_PART_TARGET_DOMAIN_GROUP = "targetDomain"
    final static String joinParseRegex = /(?</+/${JOIN_PART_SOURCE_DOMAIN_GROUP}/+/>\w+)\.(?</+/${JOIN_PART_SOURCE_FIELD_GROUP}/+/>\w+)\s+(?:as\s+)?(?</+/${JOIN_PART_TARGET_DOMAIN_GROUP}/+/>\w+)/
    final static Pattern joinPattern = Pattern.compile(joinParseRegex, Pattern.CASE_INSENSITIVE);



    JoinParsed(String joinElement, String joinClause, ParsedHql parsedHql) {
        this.joinElement = joinElement
        this.joinClause = joinClause


        try {
            Matcher joinMatcher = joinPattern.matcher(joinClause);
            boolean isMatchs = joinMatcher.matches();

            if (isMatchs && joinMatcher?.hasGroup()) {
                if(joinMatcher.groupCount() !=3) {
                    throw new DataframeException(parsedHql.dataframeName, "Join clause have to be in a format of <sourceDomain>.<sourceDomainField> <targetDomain> only, but in this case it is: ${joinClause}")
                }
                this.sourceDomain = joinMatcher.group(JOIN_PART_SOURCE_DOMAIN_GROUP)?.trim()
                this.sourceField = joinMatcher.group(JOIN_PART_SOURCE_FIELD_GROUP)?.trim()
                this.targetDomain = joinMatcher.group(JOIN_PART_TARGET_DOMAIN_GROUP)?.trim()
            }
        }catch(IllegalStateException exp){
            throw new DataframeException("No match found: Join clause have to be in a format of <sourceDomain>.<sourceDomainField> <targetDomain> only, but in this case it is: ${joinClause}; Exception: ${exp}")
        }
    }
}

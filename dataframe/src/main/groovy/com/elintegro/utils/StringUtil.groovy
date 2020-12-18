package com.elintegro.utils

class StringUtil {

    public static boolean isNullOrEmpty(String str) {
        if(str == null || str.trim().isEmpty())
            return true
        return false
    }

    public static void insert(StringBuilder stringBuilder, String string){
        stringBuilder.insert(0, string)
    }
}

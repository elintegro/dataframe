package com.elintegro.otpVerification

import com.elintegro.auth.User

class Otp {
    User user
    String verificationCode
    Date createTime
    Date expireTime

    static constraints = {
        user(nullable: false)
    }
}

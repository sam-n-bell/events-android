package com.example.loginlogout

object Global {

    private var token = "" //use for routes that require authentication
    private var user_id: Int? = null //id of person logged in
    private val url = "https://flaskappmysql.appspot.com"

    fun setToken(token: String){
        this.token = token
    }


    fun getToken(): String{
        return this.token
    }


    fun setUserId(user_id: Int) {
        this.user_id = user_id
    }

    fun getUserId(): Int? {
        return this.user_id
    }

    fun getFlaskUrl(): String {
        return this.url
    }
}
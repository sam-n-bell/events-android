package com.example.loginlogout

object Global {

    private var token = ""
    private var user_id: Int? = null

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
}
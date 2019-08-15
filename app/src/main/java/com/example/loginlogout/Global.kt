package com.example.loginlogout

object Global {

    private var token = ""

    fun setToken(token: String){
        this.token = token
    }


    fun getToken(): String{
        return this.token
    }
}
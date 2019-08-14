package com.example.loginlogout

class User_Model {

    var name: String? = null
    var email: String? = null
    var administrator: String? = null
    var user_id: Int? = null

    fun getNames(): String {
        return name.toString()
    }

    fun setNames(name: String) {
        this.name = name
    }

    fun getEmails(): String {
        return email.toString()
    }

    fun setEmails(name: String) {
        this.email = name
    }

    fun getAdministrators(): String {
        return administrator.toString()
    }

    fun setAdministrators(administrator: String) {
        this.administrator = administrator
    }
    fun getUserIds(): Int? {
        return user_id
    }

    fun setUserIds(userId: Int) {
        this.user_id = userId
    }



}
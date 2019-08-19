package com.example.loginlogout

import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import org.json.JSONObject

object HttpUtilities {


    fun posturl(url: String, body: String): String {
        println("post url is " + url)
        val json = body.trimIndent()
        val requestbody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .post(requestbody)
            .header("Authorization", "Bearer " + Global.getToken())
            .build()
        try {
            val response = client.newCall(request).execute()
            val bodystr =  response.body().string() // this can be consumed only once
            return bodystr
        } catch (e: Exception){
            println("Failed"+e.toString())
            return "ERROR"
        }
    }

}
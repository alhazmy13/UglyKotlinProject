package net.alhazmy13.demoproject

import com.google.gson.annotations.SerializedName

class PostModel {
    @SerializedName("userId")
    private val userId: Int = 0
    @SerializedName("id")
    private val id: Int = 0
    @SerializedName("title")
    private val title: String = ""
    @SerializedName("body")
    private val body: String = ""

    fun getTitle(): String {
        return title
    }

    fun getBody(): String {
        return body
    }

    fun getId(): Int {
        return id
    }

    fun getUserId(): Int {
        return userId
    }

}
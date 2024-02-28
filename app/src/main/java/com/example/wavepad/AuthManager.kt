package com.example.gymrat.Models

class AuthManager {
    var authToken: String? = null
        private set
    var userid: Int? = null
        private set
    var programid: Int? = null
        private set

    companion object {
        val instance by lazy { AuthManager() }
    }

    fun setAuthToken(token: String) {
        authToken = token

    }
    fun setUserid(id: Int) {
        userid = id

    }

    fun setProgramId(id: Int) {
        programid = id
    }

    fun clearAuthToken() {
        authToken = null
    }
    fun clearuserId() {
        userid = null
    }
}
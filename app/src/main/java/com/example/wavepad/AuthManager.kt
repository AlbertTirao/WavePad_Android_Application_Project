package com.example.wavepad

class AuthManager {
    var authToken: String? = null
        private set
    private var userId: Int? = null

    companion object {
        val instance by lazy { AuthManager() }
    }

    fun setAuthToken(token: String) {
        authToken = token
    }

    fun getUserId(): Int? {
        return userId
    }

    fun setUserId(id: Int) {
        userId = id
    }

    fun clearAuthToken() {
        authToken = null
    }

    fun clearUserId() {
        userId = null
    }
}


//class AuthManager {
//    var authToken: String? = null
//        private set
//    var userId: Int? = null
//        private set
//    var programId: Int? = null
//        private set
//
//    companion object {
//        val instance by lazy { AuthManager() }
//    }
//
//    fun setAuthToken(token: String) {
//        authToken = token
//    }
//
//    fun setUserId(id: Int) {
//        userId = id
//    }
//
//    fun setProgramId(id: Int) {
//        programId = id
//    }
//
//    fun clearAuthToken() {
//        authToken = null
//    }
//
//    fun clearUserId() {
//        userId = null
//    }
//
//    fun clearProgramId() {
//        programId = null
//    }

//    fun setUserid(id: Int) {
//        userId = id
//    }

//    fun getUserId(): Int? {
//        return userId
//    }
//}


//class AuthManager {
//    var authToken: String? = null
//        private set
//    var userid: Int? = null
//        private set
//    var programid: Int? = null
//        private set
//
//    companion object {
//        val instance by lazy { AuthManager() }
//    }
//
//    fun setAuthToken(token: String) {
//        authToken = token
//
//    }
//    fun setUserid(id: Int) {
//        userid = id
//
//    }
//
//    fun setProgramId(id: Int) {
//        programid = id
//    }
//
//    fun clearAuthToken() {
//        authToken = null
//    }
//    fun clearuserId() {
//        userid = null
//    }
//}
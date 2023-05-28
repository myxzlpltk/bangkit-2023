package com.dicoding.mysimplelogin

import com.bangkit.core.SessionManager

class UserRepository(private val sesi: SessionManager) {

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(sesi: SessionManager): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(sesi)
            }
    }

    fun loginUser(username: String) {
        sesi.createLoginSession()
        sesi.saveToPreference(com.bangkit.core.SessionManager.KEY_USERNAME, username)
    }

    fun getUser() = sesi.getFromPreference(com.bangkit.core.SessionManager.KEY_USERNAME)

    fun isUserLogin() = sesi.isLogin

    fun logoutUser() = sesi.logout()
}
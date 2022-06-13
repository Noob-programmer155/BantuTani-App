package com.bantutani.application.data.preference

import android.content.Context

class SessionPref(context: Context) {
    private val preferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)
    fun setSessionData(id: Int, fullname:String, namauser: String, email: String, image:String, status: String, token: String) {
//      Untuk mengupdate dan set data session
        val pref = preferences.edit()
        pref.putInt(SESSION_ID, id)
        pref.putString(SESSION_FULLNAME, fullname)
        pref.putString(SESSION_NAMA, namauser)
        pref.putString(SESSION_EMAIL, email)
        pref.putString(SESSION_IMAGE, image)
        pref.putString(SESSION_STATUS, status)
        pref.putString(SESSION_TOKEN, token)
        pref.apply()
    }
    //delete user from session
    fun deleteSession() {
        val pref = preferences.edit()
        pref.clear()
        pref.apply()
    }

    fun getUserId(): Int {
//        mengembalikan nilai id dari session
        return preferences.getInt(SESSION_ID, 0)
    }
    fun getFullName(): String? {
//        mengembalikan nama user yang tersimpan di session
        return preferences.getString(SESSION_FULLNAME, "")
    }
    fun getUserName(): String? {
//        mengembalikan nama user yang tersimpan di session
        return preferences.getString(SESSION_NAMA, "")
    }
    fun getUserEmail(): String? {
//        mengembalikan email user dari session
        return preferences.getString(SESSION_EMAIL, "")
    }
    fun getUserImage(): String? {
//        mengembalikan String Image Avatar User dari session
        return preferences.getString(SESSION_IMAGE, "")
    }
    fun getUserStatus(): String? {
//        mengembalikan status user dari pengguna apakah dia admin,petani, atau expert dst.
        return preferences.getString(SESSION_STATUS, "")
    }
    fun getUserToken(): String? {
//        mengembalikan status user dari pengguna apakah dia admin,petani, atau expert dst.
        return preferences.getString(SESSION_TOKEN, "")
    }
    fun getAppStatus(): Boolean? {
//        mengembalikan status user dari pengguna apakah dia admin,petani, atau expert dst.
        return preferences.getBoolean(SESSION_APPSTATUS, false)
    }
    fun setAppStatus(state: Boolean){
        val pref = preferences.edit()
        pref.putBoolean(SESSION_APPSTATUS, state)
        pref.apply()
    }
    companion object {
        const val SESSION = "session"
        const val SESSION_ID = "session_id"
        const val SESSION_FULLNAME = "session_fullname"
        const val SESSION_NAMA = "session_nama"
        const val SESSION_EMAIL = "session_email"
        const val SESSION_IMAGE = "session_image"
        const val SESSION_STATUS = "session_status"
        const val SESSION_TOKEN = "session_token"
        const val SESSION_APPSTATUS = "session_appstatus"
    }
}
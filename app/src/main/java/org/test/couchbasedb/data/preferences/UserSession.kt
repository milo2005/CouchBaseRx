package org.test.couchbasedb.data.preferences

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSession @Inject constructor(val preferences: SharedPreferences) {

    private val USER_ID = "userId"

    var userId: String
        get() = preferences.getString(USER_ID, "321")
        set(value) {
            preferences.edit().putString(USER_ID, value).apply()
        }


}
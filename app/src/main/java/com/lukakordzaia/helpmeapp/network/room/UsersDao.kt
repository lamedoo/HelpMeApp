package com.lukakordzaia.helpmeapp.network.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsersDao {
    @Query("SELECT * FROM users WHERE firebase_uid IN (:currentUserUid)")
    fun getCurrentUserData(currentUserUid: String): LiveData<Users>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend  fun insertUserData(users: Users)

    @Query("UPDATE users SET  first_name = (:first_name), last_name = (:last_name), email = (:email), phone = (:phone) WHERE firebase_uid = (:currentUserUid)")
    suspend fun updateUserData(first_name: String, last_name: String, email: String, phone: String, currentUserUid: String)

    @Query("UPDATE users SET avatar = (:avatar) WHERE firebase_uid = (:currentUserUid)")
    suspend fun updateUserAvatar(avatar: String, currentUserUid: String)

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}
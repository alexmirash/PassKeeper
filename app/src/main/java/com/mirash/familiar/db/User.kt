package com.mirash.familiar.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mirash.familiar.model.user.UserModel

/**
 * @author Mirash
 */
@Entity
class User(name: String) : UserModel(name) {
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0
}
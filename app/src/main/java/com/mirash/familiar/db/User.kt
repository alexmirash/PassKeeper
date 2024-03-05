package com.mirash.familiar.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Mirash
 */
@Entity
class User(var name: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
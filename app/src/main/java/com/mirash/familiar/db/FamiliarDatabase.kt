package com.mirash.familiar.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Credentials::class, User::class], version = 3, exportSchema = false)
abstract class FamiliarDatabase : RoomDatabase() {

    abstract fun getCredentialsDao(): CredentialsDao

    abstract fun getUserDao(): UserDao
}

const val TAG_DB = "DbC"
package com.mirash.familiar.db

import android.app.Application
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mirash.familiar.FamiliarApp
import kotlin.concurrent.thread

object RepositoryProvider {
    private val database: FamiliarDatabase by lazy {
        createDatabase(FamiliarApp.instance)
    }

    val credentialsRepository: CredentialsRepository by lazy {
        CredentialsRepository(database)
    }

    val userRepository: UserRepository by lazy {
        UserRepository(database)
    }

    private fun createDatabase(application: Application): FamiliarDatabase {
        val roomDbCallback: RoomDatabase.Callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                Log.d(TAG_DB, "db onCreate")
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                Log.d(TAG_DB, "db opOpen")
                thread {
                    logUserCredentialsSync()
                }
            }
        }
        return Room.databaseBuilder(application, FamiliarDatabase::class.java, "FamiliarDb")
            .fallbackToDestructiveMigration().addCallback(roomDbCallback).build()
    }

    fun nuke() {
        Thread { database.clearAllTables() }.start()
    }
}

fun logUserCredentialsSync() {
    for (data in RepositoryProvider.userRepository.getUsersWithCredentialsSync()) {
        for (c in data.credentials) {
            Log.d(TAG_DB, "${data.user.id}, ${data.user.name}: ${c.userId}, ${c.title}")
        }
    }
    for (data in RepositoryProvider.credentialsRepository.getAllSync()) {
        Log.d(TAG_DB, "${data.userId}, ${data.title}")
    }
}
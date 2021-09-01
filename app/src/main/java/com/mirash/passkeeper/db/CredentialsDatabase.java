package com.mirash.passkeeper.db;

import static com.mirash.passkeeper.db.tool.DbUtils.LOG_TAG;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mirash.passkeeper.db.tool.Converters;

/**
 * @author Mirash
 */
@Database(entities = {Credentials.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class CredentialsDatabase extends RoomDatabase {
    private static final String NAME = "EntryDb";

    public abstract CredentialsDao getCredentialsDao();

    public static CredentialsDatabase getDatabase(Application application) {
        Callback roomDbCallback = new Callback() {
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                Log.d(LOG_TAG, "db onCreate");
            }

            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                Log.d(LOG_TAG, "db opOpen");
            }
        };
        return Room.databaseBuilder(application.getApplicationContext(), CredentialsDatabase.class, NAME)
                .fallbackToDestructiveMigration()
                .addCallback(roomDbCallback)
                .build();
    }
}
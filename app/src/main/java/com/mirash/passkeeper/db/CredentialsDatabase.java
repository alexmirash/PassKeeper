package com.mirash.passkeeper.db;

import static com.mirash.passkeeper.db.DbUtils.LOG_TAG;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

/**
 * @author Mirash
 */
@Database(entities = {Credentials.class}, version = 3, exportSchema = false)
public abstract class CredentialsDatabase extends RoomDatabase {
    private static final String NAME = "CredentialsDb";

    public abstract CredentialsDao getCredentialsDao();

    public static CredentialsDatabase getDatabase(Application application) {
        Callback roomDbCallback = new Callback() {
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                Log.d(LOG_TAG, "db onCreate");
                Executors.newSingleThreadScheduledExecutor().execute(() ->
                        RepositoryProvider.getCredentialsRepository().insertAll(DbUtils.getTestPredefinedCredentials())
                );
            }

            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                Log.d(LOG_TAG, "db opOpen");
            }
        };
        return Room.databaseBuilder(application.getApplicationContext(), CredentialsDatabase.class, NAME)
                .fallbackToDestructiveMigration()
                .addCallback(roomDbCallback)
                .addMigrations(getMigration2_3())
                .build();
    }

    private static Migration getMigration2_3() {
        return new Migration(2, 3) {
            @Override
            public void migrate(@NonNull SupportSQLiteDatabase database) {
                Log.d(LOG_TAG, "migrate 2 -> 3");
                database.execSQL("ALTER TABLE Credentials ADD COLUMN position INTEGER DEFAULT 0 NOT NULL");
            }
        };
    }
}
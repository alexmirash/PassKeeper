package com.mirash.familiar.db;

import static com.mirash.familiar.db.DbUtils.LOG_TAG;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

/**
 * @author Mirash
 */
@Database(entities = {Credentials.class}, version = 2, exportSchema = false)
public abstract class CredentialsDatabase extends RoomDatabase {
    private static final String NAME = "FamiliarDb";

    public abstract CredentialsDao getCredentialsDao();

    public static CredentialsDatabase getDatabase(Application application) {
        Callback roomDbCallback = new Callback() {
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                Log.d(LOG_TAG, "db onCreate");
                Executors.newSingleThreadScheduledExecutor().execute(() -> RepositoryProvider.getCredentialsRepository().insertAll(DbUtils.getTestPredefinedCredentials()));
            }

            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                Log.d(LOG_TAG, "db opOpen");
            }
        };
        return Room.databaseBuilder(application.getApplicationContext(), CredentialsDatabase.class, NAME)
                .fallbackToDestructiveMigration()
                .addCallback(roomDbCallback)
                .build();
//        return Room.databaseBuilder(application.getApplicationContext(), CredentialsDatabase.class, NAME)
//                .addMigrations(getMigration1_2())
//                .build();
    }
}
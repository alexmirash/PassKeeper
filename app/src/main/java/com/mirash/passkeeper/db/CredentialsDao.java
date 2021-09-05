package com.mirash.passkeeper.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * @author Mirash
 */
@Dao
public interface CredentialsDao {

    @Query("SELECT * FROM Credentials ORDER BY position ASC")
    LiveData<List<Credentials>> getAll();

    @Query("SELECT * FROM Credentials ORDER BY position ASC")
    List<Credentials> getAllSync();

    @Query("SELECT * FROM Credentials WHERE id= :id")
    LiveData<Credentials> getById(int id);

    @Query("SELECT * FROM Credentials WHERE id= :id")
    Credentials getByIdSync(int id);

    @Query("SELECT * FROM Credentials WHERE title= :title")
    LiveData<Credentials> getByTitle(String title);

    @Query("SELECT * FROM Credentials WHERE title= :title")
    Credentials getByTitleSync(String title);

    @Query("DELETE FROM Credentials WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM Credentials WHERE position > :position")
    List<Credentials> getUnderPositionSync(int position);

    @Insert
    void insertAll(List<Credentials> entries);

    @Insert
    void insert(Credentials credentials);

    @Update
    void update(Credentials credentials);

    @Update
    void updateAll(List<Credentials> entries);

    @Delete
    void delete(Credentials credentials);

    @Delete
    void delete(List<Credentials> entries);

    @Query("DELETE FROM Credentials")
    void deleteAll();
}

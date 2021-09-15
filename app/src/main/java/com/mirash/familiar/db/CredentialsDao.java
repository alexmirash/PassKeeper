package com.mirash.familiar.db;

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

    @Query("SELECT * FROM Credentials WHERE id= :id")
    LiveData<Credentials> getById(int id);

    @Query("SELECT * FROM Credentials WHERE id= :id")
    Credentials getByIdSync(int id);

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
    void delete(List<Credentials> credentials);

    @Query("DELETE FROM Credentials")
    void deleteAll();
}

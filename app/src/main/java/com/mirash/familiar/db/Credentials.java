package com.mirash.familiar.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.mirash.familiar.model.CredentialsModel;

/**
 * @author Mirash
 */
@Entity
public class Credentials extends CredentialsModel {
    @PrimaryKey(autoGenerate = true) private int id;

    @Override
    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }
}
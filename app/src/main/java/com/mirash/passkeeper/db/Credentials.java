package com.mirash.passkeeper.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.mirash.passkeeper.model.CredentialsModel;

/**
 * @author Mirash
 */
@Entity
public class Credentials extends CredentialsModel {
    @PrimaryKey(autoGenerate = true) private int id;

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }
}
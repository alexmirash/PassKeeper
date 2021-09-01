package com.mirash.passkeeper.db.tool;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mirash
 */

public class Converters {
    @TypeConverter
    public static List<Integer> fromString(String value) {
        if (value == null) {
            return null;
        }
        Type listType = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return new Gson().toJson(list);
    }
}
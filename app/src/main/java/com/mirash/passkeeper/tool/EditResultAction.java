package com.mirash.passkeeper.tool;

import android.annotation.SuppressLint;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Mirash
 */
@IntDef(flag = true, value = {EditResultAction.NEW, EditResultAction.UPDATE, EditResultAction.DELETE, EditResultAction.UNDEFINED})
@Retention(RetentionPolicy.SOURCE)
@SuppressLint("ShiftFlags")
public @interface EditResultAction {
    int NEW = 1;
    int UPDATE = 2;
    int DELETE = 3;
    int UNDEFINED = -1;
}
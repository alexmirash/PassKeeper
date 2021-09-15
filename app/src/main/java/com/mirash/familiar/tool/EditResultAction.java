package com.mirash.familiar.tool;

import android.annotation.SuppressLint;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Mirash
 */
@IntDef(flag = true, value = {EditResultAction.CREATE, EditResultAction.UPDATE, EditResultAction.DELETE, EditResultAction.UNDEFINED})
@Retention(RetentionPolicy.SOURCE)
@SuppressLint("ShiftFlags")
public @interface EditResultAction {
    int CREATE = 1;
    int UPDATE = 2;
    int DELETE = 3;
    int UNDEFINED = -1;
}
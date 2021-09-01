package com.mirash.passkeeper.activity;

import android.content.pm.PackageManager;
import android.util.Log;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;


import com.mirash.passkeeper.tool.PermissionRequest;
import com.mirash.passkeeper.tool.listener.ValueBoolListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Mirash
 */
public abstract class BasePermissionsActivityOld extends FragmentActivity {
    private static final String TAG = "PermissionsCheck";
    private int requestCode;

    private final SparseArray<ValueBoolListener> callbacks = new SparseArray<>();
    private final Queue<PermissionRequest> pendingRequests = new LinkedList<>();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        callbacks.clear();
        pendingRequests.clear();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult requestCode = " + requestCode);
        ValueBoolListener callback = callbacks.get(requestCode);
        if (callback != null) {
            boolean requestResult = true;
            callbacks.remove(requestCode);
            if (grantResults.length > 0) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        requestResult = false;
                        break;
                    }
                }
            } else {
                requestResult = false;
            }
            Log.d(TAG, "onRequestPermissionsResult " + requestResult + ": " + Arrays.toString(permissions)
                    + "\npendingSize = " + pendingRequests.size());
            callback.onUpdate(requestResult);
            if (callbacks.size() == 0) {
                PermissionRequest request = pendingRequests.poll();
                if (request != null) {
                    performTaskOnPermissionResultInternal(request.getCallback(), request.getPermissions());
                }
            }
        }
    }

    public void requestPermissionsIfNecessary(String... permissions) {
        performTaskOnPermissionsGranted(null, permissions);
    }

    public boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void performTaskOnPermissionsGranted(ValueBoolListener callback, String... permissions) {
        if (permissions == null) return;
        if (callbacks.size() == 0) {
            performTaskOnPermissionResultInternal(callback, permissions);
        } else {
            pendingRequests.add(new PermissionRequest(callback, permissions));
        }
    }

    private void performTaskOnPermissionResultInternal(ValueBoolListener callback, String... permissions) {
        Log.d(TAG, "performTaskOnPermissionResultInternal: " + Arrays.toString(permissions));
        List<String> requestPermissions = new ArrayList<>(permissions.length);
        for (String permission : permissions) {
            if (!hasPermission(permission)) {
                requestPermissions.add(permission);
            }
        }
        if (requestPermissions.isEmpty()) {
            if (callback != null) {
                callback.onUpdate(true);
            }
        } else {
            int requestCode = getPermissionRequestCode();
            callbacks.put(requestCode, callback == null ? (ValueBoolListener) value -> {
            } : callback);
            ActivityCompat.requestPermissions(this, requestPermissions.toArray(new String[0]), requestCode);
        }
    }

    private int getPermissionRequestCode() {
        requestCode++;
        if (requestCode >= 255) requestCode = 0;
        return requestCode;
    }
}


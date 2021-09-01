package com.mirash.passkeeper.activity;

import android.content.pm.PackageManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.mirash.passkeeper.tool.listener.ValueBoolListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BasePermissionActivityNew extends AppCompatActivity {

    public void performTaskOnPermissionsGranted(@NonNull ValueBoolListener callback, String... permissions) {
        if (permissions == null) return;
        List<String> permissionList = new ArrayList<>(permissions.length);
        for (String permission : permissions) {
            if (!hasPermission(permission)) {
                permissionList.add(permission);
            }
        }
        if (permissionList.isEmpty()) {
            callback.onUpdate(true);
        } else if (permissionList.size() == 1) {
            ActivityResultLauncher<String> singleRequestPermissionLauncher =
                    registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                            result -> callback.onUpdate(result != null && result));
            singleRequestPermissionLauncher.launch(permissionList.get(0));
        } else {
            ActivityResultLauncher<String[]> multipleRequestPermissionLauncher =
                    registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                        for (Map.Entry<String, Boolean> entry : result.entrySet()) {
                            Boolean value = entry.getValue();
                            if (value == null || !value) {
                                callback.onUpdate(false);
                                return;
                            }
                            callback.onUpdate(true);
                        }
                    });
            multipleRequestPermissionLauncher.launch(permissionList.toArray(new String[]{}));
        }
    }

    public boolean hasPermission(@NonNull String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
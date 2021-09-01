package com.mirash.passkeeper.tool;

import com.mirash.passkeeper.tool.listener.ValueBoolListener;

/**
 * @author Mirash
 */
public class PermissionRequest {
    private final String[] permissions;
    private final ValueBoolListener callback;

    public PermissionRequest(ValueBoolListener callback, String... permissions) {
        this.callback = callback;
        this.permissions = permissions;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public ValueBoolListener getCallback() {
        return callback;
    }
}

package org.mmapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>PermissionUtility</b><br>
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class PermissionUtility {
    private Context _context;
    private String[] _requiredPermissions;
    private final int REQUEST_CODE_PERMISSION_UTILITY = 101;

    public PermissionUtility(Context context, String... requiredPermissions) {
        this._context = context;
        this._requiredPermissions = requiredPermissions;
    }

    public boolean arePermissionsEnabled() {
        for (String permission : _requiredPermissions) {
            if (ActivityCompat.checkSelfPermission(_context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    public void requestMultiplePermissions(){
        List<String> remainingPermissions = new ArrayList<>();
        for (String permission : _requiredPermissions) {
            if (ActivityCompat.checkSelfPermission(_context, permission) != PackageManager.PERMISSION_GRANTED) {
                remainingPermissions.add(permission);
            }
        }
        ActivityCompat.requestPermissions((Activity) _context,
                                          remainingPermissions.toArray(new String[remainingPermissions.size()]), REQUEST_CODE_PERMISSION_UTILITY);
    }
    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION_UTILITY) {
            for(int i = 0; i < grantResults.length; i++){
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) _context,
                                                                            permissions[i])) {
                        requestMultiplePermissions();
                    }
                    return false;
                }
            }
        }
        return true;
    }
}

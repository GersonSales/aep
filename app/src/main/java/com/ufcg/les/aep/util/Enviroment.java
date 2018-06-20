package com.ufcg.les.aep.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ufcg.les.aep.activity.PostCreationActivity;


public class Enviroment extends AppCompatActivity {

    public static final int REQUEST_CODE = 500;
    public static final int REQUEST_WRITE_STORAGE = 600;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean requestCameraPermission(final Activity activity) {
        boolean result = false;
        if ((ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                || (ActivityCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(activity, PERMISSION_REQ,REQUEST_CODE);
        }
        else{
            result = true;
        }
        return result;
    }

    private static final String[] PERMISSION_REQ = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    
}

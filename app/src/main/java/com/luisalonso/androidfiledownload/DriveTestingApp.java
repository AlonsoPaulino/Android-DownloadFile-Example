package com.luisalonso.androidfiledownload;

import android.app.Application;
import android.content.Context;

/**
 * @author Luis Alonso Paulino Flores <alonso.paulino@mediabyte.com.pe>
 */
public class DriveTestingApp extends Application {

    public static Context CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = getApplicationContext();
    }
}

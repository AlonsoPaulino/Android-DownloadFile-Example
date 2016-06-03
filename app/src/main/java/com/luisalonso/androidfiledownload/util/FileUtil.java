package com.luisalonso.androidfiledownload.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;
import android.util.Log;

import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import com.luisalonso.androidfiledownload.DriveTestingApp;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Luis Alonso Paulino Flores <alonso.paulino@mediabyte.com.pe>
 */
public class FileUtil {

    public static boolean saveFilesUsingSpecificDirectory(InputStream inputStream, String dirname, String name) {
        ContextWrapper contextWrapper = new ContextWrapper(DriveTestingApp.CONTEXT);
        File dir  = contextWrapper.getDir(dirname, Context.MODE_PRIVATE);
        File file = new File(name);
        try {
            String stringFromStream = CharStreams.toString(new InputStreamReader(inputStream, "UTF-8"));
            Log.d("STRING FROM STREAM -> ", stringFromStream);
            if (!file.exists()) {
                if (file.createNewFile()) {
                    Log.d("create file -> ", "success");
                } else {
                    Log.d("create file -> ", "failed");
                    return false;
                }
            }
            Files.asByteSink(file).writeFrom(inputStream);
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean saveInExternalStorage(InputStream inputStream, String name) {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + name);
        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    Log.d("create file -> ", "success");
                } else {
                    Log.d("create file -> ", "failed");
                    return false;
                }
            }
            Files.asByteSink(file).writeFrom(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /*public static boolean saveInExternalStorage(InputStream inputStream, String name) {

    }*/
}

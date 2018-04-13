package com.llc111minutes.gameday.util;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Yurii on 2/2/17.
 */

public class FileUtil {
    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * Method for retrieving a object File, for permanent storing a file
     *
     * @param activity - your Activity
     * @param name     - File name
     * @return - Object File
     * @throws IOException
     */
    public static File createImageFile(Activity activity, String name) throws IOException {
        File storage = null;
        if (isExternalStorageReadable() && isExternalStorageWritable()) {
            storage = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        } else {
            storage = activity.getFilesDir();
        }
        LogUtil.info(activity, "Storage: " + storage);
        File file = File.createTempFile(
                name,          /* prefix */
                ".jpg",        /* suffix */
                storage        /* directory */
        );
        return file;
    }

    public static String saveBitmapToFile(Activity activity, Bitmap bitmap, String name) throws IOException {
        FileOutputStream out = null;
        File file = createImageFile(activity, name);

        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    public static String getStringFromFile(AssetManager manager, String filename) {
        byte[] buffer = null;
        InputStream is;
        try {
            is = manager.open(filename);
            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(buffer);
    }
}
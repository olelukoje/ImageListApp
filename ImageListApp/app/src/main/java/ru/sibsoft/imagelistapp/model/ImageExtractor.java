package ru.sibsoft.imagelistapp.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import ru.sibsoft.imagelistapp.manager.SharedPreferencesManager;

/**
 * Created by minaevaolga on 25/05/17.
 */

public class ImageExtractor {

    public ArrayList<Bitmap> loadImageFromStorage(Context context) {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();

        try {
            for(int i = 0; i < ListInflater.links.length; i++) {
                File f = new File(SharedPreferencesManager.getString(context,
                        SharedPreferencesManager.IMAGES_DIRECTORY), i + ".jpg");
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                bitmaps.add(b);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmaps;
    }
}

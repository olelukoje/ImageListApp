package ru.sibsoft.imagelistapp.model;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import ru.sibsoft.imagelistapp.manager.SharedPreferencesManager;

/**
 * Created by minaevaolga on 24/05/17.
 */

public class ListInflater {

    private FinishedListener listener;
    private ArrayList<Image> list;
    private Context context;
    private boolean favoriteOnlyBtnState = false;
    private ArrayList<Integer> linksFromFavoriteArrToInitArr;

    public final static String links[] = {
            "https://static.pexels.com/photos/2946/dawn-nature-sunset-trees.jpg",
            "https://static.pexels.com/photos/4700/nature-forest-moss-leaves.jpg",
            "https://lh6.googleusercontent.com/-iAs-IPZyxy8/AAAAAAAAAAI/AAAAAAAAA7E/phqi_" +
                    "--xdV4/s0-c-k-no-ns/photo.jpg"
    };

    public ListInflater(FinishedListener listener, Context context) {
        this.listener = listener;
        this.context = context;
        list = new ArrayList<>();
        for (int i = 0; i < links.length; i++) {
            Image im = new Image(false, "", null);
            list.add(im);
        }
    }

    public void loadBitmapFromUrl() {
        new loadBitmapFromUrl(links).execute();
    }

    public void changeData(int position, boolean isFavorite) {
        if (favoriteOnlyBtnState) {
            list.get(linksFromFavoriteArrToInitArr.get(position)).setFavorite(isFavorite);
        } else {
            list.get(position).setFavorite(isFavorite);
        }
    }

    public void changeData(int position, String description) {
        if (favoriteOnlyBtnState) {
            list.get(linksFromFavoriteArrToInitArr.get(position)).setDescription(description);
        } else {
            list.get(position).setDescription(description);
        }
    }

    public void setFavoriteOnlyState(boolean state) {
        favoriteOnlyBtnState = state;
    }

    public ArrayList<Image> getFavoriteOnlyList() {
        ArrayList<Image> favoriteOnlyList = new ArrayList<>();
        linksFromFavoriteArrToInitArr = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isFavorite()) {
                favoriteOnlyList.add(list.get(i));
                linksFromFavoriteArrToInitArr.add(i);
            }
        }
        return favoriteOnlyList;
    }

    public ArrayList<Image> getList() {
        if (favoriteOnlyBtnState) {
            return getFavoriteOnlyList();
        } else {
            return list;
        }
    }

    private class loadBitmapFromUrl extends AsyncTask<String, Void, ArrayList<Bitmap>> {
        String[] links;
        ArrayList<Bitmap> bitmaps = new ArrayList<>();

        private loadBitmapFromUrl(String[] links) {
            this.links = links;
        }

        @Override
        protected ArrayList<Bitmap> doInBackground(String... params) {
            try {
                for(int i = 0; i < links.length; i++) {
                    URL url = new URL(links[i]);
                    Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    bitmaps.add(image);
                }
            } catch(IOException e) {
                Log.d("loadBitmapFromUrl", e.getMessage());
            }

            return bitmaps;
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> bitmaps) {
            addBitmapsToList(bitmaps);
            listener.onDownloadFinished(list);
            new saveImagesToInternalStorage(bitmaps).execute();
        }
    }

    public void addBitmapsToList(ArrayList<Bitmap> bitmaps) {
        for(int i = 0; i < bitmaps.size(); i++) {
            list.get(i).setBitmap(bitmaps.get(i));
        }
    }

    private class saveImagesToInternalStorage extends AsyncTask<Bitmap, Void, Void> {
        ArrayList<Bitmap> bitmaps;

        private saveImagesToInternalStorage(ArrayList<Bitmap> bitmaps) {
            this.bitmaps = bitmaps;
        }

        @Override
        protected Void doInBackground(Bitmap... params) {
            ContextWrapper cw = new ContextWrapper(context);
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

            SharedPreferencesManager.setString(context,
                    SharedPreferencesManager.IMAGES_DIRECTORY, directory.getAbsolutePath());

            for(int i = 0; i < bitmaps.size(); i++) {
                File mypath = new File(directory, i + ".jpg");
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(mypath);
                    bitmaps.get(i).compress(Bitmap.CompressFormat.PNG, 100, fos);
                    Log.d("saveImagesToStorage", String.valueOf(i));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void params) {
            listener.onSaveImagesFinished();
        }
    }
}

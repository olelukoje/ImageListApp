package ru.sibsoft.imagelistapp.model;

import android.graphics.Bitmap;

/**
 * Created by minaevaolga on 23/05/17.
 */

public class Image {

    private boolean favorite;
    private String description;
    private Bitmap bitmap;

    public Image(boolean favorite, String description, Bitmap bitmap) {
        this.favorite = favorite;
        this.description = description;
        this.bitmap = bitmap;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}

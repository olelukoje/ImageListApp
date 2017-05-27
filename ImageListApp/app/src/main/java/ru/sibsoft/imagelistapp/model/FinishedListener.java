package ru.sibsoft.imagelistapp.model;

import java.util.ArrayList;

/**
 * Created by minaevaolga on 24/05/17.
 */

public interface FinishedListener {

    void onSaveImagesFinished();

    void onDownloadFinished(ArrayList<Image> list);
}

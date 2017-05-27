package ru.sibsoft.imagelistapp.view;

import java.util.ArrayList;

import ru.sibsoft.imagelistapp.model.Image;

/**
 * Created by minaevaolga on 23/05/17.
 */

public interface MainView {

    void changeView(ArrayList<Image> items);
    void setMessage(String message);
}

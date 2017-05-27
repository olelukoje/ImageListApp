package ru.sibsoft.imagelistapp.presenter;

/**
 * Created by minaevaolga on 23/05/17.
 */

public interface MainPresenter {

    void onResume();
    void onFavoriteOnlyBtnChanged(boolean state);
    void onItemClick(int position, String description);
    void onItemCheckedChanged(int position, boolean state);
}

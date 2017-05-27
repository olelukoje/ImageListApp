package ru.sibsoft.imagelistapp.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;

import ru.sibsoft.imageslistapp.R;
import ru.sibsoft.imagelistapp.manager.SharedPreferencesManager;
import ru.sibsoft.imagelistapp.model.FinishedListener;
import ru.sibsoft.imagelistapp.model.Image;
import ru.sibsoft.imagelistapp.model.ImageExtractor;
import ru.sibsoft.imagelistapp.model.ListInflater;
import ru.sibsoft.imagelistapp.view.MainView;

/**
 * Created by minaevaolga on 23/05/17.
 */

public class MainPresenterImpl implements MainPresenter, FinishedListener {

    private ImageExtractor imageExtractor;
    private ListInflater listInflater;
    private MainView mainView;
    private Context context;

    public MainPresenterImpl(MainView mainView, Context context) {
        this.mainView = mainView;
        this.context = context;
        listInflater = new ListInflater(this, context);
        imageExtractor = new ImageExtractor();
    }

    @Override
    public void onResume() {
        mainView.changeView(listInflater.getList());
        if (isNetworkAvailable()) {
            mainView.showProgress();
            listInflater.loadBitmapFromUrl();
        } else {
            if (SharedPreferencesManager.getString(context,
                    SharedPreferencesManager.IMAGES_DIRECTORY) != null) {

                ArrayList<Bitmap> bitmaps = imageExtractor.loadImageFromStorage(context);
                listInflater.addBitmapsToList(bitmaps);
            } else {
                mainView.setMessage(context.getString(R.string.no_internet));
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Override
    public void onSaveImagesFinished() {
        mainView.setMessage(context.getString(R.string.images_saved));
    }

    @Override
    public void onDownloadFinished(ArrayList<Image> list) {
        mainView.changeView(list);
        mainView.hideProgress();
    }

    @Override
    public void onFavoriteOnlyBtnChanged(boolean isChecked) {
        listInflater.setFavoriteOnlyState(isChecked);
        mainView.changeView(listInflater.getList());
    }

    @Override
    public void onItemClick(int position, String description) {
        listInflater.changeData(position, description);
        mainView.changeView(listInflater.getList());
    }

    @Override
    public void onItemCheckedChanged(int position, boolean state) {
        listInflater.changeData(position, state);
        mainView.changeView(listInflater.getList());
    }
}

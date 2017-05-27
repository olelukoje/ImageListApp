package ru.sibsoft.imagelistapp.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.sibsoft.imageslistapp.R;
import ru.sibsoft.imagelistapp.adapter.ListAdapter;
import ru.sibsoft.imagelistapp.model.Image;
import ru.sibsoft.imagelistapp.presenter.MainPresenter;
import ru.sibsoft.imagelistapp.presenter.MainPresenterImpl;

public class MainActivity extends AppCompatActivity implements MainView,
        CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.favorite_only_btn) ToggleButton favoriteOnlyBtn;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    private ListAdapter listAdapter;
    private MainPresenter mainPresenter;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        handler = new Handler();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainPresenter = new MainPresenterImpl(this, this);
        listAdapter = new ListAdapter(mainPresenter);
        recyclerView.setAdapter(listAdapter);

        favoriteOnlyBtn.setOnCheckedChangeListener(this);
    }

    @Override public void onResume() {
        super.onResume();
        mainPresenter.onResume();
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void changeView(final ArrayList<Image> list) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                listAdapter.addAll(list);
                listAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mainPresenter.onFavoriteOnlyBtnChanged(isChecked);
    }
}

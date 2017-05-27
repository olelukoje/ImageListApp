package ru.sibsoft.imagelistapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.sibsoft.imageslistapp.R;
import ru.sibsoft.imagelistapp.model.Image;
import ru.sibsoft.imagelistapp.presenter.MainPresenter;

/**
 * Created by minaevaolga on 23/05/17.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ImageViewHolder> {

    private ArrayList<Image> itemsList;
    private MainPresenter presenter;

    public ListAdapter(MainPresenter presenter) {
        itemsList = new ArrayList<>();
        this.presenter = presenter;
    }

    public void addAll(ArrayList<Image> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {
        holder.imageView.setImageBitmap(itemsList.get(position).getBitmap());
        holder.favoriteBtn.setChecked(itemsList.get(position).isFavorite());
        holder.descriptionTv.setText(itemsList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        @BindView(R.id.image) ImageView imageView;
        @BindView(R.id.favorite_btn) ToggleButton favoriteBtn;
        @BindView(R.id.description_tv) TextView descriptionTv;
        @BindView(R.id.edit_text) EditText descriptionEt;
        @BindView(R.id.add_btn) Button addBtn;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            favoriteBtn.setOnCheckedChangeListener(this);
            addBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (descriptionEt.getText().length() != 0) {
                presenter.onItemClick(getAdapterPosition(), descriptionEt.getText().toString());
                descriptionEt.setText("");
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            presenter.onItemCheckedChanged(getAdapterPosition(), isChecked);
        }
    }
}

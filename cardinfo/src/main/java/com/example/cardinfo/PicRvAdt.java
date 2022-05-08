package com.example.cardinfo;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.torang_core.data.model.RestaurantData;

/*
public abstract class PicRvAdt extends RecyclerView.Adapter<ImageHolder> {
    abstract void clickPicture();

    private RestaurantData restaurant = null;

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ImageHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {

        if (restaurant == null)
            return;

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(holder.itemView.getContext());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();

        Glide.with(holder.itemView.getContext())
                .load(restaurant.getImg_url1())
                .centerCrop()
                .placeholder(circularProgressDrawable)
                .into(holder.ivRestaurant);
        holder.ivRestaurant.setOnClickListener(view -> clickPicture());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void setRestaurant(RestaurantData restaurant) {
        this.restaurant = restaurant;
        notifyDataSetChanged();
    }
}
*/

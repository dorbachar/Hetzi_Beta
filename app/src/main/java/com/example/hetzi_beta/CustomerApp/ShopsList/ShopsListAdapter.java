package com.example.hetzi_beta.CustomerApp.ShopsList;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hetzi_beta.R;
import com.example.hetzi_beta.Shops.Shop;
import com.example.hetzi_beta.Utils;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

import static com.example.hetzi_beta.Utils.HTZ_INVALID_DISTANCE;

public class ShopsListAdapter extends android.support.v7.widget.RecyclerView.Adapter<ShopsListAdapter.ShopViewHolder> {
    public ArrayList<Shop> shops_list;
    public Context mContext;

    public ShopsListAdapter(ArrayList<Shop> shops_list, Activity activity) {
        this.shops_list = shops_list;
        this.mContext = activity;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext                        = viewGroup.getContext();
        int             layout_id       = R.layout.item_shop_preview;
        LayoutInflater inflater         = LayoutInflater.from(mContext);
        View            view            = inflater.inflate(layout_id, viewGroup, false);

        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int i) {
        Shop current_shop = shops_list.get(i);

        // TODO : preloader
        Glide.with(mContext)
                .load(current_shop.getLogoUri())
                .centerCrop()
                .into(holder.logo);

        Glide.with(mContext)
                .load(current_shop.getCoverPhotoUri())
                .centerCrop()
                .into(holder.cover_photo);

        String name = current_shop.getShopName();
        holder.shop_name.setText(name);
        holder.shop_address.setText(Utils.SHOP_ADDRESS.get(name).getAddress());

        Utils.updateUserLocation((Activity)mContext);
        Float dist = current_shop.calcDistanceFromUser();
        if (dist.equals(HTZ_INVALID_DISTANCE)) {
            holder.distance.setText("מיקום כבוי");
            holder.distance_units.setText("");
        } else {
            if (dist < 1) {
                holder.distance.setText(((Float)(dist*1000)).toString());
                holder.distance_units.setText(" מטר");
            } else {
                holder.distance.setText(dist.toString());
                holder.distance_units.setText(" קילומטר");
            }
        }
    }

    @Override
    public int getItemCount() {
        return shops_list.size();
    }

    class ShopViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        CircularImageView logo;
        ImageView cover_photo;
        TextView shop_name;
        TextView shop_address;
        TextView distance;
        TextView distance_units;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);

            logo            = itemView.findViewById(R.id.logo_CircularImageView);
            cover_photo     = itemView.findViewById(R.id.shop_cover_photo_ImageView);
            shop_name       = itemView.findViewById(R.id.shop_name_TextView);
            shop_address    = itemView.findViewById(R.id.shop_address_TextView);
            distance        = itemView.findViewById(R.id.distance_TextView);
            distance_units  = itemView.findViewById(R.id.distance_units_TextView);
        }
    }
}

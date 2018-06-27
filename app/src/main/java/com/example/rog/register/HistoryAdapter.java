package com.example.rog.register;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by ROG on 4/4/2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private Context mCtx;
    private List<HistoryList> productList;

    public HistoryAdapter(Context mCtx, List<HistoryList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.history_list, null);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        HistoryList product = productList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(product.getImage())
                .into(holder.imageView);

        holder.desc.setText(product.getDesc());
        holder.location.setText(product.getLoc());
        holder.type.setText(String.valueOf(product.getType()));
        holder.date.setText(String.valueOf(product.getDate()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView desc, location, date, type;
        ImageView imageView;

        public HistoryViewHolder(View itemView) {
            super(itemView);

            desc = itemView.findViewById(R.id.desc);
            location = itemView.findViewById(R.id.location);
            type = itemView.findViewById(R.id.type);
            date = itemView.findViewById(R.id.date);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
package com.example.healthcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class recycleAdapter extends RecyclerView.Adapter<recycleView> {
    Context context;
    List<recyleitems> items;
    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public recycleAdapter(Context context, List<recyleitems> items, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.items = items;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public recycleView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new recycleView(LayoutInflater.from(context).inflate(R.layout.articleslist,parent,false ));
    }

    @Override
    public void onBindViewHolder(@NonNull recycleView holder, int position) {
        holder.nameTxtView.setText(items.get(position).getName());
        holder.descriptionTxtView.setText(items.get(position).getDescription());
        holder.imageView.setImageResource(items.get(position).getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the onItemClick method of the listener
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

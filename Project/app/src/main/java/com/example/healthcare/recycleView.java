package com.example.healthcare;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class recycleView extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView nameTxtView, descriptionTxtView;
    public recycleView(@NonNull View itemView) {
        super(itemView);
        this.imageView = itemView.findViewById(R.id.imageView2);
        this.nameTxtView = itemView.findViewById(R.id.textView3);
        this.descriptionTxtView = itemView.findViewById(R.id.textView4);
    }
}

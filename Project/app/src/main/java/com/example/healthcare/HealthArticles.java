package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class HealthArticles extends AppCompatActivity {

    ImageButton healthBtn;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_articles);

        healthBtn = findViewById(R.id.healthBackBtn);
        recyclerView = findViewById(R.id.articles);

        healthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HealthArticles.this, Home.class));
            }
        });

        List<recyleitems> items = new ArrayList<>();

        items.add(new recyleitems("How does physical activity benefit classroom learning?","Games and sports help children regulate their behaviour and emotions",R.drawable.imag1));
        items.add(new recyleitems("How do motor impairments affect learning?","Recognising the impact of developmental coordination disorder is the...",R.drawable.imag2));
        items.add(new recyleitems("How does physical activity benefit classroom learning?","Games and sports help children regulate their behaviour and emotions",R.drawable.imag1));
        items.add(new recyleitems("How do motor impairments affect learning?","Recognising the impact of developmental coordination disorder is the...",R.drawable.imag2));
        items.add(new recyleitems("How does physical activity benefit classroom learning?","Games and sports help children regulate their behaviour and emotions",R.drawable.imag1));
        items.add(new recyleitems("How do motor impairments affect learning?","Recognising the impact of developmental coordination disorder is the first...",R.drawable.imag2));
        items.add(new recyleitems("How does physical activity benefit classroom learning?","Games and sports help children regulate their behaviour and emotions",R.drawable.imag1));
        items.add(new recyleitems("How do motor impairments affect learning?","Recognising the impact of developmental coordination disorder is the first step...",R.drawable.imag2));
        items.add(new recyleitems("How does physical activity benefit classroom learning?","Games and sports help children regulate their behaviour and emotions",R.drawable.imag1));
        items.add(new recyleitems("How do motor impairments affect learning?","Recognising the impact of developmental coordination disorder is the first step...",R.drawable.imag2));
        items.add(new recyleitems("How does physical activity benefit classroom learning?","Games and sports help children regulate their behaviour and emotions",R.drawable.imag1));
        items.add(new recyleitems("How do motor impairments affect learning?","Recognising the impact of developmental coordination disorder is the first step...",R.drawable.imag2));
        items.add(new recyleitems("How does physical activity benefit classroom learning?","Games and sports help children regulate their behaviour and emotions",R.drawable.imag1));
        items.add(new recyleitems("How do motor impairments affect learning?","Recognising the impact of developmental coordination disorder is the first step...",R.drawable.imag2));
        items.add(new recyleitems("How does physical activity benefit classroom learning?","Games and sports help children regulate their behaviour and emotions",R.drawable.imag1));
        items.add(new recyleitems("How do motor impairments affect learning?","Recognising the impact of developmental coordination disorder is the first step...",R.drawable.imag2));
        items.add(new recyleitems("How does physical activity benefit classroom learning?","Games and sports help children regulate their behaviour and emotions",R.drawable.imag1));
        items.add(new recyleitems("How do motor impairments affect learning?","Recognising the impact of developmental coordination disorder is the first step...",R.drawable.imag2));
        items.add(new recyleitems("How does physical activity benefit classroom learning?","Games and sports help children regulate their behaviour and emotions",R.drawable.imag1));
        items.add(new recyleitems("How do motor impairments affect learning?","Recognising the impact of developmental coordination disorder is the first step...",R.drawable.imag2));
        items.add(new recyleitems("How does physical activity benefit classroom learning?","Games and sports help children regulate their behaviour and emotions",R.drawable.imag1));
        items.add(new recyleitems("How do motor impairments affect learning?","Recognising the impact of developmental coordination disorder is the first step...",R.drawable.imag2));
        items.add(new recyleitems("How does physical activity benefit classroom learning?","Games and sports help children regulate their behaviour and emotions",R.drawable.imag1));
        items.add(new recyleitems("How do motor impairments affect learning?","Recognising the impact of developmental coordination disorder is the first step...",R.drawable.imag2));


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new recycleAdapter(getApplicationContext(), items, new recycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                recyleitems clickedItem = items.get(position);
                String articleTitle = clickedItem.getName();
                String articleUrl;
                if (articleTitle.equals("How does physical activity benefit classroom learning?")){
                    articleUrl = "https://bold.expert/how-does-physical-activity-benefit-classroom-learning";
                }else {
                    articleUrl = "https://bold.expert/how-do-motor-impairments-affect-learning";
                }
                showBrowserChooserDialog(articleUrl);
            }
        }));

    }

    private void showBrowserChooserDialog(final String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a browser");
        final String[] browsers = {"Chrome", "Firefox", "Default Browser"};

        builder.setItems(browsers, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        openUrlInBrowser(url, "com.android.chrome");
                        break;
                    case 1:
                        openUrlInBrowser(url, "org.mozilla.firefox");
                        break;
                    case 2:
                        openUrlInDefaultBrowser(url);
                        break;
                }
            }
        });

        builder.show();
    }

    private void openUrlInBrowser(String url, String packageName) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setPackage(packageName);

        try {
            startActivity(intent);
        } catch (Exception e) {
            // Handle exception if the browser is not installed
            e.printStackTrace();
            openUrlInDefaultBrowser(url);
        }
    }

    private void openUrlInDefaultBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
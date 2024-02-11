package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class LabTest extends AppCompatActivity {

    private String[][] labTestPackages = {
            {"Package 1 : Full Body Checkup", "", "", "", "2000"},
            {"Package 2 : Blood Glucose Fasting", "", "", "", "299"},
            {"Package 3 : COVID-19 Antibody - IgG", "", "", "", "899"},
            {"Package 4 : Thyroid Check", "", "", "", "499"},
            {"Package 5 : Immunity Check", "", "", "", "699"},
    };
    private String[] packageDetail = {
            "Blood Glucose Fasting\n"+
                    "Complete Hemogram\n"+
                    "HbA1c\n"+
                    "Iron Studies\n"+
                    "Kidney Function Test\n"+
                    "LDH Lactate Dehydrogenase, Serum\n"+
                    "Lipid Profile\n"+
                    "Liver Function Test",
            "Blood Glucose Fasting",
            "COVID-19 Antibody - IgG",
            "Thyroid Profile-Total (T3, T4 & Ultra-sensitive)",
            "Complete Hemogram\n"+
                    "CRP (C Reactive Protein) Quantitative, Serum\n"+
                    "Iron Studies\n"+
                    "Kidney Function Test\n"+
                    "Vitamin D Total-25 Hydroxy\n"+
                    "Liver Function Test\n"+
                    "Lipid Profile"
    };
    HashMap<String,String> item;
    ArrayList list;
    SimpleAdapter simpleAdapter;
    ImageButton goToCartBtn, ltBackBtn;
    ListView ltListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);

        goToCartBtn = findViewById(R.id.ltGoToCart);
        ltBackBtn = findViewById(R.id.ltBackBtn);
        ltListView = findViewById(R.id.lv_LabTest);

        ltBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabTest.this, Home.class));
            }
        });

        list = new ArrayList();
        for (int i = 0; i < labTestPackages.length; i++) {
            item = new HashMap<String,String>();
            item.put("line1",labTestPackages[i][0]);
            item.put("line2",labTestPackages[i][1]);
            item.put("line3",labTestPackages[i][2]);
            item.put("line4",labTestPackages[i][3]);
            item.put("line5","Total Cost:"+labTestPackages[i][4]+"/-");
            list.add(item);
        }
        simpleAdapter = new SimpleAdapter(LabTest.this,
                list,
                R.layout.multiples_lines,
                new String[]{"line1","line2","line3","line4","line5"},
                new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e}
        );
        ltListView.setAdapter(simpleAdapter);

        ltListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LabTest.this,LabTestDetail.class);
                intent.putExtra("text1",labTestPackages[i][0]);
                intent.putExtra("text2",packageDetail[i]);
                intent.putExtra("text3",labTestPackages[i][4]);
                startActivity(intent);
            }
        });

        goToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabTest.this,LabActivity.class));
            }
        });
    }
}
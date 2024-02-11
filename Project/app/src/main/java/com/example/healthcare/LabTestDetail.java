package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabTestDetail extends AppCompatActivity {

    TextView ltPackageTV, totalCostTV;
    EditText packageDetailED;
    ImageButton btnBackToLT, btnGoToCart;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_detail);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        ltPackageTV = findViewById(R.id.ltDetailPackages);
        totalCostTV = findViewById(R.id.ltDetailTotalCost);
        packageDetailED = findViewById(R.id.detail);
        btnBackToLT = findViewById(R.id.ltDetailBackBtn);
        btnGoToCart = findViewById(R.id.ltDetailGoToCart);

        packageDetailED.setKeyListener(null);

        Intent intent = getIntent();
        ltPackageTV.setText(intent.getStringExtra("text1"));
        packageDetailED.setText(intent.getStringExtra("text2"));
        totalCostTV.setText("Total Cost : "+intent.getStringExtra("text3")+"/-");

        String pkgs = intent.getStringExtra("text1");
        String cost = intent.getStringExtra("text3");

        btnBackToLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabTestDetail.this, LabTest.class));
            }
        });
        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            Map<String, Object> cart = new HashMap<>();

            @Override
            public void onClick(View view) {
                cart.put("Package", intent.getStringExtra("text1"));
                cart.put("Price", intent.getStringExtra("text3"));

                // Check if the cart document exists for the user
                firestore.collection("cart")
                        .document(firebaseAuth.getCurrentUser().getUid())
                        .get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                // Cart document exists, update the list of packages
                                updateCart(documentSnapshot,pkgs,cost);
                            } else {
                                // Cart document does not exist, create a new one
                                createNewCart(pkgs,cost);
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(LabTestDetail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }

    // Method to update the cart with a new package
    private void updateCart(DocumentSnapshot documentSnapshot, String pkgs, String cost) {
        List<String> currentPackages = (List<String>) documentSnapshot.get("Packages");
        List<String> currentCosts = (List<String>) documentSnapshot.get("Costs");

        if (currentPackages == null) {
            currentPackages = new ArrayList<>();
        }

        if (currentCosts == null) {
            currentCosts = new ArrayList<>();
        }

        if (!currentPackages.contains(pkgs)) {
            currentPackages.add(pkgs);
            currentCosts.add(cost);

            firestore.collection("cart")
                    .document(firebaseAuth.getCurrentUser().getUid())
                    .update("Packages", currentPackages, "Costs", currentCosts)
                    .addOnSuccessListener(aVoid -> {
                        startActivity(new Intent(LabTestDetail.this, LabTest.class));
                        Toast.makeText(LabTestDetail.this, "Package Added", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(LabTestDetail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(LabTestDetail.this, "Package already in the cart", Toast.LENGTH_SHORT).show();
        }
    }

    private void createNewCart(String pkgs, String cost) {
        List<String> packages = new ArrayList<>();
        List<String> costs = new ArrayList<>();

        packages.add(pkgs);
        costs.add(cost);

        Map<String, Object> cart = new HashMap<>();
        cart.put("Packages", packages);
        cart.put("Costs", costs);

        firestore.collection("cart")
                .document(firebaseAuth.getCurrentUser().getUid())
                .set(cart)
                .addOnSuccessListener(aVoid -> {
                    startActivity(new Intent(LabTestDetail.this, LabTest.class));
                    Toast.makeText(LabTestDetail.this, "Package Added", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(LabTestDetail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

}
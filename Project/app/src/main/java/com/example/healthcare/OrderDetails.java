package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetails extends AppCompatActivity {

    private String[][] orderDetail = {};
    ListView orderDetailLV;
    ImageButton orderDetailBackBtn;
    HashMap<String,String> hashMap;
    ArrayList arrayList;
    Map<String, Object> orderData;
    SimpleAdapter simpleAdapter;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    private List<String> keysToDisplay = Arrays.asList("Full Name", "Address", "Contact No", "Pin Code", "Price");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        orderDetailBackBtn = findViewById(R.id.orderBackBtn);
        orderDetailLV = findViewById(R.id.lv_orderDetail);
        fetchCartData();

        orderDetailBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderDetails.this, Home.class));
            }
        });

    }

    private void fetchCartData() {
        firestore.collection("order")
                .document(firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            orderData = documentSnapshot.getData();
                            if (orderData != null) {

                                String name, address, contact, pin, price;
                                arrayList = new ArrayList();
                                hashMap = new HashMap<>();
                                Log.d("OrderDetails", "Adding Key: " + keysToDisplay.get(0).toString() + ", Value: " + orderData.get(keysToDisplay.get(0).toString()));
                                name = orderData.get(keysToDisplay.get(0).toString()).toString();
                                address = orderData.get(keysToDisplay.get(1).toString()).toString();
                                contact = orderData.get(keysToDisplay.get(2).toString()).toString();
                                pin = orderData.get(keysToDisplay.get(3).toString()).toString();
                                price = orderData.get(keysToDisplay.get(4).toString()).toString();

                                Log.d("OrderDetails", "Adding Key: " + keysToDisplay.get(0).toString() + ", Value: " + name);
                                Log.d("OrderDetails", "Adding Key: " + keysToDisplay.get(1).toString() + ", Value: " + address);
                                Log.d("OrderDetails", "Adding Key: " + keysToDisplay.get(2).toString() + ", Value: " + contact);
                                Log.d("OrderDetails", "Adding Key: " + keysToDisplay.get(3).toString() + ", Value: " + pin);
                                Log.d("OrderDetails", "Adding Key: " + keysToDisplay.get(4).toString() + ", Value: " + price);

                                hashMap.put("line1", keysToDisplay.get(0).toString() + ": " + name);
                                hashMap.put("line2", keysToDisplay.get(1).toString() + ": " + address);
                                hashMap.put("line3", keysToDisplay.get(2).toString() + ": " + contact);
                                hashMap.put("line4", keysToDisplay.get(3).toString() + ": " + pin);
                                hashMap.put("line5", keysToDisplay.get(4).toString() + ": " + price);
                                        // Add more lines as needed
                                arrayList.add(hashMap);


                                simpleAdapter = new SimpleAdapter(OrderDetails.this,
                                        arrayList,
                                        R.layout.multiples_lines,
                                        new String[]{"line1", "line2", "line3", "line4", "line5"},
                                        new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e}
                                );

                                orderDetailLV.setAdapter(simpleAdapter);
                            } else {
                                Toast.makeText(OrderDetails.this, "No data found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(OrderDetails.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OrderDetails.this, "Error fetching orders: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }



}
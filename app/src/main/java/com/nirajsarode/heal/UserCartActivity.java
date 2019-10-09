package com.nirajsarode.heal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Date;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Map;

public class UserCartActivity extends AppCompatActivity {

    ListView mItemListView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;

    TextView mPrice;
    Button mBuy;
    List<CartItem> itemlist;
    private CollectionReference cartref;

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart);

        mFireStore = FirebaseFirestore.getInstance();
        mItemListView = (ListView) findViewById(R.id.cartitemlist);
        mAuth = FirebaseAuth.getInstance();
        itemlist = new ArrayList<>();
        mPrice = findViewById(R.id.totalprice);
        mBuy = findViewById(R.id.buyitemsfromcart);
        final String user_id = mAuth.getCurrentUser().getUid();
        String uEmail = mAuth.getCurrentUser().getEmail();

        CartItem Cart;
        final ArrayList<CartItem> carrayList = new ArrayList<CartItem>();

        mFireStore.collection("users").document(user_id).collection("cart").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if(!documentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                    int tpr=0;
                    for (DocumentSnapshot d: list){
                        CartItem f = d.toObject(CartItem.class);
                        carrayList.add(f);
                    }
                }
            }
        });

        mFireStore.collection("users").document(user_id).collection("cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                }else{
                    mBuy.setEnabled(false);
                    finish();
                    Intent ad = new Intent(UserCartActivity.this,DashboardActivity.class);
                    startActivity(ad);
                }


            }
        });




        cartref = mFireStore.collection("users").document(user_id).collection("cart");

        mItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView dn = (TextView) view.findViewById(R.id.itemname);
                String text1 = dn.getText().toString();

//                Intent info = new Intent(SelectDoctorActivity.this, DoctorInfoActivity.class);
//                info.putExtra("doctorname", text1);
//                startActivity(info);
            }
        });

        mBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Map<String,ArrayList<CartItem>> data  = new HashMap<>();
                data.put("cart", carrayList);

                final Timestamp ts = Timestamp.now();

                final Order O = new Order(carrayList,ts);

                mFireStore.collection("users").document(user_id).collection("cart").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d: list){
                                String mid = d.get("medicalid").toString();
                                mFireStore.collection("medicals").document(user_id).collection("orders").add(O).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(getBaseContext(), "Updated", Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                        }
                    }
                });

                mFireStore.collection("users").document(user_id).collection("orders").add(O).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        mFireStore.collection("users").document(user_id).collection("cart").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if(!queryDocumentSnapshots.isEmpty()){
                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                    for (DocumentSnapshot d: list){
                                       d.getReference().delete();
                                    }
                                }
                            }
                        });
                        mBuy.setEnabled(false);
                        finish();
                        Intent ad = new Intent(UserCartActivity.this,DashboardActivity.class);
                        startActivity(ad);

                    }
                });

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mItemListView.setAdapter(null);
        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();
        itemlist = new ArrayList<>();
        final String user_id = mAuth.getCurrentUser().getUid();
        String uEmail = mAuth.getCurrentUser().getEmail();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mFireStore.collection("users").document(user_id).collection("cart").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if(!documentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                    int tpr=0;
                    for (DocumentSnapshot d: list){
                        int pr = (Integer) d.getLong("price").intValue();
                        tpr= tpr + pr;
                        String tot = String.valueOf(tpr);
                        mPrice.setText(tot);
                        CartItem f = d.toObject(CartItem.class);
                        itemlist.add(f);
                    }

                    CartItemList adapter = new CartItemList(UserCartActivity.this,itemlist);
                    mItemListView.setAdapter(adapter);

                }
            }
        });

    }

}

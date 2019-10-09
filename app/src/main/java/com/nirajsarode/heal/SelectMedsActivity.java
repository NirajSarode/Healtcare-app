package com.nirajsarode.heal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectMedsActivity extends AppCompatActivity {

    private String mMedicalID;
    private EditText mQuant;
    private static final String TAG = "MyActivity";
    private Button mAddCart;
    private TextView mPrice;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_meds);
        final Spinner medspinner = (Spinner) findViewById(R.id.medspinner);
        mFireStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mQuant = findViewById(R.id.quantityuser);
        mAddCart = findViewById(R.id.addtocartbt);
        mMedicalID = getIntent().getExtras().get("MedicalID").toString();
        mPrice = findViewById(R.id.priceview);

        final String user_id = mAuth.getCurrentUser().getUid();


        mFireStore.collection("users").document(user_id).collection("cart").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d: list) {
                        String medid = d.getString("medicalid");
                        if(!medid.equals(mMedicalID))
                        {

                            mAddCart.setEnabled(false);
                            Toast.makeText(getApplicationContext(), "Cannot Add In Cart(Different Medical)", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uEmail = user.getEmail();


        final List<String> meds = new ArrayList<String>();
        meds.add("Select Medicines");


        mFireStore.collection("medicals").document(mMedicalID).collection("medicines").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if(!documentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                    for (DocumentSnapshot d: list) {
                        String medname = d.getString("name");
                        Log.v(TAG, medname);
                        meds.add(medname);
                        ArrayAdapter<String> medsAdapter = new ArrayAdapter<String>(SelectMedsActivity.this, android.R.layout.simple_spinner_item, meds);
                        medsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        medspinner.setAdapter(medsAdapter);
                    }
                }
            }
        });

//        mFirestore.collection("medicals").document(mMedicalID).collection("medicines").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot documentSnapshots) {
//                if(!documentSnapshots.isEmpty()){
//                    List<DocumentSnapshot> list = documentSnapshots.getDocuments();
//                    for (DocumentSnapshot d: list) {
//                        String medicine = medspinner.getSelectedItem().toString();
//                        String medname = d.getString("name");
//                    if(medname.equals(medicine)){
//                        String price = d.getString("price");
//                       mPrice.setText(price);}
//                    }
//                }
//            }
//        });


        mQuant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFireStore.collection("medicals").document(mMedicalID).collection("medicines").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if(!documentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                            for (DocumentSnapshot d: list) {
                                String medicine = medspinner.getSelectedItem().toString();
                                String medname = d.getString("name");
                                if(medname.equals(medicine)){
                                    String price = d.getLong("price").toString();
                                    mPrice.setText(price);


                                    mFireStore.collection("users").document(user_id).collection("cart").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            if(!queryDocumentSnapshots.isEmpty()){
                                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                for (DocumentSnapshot d: list) {
                                                    String medicinee = medspinner.getSelectedItem().toString();
                                                    String mednamee = d.getString("medicines");
                                                    if(mednamee.equals(medicinee)){
                                                        mAddCart.setEnabled(false);
                                                        Toast.makeText(getApplicationContext(), "Already in Cart ", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }
                });
            }
        });
        mAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String medicine = medspinner.getSelectedItem().toString();
                String quantity = mQuant.getText().toString();
                int qua = Integer.valueOf(quantity);
                String priceg = mPrice.getText().toString();
                int pri = Integer.valueOf(priceg);

                Map<String,Object> data  = new HashMap<>();
                data.put("medicines", medicine);
                data.put("medicalid",mMedicalID);
                data.put("quantity", qua);
                data.put("price", pri*qua);

                mFireStore.collection("users").document(user_id).collection("cart").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                       mQuant.setText("");
                       Toast.makeText(getBaseContext(), "Added to Cart", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });


    }
}

package com.nirajsarode.heal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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

public class MedicalStockActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ListView mMedicalMedListView;
    Button mAddDb;
    Button mAddmedbd;
    private FirebaseFirestore mFireStore;
    List<Medicines> medicalmedlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_stock);

        mMedicalMedListView = (ListView) findViewById(R.id.medicalavailable);
        final Spinner medspinner = (Spinner) findViewById(R.id.medicalspinner);
        mFireStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        medicalmedlist = new ArrayList<Medicines>();
        mAddDb = findViewById(R.id.addmeddbmain);
        mAddmedbd = findViewById(R.id.addtodbmed);
        final String user_id = mAuth.getCurrentUser().getUid();

        final List<String> meds = new ArrayList<String>();
        meds.add("Select Medicines");

        mFireStore.collection("medicines").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d: list) {
                        String medname = d.getId();
                        meds.add(medname);
                        ArrayAdapter<String> medsAdapter = new ArrayAdapter<String>(MedicalStockActivity.this, android.R.layout.simple_spinner_item, meds);
                        medsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        medspinner.setAdapter(medsAdapter);
                    }
                }
            }
        });

        EditText nameM = (EditText) findViewById(R.id.addmedname);
        final String name = nameM.getText().toString();
        if (TextUtils.isEmpty(name)) {
            nameM.setError("Name Field Cannot be Empty");
            return;
        }
        EditText priceM = (EditText) findViewById(R.id.addmedprice);
        String price = priceM.getText().toString();
        int P = Integer.parseInt(price);
        if (TextUtils.isEmpty(price)) {
            priceM.setError("Price Field Cannot be Empty");
            return;
        }
        final EditText descM = (EditText) findViewById(R.id.addmeddesc);
        String desc = descM.getText().toString();
        if (TextUtils.isEmpty(desc)) {
            descM.setError("Description Field Cannot be Empty");
            return;
        }

        final Map<String,Object> datamed  = new HashMap<>();
        datamed.put("description", desc);

        final Map<String,Object> datamedi  = new HashMap<>();
        datamedi.put("name", name);
        datamedi.put("price", P);
        datamedi.put("description", desc);


        String medicine = medspinner.getSelectedItem().toString();
        mFireStore.collection("medicines").document(medicine).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
              String des = (String) documentSnapshot.get("description");
            }
        });


        mAddDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFireStore.collection("medicines").document(name).set(datamed).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mFireStore.collection("medicals").document(user_id).collection("medicines").add(datamedi).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getBaseContext(), "Updated", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        mMedicalMedListView.setAdapter(null);
        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();
        medicalmedlist = new ArrayList<Medicines>();
        final String user_id = mAuth.getCurrentUser().getUid();
        String uEmail = mAuth.getCurrentUser().getEmail();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mFireStore.collection("medicals").document(user_id).collection("medicines").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if(!documentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                    for (DocumentSnapshot d: list){

                        String usid = d.getString("userid");
                        Medicines a = d.toObject(Medicines.class);
                        medicalmedlist.add(a);
                    }

                    MedicinesList adapter = new MedicinesList(MedicalStockActivity.this,medicalmedlist);
                    mMedicalMedListView.setAdapter(adapter);
                }
            }
        });
        }

}

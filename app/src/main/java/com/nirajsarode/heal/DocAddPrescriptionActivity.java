package com.nirajsarode.heal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DocAddPrescriptionActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;
    String userID;
    String Docname;
    String Pname;
    String Loc;
    String apID;
    String apdate;
    Button addmed;
    Button addmeditem;
    Button submit;
    LinearLayout showBox;
    String docId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_add_prescription);

        mFireStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = getIntent().getExtras().get("UId").toString();
        apID = getIntent().getExtras().get("AppId").toString();


        final EditText desc = (EditText) findViewById(R.id.docpresdesc);
        mFireStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final String doc_id = mAuth.getCurrentUser().getUid();
        String uEmail = mAuth.getCurrentUser().getEmail();


        mFireStore.collection("patients").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Pname = (String) documentSnapshot.get("name");
            }
        });

        mFireStore.collection("doctors").document(doc_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Docname = (String) documentSnapshot.get("name");
                Loc = (String) documentSnapshot.get("location");

            }
        });

        mFireStore.collection("appointments").document(apID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                apdate = (String) documentSnapshot.get("date");
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        showBox = (LinearLayout) findViewById(R.id.medicine_layout);

        addmeditem = (Button) findViewById(R.id.docaddmed);
        submit = (Button) findViewById(R.id.docaddpres);

        addmed = (Button) findViewById(R.id.showmedbox);
        addmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                final String user_id = mAuth.getCurrentUser().getUid();
                String uEmail = mAuth.getCurrentUser().getEmail();

                String descr = desc.getText().toString();


                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                String format = simpleDateFormat.format(new Date());

                Map<String,Object> data  = new HashMap<>();
                data.put("date", format);
                data.put("doctor", Docname);
                data.put("description", descr);
                data.put("doctorid", user_id);
                data.put("userid", userID);
                data.put("location", Loc);
                data.put("user", Pname);

                mFireStore.collection("patients").document(userID).collection("prescriptions").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getBaseContext(), "Added", Toast.LENGTH_LONG).show();
                        docId = documentReference.getId();
                        showBox.setVisibility(View.VISIBLE);
                        submit.setVisibility(View.VISIBLE);
                        addmed.setVisibility(View.GONE);

                    }
                });
            }
        });

        addmeditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText descmed = (EditText) findViewById(R.id.meddesc);
                final EditText medN = (EditText) findViewById(R.id.docmedpres);
                final EditText qty = (EditText) findViewById(R.id.docmedqtyprec);
                String med = medN.getText().toString();
                String qt = qty.getText().toString();
                String descri = descmed.getText().toString();
                int quant = Integer.valueOf(qt);


                final Map<String, Object> data = new HashMap<>();
                data.put("medicine", med);
                data.put("description", descri);
                data.put("quantity", quant);



                mFireStore.collection("patients").document(userID).collection("prescriptions").document(docId).collection("medicines").document(med).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getBaseContext(), " Medicine Added, You Can Add More", Toast.LENGTH_LONG).show();
                        medN.setText("");
                        qty.setText("");
                        descmed.setText("");
                    }
                });
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info = new Intent(DocAddPrescriptionActivity.this,DocPatientPrescriptionsActivity.class);
                startActivity(info);
            }
        });


    }
}

package com.nirajsarode.heal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorViewPatientInfoActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_patient_info);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        userID = getIntent().getExtras().get("UID").toString();

        final TextView nameET = (TextView) findViewById(R.id.dpatientnameup);

        final TextView contactEt = (TextView) findViewById(R.id.dpatientcontup);

        final TextView bgEt = (TextView) findViewById(R.id.dpatientbgup);

        final TextView genderEt = (TextView) findViewById(R.id.dpatientgenderup);

        final TextView heightEt = (TextView) findViewById(R.id.dpatienthtup);

        final TextView wtEt = (TextView) findViewById(R.id.dpatientwtup);

        final TextView ageEt = (TextView) findViewById(R.id.dpatientageup);

        mFirestore.collection("patients").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String pname = (String) documentSnapshot.get("name");
                long con = documentSnapshot.getLong("contact");
                String gen = (String) documentSnapshot.get("gender");
                String bg = (String) documentSnapshot.get("bloodgroup");
                int age = documentSnapshot.getLong("age").intValue();
                String ht = (String) documentSnapshot.get("height");
                String wt = (String) documentSnapshot.get("weight");
                String cont = String.valueOf(con);
                String ags = String.valueOf(age);
                nameET.setText(pname);
                contactEt.setText(cont);
                bgEt.setText(bg);

                genderEt.setText(gen);
                heightEt.setText(ht);
                wtEt.setText(wt);
                ageEt.setText(ags);
            }
        });


    }
}

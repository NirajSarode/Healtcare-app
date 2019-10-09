package com.nirajsarode.heal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PatientProfileUpdateActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    private Button submitbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile_update);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        final String user_id = mAuth.getCurrentUser().getUid();
        String uEmail = mAuth.getCurrentUser().getEmail();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        submitbt = findViewById(R.id.submitbtup);


        final TextView nameET = (TextView) findViewById(R.id.patientnameup);

        final TextView contactEt = (TextView) findViewById(R.id.patientcontup);

        final EditText bgEt = (EditText) findViewById(R.id.patientbgup);

        final EditText genderEt = (EditText) findViewById(R.id.patientgenderup);

        final EditText heightEt = (EditText) findViewById(R.id.patienthtup);

        final EditText wtEt = (EditText) findViewById(R.id.patientwtup);

        final EditText ageEt = (EditText) findViewById(R.id.patientageup);




        mFirestore.collection("patients").document(user_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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

        submitbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView nameET = (TextView) findViewById(R.id.patientnameup);
                String name = nameET.getText().toString();

                TextView contactEt = (TextView) findViewById(R.id.patientcontup);
                String con = contactEt.getText().toString();

                EditText bgEt = (EditText) findViewById(R.id.patientbgup);
                String bt = bgEt.getText().toString();
                if (TextUtils.isEmpty(bt)) {
                    bgEt.setError("BloodGrp Field Cannot be Empty");
                    return;
                }
                EditText genderEt = (EditText) findViewById(R.id.patientgenderup);
                String gender = genderEt.getText().toString();
                if (TextUtils.isEmpty(gender)) {
                    genderEt.setError("Gender Field Cannot be Empty");
                    return;
                }
                EditText heightEt = (EditText) findViewById(R.id.patienthtup);
                String ht = heightEt.getText().toString();
                if (TextUtils.isEmpty(ht)) {
                    heightEt.setError("Height Field Cannot be Empty");
                    return;
                }
                EditText wtEt = (EditText) findViewById(R.id.patientwtup);
                String wt = wtEt.getText().toString();
                if (TextUtils.isEmpty(wt)) {
                    wtEt.setError("Weight Field Cannot be Empty");
                    return;
                }

                EditText ageEt = (EditText) findViewById(R.id.patientageup);
                String age = ageEt.getText().toString();
                if (TextUtils.isEmpty(age)) {
                    ageEt.setError("Age Field Cannot be Empty");
                    return;
                }

                final String user_id = mAuth.getCurrentUser().getUid();
                String uEmail = mAuth.getCurrentUser().getEmail();

                long c = Long.valueOf(con);
                int a = Integer.valueOf(age);


                final Map<String,Object> data  = new HashMap<>();
                data.put("bloodgroup", bt);
                data.put("contact", c);
                data.put("email", uEmail);
                data.put("sex", gender);
                data.put("height", ht);
                data.put("name", name);
                data.put("age",a);
                data.put("weight", wt);


                final Map<String,Object> datat  = new HashMap<>();
                datat.put("name", name);
                datat.put("email", uEmail);
                datat.put("age",a);
                datat.put("sex", gender);
                datat.put("category", "patient");


                mFirestore.collection("users").document(user_id).update(datat).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getBaseContext(), "User Profile Updated", Toast.LENGTH_LONG).show();
                    }
                });
                mFirestore.collection("patients").document(user_id).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getBaseContext(), "Profile Updated", Toast.LENGTH_LONG).show();
                        finish();
                        Intent ad = new Intent(PatientProfileUpdateActivity.this,DashboardActivity.class);
                        startActivity(ad);
                    }
                });
            }
        });


    }
}

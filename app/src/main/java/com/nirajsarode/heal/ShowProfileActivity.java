package com.nirajsarode.heal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShowProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    private Button updatebt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);


        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        updatebt = findViewById(R.id.updatebt);

        EditText nameET = (EditText) findViewById(R.id.patientnamepros);
        String name = nameET.getText().toString();
        if (TextUtils.isEmpty(name)) {
            nameET.setError("Name Field Cannot be Empty");
            return;
        }

        EditText contactEt = (EditText) findViewById(R.id.patientcontpros);
        String con = contactEt.getText().toString();
        if (TextUtils.isEmpty(con)) {
            contactEt.setError("Contact Field Cannot be Empty");
            return;
        }
        EditText bgEt = (EditText) findViewById(R.id.patientbgpro);
        String bt = bgEt.getText().toString();
        if (TextUtils.isEmpty(bt)) {
            bgEt.setError("BloodGrp Field Cannot be Empty");
            return;
        }
        EditText genderEt = (EditText) findViewById(R.id.patientgenderpro);
        String gender = genderEt.getText().toString();
        if (TextUtils.isEmpty(gender)) {
            genderEt.setError("Gender Field Cannot be Empty");
            return;
        }
        EditText heightEt = (EditText) findViewById(R.id.patienthtpro);
        String ht = heightEt.getText().toString();
        if (TextUtils.isEmpty(ht)) {
            heightEt.setError("Height Field Cannot be Empty");
            return;
        }
        EditText wtEt = (EditText) findViewById(R.id.patientwtpro);
        String wt = wtEt.getText().toString();
        if (TextUtils.isEmpty(wt)) {
            wtEt.setError("Weight Field Cannot be Empty");
            return;
        }

        EditText ageEt = (EditText) findViewById(R.id.patientagepro);
        String age = ageEt.getText().toString();
        if (TextUtils.isEmpty(age)) {
            ageEt.setError("Age Field Cannot be Empty");
            return;
        }

        final String user_id = mAuth.getCurrentUser().getUid();
        String uEmail = mAuth.getCurrentUser().getEmail();



    }
}

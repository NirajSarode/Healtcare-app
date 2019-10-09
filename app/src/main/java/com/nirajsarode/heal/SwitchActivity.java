package com.nirajsarode.heal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SwitchActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uEmail = user.getEmail();
        String user_id = mAuth.getCurrentUser().getUid();
        mFirestore.collection("users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot ds = task.getResult();
                    String uType = ds.getString("category");
                    if(uType.equals("patient")){
                        finish();
                        Intent ad = new Intent(SwitchActivity.this,DashboardActivity.class);
                        ad.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(ad);
                    }else if(uType.equals("doctor")){
                        finish();
                        Intent st = new Intent(SwitchActivity.this,DoctorDashBoardActivity.class);
                        st.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(st);
                    } else if(uType.equals("medical")){
                        finish();
                        Intent st = new Intent(SwitchActivity.this,MedicalDashBoardActivity.class);
                        st.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(st);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}

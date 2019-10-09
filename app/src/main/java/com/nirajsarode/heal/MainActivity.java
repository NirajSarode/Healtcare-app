package com.nirajsarode.heal;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
        private static final String TAG = "MainActivity";

    private TextView SignUp;
    private EditText mEditEmailLg;
    private EditText mEditPasswordLg;
    private Button mLogInbt;
    private Button SignInBt;
    private FirebaseAuth mAuth;
   // private FirebaseFirestore mFirestore;

    private FirebaseAuth.AuthStateListener mAuthListn;
    private ProgressBar mLoginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditEmailLg = findViewById(R.id.editEmailLogin);
        mEditPasswordLg = findViewById(R.id.editPasswordLogin);
        mLogInbt = findViewById(R.id.loginbt);
        mLoginProgress = (ProgressBar) findViewById(R.id.LoginProgressBar);
        //SignInBt = (Button)findViewById(R.id.sign_in_button);
        //mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

    }



    public void logUserIn(View view){
        userlogin();
    }


    private void userlogin() {
        final String email = mEditEmailLg.getText().toString().trim();
        String Password = mEditPasswordLg.getText().toString().trim();


        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEditEmailLg.setError("Please Enter Valid Email");
            mEditEmailLg.requestFocus();
            return;
        }

        if(Password.isEmpty()){
            mEditPasswordLg.setError("Password Required");
            mEditPasswordLg.requestFocus();
            return;
        }

        mLoginProgress.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    Intent ad = new Intent(MainActivity.this,SwitchActivity.class);
                    ad.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(ad);

                }else{
                    mLoginProgress.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signUpUser(View view){
        signup();
    }

    private void signup(){
        finish();
        Intent ad = new Intent(MainActivity.this,SignUpActivity.class);
        startActivity(ad);
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            finish();
            Intent ad = new Intent(MainActivity.this,SwitchActivity.class);
            startActivity(ad);

        }
    }
}

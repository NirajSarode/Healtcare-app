package com.nirajsarode.heal;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    private TextView SignUp;
    private EditText mEditEmailSu;
    private EditText mEditPasswordSu;
    private EditText mEditCPasswordSu;
    private Button mSignUpbt;

    private FirebaseAuth mAuth;
    //private FirebaseFirestore mFirestore;

    private FirebaseAuth.AuthStateListener mAuthListn;
    //private ProgressBar mLoginProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEditEmailSu = findViewById(R.id.editEmailSignUp);
        mEditPasswordSu = findViewById(R.id.editPasswordSignUp);
        mEditCPasswordSu = findViewById(R.id.editCPasswordSignUp);
        mSignUpbt = findViewById(R.id.signupbt);
        //mLoginProgress = (ProgressBar) findViewById(R.id.LoginProgressBar);

      //  mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mSignUpbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEditEmailSu.getText().toString().trim();
                String Password = mEditPasswordSu.getText().toString().trim();
                String CPassword = mEditCPasswordSu.getText().toString().trim();


                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mEditEmailSu.setError("Please Enter Valid Email");
                    mEditEmailSu.requestFocus();
                    return;
                }

                if(Password.isEmpty()){
                    mEditPasswordSu.setError("Password Required");
                    mEditPasswordSu.requestFocus();
                    return;
                }

                if(CPassword.isEmpty()){
                    mEditCPasswordSu.setError("Password Required");
                    mEditCPasswordSu.requestFocus();
                    return;
                }

                if(CPassword.equals(Password)) {
                   // mLoginProgress.setVisibility(View.VISIBLE);

                    mAuth.createUserWithEmailAndPassword(email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                finish();
                                Intent ad = new Intent(SignUpActivity.this,SelectCategoryUserActivity.class);
                                ad.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(ad);
                            } else {
                               // mLoginProgress.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                    Toast.makeText(getApplicationContext(), "Passwords Donot Match", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loginPage(View view){
        lgpage();
    }
    private void lgpage(){
        finish();
        Intent ad = new Intent(SignUpActivity.this,MainActivity.class);
        startActivity(ad);

    }

}
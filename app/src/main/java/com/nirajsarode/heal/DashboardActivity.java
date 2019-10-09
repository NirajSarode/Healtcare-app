package com.nirajsarode.heal;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    private static final int ERROR_DIALOG_REQUEST = 9001;


    private static final String TAG = "Dashboard Activity" ;
    private FirebaseAuth mAuth;
    private BottomNavigationView mbottomview;
    private CardView cv1,cv2,cv3,cv4,cv5,cv6;
    private android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuth = FirebaseAuth.getInstance();

        if(isServicesOK()){
            init();
        }
        mbottomview = (BottomNavigationView) findViewById(R.id.navigationView);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);

        cv1 = (CardView) findViewById(R.id.baid);
        cv3 = (CardView) findViewById(R.id.prid);
        cv4 = (CardView) findViewById(R.id.crid);
        cv5 = (CardView) findViewById(R.id.maid);
        cv6 = (CardView) findViewById(R.id.moid);

        mbottomview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.navigation_med:
                        Intent a = new Intent(DashboardActivity.this,SelectDoctorActivity.class);
                        startActivity(a);
                        break;

                    case R.id.navigation_appt:
                        Intent ab = new Intent(DashboardActivity.this,PatientAppointmentsActivity.class);
                        startActivity(ab);
                        break;

                    case R.id.navigation_cart:
                        Intent ac = new Intent(DashboardActivity.this,MapsActivity.class);
                        startActivity(ac);
                        break;

                    case R.id.navigation_pres:
                        Intent ad = new Intent(DashboardActivity.this,MusicPlayerActivity.class);
                        startActivity(ad);
                        break;
                }
                return true;
            }
        });

        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ad1 = new Intent(DashboardActivity.this,SelectDoctorActivity.class);
                startActivity(ad1);
            }
        });



        cv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ad2 = new Intent(DashboardActivity.this,MusicPlayerActivity.class);
                startActivity(ad2);

            }
        });

        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ad = new Intent(DashboardActivity.this,PrescriptionShowUserActivity.class);
                startActivity(ad);

            }
        });

        cv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ad3 = new Intent(DashboardActivity.this,PatientAppointmentsActivity.class);
                startActivity(ad3);

            }
        });

        cv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ad = new Intent(DashboardActivity.this,PatientProfileUpdateActivity.class);
                startActivity(ad);

            }
        });



    }


    private void init(){
        cv2 = (CardView) findViewById(R.id.bmid);
        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, MapsActivity.class);
                startActivity(intent);

            }
        });
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(DashboardActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(DashboardActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_profile:
                break;

            case R.id.action_logout:
                mAuth.signOut();
                finish();
                startActivity(new Intent(this,MainActivity.class));
                break;


        }

        return super.onOptionsItemSelected(item);
    }


    public void bookApp(View view) {
        bookappo();
    }

    private void bookappo() {
        startActivity(new Intent(this,SelectDoctorActivity.class));
    }
/*
    public void OpenPresc(View view) {
        openPres();
    }

    private void openPres() {
        startActivity(new Intent(this,PrescriptionShowUserActivity.class));
    }

    public void showApp(View view) {
        showaaa();
    }

    private void showaaa() {
        startActivity(new Intent(this,PatientAppointmentsActivity.class));
    }

    public void userpro(View view) {
        openpro();
    }

    private void openpro() {
        startActivity(new Intent(this,PatientProfileActivity.class));
    }

    public void MyOrder(View view) {
        myO();
    }

    private void myO() {
        startActivity(new Intent(this,UserMyOrdersActivity.class));
    }

    public void buyMed(View view) {
        buymedi();
    }

    private void buymedi() {
        startActivity(new Intent(this,ShowMedicalsActivity.class));
    }

    public void cart(View view) {
        car();
    }

    private void car() {
        startActivity(new Intent(this,UserCartActivity.class));
    }

    public void LogoutUser(View view) {
        logout();
    }

    private void logout() {
        mAuth.signOut();
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }*/

}

package com.nirajsarode.heal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MedicalDashBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_dash_board);

    }

    public void stock(View view) {
        checks();
    }

    private void checks() {
        startActivity(new Intent(this,MedicalStockActivity.class));
    }
    public void orders(View view) {
        bookappo();
    }

    private void bookappo() {
        startActivity(new Intent(this,SelectDoctorActivity.class));
    }
}

package com.example.binimoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpPage extends AppCompatActivity {
private Button button5,button6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        getSupportActionBar().hide();
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyersignuppage();
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sellersignuppage();
            }
        });
    }
    public void sellersignuppage() {
        Intent intent = new Intent(this, SellerSignUpPage.class);
        startActivity(intent);
    } public void buyersignuppage() {
        Intent intent = new Intent(this, BuyerSignUpPage.class);
        startActivity(intent);
    }

}

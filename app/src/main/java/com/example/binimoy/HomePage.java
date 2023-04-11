package com.example.binimoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {
    //private Button button,button2;
    ImageView button,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        getSupportActionBar().hide();
        button = (ImageView) findViewById(R.id.imageView9);
        button2 = (ImageView) findViewById(R.id.imageView10);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginpage();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signuppage();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),SellerHomePage.class));
            finish();
        }
    }

    public void loginpage() {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }public void signuppage() {
        Intent intent = new Intent(this, SignUpPage.class);
        startActivity(intent);
    }
}
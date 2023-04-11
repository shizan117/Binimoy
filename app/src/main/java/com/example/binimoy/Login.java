package com.example.binimoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class Login extends AppCompatActivity {
    private Button button_login;
    private EditText login_email, login_password;
    private TextView textView_login,textView_PersonEmail;
    private ProgressBar progressBar3;
    private FirebaseAuth mAuth;
    private Button developer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        button_login = findViewById(R.id.button_login);
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        developer = findViewById(R.id.devloperbutton);
        textView_login = findViewById(R.id.textView_login);
        progressBar3 = findViewById(R.id.progressBar3);
        //textView_PersonEmail = findViewById(R.id.textView_PersonEmail);
        developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity2();
            }
        });
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Activity();
            }
        });
        textView_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity1();
            }
        });

    }

    public void Activity() {
        String email = login_email.getText().toString().trim();
        String password = login_password.getText().toString().trim();

        if(email.isEmpty())
        {
            login_email.setError("Enter an email address");
            login_email.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            login_email.setError("Enter a valid email address");
            login_email.requestFocus();
            return;
        }

        //checking the validity of the password
        if(password.isEmpty())
        {
            login_password.setError("Enter a password");
            login_password.requestFocus();
            return;
        }
        if(password.length()<6){
            login_password.setError("Minimum length of password should be 6");
            login_password.requestFocus();
            return;
        }
        progressBar3.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull /*@org.jetbrains.annotations.NotNull*/ Task<AuthResult> task) {
                progressBar3.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    if(!mAuth.getCurrentUser().isEmailVerified()){
                        startActivity(new Intent(getApplicationContext(),PleaseVerifyAndLogin.class));
                        //finish();
                    }
                    else {

                        Intent intent = new Intent(getApplicationContext(),SellerHomePage.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Login Unseccessful",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void Activity1(){
        Intent intent = new Intent(getApplicationContext(),SignUpPage.class);
        startActivity(intent);
    }
    public void Activity2(){
        Intent intent = new Intent(getApplicationContext(),developer.class);
        startActivity(intent);
    }
}
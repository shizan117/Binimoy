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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BuyerSignUpPage extends AppCompatActivity {

    private EditText editText_name, editText_p, editText_cp, editText_phone, editTextemail;
    private Button button8;
    private TextView textView_login;
    private ProgressBar progressBar2;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_sign_up_page);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Buyer Signup Info");

        editText_name = findViewById(R.id.editText_name);
        editText_phone = findViewById(R.id.editText_phone);
        editTextemail = findViewById(R.id.editTextemail);
        editText_p = findViewById(R.id.editText_p);
        editText_cp = findViewById(R.id.editText_cp);
        button8 = findViewById(R.id.button8);
        textView_login = findViewById(R.id.textView_login);
        progressBar2 = findViewById(R.id.progressBar2);

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity();
            }
        });
        textView_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity1();
            }
        });

    }
    public void activity(){
        String email = editTextemail.getText().toString().trim();
        String password = editText_p.getText().toString().trim();
        String confirm_password = editText_cp.getText().toString().trim();

        if(email.isEmpty())
        {
            editTextemail.setError("Enter an email address");
            editTextemail.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editTextemail.setError("Enter a valid email address");
            editTextemail.requestFocus();
            return;
        }

        //checking the validity of the password
        if(password.isEmpty())
        {
            editText_p.setError("Enter a password");
            editText_p.requestFocus();
            return;
        }
        if(password.length()<6){
            editText_p.setError("Minimum length of password should be 6");
            editText_p.requestFocus();
            return;
        }

        if(!confirm_password.equals(password)){
            editText_p.setError("Passwords didnot match");
            editText_p.requestFocus();
            return;
        }

        progressBar2.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull /*@org.jetbrains.annotations.NotNull*/ Task<AuthResult> task) {
                progressBar2.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    mAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(),"Verification Email Sent", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Toast.makeText(getApplicationContext(),"Register is Successful",Toast.LENGTH_SHORT).show();
                    saveData();
                }
                else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"User is already Registered",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
    public void activity1(){
        Intent intent = new Intent(getApplicationContext(),LoginPage.class);
        startActivity(intent);
    }
    public void saveData(){
        String name = editText_name.getText().toString().trim();
        String number = editText_phone.getText().toString().trim();
        String email = editTextemail.getText().toString().trim();

        //String key = databaseReference.push().getKey();

        String[] unique = email.split("@");
        String key = unique[0];

        BuyerSignUpHelperClass buyerSignUpHelperClass = new BuyerSignUpHelperClass(name,number,email,key);

        databaseReference.child(key).setValue(buyerSignUpHelperClass);

        Toast.makeText(getApplicationContext(),"Data is also saved",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(),PleaseVerifyAndLogin.class);
        startActivity(intent);
    }
}

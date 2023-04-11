package com.example.binimoy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SellerSignUpPage extends AppCompatActivity {
    private EditText editText3,editText4,editText10,editText6,editText7,editText9;
    private Button button9;
    private ProgressBar progressBar10;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_sign_up_page);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Seller Signup Info");



        progressBar10 = findViewById(R.id.progressBar10);
        editText3=findViewById(R.id.editText3);
        editText4=findViewById(R.id.editText4);
        editText10=findViewById(R.id.editText10);
        editText6=findViewById(R.id.editText6);
        editText7=findViewById(R.id.editText7);
        editText9=findViewById(R.id.editText9);
        button9 = findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity5();
            }
        });


    }
    public void activity5(){
        String email = editText4.getText().toString().trim();
        String password = editText6.getText().toString().trim();
        String confirm_password = editText7.getText().toString().trim();

        if(email.isEmpty())
        {
            editText4.setError("Enter an email address");
            editText4.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editText4.setError("Enter a valid email address");
            editText4.requestFocus();
            return;
        }

        //checking the validity of the password
        if(password.isEmpty())
        {
            editText6.setError("Enter a password");
            editText6.requestFocus();
            return;
        }
        if(password.length()<6){
            editText6.setError("Minimum length of password should be 6");
            editText6.requestFocus();
            return;
        }

        if(!confirm_password.equals(password)){
            editText6.setError("Passwords didnot match");
            editText6.requestFocus();
            return;
        }

        progressBar10.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull /*@org.jetbrains.annotations.NotNull*/ Task<AuthResult> task) {
                progressBar10.setVisibility(View.GONE);
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
    public void saveData(){
        String name = editText3.getText().toString().trim();
        String number = editText10.getText().toString().trim();
        String email = editText4.getText().toString().trim();
        String address= editText9.getText().toString().trim();

        //String key = databaseReference.push().getKey();

        String[] unique = email.split("@");
        String key = unique[0];

        SellerSignUpHelperClass sellerSignUpHelperClass = new SellerSignUpHelperClass(name,number,email,key,address);

        databaseReference.child(key).setValue(sellerSignUpHelperClass);

        Toast.makeText(getApplicationContext(),"Data is also saved",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(),PleaseVerifyAndLogin.class);
        startActivity(intent);
    }
}

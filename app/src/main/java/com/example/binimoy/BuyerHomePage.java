package com.example.binimoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class BuyerHomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    FirebaseAuth mAuth;
    TextView textView_PersonName,textView_PersonEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_home_page);

        mAuth = FirebaseAuth.getInstance();


        NavigationView navigationView = findViewById(R.id.navigationId);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = findViewById(R.id.drawerId);

        toggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.app_name);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView_PersonName = findViewById(R.id.textView10);
        textView_PersonEmail = findViewById(R.id.textView9);
        //LinearLayout linearLayout=findViewById(R.id.drawerId);

        FirebaseUser presentUser = mAuth.getCurrentUser();

        String email = presentUser.getEmail().trim();
        //String user = presentUser.getDisplayName();

        String[] unique = email.split("@");
        String key = unique[0];


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buyer Signup Info");
        Query checkuser = reference.orderByChild("username").equalTo(key);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String namE = snapshot.child(key).child("name").getValue(String.class);
                    String emaiL = snapshot.child(key).child("email").getValue(String.class);
                    textView_PersonName.setText(namE);
                    textView_PersonEmail.setText(emaiL);
                }
                else {
                    textView_PersonName.setText("not found");
                    textView_PersonEmail.setText("not found");
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



        //linearLayout.addView(textView_PersonEmail);
        //app:headerLayout="@layout/nav_header"
        //app:menu="@menu/nav_menu_layout"
        //android:layout_gravity="start"
    }
    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.nav_menu_layout,menu);

            return super.onCreateOptionsMenu(menu);
        }
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (toggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull /*@org.jetbrains.annotations.NotNull*/ MenuItem item) {
        if(item.getItemId()==R.id.nav_signout){
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent = new Intent(getApplicationContext(),HomePage.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.nav_profile){
            //FirebaseAuth.getInstance().signOut();
            // finish();
            Intent intent = new Intent(getApplicationContext(),Seller_Profile.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.nav_addPost){
            //FirebaseAuth.getInstance().signOut();
            // finish();
            Intent intent = new Intent(getApplicationContext(),Seller_AddPost.class);
            startActivity(intent);
        }

        return false;
    }

}
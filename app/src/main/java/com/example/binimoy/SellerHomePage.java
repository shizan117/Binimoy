package com.example.binimoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class SellerHomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    FirebaseAuth mAuth;
    TextView textView_PersonName,textView_PersonEmail;

    EditText inputSearch;
    RecyclerView recyclerView;
    FloatingActionButton floatingbtn;
    FirebaseRecyclerOptions<getter_setter_image> options;
    FirebaseRecyclerAdapter<getter_setter_image,MyViewHolder> adapter;
    DatabaseReference Dataref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home_page);

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


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Seller Signup Info");
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


            Dataref= FirebaseDatabase.getInstance().getReference().child("Upload");
            inputSearch=findViewById(R.id.inputSearch);
            recyclerView=findViewById(R.id.recylerView);
            //floatingbtn=findViewById(R.id.floatingbtn);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setHasFixedSize(true);
            

        /*floatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });*/


            LoadData("");

            inputSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString()!=null)
                    {
                        LoadData(s.toString());
                    }
                    else
                    {
                        LoadData("");
                    }

                }
            });
        }
        private void LoadData(String data) {
            Query query=Dataref.orderByChild("imageName").startAt(data).endAt(data+"\uf8ff");

            options=new FirebaseRecyclerOptions.Builder<getter_setter_image>().setQuery(query,getter_setter_image.class).build();
            adapter=new FirebaseRecyclerAdapter<getter_setter_image, MyViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position, @NonNull getter_setter_image model) {
                    holder.textView.setText(model.getImageName());
                    Picasso.get().load(model.getImageUrl()).into(holder.imageView);
                    holder.v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(SellerHomePage.this,ViewActivity.class);
                            intent.putExtra("CarKey",getRef(position).getKey());
                            startActivity(intent);
                        }
                    });

                }

                @NonNull
                @Override
                public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                    View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);
                    return new MyViewHolder(v);
                }
            };
            adapter.startListening();
            recyclerView.setAdapter(adapter);

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
    public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
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
            Intent intent = new Intent(getApplicationContext(),Seller_AddPost_ARB.class);
            startActivity(intent);
        }

        return false;
    }
}
package com.example.binimoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
//import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Display extends AppCompatActivity {
    private RecyclerView recyclerView;
    //private MyAdapter myAdapter;
    //private List<Upload> UploadList;
    //DatabaseReference databaseReference;
    private ProgressBar progressBar;
    FirebaseRecyclerOptions<getter_setter_image> options;
    FirebaseRecyclerAdapter<getter_setter_image,MyViewHolder> adapter;
    DatabaseReference Dataref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        getSupportActionBar().hide();
        //Toast.makeText(this, "i am here", Toast.LENGTH_SHORT).show();
        Dataref = FirebaseDatabase.getInstance().getReference().child("Upload");
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        /*
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewId);
        databaseReference = FirebaseDatabase.getInstance().getReference("Upload");

      //  recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);

      // recyclerView.setAdapter(new MyAdapter(this,uploadList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar = findViewById(R.id.recylerprogressBarId);

        UploadList = new ArrayList<>();

       // databaseReference = FirebaseDatabase.getInstance().getReference("Upload");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Upload upload = dataSnapshot1.getValue(Upload.class);
                    UploadList.add(upload);
                }
                myAdapter = new MyAdapter(Display.this,UploadList);
                recyclerView.setLayoutManager(manager);
                recyclerView.setHasFixedSize(true);

                recyclerView.setAdapter(myAdapter);
                progressBar.setVisibility(View.INVISIBLE);



            }

            @Override
            public void onCancelled(@NonNull  DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error : "+databaseError.getMessage(),Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);

            }
        });
        */
        LoadData();
    }

    private void LoadData() {
        //Toast.makeText(this, "i am here", Toast.LENGTH_SHORT).show();
        options=new FirebaseRecyclerOptions.Builder<getter_setter_image>().setQuery(Dataref,getter_setter_image.class).build();
        adapter = new FirebaseRecyclerAdapter<getter_setter_image, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position, @NonNull @NotNull getter_setter_image model) {
                //Toast.makeText(this, "i am here", Toast.LENGTH_SHORT).show();
                System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                holder.textView.setText(model.getImageName());
                //holder.imageView.setImageURI(Uri.parse(model.getImageUrl()));
                Glide.with(holder.imageView.getContext()).load("https://upload.wikimedia.org/wikipedia/commons/a/ad/Angelina_Jolie_2_June_2014_%28cropped%29.jpg").into(holder.imageView);

                //Picasso.get().load(model.getImageUrl()).into(holder.imageView);
            }

            @NonNull
            @NotNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);

                return new MyViewHolder(v);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}
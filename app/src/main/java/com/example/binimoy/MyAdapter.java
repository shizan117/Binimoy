package com.example.binimoy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private List<Upload> UploadList= new ArrayList<>();
    private Object ViewGroup;




    public MyAdapter(Context context, List<Upload> uploads) {
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder( @NonNull ViewGroup ViewGroup, int i){

       // LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      LayoutInflater layoutInflater = LayoutInflater.from(context);
       // LayoutInflater inflater = getLayoutInflater();
      //  layoutInflater.inflate(R.layout.item,viewGroup,false);
        View view=layoutInflater.inflate(R.layout.item,  ViewGroup,false);
       // View view = LayoutInflater.from(context).inflate(R.layout.item, ViewGroup, false);
        //View view=inflater.inflate(R.layout.item, (android.view.ViewGroup) ViewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MyViewHolder myViewHolder, int i) {
        Upload upload = UploadList.get(i);
        myViewHolder.textView.setText(upload.getImageName());
        Picasso.get().load(upload.getImageUrl())
               .placeholder(R.mipmap.ic_launcher_round)
                .fit()
                .centerCrop()
                .into(myViewHolder.imageView);


    }

    @Override
    public int getItemCount() {

        return UploadList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView.findViewById(R.id.cardTextViewId);
           imageView.findViewById(R.id.cardImageViewId);
        }
    }
}

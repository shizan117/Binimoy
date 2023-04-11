package com.example.binimoy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class Seller_AddPost extends AppCompatActivity implements View.OnClickListener {
    private Button addchooseButton, addsaveButton, adddisplayButton;
    private ImageView addimageView;
    private EditText addimageNameEditText;
    private Uri addimageUri;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    StorageTask uploadTask;
    private static final int IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_add_post);
        databaseReference = FirebaseDatabase.getInstance().getReference("Upload");
        storageReference = FirebaseStorage.getInstance().getReference("Upload");

        addchooseButton = findViewById(R.id.AddPostchooseImageButton);
        addsaveButton = findViewById(R.id.AddPostsaveImageButton);
       // adddisplayButton = findViewById(R.id.AddPostdispayImageButton);

        addimageView = findViewById(R.id.PostImage);
        addimageNameEditText = findViewById(R.id.AddPostTitle);

        addsaveButton.setOnClickListener(this);
        adddisplayButton.setOnClickListener(this);
        addchooseButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.AddPostchooseImageButton:

                openFileChooser();
                break;
            case R.id.AddPostsaveImageButton:
                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(getApplicationContext(), "Uploading in progress", Toast.LENGTH_LONG).show();
                } else {
                    addsaveData();
                }


                break;
         //   case R.id.AddPostdispayImageButton:


               // Intent intent = new Intent(this, Display.class);
               // startActivity(intent);

              //  break;

        }

    }

    void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            addimageUri = data.getData();
            // Picasso.get().load(uri).into(imageview);
            //  Picasso.get().load(imageUri).into(imageView);
            Picasso.get().load(addimageUri).into(addimageView);
        }

    }

    public String getFileExtension(Uri addimageUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(addimageUri));
    }

    private void addsaveData() {
        String imageName = addimageNameEditText.getText().toString().trim();
        if (imageName.isEmpty()) {
            addimageNameEditText.setError("Enter the image name");
            addimageNameEditText.requestFocus();
            return;
        }
        StorageReference ref = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(addimageUri)); // extension nibe
        uploadTask = ref.putFile(addimageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { // Get a URL to the uploaded content


                        Toast.makeText(getApplicationContext(), "Image is  stored successfully", Toast.LENGTH_LONG).show();
                        // Upload upload = new Upload(imageName,taskSnapshot.getStorage().getDownloadUrl().toString());

                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!urlTask.isSuccessful());
                        Uri downloadUrl=urlTask.getResult();
                        Upload upload = new Upload(imageName, taskSnapshot.getStorage().getDownloadUrl().toString());

                        String uploadId = databaseReference.push().getKey();
                        databaseReference.child(uploadId).setValue(upload);
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Seller_AddPost.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


    }
}
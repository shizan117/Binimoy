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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Seller_Profile extends AppCompatActivity implements View.OnClickListener {
    private Button chooseButton,saveButton,displayButton;
    private ImageView imageView;
    private EditText imageNameEditText;
    private Uri imageUri;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    StorageTask uploadTask;

    private static final int IMAGE_REQUEST =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile);
        databaseReference= FirebaseDatabase.getInstance().getReference("SellerProfile");
        storageReference= FirebaseStorage.getInstance().getReference("SellerProfile");

        chooseButton= findViewById(R.id.chooseImageButton);
        saveButton = findViewById(R.id.saveImageButton);
        displayButton =findViewById(R.id.dispayImageButton);

        imageView = findViewById(R.id.imageViewId);
        imageNameEditText = findViewById(R.id.imageNameEditTextId);

        saveButton.setOnClickListener(this);
        displayButton.setOnClickListener(this);
        chooseButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.chooseImageButton:
                openFileChooser();
                break;
            case R.id.saveImageButton:
                if(uploadTask!=null && uploadTask.isInProgress()){
                    Toast.makeText(getApplicationContext(), "Uploading in progress", Toast.LENGTH_LONG).show();
                }
                else
                {
                    saveData();
                }
                break;
            case R.id.dispayImageButton:
                break;

        }

    }
    void openFileChooser(){
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.getData()!=null)
        {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imageView);
        }

    }


    // getting the extension of the image
    public String getFileExtension (Uri imageUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }



    private void saveData() {
        String imageName= imageNameEditText.getText().toString().trim();
        if(imageName.isEmpty()){
            imageNameEditText.setError("Eror the image name");
            imageNameEditText.requestFocus();
            return;
        }
        StorageReference ref = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri)); // extension nibe

        ref.putFile (imageUri)

                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){

                    @Override

                    public void onSuccess (UploadTask.TaskSnapshot taskSnapshot)
                    { // Get a URL to the uploaded content
                        Toast.makeText(getApplicationContext(), "Image is  stored successfully", Toast.LENGTH_LONG).show();
                        Upload upload = new Upload(imageName,taskSnapshot.getStorage().getDownloadUrl().toString());

                        String uploadId = databaseReference.push().getKey();
                        databaseReference.child(uploadId).setValue(upload);
                    }

                })

                .addOnFailureListener (new OnFailureListener() {

                    @Override

                    public void onFailure (@NonNull Exception exception) {

// Handle unsuccessful uploads
                        Toast.makeText(getApplicationContext(), "Image is not stored successfully", Toast.LENGTH_LONG).show();
                    }

                });


    }
}
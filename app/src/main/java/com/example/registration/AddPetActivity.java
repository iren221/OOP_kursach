package com.example.registration;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class AddPetActivity extends AppCompatActivity {

    TextInputEditText pet_name, pet_description;
    RadioGroup radioGroup;
    RadioButton radioButton;
    AppCompatButton btn_save_data, btn_download_photo;
    ImageView image_pet;
    Uri selectedImage;

    ProgressBar progressBar;

    FirebaseStorage storage;
    StorageReference storageReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        pet_name = findViewById(R.id.textPetName);
        pet_description = findViewById(R.id.textPetDescription);
        btn_save_data = findViewById(R.id.btn_save_data);
        btn_download_photo = findViewById(R.id.btn_download_photo);
        image_pet = findViewById(R.id.image_pet);
        radioGroup = findViewById(R.id.radio_group);
        progressBar=findViewById(R.id.progressBar);


        btn_save_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle arguments = getIntent().getExtras();
                String last_name = arguments.get("last_name").toString();
                String name = arguments.get("name").toString();
                String patronymic = arguments.get("patronymic").toString();
                String phone = arguments.get("phone").toString();
                String date_from = arguments.get("date_from").toString();
                String date_to = arguments.get("date_to").toString();
                String name_pet = pet_name.getText().toString().trim();
                String pet_des = pet_description.getText().toString().trim();
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                String gender = radioButton.getText().toString().trim();

                if ((!name_pet.isEmpty()) && (name_pet.length() <= 10) &&
                        (pet_des.length() <= 50)) {
                    final StorageReference ref = storageReference.
                            child("images/"+selectedImage.getLastPathSegment());
                    Log.i("exceptionsss", "before request");
                    ref.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    Log.i("exceptionsss", "allways okey");
                                    Toast.makeText(getApplicationContext(), "Загрузка",
                                            Toast.LENGTH_SHORT).show();
                                    DBHelper dbase = new DBHelper(AddPetActivity.this);
                                    final String imageUri = selectedImage.getLastPathSegment();
                                    dbase.addPet(last_name, name, patronymic, phone, date_from,
                                            date_to, name_pet, pet_des, imageUri, gender);
                                    Toast.makeText(AddPetActivity.this,
                                            "Данные сохранены", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddPetActivity.this,
                                            MyPetActivity.class);
                                    startActivity(intent);
                                }
                            });

                } else {
                    Toast.makeText(AddPetActivity.this,
                            "Не все поля заполнены или лимит букв превышен",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_download_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage();
            }
        });
    }

    private  void showProgress(){
        btn_save_data.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private  void showView(){
        btn_save_data.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void getImage() {
        Intent intentChooser = new Intent(Intent.ACTION_PICK);
        intentChooser.setType("image/*");
        //intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser, 1);
    }

    private void saveImage() {
        final StorageReference ref = storageReference.child(selectedImage.getLastPathSegment());
        Log.i("exceptionsss", "before request");
        ref.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Log.i("exceptionsss", "allways okey");
                        Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.i("exceptionsss", e.getMessage());
                        Toast.makeText(getApplicationContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Log.i("exceptionsss", "cancelled");
                    }
                }).addOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(@NonNull Object snapshot) {
                        Log.i("exceptionsss", "progress");
                    }
                }).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Log.i("exceptionsss", "compleete");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    selectedImage = data.getData();
                    try {
                        Log.i("selected_image", selectedImage.toString());
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    image_pet.setImageBitmap(bitmap);
                    Toast.makeText(AddPetActivity.this, "Картинка установлена", Toast.LENGTH_SHORT).show();
                }
        }
    }

}

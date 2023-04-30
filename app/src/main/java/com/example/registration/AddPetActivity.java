package com.example.registration;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

public class AddPetActivity extends AppCompatActivity {

    TextInputEditText pet_name, pet_description;
    Button btn_save_data, btn_download_photo;
    ImageView image_pet;
    Uri selectedImage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        pet_name = findViewById(R.id.textPetName);
        pet_description = findViewById(R.id.textPetDescription);
        btn_save_data = findViewById(R.id.btn_save_data);
        btn_download_photo = findViewById(R.id.btn_download_photo);
        image_pet = findViewById(R.id.image_pet);

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

                if (!name_pet.isEmpty()) {
                    DBHelper dbase = new DBHelper(AddPetActivity.this);
                    dbase.addPet(last_name, name, patronymic, phone, date_from, date_to, name_pet, pet_des, selectedImage.toString());
                    Toast.makeText(AddPetActivity.this, "Данные сохранены", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddPetActivity.this, MyPetActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddPetActivity.this, "Error", Toast.LENGTH_SHORT).show();
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

    private void getImage() {
        Intent intentChooser = new Intent(Intent.ACTION_PICK);
        intentChooser.setType("image/*");
        //intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser, 1);
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

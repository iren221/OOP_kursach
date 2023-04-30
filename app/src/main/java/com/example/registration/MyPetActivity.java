package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MyPetActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button add_btn;
    DBHelper my_db;
    ArrayList<String> pet_name;
    ArrayList<Uri> pet_photo;
    CustomAdapter customAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pet);

        recyclerView = findViewById(R.id.recyclerView);
        add_btn = findViewById(R.id.btn_add);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MyPetActivity.this, AddOwnerActivity.class);
                startActivity(intent2);
            }
        });

        my_db = new DBHelper(MyPetActivity.this);
        pet_name = new ArrayList<>();
        pet_photo = new ArrayList<>();

        storeDateInArrays();

        customAdapter = new CustomAdapter(this, pet_name, pet_photo);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyPetActivity.this));
    }

    void storeDateInArrays() {
        Cursor cursor = my_db.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(MyPetActivity.this, "Нет данных", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                pet_name.add(cursor.getString(7));
                final Uri uri = Uri.parse(cursor.getString(8));
                Log.i("selected_image",uri.toString());
                pet_photo.add(uri);
//                Bitmap bmp = BitmapFactory.decodeByteArray(image, 0 , image.length);
//                pet_photo.add(bmp);//байтовый массив превращаем
                // в картинку. Я не знаю, как это запихнуть в Custom Adapter
//                imageView.setImageBitmap(bmp);


            }
        }
    }
}
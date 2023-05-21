package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
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
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyPetActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AppCompatButton add_btn;
    DBHelper my_db;
    ArrayList<String> pet_name;
    ArrayList<String> pet_id;
    ArrayList<String> pet_photo;
    CustomAdapter customAdapter;
    //ImageView image_photo_pet;
    SearchView pet_search;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pet);

        recyclerView = findViewById(R.id.recyclerView);
        pet_search = findViewById(R.id.pet_search);
        pet_search.clearFocus();
        add_btn = findViewById(R.id.btn_add);
        //image_photo_pet = findViewById(R.id.image_photo_pet);



        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MyPetActivity.this, AddOwnerActivity.class);
                startActivity(intent2);
            }
        });

        my_db = new DBHelper(MyPetActivity.this);
        pet_name = new ArrayList<>();
        pet_id = new ArrayList<>();
        pet_photo = new ArrayList<>();

        storeDateInArrays();

        customAdapter = new CustomAdapter(this, pet_name, pet_photo, pet_id);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyPetActivity.this));

//        image_photo_pet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MyPetActivity.this, "Вы нажали на объект" + pet_id, Toast.LENGTH_SHORT).show();
//            }
//        });

        pet_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
//                List<String> filterString = filter(pet_name, newText);
//                customAdapter.setFilter(filterString);
                return true;
//                customAdapter.getFilter().filter();
//                return false;
            }
        });
    }



    private void filter(String text){
        ArrayList<String> filterString = new ArrayList<>();

        for (String word:pet_name){
            if (word.contains(text)) {
                filterString.add(word);
            }
        }
        if (pet_name.isEmpty()){
            Toast.makeText(MyPetActivity.this, "Нет данных", Toast.LENGTH_SHORT).show();
        }
        else {
            customAdapter.setFilter(filterString);
        }

    }

    void storeDateInArrays() {
        Cursor cursor = my_db.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(MyPetActivity.this, "Нет данных", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                pet_name.add(cursor.getString(7));
                pet_id.add(cursor.getString(0));
                final String uri = cursor.getString(8);
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
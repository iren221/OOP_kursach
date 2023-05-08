package com.example.registration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    TextView name_owner_one, last_name_owner_one, patronymic_owner_one, period_from_one, period_to_one, phone_owner_one, nickname_pet_one, gender_pet_one, description_pet_one;
    ArrayList<String> pet_name;
    ArrayList<String> pet_id;
    ArrayList<Uri> pet_photo;
    String name_owner, last_name_owner, patronymic_owner, period_to, period_from, phone_owner, nickname_pet, gender_pet, description_pet;

    CustomAdapter(Context context,
                  ArrayList<String> pet_name, ArrayList<Uri> pet_photo, ArrayList<String> pet_id) {
        this.context = context;
        this.pet_name = pet_name;
        this.pet_photo = pet_photo;
        this.pet_id = pet_id;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Bitmap bitmap;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.context.getContentResolver(), pet_photo.get(position));
            holder.photo_pet.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.name_pet.setText(String.valueOf(pet_name.get(position)));
        //holder.id_pet.setText(String.valueOf(pet_id.get(position)));

        DBHelper dbase = new DBHelper(context.getApplicationContext());

        holder.photo_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context.getApplicationContext(), "You clicked" + String.valueOf(pet_id.get(position)), Toast.LENGTH_SHORT).show();
                Boolean chekpet = dbase.chekPet(pet_id.get(position));
                if (chekpet) {
                    Toast.makeText(context.getApplicationContext(), "Успешно", Toast.LENGTH_SHORT).show();
                    Cursor cursor = dbase.readAllData();
                    if (cursor.getCount() == 0) {
                        Toast.makeText(context.getApplicationContext(), "Нет данных", Toast.LENGTH_SHORT).show();
                    }else {
                        while (cursor.moveToNext()) {
                            name_owner = cursor.getString(1);
                            last_name_owner = cursor.getString(2);
                            patronymic_owner = cursor.getString(3);
                            phone_owner = cursor.getString(4);
                            period_from = cursor.getString(5);
                            period_to = cursor.getString(6);
                            nickname_pet = cursor.getString(7);
                            description_pet = cursor.getString(9);
                            gender_pet = cursor.getString(10);
                            break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pet_name.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name_pet, id_pet;
        ImageView photo_pet;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_pet = itemView.findViewById(R.id.text_pet_name);
            id_pet = itemView.findViewById(R.id.id_pet);
            photo_pet = itemView.findViewById(R.id.image_photo_pet);
        }
    }
}

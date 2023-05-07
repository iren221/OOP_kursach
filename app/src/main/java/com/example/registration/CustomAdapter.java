package com.example.registration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> pet_name;
    ArrayList<String> pet_id;
    ArrayList<String> pet_photo;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    CustomAdapter(Context context,
                  ArrayList<String> pet_name, ArrayList<String> pet_photo, ArrayList<String> pet_id) {
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
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        final Bitmap bitmap;
        final String images = pet_photo.get(position);
        Log.e("images", images);
        final StorageReference itemStorage = storageReference.child("images/" + pet_photo.get(position));

        itemStorage.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                task.getResult();
                Toast.makeText(context, "Картинка загружена", Toast.LENGTH_SHORT).show();
                Glide.with(context).load(task.getResult()).into(holder.photo_pet);
//                holder.photo_pet.setImageURI(task.getResult());
            }
        });
        holder.name_pet.setText(String.valueOf(pet_name.get(position)));
        holder.id_pet.setText(String.valueOf(pet_id.get(position)));

        DBHelper dbase = new DBHelper(context.getApplicationContext());

        holder.name_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context.getApplicationContext(), "You clicked" + holder.id_pet.getText().toString(), Toast.LENGTH_SHORT).show();
                Boolean chekpet = dbase.chekPet(holder.id_pet.getText().toString());
                if (chekpet) {
                    Toast.makeText(context.getApplicationContext(), "Успешно", Toast.LENGTH_SHORT).show();
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

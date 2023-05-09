package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class OnePetInfo extends AppCompatActivity {

    TextView nickname, owner_first_name, owner_last_name, owner_patronymic,
            owner_phone, pet_gender, pet_desc, period_ot, period_po;
    ImageButton imageButton_back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_pet_info);

        nickname = findViewById(R.id.nickname);
        owner_first_name = findViewById(R.id.owner_first_name);
        owner_last_name = findViewById(R.id.owner_last_name);
        owner_patronymic = findViewById(R.id.owner_patronymic);
        owner_phone = findViewById(R.id.owner_phone);
        pet_gender = findViewById(R.id.pet_gender);
        pet_desc = findViewById(R.id.pet_desc);
        period_ot = findViewById(R.id.period_ot);
        period_po = findViewById(R.id.period_po);

        imageButton_back = findViewById(R.id.imageButton_back);

        imageButton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(OnePetInfo.this, MyPetActivity.class);
                startActivity(intent1);
            }
        });
        Bundle arguments = getIntent().getExtras();
        nickname.setText(arguments.get("nickname_pet").toString());
        owner_first_name.setText(arguments.get("name_owner").toString());
        owner_last_name.setText(arguments.get("last_name_owner").toString());
        owner_patronymic.setText(arguments.get("patronymic_owner").toString());
        owner_phone.setText(arguments.get("phone_owner").toString());
        pet_gender.setText(arguments.get("gender_pet").toString());
        pet_desc.setText(arguments.get("description_pet").toString());
        period_ot.setText(arguments.get("period_from").toString());
        period_po.setText(arguments.get("period_to").toString());
    }
}
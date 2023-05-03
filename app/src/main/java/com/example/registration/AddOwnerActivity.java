package com.example.registration;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class AddOwnerActivity extends AppCompatActivity {

    TextInputEditText last_name, name, patronymic, phone;
    TextView date_from, date_to;
    AppCompatButton btn_next_animal_registration, btn_date_from, btn_date_to;

    Calendar date_from_cl = Calendar.getInstance();
    Calendar date_to_cl = Calendar.getInstance();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_owner);

        last_name = findViewById(R.id.textInputLName);
        name = findViewById(R.id.textInputFName);
        patronymic = findViewById(R.id.textInputP);
        date_from = findViewById(R.id.text_date_from);
        date_to = findViewById(R.id.text_date_to);
        btn_next_animal_registration = findViewById(R.id.btn_add_pet);
        btn_date_from = findViewById(R.id.btn_period_from);
        btn_date_to = findViewById(R.id.btn_period_to);
        phone = findViewById(R.id.textInputPhone);
        btn_next_animal_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name_last = last_name.getText().toString().trim();
                String name_owner = name.getText().toString().trim();
                String pat = patronymic.getText().toString().trim();
                String ph = phone.getText().toString().trim();
                String date_f = date_from.getText().toString().trim();
                String date_t = date_to.getText().toString().trim();

                Intent intent1 = new Intent(AddOwnerActivity.this, AddPetActivity.class);
                if (!name_last.isEmpty() && !name_owner.isEmpty() && !pat.isEmpty() && !ph.isEmpty() && !date_f.isEmpty() && !date_t.isEmpty()) {

                    intent1.putExtra("last_name", last_name.getText().toString().trim());
                    intent1.putExtra("name", name.getText().toString().trim());
                    intent1.putExtra("patronymic", patronymic.getText().toString().trim());
                    intent1.putExtra("phone", phone.getText().toString().trim());
                    intent1.putExtra("date_from", date_from.getText().toString().trim());
                    intent1.putExtra("date_to", date_to.getText().toString().trim());
                    startActivity(intent1);
//                    Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
//                    startActivity(intent);
                } else {
                    Toast.makeText(AddOwnerActivity.this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
                }



//                DBHelper dbase = new DBHelper(MainActivity3.this);
//                dbase.addPet(last_name.getText().toString().trim(),
//                        name.getText().toString().trim(),
//                        patronymic.getText().toString().trim(),
//                        phone.getText().toString().trim(),
//                        date_from.getText().toString().trim(),
//                        date_to.getText().toString().trim());
//
//                //это для проверки
//                Toast.makeText(MainActivity3.this, phone.getText().toString().trim() + "," + date_from.getText().toString().trim() + "," + date_from.getText().toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });
        //период проживания животного
        btn_date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate1(date_from);
            }
        });
        btn_date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate2(date_to);
            }
        });
    }


    public void setDate1(View v) {
        new DatePickerDialog(this, d,
                date_from_cl.get(Calendar.YEAR),
                date_from_cl.get(Calendar.MONTH),
                date_from_cl.get(Calendar.DAY_OF_MONTH))
                .show();
    }
    public void setDate2(View v) {
        new DatePickerDialog(this, k,
                date_to_cl.get(Calendar.YEAR),
                date_to_cl.get(Calendar.MONTH),
                date_to_cl.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setInitialDate1() {

        date_from.setText(DateUtils.formatDateTime(this,
                date_from_cl.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }
    DatePickerDialog.OnDateSetListener d= new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date_from_cl.set(Calendar.YEAR, year);
            date_from_cl.set(Calendar.MONTH, monthOfYear);
            date_from_cl.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate1();
        }
    };
    private void setInitialDate2() {

        date_to.setText(DateUtils.formatDateTime(this,
                date_to_cl.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }
    DatePickerDialog.OnDateSetListener k = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date_to_cl.set(Calendar.YEAR, year);
            date_to_cl.set(Calendar.MONTH, monthOfYear);
            date_to_cl.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate2();
        }
    };
}
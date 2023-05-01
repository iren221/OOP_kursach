package com.example.registration;

import static android.widget.Toast.makeText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    TextInputEditText login, password;

    AppCompatButton btn_login;
    AppCompatButton btn_remember;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        btn_remember = findViewById(R.id.btn_remember);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String login_user = login.getText().toString().trim();
                String password_user = password.getText().toString().trim();
                DBHelper dbase = new DBHelper(MainActivity.this);
                //String remember_user_name = "";

                if (!login_user.isEmpty() && !password_user.isEmpty()) {
                    if (login_user.length() >= 2 && login_user.length() <= 10 && password_user.length() == 4){
                        Boolean chekusername = dbase.chekUsername(login_user);
                        if (!chekusername) {
                            dbase.addUser(login_user, password_user);
                            Toast.makeText(MainActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                            myPetActivity();
                        }else {
                            //login.setText(login_user);
                            Boolean chekuser = dbase.chekUser(login_user, password_user);
                            if (chekuser) {
                                myPetActivity();
                            }else {
                                Toast.makeText(MainActivity.this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }else {
                        Toast.makeText(MainActivity.this, "Некорректный логин и пароль", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_remember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dontRememberAlert();
            }
        });
    }


    private void dontRememberAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Свяжитесь с менеджером: 0101")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void myPetActivity() {
        Intent my_pet = new Intent(this, MyPetActivity.class);
        startActivity(my_pet);
    }

}
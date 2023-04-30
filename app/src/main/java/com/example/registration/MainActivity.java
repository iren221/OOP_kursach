package com.example.registration;

import static android.widget.Toast.makeText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

    Button btn_login;
    Button btn_remember;


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
                myPetActivity();
            }
        });

//        String lines = "";
//        try {
//            FileInputStream fileInput_login_auth = openFileInput("login.txt");
//            InputStreamReader reader_auth = new InputStreamReader(fileInput_login_auth);
//            BufferedReader bR_auth = new BufferedReader(reader_auth);
//            lines = bR_auth.readLine();
//            login.setText(lines);
//        } catch (IOException e) {
//            e.printStackTrace();
//            makeText(MainActivity.this, "Ошибка с файлом", Toast.LENGTH_SHORT).show();
//        }


//        btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String user_login = login.getText().toString();
//                if (user_login.length() >= 2) {
//
//                    String user_password = password.getText().toString();
//                    //String new_string = user_login + user_password;
//                    //Toast.makeText(MainActivity.this, new_string, Toast.LENGTH_LONG).show();
//
//                    try {
//
//                        FileInputStream fileInput_login1 = openFileInput("login.txt");
//                        FileInputStream fileInput_password1 = openFileInput("password.txt");
//                        InputStreamReader reader1 = new InputStreamReader(fileInput_login1);
//                        BufferedReader bR = new BufferedReader(reader1);
//                        InputStreamReader reader2 = new InputStreamReader(fileInput_password1);
//                        BufferedReader bR2 = new BufferedReader(reader2);
//
//                        String lines_name = "";
//                        String lines_password = "";
//                        while (((lines_name = bR.readLine()) != null) && (
//                                (lines_password = bR2.readLine()) != null
//                        )) {
//                            if (lines_name.equalsIgnoreCase(user_login)) {
//
//
//                                if (lines_password.equalsIgnoreCase(user_password)) {
//                                    makeText(MainActivity.this, "Успешный вход (пароль и логин совпали))", Toast.LENGTH_LONG).show();
//                                    myPetActivity();
//                                } else {
//                                    makeText(MainActivity.this, "Пользователь с таким логином уже существует", Toast.LENGTH_LONG).show();
//                                    break;
//                                }
//                            } else {
//                                FileOutputStream fileOutput_login1 = openFileOutput("login.txt", MODE_PRIVATE);
//                                fileOutput_login1.write(user_login.getBytes());
//                                FileOutputStream fileOutput_password = openFileOutput("password.txt", MODE_PRIVATE);
//                                fileOutput_password.write(user_password.getBytes());
//                                fileOutput_login1.close();
//                                fileOutput_password.close();
//                                makeText(MainActivity.this, "Регистрация прошла успешно", Toast.LENGTH_LONG).show();
//                                myPetActivity();
//                                //login.setText("");
//                                //password.setText("");
//                            }
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        makeText(MainActivity.this, "Ошибка с файлом", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else {
//                    makeText(MainActivity.this, "Минимальное кол-во символов в логине 2", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
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
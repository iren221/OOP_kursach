package com.example.registration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DBPet";
    public static final String TABLE_NAME = "Petdb";
    public static final String TABLE_NAME2 = "Userdb";

    public static final String KEY_ID = "_id";
    public static final String KEY_ID_USER = "_id";

    public static final String KEY_NAME = "name";
    public static final String KEY_NAME_USER = "name_user";
    public static final String KEY_PASSWORD_USER = "password_user";
    public static final String KEY_LASTNAME = "last_name";
    public static final String KEY_P = "p";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_FROM_TIME = "from_time";
    public static final String KEY_TO_TIME = "to_time";
    public static final String KEY_PET_NAME = "pet_name";
    public static final String KEY_PET_DES = "description";
    public static final String KEY_PET_PHOTO = "photo";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT, " +
                KEY_LASTNAME + " TEXT, " +
                KEY_P + " TEXT, " +
                KEY_PHONE + " TEXT, " +
                KEY_FROM_TIME + " TEXT, " +
                KEY_TO_TIME + " TEXT, " +
                KEY_PET_NAME + " TEXT, " +
                KEY_PET_PHOTO + " TEXT, " +
                KEY_PET_DES + " TEXT);";
        db.execSQL(query);

        String query2 = "CREATE TABLE " + TABLE_NAME2 +
                " (" + KEY_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME_USER + " TEXT, " +
                KEY_PASSWORD_USER + " TEXT);";
        db.execSQL(query2);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }
    void addPet(String last_name, String name, String p, String phone, String from_time, String to_time, String pet_name, String description, String photo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_LASTNAME, last_name);
        cv.put(KEY_NAME, name);
        cv.put(KEY_P, p);
        cv.put(KEY_PHONE, phone);
        cv.put(KEY_FROM_TIME, from_time);
        cv.put(KEY_TO_TIME, to_time);
        cv.put(KEY_PET_NAME, pet_name);
        cv.put(KEY_PET_PHOTO, photo);
        cv.put(KEY_PET_DES, description);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "GOOD", Toast.LENGTH_SHORT).show();
        }
    }

    void addUser(String name_user, String password) {
        SQLiteDatabase db2 = this.getWritableDatabase();
        ContentValues cv2 = new ContentValues();
        cv2.put(KEY_NAME_USER, name_user);
        cv2.put(KEY_PASSWORD_USER, password);
        long result = db2.insert(TABLE_NAME2, null, cv2);
        if (result == -1) {
            Toast.makeText(context, "ERROR REGISTRATION", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "GOOD REGISTRATION", Toast.LENGTH_SHORT).show();
        }
    }
    Boolean chekUsername(String name_user){ //для регистрации
        SQLiteDatabase db2 = this.getWritableDatabase();
        Cursor cursor = db2.rawQuery("SELECT * FROM users WHERE name_user = ?", new String[] {name_user});
        if (cursor.getCount() > 0) {
            return true; //имя существует
        }else {
            return false;
        }
    }
    Boolean chekUser(String name_user, String password) { //это для входа
        SQLiteDatabase db2 = this.getWritableDatabase();
        Cursor cursor = db2.rawQuery("SELECT * FROM users WHERE name_user = ? and password = ?", new String[] {name_user, password});
        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

}

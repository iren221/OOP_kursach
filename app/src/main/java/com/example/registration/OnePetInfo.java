package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OnePetInfo extends AppCompatActivity {

    TextView nickname, owner_first_name, owner_last_name, owner_patronymic,
            owner_phone, pet_gender, pet_desc, period_ot, period_po;
    ImageButton imageButton_back;
    CheckBox check_eat;
    private static final String CHANNEL_ID = "pet_channel";
    private static final String EXTRA_ITEM_ID = "pet_notifi";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_pet_info);

        check_eat = findViewById(R.id.check_eat);

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

        check_eat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (check_eat.isChecked()) {
                        Toast.makeText(OnePetInfo.this, nickname.getText().toString() + " покормлен(а)", Toast.LENGTH_SHORT).show();
                        //showNotification();
                    } else {
                        // Notifications are not enabled, request permission
                    if (areNotificationsEnabled()) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showNotification();
                            }
                        }, 10000);
                    }
                }
            }
        });

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
    private boolean areNotificationsEnabled() {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        return notificationManagerCompat.areNotificationsEnabled();
    }

    private void showNotification() {
        // Create an Intent for the activity you want to start
        Intent resultIntent = new Intent(this, MyPetActivity.class);
// Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
// Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Pet Eat",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Кормление")
                .setContentText("Осталось 20 минут до кормления!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent)
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManagerCompat.notify(1, notification);
    }
}

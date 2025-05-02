package com.example.smsforwardapp;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    public static String IPAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Получение необходимых разрешений
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.READ_SMS,
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                Manifest.permission.INTERNET},
                PackageManager.PERMISSION_GRANTED);

        //Загрузка настроек
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        IPAddress = sharedPreferences.getString("ipaddress", "0.0.0.0");
        boolean notificationsEnabled = sharedPreferences.getBoolean("notifications_enabled", false);

        TextView ipTextView = findViewById(R.id.textIpView);
        String chosenIp = "Указанный айпи: " + IPAddress;
        ipTextView.setText(chosenIp);
    }

    public void saveSettings(boolean notificationsEnabled) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ipaddress", IPAddress);
        editor.putBoolean("notifications_enabled", notificationsEnabled);
        editor.apply();
    }

    public void buttoncl(View view){
        TextView textView = (TextView) findViewById(R.id.editTextText);
        IPAddress = textView.getText().toString();
        saveSettings(false);

        TextView ipTextView = findViewById(R.id.textIpView);
        String chosenIp = "Указанный IP:\n" + IPAddress;
        ipTextView.setText(chosenIp);
    }

    public void testbtn(View view){
        Sender.sendToServer(this, "0000");
    }
}

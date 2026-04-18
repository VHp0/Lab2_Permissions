package com.example.haiduk.mobileapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;

    private TextView statusCamera, statusContacts, statusLocation;
    private Button btnRequestAll, btnOpenSettings;

    // Массив разрешений
    private final String[] requiredPermissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusCamera = findViewById(R.id.statusCamera);
        statusContacts = findViewById(R.id.statusContacts);
        statusLocation = findViewById(R.id.statusLocation);
        btnRequestAll = findViewById(R.id.btnRequestAll);
        btnOpenSettings = findViewById(R.id.btnOpenSettings);

        // Кнопка запроса разрешений
        btnRequestAll.setOnClickListener(v -> ActivityCompat.requestPermissions(
                MainActivity.this,
                requiredPermissions,
                PERMISSION_REQUEST_CODE
        ));

        // Кнопка перехода в настройки
        btnOpenSettings.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateStatuses();
    }

    // Функция проверки прав и покраски текста
    private void updateStatuses() {
        checkAndUpdateUI(Manifest.permission.CAMERA, statusCamera);
        checkAndUpdateUI(Manifest.permission.READ_CONTACTS, statusContacts);
        checkAndUpdateUI(Manifest.permission.ACCESS_FINE_LOCATION, statusLocation);
    }

    private void checkAndUpdateUI(String permission, TextView textView) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            textView.setText("GRANTED");
            textView.setTextColor(Color.parseColor("#39FF14")); // Неоновый зеленый
        } else {
            // Если разрешение не выдано, считаем его DENIED
            textView.setText("DENIED");
            textView.setTextColor(Color.parseColor("#FF003C")); // Неоновый красный
        }
    }

    // Слушатель результатов запроса
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            updateStatuses(); // Обновляем UI после ответа пользователя
        }
    }
}
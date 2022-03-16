package com.reckordp.iloveyouforever;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private final byte[] hadapan = new byte[16];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            InputStream stream = getResources().openRawResource(R.raw.flag);
            if (stream.read(hadapan) == -1) return;
        } catch (Resources.NotFoundException | IOException e) {
            e.printStackTrace();
        }

        EditText jawabanEditText = findViewById(R.id.jawaban);
        findViewById(R.id.selesai).setOnClickListener(v -> {
            try {
                String jawaban = jawabanEditText.getText().toString();
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                byte[] encJawaban = md5.digest(jawaban.getBytes(StandardCharsets.UTF_8));
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setPositiveButton(android.R.string.ok, (dialog, which) -> {});
                if (Arrays.equals(hadapan, encJawaban)) {
                    alert.setMessage("CTF captured!");
                } else {
                    alert.setMessage("Nope");
                }
                alert.show();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        });
    }
}
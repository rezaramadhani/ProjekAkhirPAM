package com.example.projekuas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.projekuas.R;

public class LihatData extends AppCompatActivity {
    TextView edNama,edSinopsis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data);

        edNama = findViewById(R.id.nb);
        edSinopsis = findViewById(R.id.sb);
        Intent intent = getIntent();
        if (intent!=null)
        {
            edNama.setText(intent.getStringExtra("name"));
            edSinopsis.setText(intent.getStringExtra("synopsis"));

        }

    }
}
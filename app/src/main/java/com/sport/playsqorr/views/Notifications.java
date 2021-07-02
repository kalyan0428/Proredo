package com.sport.playsqorr.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sport.playsqorr.R;

public class Notifications extends AppCompatActivity {

    TextView toolbar_title_x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        toolbar_title_x = findViewById(R.id.toolbar_title_x);
        toolbar_title_x.setText("Notifications");
        toolbar_title_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

package com.sport.playsqorr.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.sport.playsqorr.R;
import com.sport.playsqorr.model.StatsPlayerStatistics;

import java.util.ArrayList;

public class SqorrAvgs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqorr_avgs);


//Getting MyObject List
        Intent mIntent = getIntent();
//        ArrayList<StatsPlayerStatistics> mUsers = mIntent.getParcelableArrayList("dd");
    }
}
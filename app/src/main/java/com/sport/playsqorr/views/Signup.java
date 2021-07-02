package com.sport.playsqorr.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.sport.playsqorr.R;

public class Signup extends AppCompatActivity implements View.OnClickListener{

    private String fromPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        }
        Bundle bundle =getIntent().getExtras();
        if(bundle!=null){
            if(bundle.containsKey("fromPage")){
                fromPage=bundle.getString("fromPage");
            }
        }


        init();
    }

    private void init() {
        TextView toolbar_title_x = (TextView) findViewById(R.id.toolbar_title_x);
        if(fromPage!=null&&!fromPage.equals("")){
            toolbar_title_x.setText(getString(R.string.log_in));
        }else {
            toolbar_title_x.setText(getString(R.string.sign_up));
        }
        CardView playforcash = (CardView) findViewById(R.id.playforcash);
        CardView playfortoken = (CardView) findViewById(R.id.playfortoken);
        toolbar_title_x.setOnClickListener(this);
        playforcash.setOnClickListener(this);
        playfortoken.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_title_x:
                finish();
                break;
            case R.id.playforcash:
               // startActivity(new Intent(this, PlayWithCash.class));
                Intent in = new Intent(Signup.this, PlayWithCash.class);
//                in.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                startActivity(in);
                break;
            case R.id.playfortoken:
                startActivity(new Intent(this, PlayWithTokens.class));
        }
    }
}
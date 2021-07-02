package com.sport.playsqorr.views;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonElement;
import com.sport.playsqorr.Api.API_class;
import com.sport.playsqorr.Api.Retrofit_funtion_class;
import com.sport.playsqorr.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.sport.playsqorr.views.Dashboard.ACCREF;

public class Referfriend extends AppCompatActivity {

    TextView toolbar_title_x,coderef;
    Button invitebutton;
    LinearLayout linearViewFAQs;
    ImageView copytext;
    ProgressDialog pdialogue;
    TextView faqtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referfriend);
        toolbar_title_x=findViewById(R.id.toolbar_title_x);
        toolbar_title_x.setText("Refer a friend");
        invitebutton=findViewById(R.id.invitebutton);
        copytext=findViewById(R.id.copytext);
        coderef=findViewById(R.id.coderef);


        if(ACCREF!=null){
            coderef.setText("playsqor.com/?referralCode="+ACCREF);
        }

        copytext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                if(ACCREF!=null){
                    ClipData clip = ClipData.newPlainText("label", "https://games.playsqor.com/?referralCode="+ACCREF);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getApplicationContext(),"Coiped",Toast.LENGTH_LONG).show();
                }

            }
        });
        invitebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "Join me on PlaySqor and use code "+ACCREF+" to get a $5 sign-up bonus. PlaySqor will also match your first deposit up to $20!  Happy gaming! ");
                if(ACCREF!=null){
                    share.putExtra(Intent.EXTRA_TEXT, "https://games.playsqor.com/?referralCode="+ACCREF);
                }


                startActivity(Intent.createChooser(share, "Share link!"));
            }
        });
        faqtxt=findViewById(R.id.faqtxt);


        toolbar_title_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        linearViewFAQs=findViewById(R.id.linearViewFAQs);
        linearViewFAQs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Referfriend.this,ReferDetials.class);
                startActivity(intent);
            }
        });

        pdialogue = new ProgressDialog(Referfriend.this);
        getfriendslist();
    }
    private void getfriendslist()
    {
        pdialogue.setMessage("Loading ...");
        pdialogue.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdialogue.setCancelable(false);
        pdialogue.show();
        pdialogue.dismiss();
        final API_class service = Retrofit_funtion_class.getClient().create(API_class.class);

        String Accept="application/json";
        Call<JsonElement> callRetrofit = null;

//        callRetrofit = service.GetPrefere(Accept, Dashboard.SESSIONTOKEN);
        callRetrofit = service.GetPrefere(Accept, Dashboard.SESSIONTOKEN, "bearer " +  Dashboard.NEWTOKEN);

        callRetrofit.enqueue(new Callback<JsonElement>()
        {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                System.out.println("----------------------------------------------------");
                Log.e("Call request", call.request().toString());
                Log.e("Call request header", call.request().headers().toString());
                Log.e("Response raw header", response.headers().toString());
                Log.e("Response raw", String.valueOf(response.raw().body()));
                Log.e("Response code", String.valueOf(response.code()));

                System.out.println("----------------------------------------------------");

                if (response.isSuccessful())
                {

                    String searchResponse = response.body().toString();
                    Log.e("Regestration", "response  >>" + searchResponse.toString());
                    pdialogue.dismiss();
                    try {

                        JSONObject jsonObject1 = new JSONObject(searchResponse);
//                        JSONObject jsonObject1=lObj.getJSONObject("data");

                        JSONArray jsonArray=jsonObject1.getJSONArray("friendDetails");

                        String friendscounts= String.valueOf(jsonArray.length());
                        System.out.println("friendscounts "+friendscounts);
                        faqtxt.setText(friendscounts+" Friends Joined");


//                        JSONObject jsonObject = null;

                    } catch (JSONException e) {
                        Log.e("error", e.getMessage());
                        pdialogue.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("Error Call", ">>>>" + call.toString());
                Log.e("Error", ">>>>" + t.toString());
                Toast.makeText(Referfriend.this,t.toString(),Toast.LENGTH_LONG).show();
                pdialogue.dismiss();
            }
        });
    }
}

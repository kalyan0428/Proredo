package com.sport.playsqorr.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonElement;

import com.sport.playsqorr.Api.API_class;
import com.sport.playsqorr.Api.Retrofit_funtion_class;
import com.sport.playsqorr.R;

import com.sport.playsqorr.adapters.Referalsaddapetr;
import com.sport.playsqorr.database.DB_Constants;
import com.sport.playsqorr.database.DataBaseHelper;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sport.playsqorr.views.Dashboard.ACCREF;


public class ReferDetials extends AppCompatActivity {

    TextView toolbar_title_x;
    private DataBaseHelper mydb;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    String earnings, tobeEarned;
    RecyclerView recyview;
    ProgressDialog pdialogue;
    Button invitebutton;
    ReferUsedetails referUsedetails;
    ArrayList<ReferUsedetails> myCardsPojo_u;
    String ROLE, SESSIONTOKEN,NEWTOKEN, STATE_, COUNTRY_, CITY_, AMOUNT_TOKEN, AVATAR, MYWiNS, ACCNAME, EMAIL, NUMBER, DOB;
    TextView earning, tobe, done;
    RelativeLayout toolbar_sub_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_detials);
        toolbar_title_x = findViewById(R.id.toolbar_title_x);
        pdialogue = new ProgressDialog(ReferDetials.this);


        recyview = findViewById(R.id.recyview);
        myCardsPojo_u = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ReferDetials.this);
        recyview.setLayoutManager(mLayoutManager);
        recyview.setItemAnimator(new DefaultItemAnimator());
        earning = findViewById(R.id.earning);
        tobe = findViewById(R.id.tobe);
        invitebutton = findViewById(R.id.invitebutton);
        invitebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "Join me on PlaySqor and use code " + ACCREF + " to get a $5 sign-up bonus. PlaySqor will also match your first deposit up to $20!  Happy gaming! ");
                share.putExtra(Intent.EXTRA_TEXT, "https://games.playsqorr.com/?referralCode=" + ACCREF);

                startActivity(Intent.createChooser(share, "Share link!"));
            }
        });
        done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar_title_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mydb = new DataBaseHelper(this);
        sqLiteDatabase = mydb.getReadableDatabase();
        getDataFromLocalDb();


    }

    private void getDataFromLocalDb() {

        cursor = mydb.getAllUserInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                System.out.println(cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NAME)));
                SESSIONTOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_SESSIONTOKEN));
                NEWTOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKEN));
                ROLE = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_MODETYPE));
                STATE_ = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_STATE)).trim();
                CITY_ = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_CITY)).trim();

                COUNTRY_ = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_COUNTRY)).trim();
                AMOUNT_TOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKENBALANCE));
                AVATAR = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_IMAGE));
                MYWiNS = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_WINS));
                ACCNAME = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NAME));
                EMAIL = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_EMAIL));
                NUMBER = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NUMBER));
                DOB = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_DOB));
                getfriendslist();
            }

            cursor.close();

        } else {
            ROLE = "0";

        }
//        setUserAvatar();
    }


    private void getfriendslist() {
        pdialogue.setMessage("Loading ...");
        pdialogue.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdialogue.setCancelable(true);
        pdialogue.show();
        pdialogue.dismiss();

        final API_class service = Retrofit_funtion_class.getClient().create(API_class.class);

        String Accept = "application/json";
        Call<JsonElement> callRetrofit = null;

//        callRetrofit = service.GetPrefere(Accept, Dashboard.SESSIONTOKEN);
        callRetrofit = service.GetPrefere(Accept, Dashboard.SESSIONTOKEN, "bearer " +  Dashboard.NEWTOKEN);


        callRetrofit.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                System.out.println("----------------------------------------------------");
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));

                System.out.println("----------------------------------------------------");

                if (response.isSuccessful()) {

                    String searchResponse = response.body().toString();
                    Log.d("Regestration", "response  >>" + searchResponse.toString());
                    pdialogue.dismiss();
                    try {

                        JSONObject jsonObject1 = new JSONObject(searchResponse);
//                        JSONObject jsonObject1=lObj.getJSONObject("data");
                        earnings = jsonObject1.getString("earnings");
                        tobeEarned = jsonObject1.getString("tobeEarned");
                        earning.setText("$ " + earnings);
                        tobe.setText("$ " + tobeEarned);
                        JSONArray jsonArray = jsonObject1.getJSONArray("friendDetails");

                        //     referUsedetails=new ReferUsedetails();
                        //   referUsedetails.setName("Ashok Reddy");
                        //  referUsedetails.setBenefitAmount("10");
                        // referUsedetails.setAccountId("11");
                        //myCardsPojo_u.add(referUsedetails);
                        for (int f = 0; f < jsonArray.length(); f++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(f);
                            String name = jsonObject.getString("name");
                            String accountId = jsonObject.getString("accountId");
                            String benefitAmount = jsonObject.getString("benefitAmount");
                            referUsedetails = new ReferUsedetails();
                            referUsedetails.setName(name);
                            referUsedetails.setBenefitAmount(benefitAmount);
                            referUsedetails.setAccountId(accountId);
                            myCardsPojo_u.add(referUsedetails);
                        }
                        Referalsaddapetr adapter = new Referalsaddapetr(ReferDetials.this, myCardsPojo_u);
                        recyview.setAdapter(adapter);

//                        JSONObject jsonObject = null;

                    } catch (JSONException e) {
                        Log.e("error", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("Error Call", ">>>>" + call.toString());
                Log.d("Error", ">>>>" + t.toString());
                pdialogue.dismiss();
            }
        });
    }

}

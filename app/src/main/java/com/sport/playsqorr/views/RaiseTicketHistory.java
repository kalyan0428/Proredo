package com.sport.playsqorr.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonObject;
import com.sport.playsqorr.R;
import com.sport.playsqorr.database.DB_Constants;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.model.Picks;
import com.sport.playsqorr.pojos.BiggestWinners;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.Utilities;
import com.sport.playsqorr.pojos.FreshDeskHistoryPojo;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RaiseTicketHistory extends AppCompatActivity implements View.OnClickListener {


    TextView toolbar_title_x;
    private DataBaseHelper mydb;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    private String ROLE, CASH_BAL, AMOUNT_CASH, AMOUNT_TOKEN, AVATAR, MYWiNS, ACCNAME, USEREMAIL;
    RecyclerView tickhis_list;

    List<FreshDeskHistoryPojo> FreshDeskHistoryPojo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_ticket_history);

        mydb = new DataBaseHelper(this);
        sqLiteDatabase = mydb.getReadableDatabase();
        init();
    }

    @SuppressLint({"SetTextI18n", "ResourceType"})
    private void init() {

        toolbar_title_x = findViewById(R.id.toolbar_title_x);

        toolbar_title_x.setText("History");
        //Add listener(s)
        toolbar_title_x.setOnClickListener(this);

        tickhis_list = findViewById(R.id.tickhis_list);
        LinearLayoutManager llm = new LinearLayoutManager(RaiseTicketHistory.this);
        tickhis_list.setLayoutManager(llm);
        tickhis_list.setItemAnimator(null);
        tickhis_list.setNestedScrollingEnabled(false);
    }

    @SuppressLint("SetTextI18n")
    private void updateUI() {

        cursor = mydb.getAllUserInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                System.out.println(cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NAME)));
                ROLE = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_MODETYPE));
                CASH_BAL = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_CASHBALANCE));
                AMOUNT_CASH = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOTALCASHBALANCE));
                AMOUNT_TOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKENBALANCE));
                AVATAR = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_IMAGE));
                MYWiNS = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_WINS));
                ACCNAME = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NAME)).trim();
                USEREMAIL = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_EMAIL)).trim();
            }
            cursor.close();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
        historyList();
    }

    private void historyList() {
        FreshDeskHistoryPojo.clear();
        //   obj_list_token(state_txt,city_txt,country_txt);?email=prak7@myorigami.co&&include=description
        AndroidNetworking.get(APIs.FRESHDESK_CREATE + "?email=" + USEREMAIL + "&&include=description")
//        AndroidNetworking.get("https://newaccount1603908299081.freshdesk.com/api/v2/tickets?email=prak7@myorigami.co&&include=description")
                .addHeaders("Authorization", APIs.FRESHDESK_AUTH)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

//                            if (progressDialog != null)
//                                progressDialog.dismiss();
                        Log.e("***MA: ctick h:  104", response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {


                                JSONObject his_json = response.getJSONObject(i);
                                FreshDeskHistoryPojo fdhp = new FreshDeskHistoryPojo();
                                fdhp.setId(his_json.getString("id"));
                                fdhp.setGroup_id(his_json.getString("group_id"));
                                fdhp.setPriority(his_json.getString("priority"));
                                fdhp.setRequester_id(his_json.getString("requester_id"));
                                fdhp.setStatus(his_json.getString("status"));
                                fdhp.setSubject(his_json.getString("subject"));
                                fdhp.setCreated_at(his_json.getString("created_at"));
                                fdhp.setUpdated_at(his_json.getString("updated_at"));
                                fdhp.setDescription_text(his_json.getString("description_text"));
                                FreshDeskHistoryPojo.add(fdhp);
                            }

                            RaisedHistoryAdapter recycleAdapter = new RaisedHistoryAdapter(FreshDeskHistoryPojo, RaiseTicketHistory.this);
                            tickhis_list.setAdapter(recycleAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("js", "create----error---168---- 329" + anError.getErrorBody() +  anError.getErrorDetail());

//                            if (progressDialog != null)
//                                progressDialog.dismiss();
                        if (anError.getErrorCode() != 0) {
                            Log.e("", "onError errorCode : " + anError.getErrorCode());
                            Log.e("", "onError errorBody : " + anError.getErrorBody());
                            Log.e("", "onError errorDetail : " + anError.getErrorDetail());

                            try {
                                JSONObject jjb = new JSONObject(anError.getErrorBody());

                                Log.e("184---",jjb.toString());

                                JSONArray new_jn = jjb.getJSONArray("errors");

                                Log.e("184--189-",new_jn.toString());
                                for (int i = 0; i <new_jn.length() ; i++) {

                                    JSONObject jb1 = new_jn.getJSONObject(i);

                                    Log.e("184--189-",jb1.getString("message"));
                                    if(jb1.getString("message").equalsIgnoreCase("There is no contact matching the given email")){
                                        Utilities.showToast(RaiseTicketHistory.this, "You have no tickets");
                                    }
//                                    else{
//                                        Utilities.showToast(RaiseTicketHistory.this, jb1.getString("message"));
//                                    }

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

//                        else {
//
//                         //   Log.e("181--",anError.getErrorBody());
//
//                            //message
//                            Utilities.showToast(RaiseTicketHistory.this, anError.getErrorDetail());
//                            Log.d("", "onError errorDetail  0: " + anError.getErrorDetail());
//                        }

                    }

                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_title_x:
                //================ Hide Virtual Key Board When  Clicking==================//

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(toolbar_title_x.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

//======== Hide Virtual Keyboard =====================//
                finish();
                break;

            default:
                break;

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

//    @Override
//    public void onCLickList(int position) {
//        FreshDeskHistoryPojo hp = FreshDeskHistoryPojo.get(position);
//        Toast.makeText(RaiseTicketHistory.this,hp.getGroup_id()+"",Toast.LENGTH_LONG).show();
//    }

    //Adapter
    public static class RaisedHistoryAdapter extends RecyclerView.Adapter<RaisedHistoryAdapter.ViewHolder> {


        private final List<FreshDeskHistoryPojo> mValues;
        private Context context;


        public RaisedHistoryAdapter(List<FreshDeskHistoryPojo> items, Context context) {
            mValues = items;
            this.context = context;
        }

        @NonNull
        @Override
        public RaisedHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tickets_hisadp, parent, false);

            return new RaisedHistoryAdapter.ViewHolder(itemView);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final RaisedHistoryAdapter.ViewHolder holder, final int position) {

            final FreshDeskHistoryPojo fresh_list = mValues.get(position);


            if (fresh_list.getStatus().equalsIgnoreCase("2")) {
                holder.tvStatus.setText("Open");
            } else if (fresh_list.getStatus().equalsIgnoreCase("3")) {
                holder.tvStatus.setText("Pending");
            } else if (fresh_list.getStatus().equalsIgnoreCase("4")) {
                holder.tvStatus.setText("Resolved");
            } else if (fresh_list.getStatus().equalsIgnoreCase("5")) {
                holder.tvStatus.setText("Closed");
            } else if (fresh_list.getStatus().equalsIgnoreCase("6")) {
                holder.tvStatus.setText("Waiting On Customer");
            } else if (fresh_list.getStatus().equalsIgnoreCase("7")) {
                holder.tvStatus.setText("Waiting On Third Party");
            }

            holder.tvSubject.setText(fresh_list.getSubject());
            holder.tvdestxt.setText(fresh_list.getDescription_text());



            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                SimpleDateFormat output = new SimpleDateFormat("MMM dd, yyyy");
                Date   d = inputFormat.parse(fresh_list.getCreated_at());
                String formattedTime = output.format(d);

                holder.tvcdate.setText( formattedTime);
//                holder.tvcdate.setText(fresh_list.getCreated_at());
            } catch (ParseException e) {
                e.printStackTrace();
            }




            holder.card_raise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context,fresh_list.getSubject()+"",Toast.LENGTH_LONG).show();
                    Intent ticket_c = new Intent(context,RasieTicketChatScreen.class);
                    ticket_c.putExtra("tid",fresh_list.getId());
                    ticket_c.putExtra("reqid",fresh_list.getRequester_id());
                    ticket_c.putExtra("tit",fresh_list.getSubject());
                    context.startActivity(ticket_c);
                }
            });
        }

        @Override
        public int getItemCount() {
            return this.mValues.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvSubject, tvStatus, tvdestxt, tvcdate;

            CardView card_raise;
            public ViewHolder(View view) {
                super(view);
                tvSubject = view.findViewById(R.id.tvSubject);
                tvStatus = view.findViewById(R.id.tvStatus);
                tvdestxt = view.findViewById(R.id.tvdestxt);
                tvcdate = view.findViewById(R.id.tvcdate);
                card_raise = view.findViewById(R.id.card_raise);


            }
        }
//        public interface OnClickListener{
//            void onCLickList(int position);
//        }

    }
}
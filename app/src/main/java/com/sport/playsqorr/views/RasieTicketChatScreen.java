package com.sport.playsqorr.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.login.LoginManager;
import com.sport.playsqorr.R;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.pojos.FreshDeskChatPojo;
import com.sport.playsqorr.pojos.FreshDeskHistoryPojo;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RasieTicketChatScreen extends AppCompatActivity implements View.OnClickListener {

    String myid, redid, tit;

    TextView toolbar_title_x, chattitle;
    private DataBaseHelper mydb;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    private String ROLE, CASH_BAL, AMOUNT_CASH, AMOUNT_TOKEN, AVATAR, MYWiNS, ACCNAME, USEREMAIL;
    RecyclerView tickchat_list;
    ImageView sendbtn;
    EditText tv_chatfield;
    List<FreshDeskChatPojo> FreshDeskChatPojo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rasie_ticket_chat_screen);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("tid"))
                myid = bundle.getString("tid");
            if (bundle.containsKey("reqid"))
                redid = bundle.getString("reqid");
            if (bundle.containsKey("tit"))
                tit = bundle.getString("tit");

        }

        init();
    }

    @SuppressLint({"SetTextI18n", "ResourceType"})
    private void init() {

        toolbar_title_x = findViewById(R.id.toolbar_title_x);
        chattitle = findViewById(R.id.chattitle);
        tv_chatfield = findViewById(R.id.tv_chatfield);
        sendbtn = findViewById(R.id.sendbtn);

        toolbar_title_x.setText("Thread Discussion");
        //Add listener(s)
        toolbar_title_x.setOnClickListener(this);
        sendbtn.setOnClickListener(this);

        tickchat_list = findViewById(R.id.tickchat_list);
        LinearLayoutManager llm = new LinearLayoutManager(RasieTicketChatScreen.this);
        tickchat_list.setLayoutManager(llm);
        tickchat_list.setItemAnimator(null);
        tickchat_list.setNestedScrollingEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        onchatHistoryAPI(myid, redid);
        chattitle.setText(tit);
    }

    private void onchatHistoryAPI(String myid, final String redid) {
        FreshDeskChatPojo.clear();
        Log.e("my id",myid + "--"+ redid);
        //   obj_list_token(state_txt,city_txt,country_txt);?email=prak7@myorigami.co&&include=descriptionhttps://newaccount1603908299081.freshdesk.com/api/v2/tickets/8/conversations
        AndroidNetworking.get(APIs.FRESHDESK_CREATE + "/" + myid + "/conversations")
                .addHeaders("Authorization", APIs.FRESHDESK_AUTH)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

//                            if (progressDialog != null)
//                                progressDialog.dismiss();
                        Log.e("***MA: ctick h:  97", response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {


                                JSONObject his_json = response.getJSONObject(i);
                                FreshDeskChatPojo fdhp = new FreshDeskChatPojo();
                                fdhp.setId(his_json.getString("id"));
                                fdhp.setUser_id(his_json.getString("user_id"));
                                fdhp.setTicket_id(his_json.getString("ticket_id"));
                                fdhp.setBody_text(his_json.getString("body_text"));
                                fdhp.setCreated_at(his_json.getString("created_at"));
                                FreshDeskChatPojo.add(fdhp);
                            }

                            RaisedChatAdapter recycleAdapter = new RaisedChatAdapter(FreshDeskChatPojo, RasieTicketChatScreen.this, redid);
                            tickchat_list.setAdapter(recycleAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("js", "His----error------- 144" + anError.getErrorBody());

//                            if (progressDialog != null)
//                                progressDialog.dismiss();
                        if (anError.getErrorCode() != 0) {
                            Utilities.showToast(RasieTicketChatScreen.this, anError.getErrorDetail());
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
                                        Utilities.showToast(RasieTicketChatScreen.this, "You have no tickets");
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
//                            Utilities.showToast(RasieTicketChatScreen.this, anError.getErrorDetail());
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
            case R.id.sendbtn:
                String text = tv_chatfield.getText().toString().trim();

                if (tv_chatfield.getText().toString().trim().length() > 1) {

                    tv_chatfield.setText("");
                    tv_chatfield.setHint("Write your reply");
                    onSubmintAPi(text, myid);

                } else {

                }

                break;

            default:
                break;

        }
    }

    // SubmitAPi
    private void onSubmintAPi(String text, final String myid) {


//        ["description": "Add funds issues", "subject": "Add Funds", "cc_emails": [], "status": 2,
//        "priority": 2, "email": "prak7@myorigami.co"]

        JSONObject fJSon = null;
        try {
            fJSon = new JSONObject();
            JSONArray farray = new JSONArray();

            fJSon.put("user_id", Double.valueOf(redid));
            fJSon.put("body", text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //   obj_list_token(state_txt,city_txt,country_txt);
        AndroidNetworking.post(APIs.FRESHDESK_CREATE + "/" + myid + "/reply")
                .addJSONObjectBody(fJSon) // posting json
//                    .addHeaders("sessionToken", sessionToken)
                .addHeaders("Authorization", APIs.FRESHDESK_AUTH)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

//                            if (progressDialog != null)
//                                progressDialog.dismiss();
                        Log.e("***MA: cash 285:", response.toString());

                        onchatHistoryAPI(myid, redid);


                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("js", "Login----error------- 329" + anError.getErrorBody());

//                            if (progressDialog != null)
//                                progressDialog.dismiss();
                        if (anError.getErrorCode() != 0) {
                          //  Utilities.showToast(RasieTicketChatScreen.this, anError.getErrorDetail());
                            Log.d("", "onError errorCode : " + anError.getErrorCode());
                            Log.d("", "onError errorBody : " + anError.getErrorBody());
                            Log.d("", "onError errorDetail : " + anError.getErrorDetail());
                            try {
                                JSONObject ej = new JSONObject(anError.getErrorBody());
//

                               // Utilities.showToast(RasieTicketChatScreen.this, ej.getString("message"));
                           //     showAlertBoxSingle(RasieTicketChatScreen.this, ej.getString("code"),ej.getString("message"));



                            } catch (Exception e) {

                            }

                        } else {
//                            Utilities.showToast(RasieTicketChatScreen.this, anError.getErrorDetail());
                          //  Log.d("", "onError errorDetail  0: " + anError.getErrorDetail());

                        }

                    }

                });

    }

    private void showAlertBoxSingle(final Context context, String title, String message) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.alerts);


        Window window = dialog.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        // window.setGravity(Gravity.CENTER);
//        window.setGravity(Gravity.BOTTOM);


        dialog.show();
        dialog.setCancelable(false);


        TextView alert_title = (TextView) dialog.findViewById(R.id.alert_title);
        TextView alert_msg = (TextView) dialog.findViewById(R.id.alert_msg);

        alert_title.setText(title);
        alert_msg.setText(message);
        TextView alert_ok = (TextView) dialog.findViewById(R.id.alert_ok);
        alert_ok.setText("OK");
        // if decline button is clicked, close the custom dialog
        alert_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                finish();



            }
        });

    }
    //Adapter
    public static class RaisedChatAdapter extends RecyclerView.Adapter<RasieTicketChatScreen.RaisedChatAdapter.ViewHolder> {


        private final List<FreshDeskChatPojo> mValues;
        private Context context;
        String requ_id;

        public RaisedChatAdapter(List<FreshDeskChatPojo> items, Context context, String redid) {
            this.mValues = items;
            this.context = context;
            this.requ_id = redid;
        }

        @NonNull
        @Override
        public RasieTicketChatScreen.RaisedChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tickets_chat, parent, false);

            return new RaisedChatAdapter.ViewHolder(itemView);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final RasieTicketChatScreen.RaisedChatAdapter.ViewHolder holder, final int position) {

            final FreshDeskChatPojo fresh_list = mValues.get(position);


//            if (fresh_list.getStatus().equalsIgnoreCase("2")) {
//                holder.tvStatus.setText("Open");
//            } else if (fresh_list.getStatus().equalsIgnoreCase("3")) {
//                holder.tvStatus.setText("Pending");
//            } else if (fresh_list.getStatus().equalsIgnoreCase("4")) {
//                holder.tvStatus.setText("Resolved");
//            } else if (fresh_list.getStatus().equalsIgnoreCase("5")) {
//                holder.tvStatus.setText("Closed");
//            } else
            if (fresh_list.getUser_id().equalsIgnoreCase(requ_id)) {
                holder.tvStatus.setText("You");
            } else {
                holder.tvStatus.setText("Admin");
            }

            holder.tvSubject.setText(fresh_list.getBody_text());


//            holder.tvdestxt.setText(fresh_list.getCreated_at());
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                SimpleDateFormat output = new SimpleDateFormat("MMM dd, yyyy");
                Date d = inputFormat.parse(fresh_list.getCreated_at());
                String formattedTime = output.format(d);

                holder.tvdestxt.setText( formattedTime);
//                holder.tvcdate.setText(fresh_list.getCreated_at());
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return this.mValues.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvSubject, tvStatus, tvdestxt;

            CardView card_raise;

            public ViewHolder(View view) {
                super(view);
                tvSubject = view.findViewById(R.id.tvSubject);
                tvStatus = view.findViewById(R.id.tvStatus);
                tvdestxt = view.findViewById(R.id.tvdestxt);
                card_raise = view.findViewById(R.id.card_raise);


            }
        }
//        public interface OnClickListener{
//            void onCLickList(int position);
//        }

    }
}
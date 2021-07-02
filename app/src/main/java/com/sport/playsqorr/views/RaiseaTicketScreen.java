package com.sport.playsqorr.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.sport.playsqorr.R;
import com.sport.playsqorr.database.DB_Constants;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.model.WithdrawResponse;
import com.sport.playsqorr.ui.AppConstants;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RaiseaTicketScreen extends AppCompatActivity implements View.OnClickListener, TextWatcher, AdapterView.OnItemSelectedListener  {


    TextView toolbar_title_x, his_ticket;
    private TextView tv_ticket_submit;
    EditText ticket_des;
    private DataBaseHelper mydb;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    private String ROLE, CASH_BAL, AMOUNT_CASH, AMOUNT_TOKEN, AVATAR, MYWiNS, ACCNAME, USEREMAIL;

    String[] users_issues = { "Purchase", "Transactions", "Add funds", "Cards", "Profile" };

    Spinner spin_tick ;

    String issue_se;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raisea_ticket_screen);
        mydb = new DataBaseHelper(this);
        sqLiteDatabase = mydb.getReadableDatabase();
        init();
    }

    @SuppressLint({"SetTextI18n", "ResourceType"})
    private void init() {

        toolbar_title_x = findViewById(R.id.toolbar_title_x);
        his_ticket = findViewById(R.id.his_ticket);
        tv_ticket_submit = findViewById(R.id.tv_ticket_submit);
        ticket_des = findViewById(R.id.ticket_des);
        spin_tick =  findViewById(R.id.ticket_spin);
        toolbar_title_x.setText(AppConstants.CUSTOMER_SUPPORT);
//        toolbar_title_x.setText(getString(R.string._raise));
        //Add listener(s)
        toolbar_title_x.setOnClickListener(this);
        his_ticket.setOnClickListener(this);
        tv_ticket_submit.setEnabled(false);
        tv_ticket_submit.setOnClickListener(this);
        ticket_des.addTextChangedListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_title_x:
                //================ Hide Virtual Key Board When  Clicking==================//

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(toolbar_title_x.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

//======== Hide Virtual Keyboard =====================//
                finish();
                break;
            case R.id.his_ticket:
                Intent his_intent = new Intent(getApplicationContext(), RaiseTicketHistory.class);
                startActivity(his_intent);
                break;
            case R.id.tv_ticket_submit:

                tv_ticket_submit.setBackgroundResource(R.drawable.btn_bg_red_ripple);
//                if(progressDialog!=null)
//                    progressDialog.show();
                freshDeskCreate();
                break;
            default:
                break;

        }
    }

    private void freshDeskCreate() {


//        ["description": "Add funds issues", "subject": "Add Funds", "cc_emails": [], "status": 2,
//        "priority": 2, "email": "prak7@myorigami.co"]

        JSONObject fJSon = null;
        try {
            fJSon = new JSONObject();
            JSONArray farray = new JSONArray();
            fJSon.put("description", ticket_des.getText().toString().trim());
            fJSon.put("subject", issue_se);
            fJSon.put("cc_emails", farray);
            fJSon.put("status", 2);
            fJSon.put("priority", 2);
//            fJSon.put("email", "prak7@myorigami.co");
            fJSon.put("email", USEREMAIL);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //   obj_list_token(state_txt,city_txt,country_txt);
        AndroidNetworking.post(APIs.FRESHDESK_CREATE)
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

                        Utilities.showAlertBox(RaiseaTicketScreen.this, "Ticket submitted successfuly", "");


                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("js", "Login----error------- 329" + anError.getErrorBody());

//                            if (progressDialog != null)
//                                progressDialog.dismiss();
                        if (anError.getErrorCode() != 0) {
                            Utilities.showToast(RaiseaTicketScreen.this, anError.getErrorDetail());
                            Log.d("", "onError errorCode : " + anError.getErrorCode());
                            Log.d("", "onError errorBody : " + anError.getErrorBody());
                            Log.d("", "onError errorDetail : " + anError.getErrorDetail());


                        } else {
                            Utilities.showToast(RaiseaTicketScreen.this, anError.getErrorDetail());
                            Log.d("", "onError errorDetail  0: " + anError.getErrorDetail());
                        }

                    }

                });
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable s) {
        int ticket_des_length = ticket_des.getText().toString().trim().length();
        if (ticket_des_length > 0) {
            tv_ticket_submit.setEnabled(true);
            tv_ticket_submit.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
            tv_ticket_submit.setBackgroundResource(R.drawable.btn_bg_red);
        } else {
            tv_ticket_submit.setEnabled(false);
            tv_ticket_submit.setTextColor(ResourcesCompat.getColor(getResources(), R.color.btn_dis_text, null));
            tv_ticket_submit.setBackgroundResource(R.drawable.login_bg_disable);

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateUI();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users_issues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_tick.setAdapter(adapter);
        spin_tick.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
     //   Toast.makeText(getApplicationContext(), "Selected User: "+users_issues[position] ,Toast.LENGTH_SHORT).show();
        issue_se = users_issues[position];
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO - Custom Code
    }
}






















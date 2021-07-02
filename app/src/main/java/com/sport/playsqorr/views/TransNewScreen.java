package com.sport.playsqorr.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.sport.playsqorr.R;
import com.sport.playsqorr.database.DB_Constants;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.model.Transactions;
import com.sport.playsqorr.ui.AppConstants;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.LocationTrack;
import com.sport.playsqorr.utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.sport.playsqorr.utilities.LocationTrack.getLocationFu;
import static com.sport.playsqorr.utilities.LocationTrack.llat;
import static com.sport.playsqorr.utilities.LocationTrack.llong;
import static com.sport.playsqorr.utilities.Utilities.checkLocationPermission;

public class TransNewScreen extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rl_add_funds, rlBalance;
    private RecyclerView rvTransactions;
    private TextView tvBalanceHeader, tvBalAmount, tvWithdrawFunds, trans_token_txt, trans_cash_txt;
    private ImageView ivTokens;
    private ArrayList<Transactions> transactionsList = new ArrayList<>();
    private ArrayList<Transactions> transactionsList_new = new ArrayList<>();
    private String sessionToken,NEWTOKEN;
    private TransNewScreen.TransactionsAdapter transactionsAdapter;
    private String userRole, AMOUNT_CASH, AMOUNT_TOKEN, DATA_DOB, DATA_STATE;
    private ProgressBar trans_progressBar;
    private LinearLayout llFaq, trans_token_ll, trans_cash_ll;
    private DataBaseHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_new_screen);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        }

       /* SharedPreferences sharedPreferences = getSharedPreferences("SESSION_TOKEN", MODE_PRIVATE);
        sessionToken= sharedPreferences.getString("token", "");

        userRole = Dashboard.ROLE;*/
        myDbHelper = new DataBaseHelper(TransNewScreen.this);

        init();

        getInfoFromLocalDB();

        // getTransactionsList();
    }

    private void getInfoFromLocalDB() {
        Cursor cursor = myDbHelper.getAllUserInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                sessionToken = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_SESSIONTOKEN));
                NEWTOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKEN));
                userRole = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_MODETYPE));
                AMOUNT_CASH = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOTALCASHBALANCE));
                AMOUNT_TOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKENBALANCE));
                DATA_DOB = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_DOB));
                DATA_STATE = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_STATE)).trim();
            }
            cursor.close();
        }
        setPageData();
    }


    @SuppressLint("SetTextI18n")
    private void setPageData() {

        LinearLayoutManager llm = new LinearLayoutManager(TransNewScreen.this);
        rvTransactions.setLayoutManager(llm);

        if (userRole != null) {
            if (userRole.equalsIgnoreCase("cash")) {
                tvBalanceHeader.setText("Cash balance");
                tvBalAmount.setText("$" + AMOUNT_CASH);
                ivTokens.setVisibility(View.GONE);
                rl_add_funds.setVisibility(View.GONE);
                tvWithdrawFunds.setVisibility(View.VISIBLE);
                llFaq.setVisibility(View.VISIBLE);

//                trans_cash_ll.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_border_red, null));
//                trans_cash_txt.setTextColor(ResourcesCompat.getColor(getResources(), R.color.sqorr_red, null));
//
//                trans_token_ll.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_border_white, null));
//                trans_token_txt.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));


                tvBalanceHeader.setText("Cash balance");
                tvBalAmount.setText("$" + AMOUNT_CASH);
                ivTokens.setVisibility(View.GONE);

               /* if (transactionsList.size() > 0) {
                    transactionsList_new.clear();
                    rvTransactions.setVisibility(View.VISIBLE);

                    for (Transactions t : transactionsList) {
                        if (t.getCurrencyType().startsWith("C")) {

                            transactionsList_new.add(t);
                            transactionsAdapter = new TransNewScreen.TransactionsAdapter(transactionsList_new, TransNewScreen.this, "CASH");
                            rvTransactions.setAdapter(transactionsAdapter);
                        }

                    }


                } else {
                    rvTransactions.setVisibility(View.GONE);
                    Utilities.showToast(TransNewScreen.this, "No Cash Transactions");
                }
*/

            } else if (userRole.equalsIgnoreCase("tokens")) {
                tvBalanceHeader.setText("Token balance");
                tvBalAmount.setText(AMOUNT_TOKEN);
                ivTokens.setVisibility(View.VISIBLE);
                rl_add_funds.setVisibility(View.VISIBLE);
                tvWithdrawFunds.setVisibility(View.GONE);
                llFaq.setVisibility(View.GONE);

//                trans_token_ll.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_border_red, null));
//                trans_token_txt.setTextColor(ResourcesCompat.getColor(getResources(), R.color.sqorr_red, null));
//
//                trans_cash_ll.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_border_white, null));
//                trans_cash_txt.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));


                ivTokens.setVisibility(View.VISIBLE);
                tvBalanceHeader.setText("Token balance");
                tvBalAmount.setText(AMOUNT_TOKEN);

                /*if (transactionsList.size() > 0) {
                    transactionsList_new.clear();
                    rvTransactions.setVisibility(View.VISIBLE);

                    for (Transactions t : transactionsList) {
                        if (t.getCurrencyType().startsWith("T")) {

                            transactionsList_new.add(t);
                            transactionsAdapter = new TransNewScreen.TransactionsAdapter(transactionsList_new, TransNewScreen.this, "Token");
                            rvTransactions.setAdapter(transactionsAdapter);
                        }
                    }


                } else {
                    rvTransactions.setVisibility(View.GONE);
                    Utilities.showToast(TransNewScreen.this, "No Cash Transactions");
                }
*/
            } else {
                rlBalance.setVisibility(View.GONE);
            }
        } else {
            rlBalance.setVisibility(View.GONE);
        }
    }

    private void init() {
        TextView toolbar_title_x = findViewById(R.id.toolbar_title_x);
        rl_add_funds = findViewById(R.id.rl_add_funds);
        TextView btn_add_funds = findViewById(R.id.btn_add_funds);
        rvTransactions = findViewById(R.id.rvTransactions);
        tvBalanceHeader = findViewById(R.id.tvBalanceHeader);
        ivTokens = findViewById(R.id.ivTokens);
        tvBalAmount = findViewById(R.id.tvBalAmount);
        rlBalance = findViewById(R.id.rlBalance);
        trans_progressBar = findViewById(R.id.trans_progressBar);
        tvWithdrawFunds = findViewById(R.id.tvWithdrawFunds);
        TextView tvFaq = findViewById(R.id.tvFaq);
        llFaq = findViewById(R.id.llFaq);


        trans_token_txt = findViewById(R.id.trans_token_txt);
        trans_cash_txt = findViewById(R.id.trans_cash_txt);
        trans_cash_ll = findViewById(R.id.trans_cash_ll);
        trans_token_ll = findViewById(R.id.trans_token_ll);


        trans_progressBar.setVisibility(View.VISIBLE);
        rvTransactions.setVisibility(View.GONE);

        //setPageData();


//        transactionsAdapter = new TransNewScreen.TransactionsAdapter(transactionsList, TransNewScreen.this);
//        rvTransactions.setAdapter(transactionsAdapter);

        toolbar_title_x.setText(getString(R.string._transactions));

        //Add listener(s)
        toolbar_title_x.setOnClickListener(this);
        btn_add_funds.setOnClickListener(this);
        tvWithdrawFunds.setOnClickListener(this);
        tvFaq.setOnClickListener(this);
        trans_token_ll.setOnClickListener(this);
        trans_cash_ll.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getInfoFromLocalDB();
        getTransactionsList();
        if (transactionsAdapter != null) {
            transactionsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_title_x:
                finish();
                Intent i1 = new Intent(TransNewScreen.this, Profile.class);
                i1.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i1);
                break;
            case R.id.btn_add_funds:

                if (userRole.equalsIgnoreCase("cash")) {
                    Intent addFundsIntent = new Intent(TransNewScreen.this, AddFunds.class);
                    addFundsIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(addFundsIntent, 1212);
                } else if (userRole.equalsIgnoreCase("tokens")) {
                    getTokenFromCash();
                }

                break;
            case R.id.tvFaq:
                Intent webIntent = new Intent(TransNewScreen.this, WebScreens.class);
                webIntent.putExtra("title", AppConstants.FAQS);
                startActivity(webIntent);
                break;
            case R.id.trans_token_ll:


                trans_token_ll.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_border_red, null));
                trans_token_txt.setTextColor(ResourcesCompat.getColor(getResources(), R.color.sqorr_red, null));

                trans_cash_ll.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_border_white, null));
                trans_cash_txt.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));


                ivTokens.setVisibility(View.VISIBLE);
                tvBalanceHeader.setText("Token balance");
                tvBalAmount.setText(AMOUNT_TOKEN);

                if (transactionsList.size() > 0) {
                    transactionsList_new.clear();
                    rvTransactions.setVisibility(View.VISIBLE);

                    for (Transactions t : transactionsList) {
                        if (t.getCurrencyType().startsWith("t")) {

                            transactionsList_new.add(t);
                            transactionsAdapter = new TransNewScreen.TransactionsAdapter(transactionsList_new, TransNewScreen.this, "Token");
                            rvTransactions.setAdapter(transactionsAdapter);
                        }

                        Log.e("size---k", "330--" + transactionsList_new.size());
                    }


                } else {
                    rvTransactions.setVisibility(View.GONE);
                    Utilities.showToast(TransNewScreen.this, "No Cash Transactions");
                }
//                for (int i = 0; i < transactionsList.size(); i++) {
//
//                    if (transactionsList.get(i).getCurrencyType().equalsIgnoreCase("Tokens")) {
//                        transactionsAdapter = new TransNewScreen.TransactionsAdapter(transactionsList, TransNewScreen.this, "TOKEN");
//                        rvTransactions.setAdapter(transactionsAdapter);
//
//                    }
////                    if (transactionsAdapter != null)
////                        transactionsAdapter.notifyDataSetChanged();
//
//                }


                break;
            case R.id.trans_cash_ll:

                trans_cash_ll.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_border_red, null));
                trans_cash_txt.setTextColor(ResourcesCompat.getColor(getResources(), R.color.sqorr_red, null));

                trans_token_ll.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_border_white, null));
                trans_token_txt.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));


                tvBalanceHeader.setText("Cash balance");
                tvBalAmount.setText("$" + AMOUNT_CASH);
                ivTokens.setVisibility(View.GONE);


                if (transactionsList.size() > 0) {
                    transactionsList_new.clear();
                    rvTransactions.setVisibility(View.VISIBLE);

                    for (Transactions t : transactionsList) {
                        if (t.getCurrencyType().startsWith("c")) {

                            transactionsList_new.add(t);
                            transactionsAdapter = new TransNewScreen.TransactionsAdapter(transactionsList_new, TransNewScreen.this, "CASH");
                            rvTransactions.setAdapter(transactionsAdapter);
                        }

                        Log.e("size---k", "330--" + transactionsList_new.size());
                    }


                } else {
                    rvTransactions.setVisibility(View.GONE);
                    Utilities.showToast(TransNewScreen.this, "No Cash Transactions");
                }


              /*  for (int i = 0; i < transactionsList.size(); i++) {
                    Log.e("cash---ka", " " + transactionsList.size());
//                    transactionsList.
                    if (transactionsList.get(i).getCurrencyType().contains("C")) {
//                    if(transactionsList.get(i).getCurrencyType().equalsIgnoreCase("Cash")){
                        Log.e("cash---321", transactionsList.get(i).getCurrencyType() + transactionsList.size());
                        transactionsAdapter = new TransNewScreen.TransactionsAdapter(transactionsList, TransNewScreen.this, "CASH");
                        rvTransactions.setAdapter(transactionsAdapter);

                    } else {
                        Log.e("cash---else 321", transactionsList.get(i).getCurrencyType() + transactionsList.size());
                    }

                }*/

//                if (transactionsAdapter != null)
//                    transactionsAdapter.notifyDataSetChanged();
                break;
            case R.id.tvWithdrawFunds:
                Intent withdrawIntent = new Intent(TransNewScreen.this, Withdraw_paypal.class);
//                Intent withdrawIntent = new Intent(TransNewScreen.this, WithdrawFunds.class);
                withdrawIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(withdrawIntent, 1212);

                break;
            default:
                break;
        }
    }

    //get user transactions list
    private void getTransactionsList() {

        AndroidNetworking.get(APIs.TS)
                .setPriority(Priority.HIGH)
//                .addHeaders("sessionToken", sessionToken)
                .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        trans_progressBar.setVisibility(View.GONE);

                        Log.e("getTransactionsList :: ", response.toString());

                        transactionsList.clear();
                        Type listType = new TypeToken<List<Transactions>>() {
                        }.getType();
                        transactionsList = new Gson().fromJson(response.toString(), listType);


                        trans_cash_ll.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_border_red, null));
                        trans_cash_txt.setTextColor(ResourcesCompat.getColor(getResources(), R.color.sqorr_red, null));

                        trans_token_ll.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_border_white, null));
                        trans_token_txt.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                        tvBalanceHeader.setText("Cash balance");
                        tvBalAmount.setText("$" + AMOUNT_CASH);
                        ivTokens.setVisibility(View.GONE);


                        if (transactionsList.size() > 0) {
                            rvTransactions.setVisibility(View.VISIBLE);
                            transactionsList_new.clear();
                            rvTransactions.setVisibility(View.VISIBLE);

                            for (Transactions t : transactionsList) {
                                if (t.getCurrencyType().startsWith("c")) {

                                    transactionsList_new.add(t);
                                    transactionsAdapter = new TransNewScreen.TransactionsAdapter(transactionsList_new, TransNewScreen.this, "CASH");
                                    rvTransactions.setAdapter(transactionsAdapter);
                                }

                            }
                        } else {
                            rvTransactions.setVisibility(View.GONE);
                            Utilities.showToast(TransNewScreen.this, "No Transactions ");
                        }

                        if (transactionsAdapter != null)
                            transactionsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        trans_progressBar.setVisibility(View.GONE);

                        try {
                            JSONObject ej = new JSONObject(error.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                            String au = ej.getString("message");
                            if (au.contains("Unauthorized")) {
                                showAlertBoxAU(TransNewScreen.this, "Error", "Session has expired,please try logining again");
                            } else {
                                Utilities.showToast(TransNewScreen.this, ej.getString("message"));
                            }


                        } catch (Exception e) {

                        }

                        //   Utilities.showToast(TransNewScreen.this, error.getErrorDetail());
                    }
                });

    }


    private void showAlertBoxAU(final Context context, String title, String message) {

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

//                dialog.dismiss();

                SharedPreferences sp = getSharedPreferences("SESSION_TOKEN", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();

                //mydb.clearTableMobileUser();
                myDbHelper.resetLocalData();

                LoginManager.getInstance().logOut();

                Intent in = new Intent(TransNewScreen.this, OnBoarding.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                // getApplicationContext().deleteDatabase(DataBaseHelper.DATABASE_NAME);


                //    resetDatabase();

              /*  Intent mStartActivity = new Intent(Profile.this, OnBoarding.class);
                int mPendingIntentId = 123456;
                PendingIntent mPendingIntent = PendingIntent.getActivity(Profile.this, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);*/

            }
        });

    }

    public class TransactionsAdapter extends RecyclerView.Adapter<TransNewScreen.TransactionsAdapter.ViewHolder> {

        private ArrayList<Transactions> transactionList_adap;
        private Context context;
        private String ct;

        public TransactionsAdapter(ArrayList<Transactions> items, Context context, String cash) {
            this.transactionList_adap = items;
            this.context = context;
            this.ct = cash;
        }

        @NonNull
        @Override
        public TransNewScreen.TransactionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.transactions_cell, parent, false);

            return new TransNewScreen.TransactionsAdapter.ViewHolder(itemView);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull TransNewScreen.TransactionsAdapter.ViewHolder holder, final int position) {

//            final int pos = holder.getAdapterPosition();

            Log.e("cash---595k", ct);
            Transactions tt = transactionList_adap.get(position);

            String transType = tt.getType();
//            String transType = transactionsList.get(pos).getType();

            holder.tvTransactionType.setText(transType);

            holder.tvDescription.setText(tt.getDescription());


            if (userRole != null && userRole.equalsIgnoreCase("cash")) {

                Log.e("cash--d1", tt.getDescription());
                String userUsedAmount = tt.getAmount();
                if (userUsedAmount.contains("-")) {
                    userUsedAmount = userUsedAmount.replace("-", "");
                }

                if (ct.equalsIgnoreCase("CASH")) {
                    holder.llToken.setVisibility(View.GONE);
                    holder.tvTokenAmount.setVisibility(View.GONE);
                    holder.tvAmount.setVisibility(View.VISIBLE);
                    holder.tvAddOrRemove.setVisibility(View.VISIBLE);
                    holder.tvAmount.setText("$" + userUsedAmount);
                    Log.e("cash--d", "c--------" + tt.getDescription());

                    holder.tvBalAmount.setText("$" + tt.getBalance());

                } else {
                    holder.tvAmount.setVisibility(View.INVISIBLE);
                    holder.tvAddOrRemove.setVisibility(View.INVISIBLE);
                    holder.llToken.setVisibility(View.VISIBLE);
                    holder.tvTokenAmount.setVisibility(View.VISIBLE);
                    holder.tvTokenAmount.setText(userUsedAmount);
                    holder.tvBalAmount.setText(tt.getBalance());
                }

            } else {
//                holder.llToken.setVisibility(View.VISIBLE);
//                holder.tvTokenAmount.setVisibility(View.VISIBLE);
//                holder.tvAmount.setVisibility(View.GONE);
                String userUsedAmount = tt.getAmount();
                if (userUsedAmount.contains("-")) {
                    userUsedAmount = userUsedAmount.replace("-", "");
                }
                // holder.tvTokenAmount.setText(userUsedAmount);
                //  holder.tvBalAmount.setText(transactionsList.get(pos).getBalance());
                if (ct.equalsIgnoreCase("CASH")) {
                    holder.llToken.setVisibility(View.GONE);
                    holder.tvTokenAmount.setVisibility(View.GONE);
                    holder.tvAmount.setVisibility(View.VISIBLE);
                    holder.tvAddOrRemove.setVisibility(View.VISIBLE);

                    holder.tvAmount.setText("$" + userUsedAmount);
                    holder.tvBalAmount.setText("$" + tt.getBalance());

                } else {
//                    holder.tvAmount.setVisibility(View.GONE);
                    holder.tvAmount.setVisibility(View.INVISIBLE);
                    holder.tvAddOrRemove.setVisibility(View.INVISIBLE);
                    holder.llToken.setVisibility(View.VISIBLE);
                    holder.tvTokenAmount.setVisibility(View.VISIBLE);
                    holder.tvTokenAmount.setText(userUsedAmount);
                    holder.tvBalAmount.setText(tt.getBalance());
                }
            }

            if (transType != null && transType.equalsIgnoreCase("withdraw")) {
                holder.ivIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_withdraw, null));
                holder.rlIcon.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_withdraw, null));
                holder.tvAddOrRemove.setText("-");
                holder.tvAddOrRemoveToken.setText("-");
            } else if (transType != null && transType.equalsIgnoreCase("deposit")) {
                holder.ivIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_deposit, null));
                holder.rlIcon.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_deposit, null));
                holder.tvAddOrRemove.setText("+");
                holder.tvAddOrRemoveToken.setText("+");
            } else if (transType != null && transType.equalsIgnoreCase("winning")) {
                holder.ivIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_trophy, null));
                holder.rlIcon.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_deposit, null));
                holder.tvAddOrRemove.setText("+");
                holder.tvAddOrRemoveToken.setText("+");
            } else if (transType != null && transType.equalsIgnoreCase("wager")) {
                holder.ivIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_wager, null));
                holder.rlIcon.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_withdraw, null));
                holder.tvAddOrRemove.setText("-");
                holder.tvAddOrRemoveToken.setText("-");
            }


            //Set Date
            try {
                holder.tvDate.setVisibility(View.GONE);
                String dateFromServer = tt.getDate();
                if (dateFromServer != null && !dateFromServer.equals("")) {
                    String[] dateTime = dateFromServer.split("T");
                    if (dateTime.length > 0) {
                        String[] dateArray = dateTime[0].split("-");
                        if (dateArray.length == 3) {
                            holder.tvDate.setText(dateArray[1] + "/" + dateArray[2] + "/" + dateArray[0].substring(2, 4));
                            holder.tvDate.setVisibility(View.VISIBLE);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return transactionList_adap.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvTransactionType, tvDescription, tvAddOrRemove, tvDate, tvBalAmount, tvAmount,
                    tvTokenAmount, tvAddOrRemoveToken;
            private ImageView ivIcon;
            private RelativeLayout rlIcon;
            private LinearLayout llToken;

            public ViewHolder(View view) {
                super(view);
                this.tvTransactionType = itemView.findViewById(R.id.tvTransactionType);
                this.tvDescription = itemView.findViewById(R.id.tvDescription);
                this.tvAddOrRemove = itemView.findViewById(R.id.tvAddOrRemove);
                this.tvDate = itemView.findViewById(R.id.tvDate);
                this.tvBalAmount = itemView.findViewById(R.id.tvBalAmount);
                this.tvAmount = itemView.findViewById(R.id.tvAmount);
                this.ivIcon = itemView.findViewById(R.id.ivIcon);
                this.rlIcon = itemView.findViewById(R.id.rlIcon);
                this.tvDate = itemView.findViewById(R.id.tvDate);
                this.tvTokenAmount = itemView.findViewById(R.id.tvTokenAmount);
                this.llToken = itemView.findViewById(R.id.llToken);
                this.tvAddOrRemoveToken = itemView.findViewById(R.id.tvAddOrRemoveToken);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {
                if (requestCode == 1212) {
                    Log.e("HEy called Back", "FRom Add FUNDS");
                }
            }

        } catch (Exception ex) {
            Toast.makeText(TransNewScreen.this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void getTokenFromCash() {

        try {
            Dexter.withContext(TransNewScreen.this)
                    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {


                            //  if (Helper.isGPSEnabled(MainActivity.this)) {
                            LocationTrack locationTrack = new LocationTrack(TransNewScreen.this);
//                            if (locationTrack.canGetLocation) {
//                                double lat = locationTrack.getLatitude();
//                                double lon = locationTrack.getLongitude();
                            getLocationFu(TransNewScreen.this);
                            double lat = llat;
                            double lon = llong;
                                try {
                                    Geocoder gcd = new Geocoder(TransNewScreen.this, Locale.getDefault());
                                    List<Address> addresses = gcd.getFromLocation(lat,
                                            lon, 1);

                                    if (addresses.size() > 0) {

                                        final String state_txt = addresses.get(0).getAdminArea();
                                        final String city_txt = addresses.get(0).getLocality();
                                        final String country_txt = addresses.get(0).getCountryName();

                                        {


                                            JSONObject jsonObj = new JSONObject();

                                            try {
                                                jsonObj.put("city", city_txt);
                                                jsonObj.put("stateName", state_txt);
                                                jsonObj.put("stateCode", "");
                                                jsonObj.put("country", country_txt);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

//                                            obj_list_token(state_txt);
                                            if (DATA_DOB != null && !TextUtils.isEmpty(DATA_DOB) && !DATA_DOB.equalsIgnoreCase("null")) {
                                                if (Utilities.getAge(DATA_DOB) >= 18) {
                                                    Log.e("524--", DATA_DOB + "-----" + Utilities.getAge(DATA_DOB));

                                                    getFinalLocationChekup();

                                                } else {
                                                    showAlertBox(TransNewScreen.this, getResources().getString(R.string.dob_title), getResources().getString(R.string.dob_msg));
                                                }


                                            } else {
                                                Utilities.showAlertBoxTrans(TransNewScreen.this, getString(R.string.token_to_cash_title), getString(R.string.token_to_cash_msg));
                                            }
                                        }
//
                                    } else {
                                        Log.e("test--", "enable loction");
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            checkLocationPermission(getApplicationContext(), TransNewScreen.this);

                                        }
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                          //  }
                                      /*  } else {
                                            Toast.makeText(MainActivity.this, "Location service not enabled", Toast.LENGTH_LONG).show();
                                        }*/


                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            // check for permanent denial of permission
                            if (response.isPermanentlyDenied()) {
                                // navigate user to app settings
//                                        Toast.makeText(getApplicationContext(), "Location service not enabled", Toast.LENGTH_LONG).show();
                                Utilities.showAlertBoxLoc(TransNewScreen.this, getResources().getString(R.string.enable_location_title), getResources().getString(R.string.enable_location_msg));
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }


                    }).check();


            // finish();
        } catch (Exception e) {

        }

    }

    public String getJson() {
        String json = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.state_rules);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return json;
        }
        return json;
    }

    //Tokens
    void obj_list_token(String state_txt) {

        try {
            JSONArray array = new JSONArray(getJson());
            for (int k = 0; k < array.length(); k++) {
                JSONObject object = array.getJSONObject(k);
                String State = object.getString("State");
                if (State.equalsIgnoreCase(state_txt)) {
                    String cashValue = object.getString("Cash");
                    System.out.println(cashValue);

                    if (cashValue.equalsIgnoreCase("YES")) {


                        if (DATA_DOB != null && !TextUtils.isEmpty(DATA_DOB) && !DATA_DOB.equalsIgnoreCase("null")) {
                            if (Utilities.getAge(DATA_DOB) >= 18) {
                                Log.e("524--", DATA_DOB + "-----" + Utilities.getAge(DATA_DOB));

//                                getFinalLocationChekup();
                                Intent addFundsIntent = new Intent(TransNewScreen.this, AddFunds.class);
                                addFundsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(addFundsIntent);
                            } else {
                                showAlertBox(TransNewScreen.this, getResources().getString(R.string.dob_title), getResources().getString(R.string.dob_msg));
                            }


                        } else {
                            Utilities.showAlertBoxTrans(TransNewScreen.this, getString(R.string.token_to_cash_title), getString(R.string.token_to_cash_msg));
                        }

//                        showAlertBox(TransNewScreen.this, getString(R.string.token_to_cash_title) , getString(R.string.token_to_cash_msg));


                    } else {

                        showAlertBox(TransNewScreen.this, getString(R.string.location_title) + " " + state_txt, getString(R.string.location_msg));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getFinalLocationChekup() {
        {


            //  if (Helper.isGPSEnabled(MainActivity.this)) {
            LocationTrack locationTrack = new LocationTrack(TransNewScreen.this);
//            if (locationTrack.canGetLocation) {
//                double lat = locationTrack.getLatitude();
//                double lon = locationTrack.getLongitude();
            getLocationFu(TransNewScreen.this);
            double lat = llat;
            double lon = llong;
                try {
                    Geocoder gcd = new Geocoder(TransNewScreen.this, Locale.getDefault());
                    List<Address> addresses = gcd.getFromLocation(lat,
                            lon, 1);

                    if (addresses.size() > 0) {

                        final String state_txt = addresses.get(0).getAdminArea();
                        final String city_txt = addresses.get(0).getLocality();
                        final String country_txt = addresses.get(0).getCountryName();
                        {


                            JSONObject jsonObj = new JSONObject();

                            try {
                                jsonObj.put("city", city_txt);
                                jsonObj.put("stateName", state_txt);
                                jsonObj.put("stateCode", "");
                                if (country_txt.equalsIgnoreCase("United States")) {
                                    jsonObj.put("country", "USA");
                                } else {
                                    jsonObj.put("country", country_txt);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            AndroidNetworking.post(APIs.LOCATION_USER_VAL)
                                    .addJSONObjectBody(jsonObj) // posting json
                                    .addHeaders("sessionToken", sessionToken)
                                    .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                                    .setPriority(Priority.MEDIUM)
                                    .build()
                                    .getAsJSONObject(new JSONObjectRequestListener() {
                                        @Override
                                        public void onResponse(JSONObject response) {


                                            Log.e("***MA: Token:", response.toString());

                                            try {
                                                String Usermode = response.getString("userPlayMode");

                                                if (Usermode.equalsIgnoreCase("cash")) {
                                                    Intent addFundsIntent = new Intent(TransNewScreen.this, AddFunds.class);
                                                    addFundsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                    startActivity(addFundsIntent);

                                                } else {
                                                    showAlertBox(TransNewScreen.this, getString(R.string.location_title) + " " + state_txt, getString(R.string.location_msg));
                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onError(ANError anError) {
                                            Log.e("js", "Login----error-------" + anError);


                                            if (anError.getErrorCode() != 0) {
                                                Log.d("", "onError errorCode : " + anError.getErrorCode());
                                                Log.d("", "onError errorBody : " + anError.getErrorBody());
                                                Log.d("", "onError errorDetail : " + anError.getErrorDetail());


                                            } else {
                                                Log.d("", "onError errorDetail  0: " + anError.getErrorDetail());
                                            }

                                        }
                                    });
                        }
//
                    } else {
                        Log.e("test--", "enable loction");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            checkLocationPermission(getApplicationContext(), TransNewScreen.this);

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
          //  }


        }
    }


    private void showAlertBox(final Context context, String title, String message) {

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


            }
        });

    }
}


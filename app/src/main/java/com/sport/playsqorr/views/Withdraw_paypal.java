package com.sport.playsqorr.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.sport.playsqorr.R;
import com.sport.playsqorr.database.DB_Constants;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.model.PayPalServerResponse;
import com.sport.playsqorr.model.WithdrawResponse;
import com.sport.playsqorr.pojos.ACHCardDataPojo;
import com.sport.playsqorr.pojos.CardDataPojo;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Withdraw_paypal extends AppCompatActivity implements View.OnClickListener, TextWatcher, PopupMenu.OnMenuItemClickListener {

    private TextView tvWithdrawFunds, pptxt;
    private View amountView;
    private EditText etAmount, etFullName, etStreet, etCity, etZipCode;
    private LinearLayout llwithdrawAmount, llPaypal;
    private Spinner spnrState;
    private DataBaseHelper myDbHelper;
    private String withdrawCash, totalCash, promoBal, sessionToken,NEWTOKEN;
    private RelativeLayout rLAddBankAccount, rLAddACHAccount;
    private RecyclerView rvACHCardsList;
//    private RecyclerView rvCardsList, rvACHCardsList;

    private String selectedCardType, selCardId;
    private List<CardDataPojo> cardsResponse = new ArrayList<>();
    private List<ACHCardDataPojo> ACH_cardsResponse = new ArrayList<>();
    private CardsListAdapter recycleAdapter;
    private ACHCardsListAdapter ACHrecycleAdapter;
    private static final int REQUEST_CODE_PAYMENT = 1;
    PayPalServerResponse paypalResponse;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;
    private static final String CONFIG_CLIENT_ID = "AZXBmKKIckXQ_g4DKbZfgwKLRSluu287MQ2V0tVW6-VG17460VhE5NK0zkLipOrvf_cDFtsJC37CbQRL";
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            .acceptCreditCards(false)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Hipster Store")
            .merchantPrivacyPolicyUri(
                    Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(
                    Uri.parse("https://www.example.com/legal"));


    String method = "";
    boolean thatThingHappened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_paypal);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.white, null));


        initDatabase();
//        initComponents();
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
    }

    private void initDatabase() {
        myDbHelper = new DataBaseHelper(getApplicationContext());
        Cursor cursor = myDbHelper.getAllUserInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                System.out.println(cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NAME)));
                sessionToken = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_SESSIONTOKEN));
                NEWTOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKEN));
                withdrawCash = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_CASHBALANCE));
                promoBal = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_PROMOBALANCE));
                totalCash = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOTALCASHBALANCE));
            }

            cursor.close();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


        initComponents();
        inutMethods();
        selCardId = "";
        method = "";
        llPaypal.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.card_sel_normal, null));
        pptxt.setTextColor(ResourcesCompat.getColor(getResources(), R.color.header, null));

//        getAllACHCardsList();


    }

    private void inutMethods() {
        LinearLayoutManager ll_ach = new LinearLayoutManager(Withdraw_paypal.this);
        rvACHCardsList.setLayoutManager(ll_ach);
        rvACHCardsList.setItemAnimator(null);
        rvACHCardsList.setNestedScrollingEnabled(false);
        ACHrecycleAdapter = new ACHCardsListAdapter(ACH_cardsResponse, Withdraw_paypal.this);
        rvACHCardsList.setAdapter(ACHrecycleAdapter);
    }

    @SuppressLint("SetTextI18n")
    private void initComponents() {
        TextView toolbar_title_x = findViewById(R.id.toolbar_title_x);
        TextView tvCashBalance = findViewById(R.id.tvCashBalance);
        TextView tvWithdrawBalance = findViewById(R.id.tvWithdrawBalance);
        TextView tvPromoBalance = findViewById(R.id.tvPromoBalance);
        llPaypal = findViewById(R.id.llPaypal);
        etAmount = findViewById(R.id.etAmount);
        amountView = findViewById(R.id.amountView);
        rLAddBankAccount = findViewById(R.id.rl_add_new_bank_account);
        rLAddACHAccount = findViewById(R.id.rl_add_new_ach_account);
//        etFullName=findViewById(R.id.etFullName);
//        etCity=findViewById(R.id.etCity);
//        etStreet=findViewById(R.id.etStreet);
//        etZipCode=findViewById(R.id.etZipCode);
//        spnrState=findViewById(R.id.spnrState);
        llwithdrawAmount = findViewById(R.id.llwithdraw_funds);
        tvWithdrawFunds = findViewById(R.id.tvWithdrawFunds);
        pptxt = findViewById(R.id.pptxt);

        //Add listener(s)
        toolbar_title_x.setOnClickListener(this);
        rLAddBankAccount.setOnClickListener(this);
        rLAddACHAccount.setOnClickListener(this);
//        etFullName.addTextChangedListener(this);
//        etStreet.addTextChangedListener(this);
//        etCity.addTextChangedListener(this);
//        etZipCode.addTextChangedListener(this);
        llwithdrawAmount.setOnClickListener(this);
        llPaypal.setOnClickListener(this);

        String[] statesArray = getResources().getStringArray(R.array.states_array);
//        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, statesArray);
//        spnrState.setAdapter(courseAdapter);

        toolbar_title_x.setText("Withdraw funds");
        tvCashBalance.setText("$" + totalCash);
        tvWithdrawBalance.setText("$" + withdrawCash);

        tvPromoBalance.setText("Promo funds are not available for withdrawal.You currently have $" + promoBal + " in promo funds.");


        rvACHCardsList = findViewById(R.id.rvachCardsList);

//        rvCardsList = findViewById(R.id.rvCardsList);
//        LinearLayoutManager llm = new LinearLayoutManager(Withdraw_paypal.this);
//        rvCardsList.setLayoutManager(llm);
//        rvCardsList.setItemAnimator(null);
//        rvCardsList.setNestedScrollingEnabled(false);
//        recycleAdapter = new CardsListAdapter(cardsResponse, Withdraw_paypal.this);
//        rvCardsList.setAdapter(recycleAdapter);

// ACH CRADS LIST

        aa();

    }


    private void aa() {

        etAmount.setText("");
        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String withdrawAmount = etAmount.getText().toString();
                if (charSequence.length() == 0) {
                    return;
                }
                if (withdrawAmount.indexOf("$") == -1 && charSequence.length() >= 1) {
                    withdrawAmount = "$" + withdrawAmount;
                    etAmount.setText(withdrawAmount);
                    if (i1 == 0 && i2 == 1) {
                        etAmount.setSelection(2);
                    } else {
                        etAmount.setSelection(1);
                    }
                } else {
                    if (withdrawAmount.length() == 1) {
                        etAmount.setText("");
                    }
                }
                if (!etAmount.getText().toString().trim().isEmpty()) {
                    llwithdrawAmount.setVisibility(View.GONE);
//                    llwithdrawAmount.setVisibility(View.VISIBLE);
                    llwithdrawAmount.setClickable(true);
                    llwithdrawAmount.setEnabled(true);
                    llwithdrawAmount.setBackgroundResource(R.drawable.btn_bg_green);
                    tvWithdrawFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                } else {
                    llwithdrawAmount.setVisibility(View.GONE);
                    llwithdrawAmount.setClickable(false);
                    llwithdrawAmount.setEnabled(false);
                    llwithdrawAmount.setBackgroundResource(R.drawable.btn_bg_grey);
                    tvWithdrawFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.add_fund_clr, null));

                    method = "";
                    llPaypal.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.card_sel_normal, null));
                    pptxt.setTextColor(ResourcesCompat.getColor(getResources(), R.color.header, null));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

//
//                }
//                if(s.toString().length()>0&&!s.toString().startsWith("$")){
//                    etAmount.setText("$");
//                    Selection.setSelection(etAmount.getText(), etAmount.getText().length());
//                }
                if (etAmount.getText().toString().trim().length() == 0) {
                    amountView.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sep_view_color, null));
                } else {
                    amountView.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.text_color_new, null));
                }
            }
        });
    }

    public class CardsListAdapter extends RecyclerView.Adapter<CardsListAdapter.ViewHolder> {


        private final List<CardDataPojo> mValues;
        private Context context;
        private int selPos = -1;

        public CardsListAdapter(List<CardDataPojo> items, Context context) {
            mValues = items;
            this.context = context;
        }

        @NonNull
        @Override
        public CardsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.added_card_view, parent, false);

            return new ViewHolder(itemView);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final CardsListAdapter.ViewHolder holder, final int position) {

            String cardType = cardsResponse.get(position).getCardType();
            selectedCardType = cardType;
            if (cardType.equalsIgnoreCase("master")) {
                holder.ivCard.setImageResource(R.drawable.ic_cc_mastercard);
                holder.tvCardName.setText("Mastercard* " + cardsResponse.get(position).getLastFourDigits());
            } else if (cardType.equalsIgnoreCase("visa")) {
                holder.ivCard.setImageResource(R.drawable.ic_cc_visa);
                holder.tvCardName.setText("Visa* " + cardsResponse.get(position).getLastFourDigits());
            } else if (cardType.equalsIgnoreCase("JCB")) {
                holder.ivCard.setImageResource(R.drawable.ic_cc_generic);
                holder.tvCardName.setText("JCB* " + cardsResponse.get(position).getLastFourDigits());
            } else if (cardType.equalsIgnoreCase("Discover")) {
                holder.ivCard.setImageResource(R.drawable.ic_cc_generic);
                holder.tvCardName.setText("Discover* " + cardsResponse.get(position).getLastFourDigits());
            } else {
                holder.ivCard.setImageResource(R.drawable.ic_cc_generic);
            }

            String expDate = cardsResponse.get(position).getExpiry();
            if (expDate != null && !expDate.equalsIgnoreCase("")) {
                holder.tvExpiry.setVisibility(View.VISIBLE);
                holder.tvExpiry.setText("exp " + expDate);
            } else {
                holder.tvExpiry.setVisibility(View.GONE);
            }

            holder.ivOverFlow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selCardId = cardsResponse.get(position).get_id();
                    holder.llCard.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.card_sel_normal, null));
                    holder.tvCardName.setTextColor(ResourcesCompat.getColor(getResources(), R.color.header, null));
                    notifyDataSetChanged();
                    Context wrapper = new ContextThemeWrapper(Withdraw_paypal.this, R.style.popupMenuStyle1);
                    PopupMenu popup = new PopupMenu(wrapper, view);
                    popup.setOnMenuItemClickListener(Withdraw_paypal.this);
                    popup.inflate(R.menu.cc_card_menu);
                    popup.show();
                }
            });

            if (etAmount.getText().toString().trim().length() > 0) {
                if (selPos == position) {
                    holder.llCard.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.card_sel_highlight, null));
                    holder.tvCardName.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                } else {
                    holder.llCard.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.card_sel_normal, null));
                    holder.tvCardName.setTextColor(ResourcesCompat.getColor(getResources(), R.color.header, null));
                }
            }

            holder.llCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selCardId = cardsResponse.get(position).get_id();
                    selPos = position;
                    notifyDataSetChanged();

                    if (etAmount.getText().toString().trim().length() > 0) {
                        llwithdrawAmount.setEnabled(true);
                        llwithdrawAmount.setVisibility(View.GONE);
//                        llwithdrawAmount.setVisibility(View.VISIBLE);

//                        if (customAmount > 0) {
//                            llwithdrawAmount.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_bg_red_ripple, null));
//                        } else {
//                            llwithdrawAmount.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_bg_green, null));
//                        }
                        tvWithdrawFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                    } else {
                        llwithdrawAmount.setVisibility(View.GONE);
                        llwithdrawAmount.setEnabled(false);
                        llwithdrawAmount.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_gray_bg, null));
                        tvWithdrawFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.add_fund_clr, null));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return this.mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView ivCard, ivOverFlow;
            TextView tvCardName, tvExpiry;
            LinearLayout llCard;

            public ViewHolder(View view) {
                super(view);
                llCard = view.findViewById(R.id.llCard);
                ivCard = view.findViewById(R.id.ivCard);
                tvCardName = view.findViewById(R.id.tvCardName);
                tvExpiry = view.findViewById(R.id.tvExpiry);
                ivOverFlow = view.findViewById(R.id.ivOverFlow);

            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_title_x:
                finish();
//                Intent i1 = new Intent(Withdraw_paypal.this, TransactionsActivity.class);
                Intent i1 = new Intent(Withdraw_paypal.this, TransNewScreen.class);
                i1.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i1);
                break;
            case R.id.llwithdraw_funds:
                JSONObject jsonObj_card = new JSONObject();


                if (selCardId != null && !selCardId.isEmpty()) {

                    if (etAmount.getText().toString().trim().length() >= 1) {

                        String amountTxtuse = etAmount.getText().toString().trim().replace("$", "");
                        double reqAmountuse = Double.parseDouble(amountTxtuse);

                        if (reqAmountuse >= 1) {
                            try {
                                String amountTxt11 = etAmount.getText().toString().trim().replace("$", "");
                                String withdrawAmount = withdrawCash;

                                double reqAmount11 = Double.parseDouble(amountTxt11);
                                double availAmount = Double.parseDouble(withdrawAmount);

                                if (availAmount - reqAmount11 >= 0) {
                                    jsonObj_card.put("achId", selCardId);
                                    jsonObj_card.put("amount", amountTxt11);
//
                                    WithdrawViaACH(jsonObj_card);
                                    llwithdrawAmount.setVisibility(View.GONE);

                                } else {
                                    Utilities.showToast(Withdraw_paypal.this, "Your withdrawal amount should be less than or equals to " + "$" + withdrawCash);

                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                                llwithdrawAmount.setVisibility(View.GONE);
//                                llwithdrawAmount.setVisibility(View.VISIBLE);
                            }

                        } else {
                            Utilities.showToast(Withdraw_paypal.this, "Please enter vaild withdraw amount");

                        }
                    } else {
                        Utilities.showToast(Withdraw_paypal.this, "Please enter withdraw amount");

                    }

                } else {
                    if (etAmount.getText().toString().trim().length() >= 1) {
                        String amountTxt11 = etAmount.getText().toString().trim().replace("$", "");
                        Integer reqAmount11 = Integer.parseInt(amountTxt11);

                        if (reqAmount11 >= 1) {
                            try {
                                String amountTxt = etAmount.getText().toString().trim().replace("$", "");
                                String withdrawAmount = withdrawCash;

                                if (!amountTxt.equals("") && !withdrawAmount.equals("")) {
                                    Integer reqAmount = Integer.parseInt(amountTxt);
                                    Integer availAmount = Integer.parseInt(withdrawAmount);
                                    //if(reqAmount<=availAmount) {
                                    if (availAmount - reqAmount >= 0) {
//                            JSONObject jsonObject = new JSONObject();
//
//                            jsonObject.put("amount", reqAmount);

//                            SendDataToServer(jsonObject, reqAmount);
                                        if (method.equalsIgnoreCase("paypal")) {

                                            //   Intent withdrawIntent = new Intent(Withdraw_paypal.this, WithdrawlPaypalScreen.class);
                                            Intent withdrawIntent = new Intent(Withdraw_paypal.this, Withdraw_ACH.class);
                                            withdrawIntent.putExtra("reqAmount", String.valueOf(reqAmount));
                                            withdrawIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);

                                            startActivity(withdrawIntent);
                                        } else {

                                            Utilities.showToast(Withdraw_paypal.this, "Please Select any one Withdrawl Method");

                                        }

                                    } else {
                                        Utilities.showToast(Withdraw_paypal.this, "Your withdrawal amount should be less than or equals to " + "$" + withdrawCash);
                                    }
                                } else {
                                    Utilities.showToast(Withdraw_paypal.this, "Something went wrong please try again after sometime");
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Utilities.showToast(Withdraw_paypal.this, "Please enter vaild withdraw amount");

                        }

                    } else {
                        Utilities.showToast(Withdraw_paypal.this, "Please enter  withdraw amount");

                    }


                }

//               buildRequest();
                break;
            case R.id.rl_add_new_bank_account:

                if (etAmount.getText().toString().trim().length() >= 1) {
                    String amountTxtuse = etAmount.getText().toString().trim().replace("$", "");
//                    Integer reqAmountuse = Integer.parseInt(amountTxtuse);
                    double reqAmountuse = Double.parseDouble(amountTxtuse);

                    if (reqAmountuse >= 1) {
                        try {
                            String amountTxt = etAmount.getText().toString().trim().replace("$", "");
                            String withdrawAmount = withdrawCash;

                            if (!amountTxt.equals("") && !withdrawAmount.equals("")) {
//                                Integer reqAmount = Integer.parseInt(amountTxt);
//                                Integer availAmount = Integer.parseInt(withdrawAmount);
                                double reqAmount = Double.parseDouble(amountTxt);
                                double availAmount = Double.parseDouble(withdrawAmount);
                                //if(reqAmount<=availAmount) {
                                if (availAmount - reqAmount >= 0) {
                                    Intent withdrawIntent = new Intent(Withdraw_paypal.this, UserNewAddress.class);
                                    withdrawIntent.putExtra("reqAmount", String.valueOf(reqAmount));
                                    withdrawIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);

                                    startActivity(withdrawIntent);

                                } else {
                                    Utilities.showToast(Withdraw_paypal.this, "Your withdrawal amount should be less than or equals to " + "$" + withdrawCash);
                                }
                            } else {
                                Utilities.showToast(Withdraw_paypal.this, "Something went wrong please try again after sometime");
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Utilities.showToast(Withdraw_paypal.this, "Please enter vaild withdraw amount");

                    }
                } else {
                    Utilities.showToast(Withdraw_paypal.this, "Please enter  withdraw amount");

                }


                break;
            case R.id.rl_add_new_ach_account:

                if (etAmount.getText().toString().trim().length() >= 1) {
                    String amountTxtuse = etAmount.getText().toString().trim().replace("$", "");
                    Integer reqAmountuse = Integer.parseInt(amountTxtuse);

                    if (reqAmountuse >= 1) {
                        try {
                            String amountTxt = etAmount.getText().toString().trim().replace("$", "");
                            String withdrawAmount = withdrawCash;

                            if (!amountTxt.equals("") && !withdrawAmount.equals("")) {
                                Integer reqAmount = Integer.parseInt(amountTxt);
                                Integer availAmount = Integer.parseInt(withdrawAmount);
                                //if(reqAmount<=availAmount) {
                                if (availAmount - reqAmount >= 0) {
                                    Intent withdrawIntent = new Intent(Withdraw_paypal.this, Withdraw_ACH.class);
                                    withdrawIntent.putExtra("reqAmount", String.valueOf(reqAmount));
                                    withdrawIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(withdrawIntent);

                                } else {
                                    Utilities.showToast(Withdraw_paypal.this, "Your withdrawal amount should be less than or equals to " + "$" + withdrawCash);
                                }
                            } else {
                                Utilities.showToast(Withdraw_paypal.this, "Something went wrong please try again after sometime");
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Utilities.showToast(Withdraw_paypal.this, "Please enter vaild withdraw amount");

                    }
                } else {
                    Utilities.showToast(Withdraw_paypal.this, "Please enter  withdraw amount");

                }


                break;
            case R.id.llPaypal:

                if (withdrawCash.length() >= 1) {
                    String amountTxt1 = etAmount.getText().toString().trim().replace("$", "");
                    method = "";
                    if (!amountTxt1.equals("") && Integer.parseInt(amountTxt1) >= 1) {
                        //      setupPayPal();

                        method = "paypal";
                        llPaypal.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.card_sel_highlight, null));
                        pptxt.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
//
//                    if (llPaypal.isSelected()) {
//                        llPaypal.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.card_sel_highlight, null));
//                        //         holder.tvCardName.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
//                    } else {
//                        llPaypal.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.card_sel_normal, null));
//                        //       holder.tvCardName.setTextColor(ResourcesCompat.getColor(getResources(), R.color.header, null));
//                    }

                    } else {
                        Utilities.showToast(Withdraw_paypal.this, "Please enter minimum amount , it should be at least of " + withdrawCash);
                    }


//                if (!amountTxt1.equals("") && Integer.parseInt(amountTxt1) >= 5) {
//                    setupPayPal();
//                } else {
//                    Utilities.showToast(Withdraw_paypal.this, "Please enter minimum amount , it should be at least of $5");
//                }
                } else {
                    Utilities.showToast(Withdraw_paypal.this, "Please enter vaild withdraw amount");

                }

                break;
            default:
                break;
        }
    }

    private void WithdrawViaACH(JSONObject jsonObj_card) {


        AndroidNetworking.post(APIs.WITHDRAWVIAACH)
                .setPriority(Priority.HIGH)
                .addJSONObjectBody(jsonObj_card) // posting json
                .addHeaders("Content-Type", "application/json")
                .addHeaders("sessionToken", sessionToken)
                .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("JSON ACH", response.toString());

                        try {
                            WithdrawResponse withdrawResponse = new Gson().fromJson(response.toString(), WithdrawResponse.class);
                            if (withdrawResponse != null) {

                                Utilities.showAlertBox(Withdraw_paypal.this, getResources().getString(R.string.process_request), getResources().getString(R.string.withdraw_over_msg));


                                //Update Values to SqliteDb
                                ContentValues cv = new ContentValues();
                                cv.put(DB_Constants.USER_TOTALCASHBALANCE, withdrawResponse.getTotalCashBalance());
                                cv.put(DB_Constants.USER_CASHBALANCE, (withdrawResponse.getCashBalance()));
                                cv.put(DB_Constants.USER_PROMOBALANCE, withdrawResponse.getPromoBalance());
                                cv.put(DB_Constants.USER_TOKENBALANCE, withdrawResponse.getTokenBalance());
                                myDbHelper.updateUser(cv);

                            }

                            Log.e("**WITHDRAW::Response::", response.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            llwithdrawAmount.setVisibility(View.GONE);
//                            llwithdrawAmount.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        llwithdrawAmount.setVisibility(View.GONE);
//                        llwithdrawAmount.setVisibility(View.VISIBLE);
                        try {

                            JSONObject ej = new JSONObject(anError.getErrorBody());
                            Utilities.showToast(Withdraw_paypal.this, ej.getString("message"));

                        } catch (Exception e) {

                        }
                        /*Till here */
                        Log.e("ERROR####", "ERROR-------" + anError.getErrorBody());

                        if (anError.getErrorCode() != 0) {
                            Log.e("", "onError errorCode : " + anError.getErrorCode());
                            Log.e("", "onError errorBody : " + anError.getErrorBody());
                            Log.e("", "onError errorDetail : " + anError.getErrorDetail());

                        } else {
                            Log.e("", "onError errorDetail  0: " + anError.getErrorDetail());
                        }
                    }
                });

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        int nameLength = etFullName.getText().toString().trim().length();
        int streetLength = etStreet.getText().toString().trim().length();
        int cityLength = etCity.getText().toString().trim().length();
        int zipLength = etZipCode.getText().toString().trim().length();

//        if (nameLength > 0 && streetLength > 0 && cityLength > 0 && zipLength > 4) {
        if (!etAmount.getText().toString().trim().isEmpty()) {
            llwithdrawAmount.setEnabled(true);
            llwithdrawAmount.setVisibility(View.GONE);
//            llwithdrawAmount.setVisibility(View.VISIBLE);
            llwithdrawAmount.setBackgroundResource(R.drawable.btn_bg_green);
            tvWithdrawFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        } else {
            llwithdrawAmount.setEnabled(false);
            llwithdrawAmount.setVisibility(View.GONE);
            llwithdrawAmount.setBackgroundResource(R.drawable.btn_bg_grey);
            tvWithdrawFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.add_fund_clr, null));
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void setupPayPal() {
        PayPalPayment thingToBuy;
        String purchaseType = "";
        purchaseType = "Add Funds";

//        thingToBuy = new PayPalPayment(new BigDecimal(String.valueOf(1)), "USD",
        thingToBuy = new PayPalPayment(new BigDecimal(etAmount.getText().toString().replace("$", "")), "USD",
                purchaseType, PayPalPayment.PAYMENT_INTENT_SALE);
        thingToBuy.enablePayPalShippingAddressesRetrieval(true);
        Intent intent = new Intent(Withdraw_paypal.this,
                PaymentActivity.class);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {
                //     getAllCardsList();
//                if (requestCode == ADD_NEW_CARD || requestCode == EDIT_CARD) {
//                    getAllCardsList();
//                }
            }


            if (requestCode == REQUEST_CODE_PAYMENT) {
                if (resultCode == Activity.RESULT_OK) {

                    PaymentConfirmation confirm = data
                            .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                    if (confirm != null) {
                        try {
                            System.out.println(confirm.toJSONObject().toString(4));
                            System.out.println(confirm.getPayment().toJSONObject()
                                    .toString(4));

                            paypalResponse = new Gson().fromJson(confirm.toJSONObject().toString(4), PayPalServerResponse.class);

                            if (paypalResponse != null) {
                               /* PayPal_Invoice_id =paypalResponse.getResponse().getId();
                                paypal_state=paypalResponse.getResponse().getState();
                                Toast.makeText(AddFunds.this,"INVOICE::"+PayPal_Invoice_id,Toast.LENGTH_LONG).show();
                                sendNonceToServer(PayPal_Invoice_id);*/
                                //PayPal_Invoice_id =paypalResponse.getResponse().getId();
                                //paypal_state=paypalResponse.getResponse().getState();
                                //  Toast.makeText(AddFunds.this, "INVOICE::" + paypalResponse.getResponse().getId(), Toast.LENGTH_LONG).show();
                                //sendNonceToServer(paypalResponse.getResponse().getId());
                            }
                        } catch (JSONException e) {
                            Toast.makeText(Withdraw_paypal.this, "ERROR::" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    System.out.println("The user canceled.");
                    Toast.makeText(Withdraw_paypal.this, "The user canceled.", Toast.LENGTH_LONG).show();
                } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                    System.out
                            .println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
                    Toast.makeText(Withdraw_paypal.this, "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.", Toast.LENGTH_LONG).show();
                }
            }

        } catch (Exception ex) {
            Toast.makeText(Withdraw_paypal.this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void addBankAccount() {
//        Intent addBankAccountIntent = new Intent(Withdraw_paypal.this, AddNewCard.class);
//        startActivity(addBankAccountIntent);
    }

    private void buildRequest() {
        try {
            String amountTxt = etAmount.getText().toString().trim().replace("$", "");
            String withdrawAmount = withdrawCash;

            if (!amountTxt.equals("") && !withdrawAmount.equals("")) {
                Integer reqAmount = Integer.parseInt(amountTxt);
                Integer availAmount = Integer.parseInt(withdrawAmount);
                //if(reqAmount<=availAmount) {
                if (availAmount - reqAmount >= 0) {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("amount", reqAmount);
//                    jsonObject.put("fullName", etFullName.getText().toString().trim());
//                    jsonObject.put("billingAddress", etStreet.getText().toString().trim());
//                    jsonObject.put("city", etCity.getText().toString().trim());
//                    jsonObject.put("state", spnrState.getSelectedItem().toString());
//                    if (spnrState.getSelectedItemPosition() <= 50)
//                        jsonObject.put("country", "USA");
//                    else
//                        jsonObject.put("country", "CANADA");
//                    jsonObject.put("zipCode", etZipCode.getText().toString().trim());

                    SendDataToServer(jsonObject, reqAmount);
                } else {
                    Utilities.showToast(Withdraw_paypal.this, "Your withdrawal amount should be less than or equals to " + "$" + Dashboard.CASH_BAL);
                }
            } else {
                Utilities.showToast(Withdraw_paypal.this, "Something went wrong please try again after sometime");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SendDataToServer(final JSONObject withdrawObject, final int requestAmount) {
        Log.e("WITHDRAW::REQESTOBJ", withdrawObject.toString());
        AndroidNetworking.post(APIs.WITHDRAW_FUNDS)
                .addJSONObjectBody(withdrawObject) // posting json
                .addHeaders("Content-Type", "application/json")
                .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer "+ Dashboard.NEWTOKEN)

                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            WithdrawResponse withdrawResponse = new Gson().fromJson(response.toString(), WithdrawResponse.class);
                            if (withdrawResponse != null) {
                                if (requestAmount < 100) {
                                    Utilities.showAlertBox(Withdraw_paypal.this, getResources().getString(R.string.process_request), getResources().getString(R.string.withdraw_under_msg));
                                } else {
                                    Utilities.showAlertBox(Withdraw_paypal.this, getResources().getString(R.string.process_request), getResources().getString(R.string.withdraw_over_msg));

                                }

                                //Update Values to SqliteDb
                                ContentValues cv = new ContentValues();
                                cv.put(DB_Constants.USER_TOTALCASHBALANCE, withdrawResponse.getTotalCashBalance());
                                cv.put(DB_Constants.USER_CASHBALANCE, (withdrawResponse.getCashBalance()));
                                cv.put(DB_Constants.USER_PROMOBALANCE, withdrawResponse.getPromoBalance());
                                cv.put(DB_Constants.USER_TOKENBALANCE, withdrawResponse.getTokenBalance());
                                myDbHelper.updateUser(cv);

                            }

                            Log.e("**WITHDRAW::Response::", response.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        Utilities.showToast(Withdraw_paypal.this, anError.getErrorBody());

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

    //get user saved cards list
    private void getAllCardsList() {

        AndroidNetworking.get(APIs.CARDS_LIST_URL)
                .setPriority(Priority.HIGH)
                .addHeaders("sessionToken", sessionToken)
                .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.e("getAllCardsList :: ", response.toString());

                        cardsResponse.clear();

                        // do anything with response
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject jb = response.getJSONObject(i);

                                CardDataPojo cardData = new CardDataPojo();
                                cardData.set_id(jb.getString("_id"));
                                cardData.setAccount(jb.getString("account"));
                                cardData.setToken(jb.getString("token"));
                                cardData.setAuthorizeNetCustomerProfileId(jb.getString("authorizeNetCustomerProfileId"));
                                cardData.setAuthorizeNetCustomerPaymentProfileId(jb.getString("authorizeNetCustomerPaymentProfileId"));
                                cardData.setLastFourDigits(jb.getString("lastFourDigits"));
                                cardData.setExpiry(jb.getString("expiry"));
                                cardData.setCardType(jb.getString("cardType"));
                                cardData.setCreatedAt(jb.getString("createdAt"));
                                cardsResponse.add(cardData);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (cardsResponse.size() > 0) {
                            //    rvCardsList.setVisibility(View.VISIBLE);
                            if (recycleAdapter != null)
                                recycleAdapter.notifyDataSetChanged();
                        } else {
                            //      rvCardsList.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Utilities.showToast(Withdraw_paypal.this, error.getErrorBody());
                    }
                });

    }


    // Get ACH cards

    //get user saved cards list
    private void getAllACHCardsList() {

        AndroidNetworking.get(APIs.GETACHBANKCARDS)
                .setPriority(Priority.HIGH)
                .addHeaders("sessionToken", sessionToken)
                .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.e("getAllCardsList :: ", response.toString());

                        ACH_cardsResponse.clear();

                        // do anything with response
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject jb = response.getJSONObject(i);

                                ACHCardDataPojo cardData = new ACHCardDataPojo();
                                cardData.set_id(jb.getString("_id"));
                                cardData.setAccountId(jb.getString("accountId"));
                                cardData.setNameOnAccount(jb.getString("nameOnAccount"));
                                cardData.setAccountType(jb.getString("accountType"));
                                cardData.setAccountNumber(jb.getString("accountNumber"));
                                cardData.setRoutingNumber(jb.getString("routingNumber"));
                                cardData.setCreatedDate(jb.getString("createdDate"));

                                ACH_cardsResponse.add(cardData);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (ACH_cardsResponse.size() > 0) {
                            rvACHCardsList.setVisibility(View.VISIBLE);
                            if (ACHrecycleAdapter != null)
                                ACHrecycleAdapter.notifyDataSetChanged();
                        } else {
                            rvACHCardsList.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Utilities.showToast(Withdraw_paypal.this, error.getErrorBody());
                    }
                });

    }
    // ACH CARDS Close


    // ACH ADAPTER
    public class ACHCardsListAdapter extends RecyclerView.Adapter<ACHCardsListAdapter.ViewHolder> {


        private final List<ACHCardDataPojo> mValues;
        private Context context;
        private int selPos = -1;

        public ACHCardsListAdapter(List<ACHCardDataPojo> items, Context context) {
            mValues = items;
            this.context = context;
        }

        @NonNull
        @Override
        public ACHCardsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.added_card_view, parent, false);

            return new ViewHolder(itemView);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final ACHCardsListAdapter.ViewHolder holder, final int position) {

            String accountNumber = ACH_cardsResponse.get(position).getAccountNumber();
            String cardType = ACH_cardsResponse.get(position).getAccountType();

            holder.ivCard.setImageResource(R.drawable.ic_cc_generic);
            holder.tvCardName.setText(accountNumber + " " + cardType);


            holder.ivOverFlow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selCardId = ACH_cardsResponse.get(position).get_id();
                    holder.llCard.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.card_sel_normal, null));
                    holder.tvCardName.setTextColor(ResourcesCompat.getColor(getResources(), R.color.header, null));

                    notifyDataSetChanged();

                    Context wrapper = new ContextThemeWrapper(Withdraw_paypal.this, R.style.popupMenuStyle1);
                    PopupMenu popup = new PopupMenu(wrapper, view);
                    popup.setOnMenuItemClickListener(Withdraw_paypal.this);
                    popup.inflate(R.menu.cc_card_menu);
                    popup.show();
                }
            });

            if (etAmount.getText().toString().trim().length() > 0) {
                if (selPos == position) {
                    holder.llCard.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.card_sel_highlight, null));
                    holder.tvCardName.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                } else {
                    holder.llCard.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.card_sel_normal, null));
                    holder.tvCardName.setTextColor(ResourcesCompat.getColor(getResources(), R.color.header, null));
                }
            }

            holder.llCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selCardId = ACH_cardsResponse.get(position).get_id();
                    selPos = position;
                    notifyDataSetChanged();

                    if (etAmount.getText().toString().trim().length() > 0) {
                        llwithdrawAmount.setEnabled(true);
                        llwithdrawAmount.setVisibility(View.GONE);
//                        llwithdrawAmount.setVisibility(View.VISIBLE);

//                        if (customAmount > 0) {
//                            llwithdrawAmount.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_bg_red_ripple, null));
//                        } else {
//                            llwithdrawAmount.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_bg_green, null));
//                        }
                        tvWithdrawFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                    } else {
                        selCardId = "";
                        Utilities.showToast(Withdraw_paypal.this, "Please Enter withdraw amount");
                        llwithdrawAmount.setVisibility(View.GONE);
                        llwithdrawAmount.setEnabled(false);
                        llwithdrawAmount.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_gray_bg, null));
                        tvWithdrawFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.add_fund_clr, null));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return this.mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView ivCard, ivOverFlow;
            TextView tvCardName, tvExpiry;
            LinearLayout llCard;

            public ViewHolder(View view) {
                super(view);
                llCard = view.findViewById(R.id.llCard);
                ivCard = view.findViewById(R.id.ivCard);
                tvCardName = view.findViewById(R.id.tvCardName);
                tvExpiry = view.findViewById(R.id.tvExpiry);
                ivOverFlow = view.findViewById(R.id.ivOverFlow);

            }
        }
    }


    // DELETE
    public boolean onMenuItemClick(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.delete_card:
                try {
                    // do your code
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", selCardId);
                    DeleteCreditCard(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;


            default:
                return false;
        }

    }

    private void DeleteCreditCard(JSONObject jsonObject) {
        Log.e("DeleteCard::REQUEST::", jsonObject.toString());
        AndroidNetworking.delete(APIs.DELETEACHACCOUNTS)
                .setPriority(Priority.HIGH)
                .addJSONObjectBody(jsonObject) // posting json
                .addHeaders("Content-Type", "application/json")
                .addHeaders("sessionToken", sessionToken)
                .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            for (int i = 0; i < ACH_cardsResponse.size(); i++) {
                                if (ACH_cardsResponse.get(i).get_id().equalsIgnoreCase(selCardId)) {
                                    if (ACH_cardsResponse.size() > i) {
                                        ACH_cardsResponse.remove(i);
                                        break;
                                    }
                                }
                            }

                            if (ACHrecycleAdapter != null) {
                                ACHrecycleAdapter.notifyDataSetChanged();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        /*Remove this while pushing it to live*/
                        for (int i = 0; i < ACH_cardsResponse.size(); i++) {
                            if (ACH_cardsResponse.get(i).get_id().equalsIgnoreCase(selCardId)) {
                                if (ACH_cardsResponse.size() > i) {
                                    ACH_cardsResponse.remove(i);
                                    break;
                                }
                            }
                        }

                        if (ACHrecycleAdapter != null) {
                            ACHrecycleAdapter.notifyDataSetChanged();
                        }
                        /*Till here */
                        Log.e("ERROR####", "ERROR-------" + anError.getErrorBody());

                        if (anError.getErrorCode() != 0) {
                            Log.e("", "onError errorCode : " + anError.getErrorCode());
                            Log.e("", "onError errorBody : " + anError.getErrorBody());
                            Log.e("", "onError errorDetail : " + anError.getErrorDetail());

                        } else {
                            Log.e("", "onError errorDetail  0: " + anError.getErrorDetail());
                        }
                    }
                });
    }
}

//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//import com.sport.playsqorr.R;
//
//public class Withdraw_paypal extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_withdraw_paypal);
//    }
//}

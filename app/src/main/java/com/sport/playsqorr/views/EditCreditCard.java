package com.sport.playsqorr.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.sport.playsqorr.R;
import com.sport.playsqorr.model.EditCardResponse;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.Utilities;
import org.json.JSONObject;

public class EditCreditCard extends AppCompatActivity implements View.OnClickListener ,TextWatcher{

    private EditText etCardNumber,etExpiry,etCVV,etFullName,etStreet,etCity,etZipCode;
    private TextView tvSubmitCard;
    private Spinner spnrState;
    private ImageView ivCardType;
    private LinearLayout llSubmit;
    private String sessionToken,NEWTOKEN,cardType="",CARD_ID;
    EditCardResponse editCardResponse;
    String [] statesArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(),R.color.white,null));
        }
        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_TOKEN", MODE_PRIVATE);
        sessionToken= sharedPreferences.getString("token", "");
        NEWTOKEN= sharedPreferences.getString("updatetoken", "");

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            if(bundle.containsKey("CARD_ID")){
                CARD_ID=bundle.getString("CARD_ID");
            }
            if (bundle.containsKey("CARD_TYPE")) {
                cardType = bundle.getString("CARD_TYPE");
            }
        }

        init();

        getCardDataFromServer();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        TextView toolbar_title_x = findViewById(R.id.toolbar_title_x);
        etCardNumber=findViewById(R.id.etCardNumber);
        etExpiry=findViewById(R.id.etExpiry);
        etCVV=findViewById(R.id.etCVV);
        etFullName=findViewById(R.id.etFullName);
        etStreet=findViewById(R.id.etStreet);
        etCity=findViewById(R.id.etCity);
        spnrState=findViewById(R.id.spnrState);
        etZipCode=findViewById(R.id.etZipCode);
        ivCardType = findViewById(R.id.ivCardType);

        tvSubmitCard=findViewById(R.id.tvSubmitCard);
        llSubmit=findViewById(R.id.llSubmit);

        toolbar_title_x.setText("Edit card");

        //Add listener(s)
        toolbar_title_x.setOnClickListener(this);
        etCardNumber.addTextChangedListener(this);
        llSubmit.setOnClickListener(this);
        etCVV.addTextChangedListener(this);
        etFullName.addTextChangedListener(this);
        etStreet.addTextChangedListener(this);
        etCity.addTextChangedListener(this);
        etZipCode.addTextChangedListener(this);

        statesArray=getResources().getStringArray(R.array.states_array);
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, statesArray);
        spnrState.setAdapter(courseAdapter);

        etExpiry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String current = s.toString();
                if (current.length() == 2 && start == 1) {
                    etExpiry.setText(current + "/");
                    etExpiry.setSelection(current.length() + 1);
                } else if (current.length() == 2 && before == 1) {
                    current = current.substring(0, 1);
                    etExpiry.setText(current);
                    etExpiry.setSelection(current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (etExpiry.getText().length() > 4) {
                    llSubmit.setEnabled(true);
                    llSubmit.setBackgroundResource(R.drawable.btn_bg_green);
                    tvSubmitCard.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                } else {
                    llSubmit.setEnabled(false);
                    llSubmit.setBackgroundResource(R.drawable.btn_bg_grey);
                    tvSubmitCard.setTextColor(ResourcesCompat.getColor(getResources(), R.color.add_fund_clr, null));
                }
            }
        });

    }


    private void getCardDataFromServer() {
        AndroidNetworking.get(APIs.GET_CARD_DATA+"/"+CARD_ID)
                .setPriority(Priority.HIGH)
                .addHeaders("sessionToken",sessionToken)
                .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        editCardResponse=new Gson().fromJson(response.toString(), EditCardResponse.class);
                        setPageData();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Utilities.showToast(EditCreditCard.this,anError.getErrorBody());
                    }
                });
    }


    @SuppressLint("SetTextI18n")
    private void setPageData(){
        if(editCardResponse!=null){
            etCardNumber.setText("xxxx xxxx xxxx "+editCardResponse.getCardNumber());
            etCardNumber.setEnabled(false);
            etCVV.setText("123");
            etCVV.setEnabled(false);
            etExpiry.setText(editCardResponse.getExpiryMonth()+"/"+editCardResponse.getExpiryYear());
            etFullName.setText(editCardResponse.getFullName());
            etStreet.setText(editCardResponse.getAddress());
            etCity.setText(editCardResponse.getCity());
            etZipCode.setText(editCardResponse.getZipCode());
            String state= editCardResponse.getState();
            int selPos=-1;
            for(int i=0;i<statesArray.length;i++){
                if(state.contains(statesArray[i])){
                    selPos=i;
                    break;
                }
            }
            if(selPos!=-1){
                spnrState.setSelection(selPos);
            }else {
                spnrState.setSelection(0);
            }
            llSubmit.setEnabled(true);
            llSubmit.setBackgroundResource(R.drawable.btn_bg_green);
            tvSubmitCard.setTextColor(ResourcesCompat.getColor(getResources(),R.color.white,null));

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_title_x:
                finish();
                break;
            case R.id.llSubmit:
                buildRequest();
                break;
            default:
                break;
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable editable) {
        int card_length= etCardNumber.getText().toString().trim().length();
        int expLength =etExpiry.getText().toString().trim().length();
        int cvvLength = etCVV.getText().toString().trim().length();
        int nameLength=etFullName.getText().toString().trim().length();
        int streetLength=etStreet.getText().toString().trim().length();
        int cityLength=etCity.getText().toString().trim().length();
        int zipLength=etZipCode.getText().toString().trim().length();

        if(!cardType.equals("")&&card_length>14&&expLength>4&&cvvLength==3
                &&nameLength>0&&streetLength>0&&cityLength>0&&zipLength>4){
            llSubmit.setEnabled(true);
            llSubmit.setBackgroundResource(R.drawable.btn_bg_green);
            tvSubmitCard.setTextColor(ResourcesCompat.getColor(getResources(),R.color.white,null));
        }else{
            llSubmit.setEnabled(false);
            llSubmit.setBackgroundResource(R.drawable.btn_bg_grey);
            tvSubmitCard.setTextColor(ResourcesCompat.getColor(getResources(),R.color.add_fund_clr,null));
        }
    }


    private void buildRequest(){
        try{
            JSONObject jsonObject = new JSONObject();
            String [] expiry = etExpiry.getText().toString().trim().split("/");

           // jsonObject.put("cardType",cardType);
          //  jsonObject.put("cardNumber",etCardNumber.getText().toString().trim().replaceAll("\\s+",""));
           // jsonObject.put("securityCde",etCVV.getText().toString().trim());
            jsonObject.put("cardId",CARD_ID);
            jsonObject.put("expMonth",expiry[0]);
            jsonObject.put( "expYear",expiry[1]);
            jsonObject.put(   "fullName",etFullName.getText().toString().trim());
            jsonObject.put( "billingAddress",etStreet.getText().toString().trim());
            jsonObject.put("city",etCity.getText().toString().trim());
            jsonObject.put("state",spnrState.getSelectedItem().toString());
            if(spnrState.getSelectedItemPosition()<=50)
                jsonObject.put("country", "USA");
            else
                jsonObject.put("country", "CANADA");
            jsonObject.put("zipCode",etZipCode.getText().toString().trim());

            SendDataToAddCard(jsonObject);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void SendDataToAddCard(JSONObject addCardObject){
        Log.e("EDITCARD::REQESTOBJ",addCardObject.toString());
        AndroidNetworking.post(APIs.EDIT_CARD_URL)
                .addJSONObjectBody(addCardObject) // posting json
                .addHeaders("Content-Type", "application/json")
                .addHeaders("sessionToken",sessionToken)
                .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Intent intent= new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                            Log.e("EDITCARD::RESPONSE",response.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("ERROR::","------"+anError.getErrorBody());
                        Intent intent= new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
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
}

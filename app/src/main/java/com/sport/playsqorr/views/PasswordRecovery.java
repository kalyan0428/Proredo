package com.sport.playsqorr.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.sport.playsqorr.R;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;

public class PasswordRecovery extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private EditText et_email_address;
    private TextView tv_submit,tv_error_msg;
    private View email_view;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(),R.color.white,null));
        }
        init();
    }
    private void init(){
        TextView toolbar_title_x = findViewById(R.id.toolbar_title_x);
        et_email_address=findViewById(R.id.et_email_address);
        tv_submit=findViewById(R.id.tv_submit);
        email_view = findViewById(R.id.email_view);
        tv_error_msg = findViewById(R.id.tv_error_msg);

        toolbar_title_x.setText(getString(R.string.pwd_recovery));

        //Add listener(s)
        toolbar_title_x.setOnClickListener(this);
        et_email_address.addTextChangedListener(this);
        tv_submit.setEnabled(false);
        tv_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_title_x:
                finish();
                break;
            case R.id.tv_submit:
//                String email_address = et_email_address.getText().toString().trim();
//                if (email_address.matches(emailPattern)) {
                    if (Utilities.isNetworkAvailable(getApplicationContext())) {
                        submitData();
                    }else{
                        Utilities.showNoInternetAlert(PasswordRecovery.this);
                    }
//                }else{
//                    et_email_address.requestFocus();
//                    tv_error_msg.setText("Invalid Email Address");
//                    tv_error_msg.setVisibility(View.VISIBLE);
//                    email_view.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.colorAccent,null));
//                }
                break;
             default:break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        tv_error_msg.setVisibility(View.GONE);
        String email_address = et_email_address.getText().toString().trim();
        if (email_address.matches(emailPattern)) {
            //int email_length=et_email_address.getText().toString().trim().length();
            //if(email_length>0){
                tv_submit.setEnabled(true);
                tv_submit.setTextColor(ResourcesCompat.getColor(getResources(),R.color.white,null));
                tv_submit.setBackgroundResource(R.drawable.btn_bg_red);
                email_view.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.medium_gray,null));
            }else{
                tv_submit.setEnabled(false);
                email_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sep_view_color, null));
                tv_submit.setTextColor(ResourcesCompat.getColor(getResources(),R.color.btn_dis_text,null));
                tv_submit.setBackgroundResource(R.drawable.login_bg_disable);
            }
    }


    //Service call to send data to server
    private void submitData(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", et_email_address.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(APIs.FORGOT_PASSWORD_URL)
                .addJSONObjectBody(jsonObject) // posting json
                .addHeaders("Content-Type", "application/json")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if(response.code()==200) {
                            showAlertBox(PasswordRecovery.this,"Email sent successfully",getResources().getString(R.string.rec_pwd));
                        }else{
                            Utilities.showToast(getApplicationContext(), ""+response.message());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Utilities.showToast(getApplicationContext(),""+anError.getErrorBody());
                    }
                });
    }

    private void showAlertBox(Context context, String title, String message) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.email_sent_popup);
        Window window = dialog.getWindow();
        if(window!=null) {
            dialog.getWindow().setLayout(((Utilities.getWidth(context) / 100) * 94), LinearLayout.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.CENTER);
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.show();
        }
        dialog.setCancelable(false);

        TextView alert_title = dialog.findViewById(R.id.alert_title);
        TextView alert_msg = dialog.findViewById(R.id.alert_msg);

        alert_title.setText(title);
        alert_msg.setText(message);
        TextView alert_ok = dialog.findViewById(R.id.alert_ok);
        // if decline button is clicked, close the custom dialog
        alert_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });

    }
}

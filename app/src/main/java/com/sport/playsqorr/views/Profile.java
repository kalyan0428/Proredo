package com.sport.playsqorr.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.login.LoginManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.sport.playsqorr.BuildConfig;
import com.sport.playsqorr.crop.CropImage;
import com.sport.playsqorr.crop.CropImageView;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.Utilities;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.sport.playsqorr.R;
import com.sport.playsqorr.database.DB_Constants;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.ui.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.sport.playsqorr.utilities.UtilitiesAna.trackEvent;


public class Profile extends AppCompatActivity implements View.OnClickListener {

    private TextView login_txt, edit_profile, jn_home, tv_cash_toker, tvName, tvProfileScore, toolbar_title_x, tvTokens, age_status, tv_agebtn;
    private LinearLayout ll_add_funds, ull, llTokens;
    private FrameLayout fl;
    private RelativeLayout viewTransactions;
    LinearLayout how_ll, faq_ll, cust_ll, pp_ll, terms_ll, about_ll, linearViewrefer, linearViewraiseaticket, linearViewnotification, linearLeaderboard, linearNFLLeaderboard;
    ImageView cash_add;
    private DataBaseHelper mydb;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    private String ROLE, CASH_BAL, AMOUNT_CASH, AMOUNT_TOKEN, AVATAR, MYWiNS, ACCNAME, USEREMAIL;
    ImageView profileImage;
    MixpanelAPI mMixpanel;
    ProgressBar pr_images;


    public static String PID = "";
    public static String PNAME = "";
    public static String PIMAGE = "";
    public static String PEMAIL = "";

    String age_verfiy_status;

    TextView tvVer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        }
        mydb = new DataBaseHelper(this);
        sqLiteDatabase = mydb.getReadableDatabase();
        mMixpanel = MixpanelAPI.getInstance(Profile.this, getString(R.string.test_MIX_PANEL_TOKEN));
        mMixpanel.track("Page Profile", null);
        trackEvent("Page Profile",null);
        init();
        setUserAvatar();
        updateUI();
//        Log.d("AVATAR",AVATAR);
    }


    private void init() {
        toolbar_title_x = findViewById(R.id.toolbar_title_x);
        tvVer = findViewById(R.id.tvVer);
        login_txt = findViewById(R.id.tvSignOut);
        tv_agebtn = findViewById(R.id.tv_agebtn);
        age_status = findViewById(R.id.age_status);
        pr_images = findViewById(R.id.pr_images);
        edit_profile = findViewById(R.id.tVEditProfile);
        fl = findViewById(R.id.notlogin);
        ull = findViewById(R.id.userlogin);
        ll_add_funds = findViewById(R.id.ll_add_funds);
        viewTransactions = findViewById(R.id.viewTransactions);
        how_ll = findViewById(R.id.linearViewHowToPaly);
        faq_ll = findViewById(R.id.linearViewFAQs);
        cust_ll = findViewById(R.id.linearViewCustomerSupport);
        pp_ll = findViewById(R.id.linearViewPrivacyPolicy);
        terms_ll = findViewById(R.id.linearViewTermsOfService);
        about_ll = findViewById(R.id.linearViewAboutVetnos);
        cash_add = findViewById(R.id.imgProfileAdd);
        jn_home = findViewById(R.id.jn);
        tv_cash_toker = findViewById(R.id.tv_cash_toker);
        tvName = findViewById(R.id.tvName);
        tvProfileScore = findViewById(R.id.tvProfileScore);
        llTokens = findViewById(R.id.llTokens);
        tvTokens = findViewById(R.id.tvTokens);
        profileImage = findViewById(R.id.profileImage);
        linearViewrefer = findViewById(R.id.linearViewrefer);
        linearViewraiseaticket = findViewById(R.id.linearViewraiseaticket);
        linearLeaderboard = findViewById(R.id.linearViewLeadrer);
        linearNFLLeaderboard = findViewById(R.id.linearViewNFLLeadrer);
        linearViewnotification = findViewById(R.id.linearViewnotification);


        linearLeaderboard.setOnClickListener(this);
        linearNFLLeaderboard.setOnClickListener(this);
        linearViewrefer.setOnClickListener(this);
        tv_agebtn.setOnClickListener(this);
        linearViewraiseaticket.setOnClickListener(this);

        linearViewnotification.setOnClickListener(this);
        //Add listener(s)
        toolbar_title_x.setOnClickListener(this);
        login_txt.setOnClickListener(this);
        edit_profile.setOnClickListener(this);
        ll_add_funds.setOnClickListener(this);
        viewTransactions.setOnClickListener(this);
        how_ll.setOnClickListener(this);
        faq_ll.setOnClickListener(this);
        cust_ll.setOnClickListener(this);
        pp_ll.setOnClickListener(this);
        terms_ll.setOnClickListener(this);
        about_ll.setOnClickListener(this);
        jn_home.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        userprofdileupdate();
        updateUI();

        String VERSION_NAME = BuildConfig.VERSION_NAME;

        tvVer.setText("Version: "+VERSION_NAME);

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

        setUserAvatar();

        if (ROLE != null && ROLE.equalsIgnoreCase("cash")) {

            fl.setVisibility(View.GONE);
            ull.setVisibility(View.VISIBLE);
            cust_ll.setVisibility(View.VISIBLE);
            linearViewrefer.setVisibility(View.VISIBLE);
      //      linearViewraiseaticket.setVisibility(View.VISIBLE);
            linearLeaderboard.setVisibility(View.VISIBLE);
            linearNFLLeaderboard.setVisibility(View.GONE);
            cash_add.setVisibility(View.VISIBLE);
            ll_add_funds.setVisibility(View.VISIBLE);
            llTokens.setVisibility(View.GONE);

            login_txt.setText("Sign out");
            toolbar_title_x.setText(getString(R.string._account));

            tv_cash_toker.setText("$" + AMOUNT_CASH);
//            tvName.setText(ACCNAME);
            if (ACCNAME != null && !ACCNAME.equals("") && !ACCNAME.equals(" ")) {
                tvName.setText(ACCNAME);
            } else {
                tvName.setText(USEREMAIL);
            }
            tvProfileScore.setText(MYWiNS);

        } else if (ROLE != null && ROLE.equalsIgnoreCase("tokens")) {

            fl.setVisibility(View.GONE);
            ull.setVisibility(View.VISIBLE);
            cust_ll.setVisibility(View.VISIBLE);
            linearViewrefer.setVisibility(View.VISIBLE);
        //    linearViewraiseaticket.setVisibility(View.VISIBLE);
            linearLeaderboard.setVisibility(View.VISIBLE);
            linearNFLLeaderboard.setVisibility(View.GONE);
            cash_add.setVisibility(View.GONE);
            ll_add_funds.setVisibility(View.GONE);
            llTokens.setVisibility(View.VISIBLE);

            login_txt.setText("Sign out");
            toolbar_title_x.setText(getString(R.string._account));
//            tvTokens.setText(AMOUNT_TOKEN);
            if (ACCNAME != null && !ACCNAME.equals("") && !ACCNAME.equals(" ")) {
                tvName.setText(ACCNAME);
            } else {
                tvName.setText(USEREMAIL);
            }
            tvProfileScore.setText(MYWiNS);

        } else {
            login_txt.setText("Log in");
            fl.setVisibility(View.VISIBLE);
            ull.setVisibility(View.GONE);
            cust_ll.setVisibility(View.GONE);
            linearViewrefer.setVisibility(View.GONE);
            linearViewraiseaticket.setVisibility(View.GONE);
            linearLeaderboard.setVisibility(View.GONE);
            linearNFLLeaderboard.setVisibility(View.GONE);
            toolbar_title_x.setText("Settings");
        }
    }


    public void resetDatabase() {
        SQLiteDatabase database = mydb.getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS " + DB_Constants.TABLE_USERINFO);
        database.close();
    }

    private void setUserAvatar() {
      /*  if(AVATAR.equalsIgnoreCase("null")||AVATAR.equalsIgnoreCase(""))
        {
            pr_images.setVisibility(View.GONE);
        }else
            {

            }*/
        pr_images.setVisibility(View.VISIBLE);

        Picasso.with(Profile.this)
                .load(AVATAR)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .resize(512, 512)
                .error(R.drawable.profile_placeholder)
                .noFade()
                .into(profileImage, new Callback() {
                    @Override
                    public void onSuccess() {

                        pr_images.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        // TODO Auto-generated method stub
                        pr_images.setVisibility(View.GONE);
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearViewnotification:
                Intent intentno = new Intent(Profile.this, Notifications.class);
                startActivity(intentno);
                break;
            case R.id.linearViewrefer:
                Intent intent = new Intent(Profile.this, Referfriend.class);
                startActivity(intent);
                break;
            case R.id.linearViewraiseaticket:
                Intent intent_raise = new Intent(Profile.this, RaiseaTicketScreen.class);
                startActivity(intent_raise);
                break;
            case R.id.linearViewLeadrer:
                Intent intent_leader = new Intent(Profile.this, LeaderBoardScreen.class);
                startActivity(intent_leader);
                break;
            case R.id.linearViewNFLLeadrer:
                Intent intent_NFLleader = new Intent(Profile.this, NFLLeaderBoardScreen.class);
                startActivity(intent_NFLleader);
                break;
            case R.id.toolbar_title_x:
//                overridePendingTransition(  R.anim.slide_down,R.anim.slide_up );

//                PlayWithTokens.ppto = "tt2";
//                UserLocation.ppco = "tt2";
                finish();
                Intent i1 = new Intent(Profile.this, Dashboard.class);
                i1.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i1);


                break;
            case R.id.jn:
             /*   Intent in1 = new Intent(Profile.this, Login.class);
                startActivity(in1);*/
//                Intent in1 = new Intent(Profile.this, Signup.class);
                Intent in1 = new Intent(Profile.this, PlayWithCash.class);
//                in1.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                startActivity(in1);
                break;
            case R.id.tv_agebtn:
                ageVerfiy(Profile.this, "Age verification");
                break;
            case R.id.tvSignOut:
                SharedPreferences sp = getSharedPreferences("SESSION_TOKEN", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();

                //mydb.clearTableMobileUser();
                mydb.resetLocalData();

                LoginManager.getInstance().logOut();

                // getApplicationContext().deleteDatabase(DataBaseHelper.DATABASE_NAME);


                //    resetDatabase();

              /*  Intent mStartActivity = new Intent(Profile.this, OnBoarding.class);
                int mPendingIntentId = 123456;
                PendingIntent mPendingIntent = PendingIntent.getActivity(Profile.this, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);*/


                if (login_txt.getText().equals("Log in")) {
                    Intent in = new Intent(Profile.this, Login.class);
                    startActivity(in);
                } else {
                    Intent in = new Intent(Profile.this, OnBoarding.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(in);
                }
                break;
            case R.id.ll_add_funds:
                Intent addFundsIntent = new Intent(Profile.this, AddFunds.class);
                addFundsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(addFundsIntent);
                break;

            case R.id.viewTransactions:
//                Intent transactionsIntent = new Intent(Profile.this, TransactionsActivity.class);
//                transactionsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(transactionsIntent);
                Intent transactionsIntent = new Intent(Profile.this, TransNewScreen.class);
                transactionsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(transactionsIntent);
                break;
            case R.id.tVEditProfile:
                Intent profileEditIntent = new Intent(Profile.this, ProfileEdit.class);
//                profileEditIntent.putExtra("sessionToken", sessionToken);
//                profileEditIntent.putExtra("acc_name", playerMode);
//                profileEditIntent.putExtra("avatar", avatar);
//                profileEditIntent.putExtra("amount_cash", amount_cash);
//                profileEditIntent.putExtra("amount_token", amount_token);
                profileEditIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(profileEditIntent);
                break;
            case R.id.linearViewHowToPaly:
                Intent how_Intent = new Intent(Profile.this, WebScreens.class);
                how_Intent.putExtra("title", AppConstants.HOW_TO_PLAY);
                how_Intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(how_Intent);
                break;
            case R.id.linearViewFAQs:
                Intent faq_Intent = new Intent(Profile.this, WebScreens.class);
                faq_Intent.putExtra("title", AppConstants.FAQS);
                faq_Intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(faq_Intent);
                break;
            case R.id.linearViewCustomerSupport:
//                Intent cust_Intent = new Intent(Profile.this, WebScreens.class);
//                cust_Intent.putExtra("title", AppConstants.CUSTOMER_SUPPORT);
//                cust_Intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(cust_Intent);
                Intent cust_Intent = new Intent(Profile.this, RaiseaTicketScreen.class);
                startActivity(cust_Intent);
                break;
            case R.id.linearViewPrivacyPolicy:
                Intent pp_Intent = new Intent(Profile.this, WebScreens.class);
                pp_Intent.putExtra("title", AppConstants.PRIVACY_POLICY);
                pp_Intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(pp_Intent);
                break;
            case R.id.linearViewTermsOfService:
                Intent tos_Intent = new Intent(Profile.this, WebScreens.class);
                tos_Intent.putExtra("title", AppConstants.TERMS_OF_SERVICE);
                tos_Intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(tos_Intent);
                break;
            case R.id.linearViewAboutVetnos:
                Intent about_Intent = new Intent(Profile.this, WebScreens.class);
                about_Intent.putExtra("title", AppConstants.ABOUT_VETNOS);
                about_Intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(about_Intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mMixpanel.flush();
        super.onDestroy();
    }


    public void userprofdileupdate() {

        AndroidNetworking.get(APIs.MYINFO)
                .setPriority(Priority.HIGH)
                .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer " + Dashboard.NEWTOKEN)

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String searchResponse = response.toString();
                        Log.e("userdetails", "response  >>" + searchResponse.toString());

                        try {

                            JSONObject signup_res = new JSONObject(searchResponse);

                            JSONObject agev = signup_res.getJSONObject("userAgeVerification");

                            age_verfiy_status = agev.getString("verification_status");
                            age_status.setText(age_verfiy_status);


                            if (age_status.getText().toString().equalsIgnoreCase("processing")) {
                                tv_agebtn.setVisibility(View.GONE);
                            } else {
                                tv_agebtn.setVisibility(View.VISIBLE);
                            }

//                        JSONObject jsonObject1=lObj.getJSONObject("data");
                            PID = signup_res.getString("_id");
                            PNAME = signup_res.getString("fullName");
                            PIMAGE = signup_res.getString("avatar");
                            PEMAIL = signup_res.getString("email");
                            ContentValues cv = new ContentValues();
                            cv.put(DB_Constants.USER_NAME, signup_res.getString("fullName"));
                            cv.put(DB_Constants.USER_EMAIL, signup_res.getString("email"));
                            cv.put(DB_Constants.USER_IMAGE, signup_res.getString("avatar"));
                            cv.put(DB_Constants.USER_WINS, signup_res.getString("totalWins"));
                            cv.put(DB_Constants.USER_CITY, signup_res.getString("city"));
                            cv.put(DB_Constants.USER_STATE, signup_res.getString("state"));
                            cv.put(DB_Constants.USER_COUNTRY, signup_res.getString("country"));
                            cv.put(DB_Constants.USER_MODETYPE, signup_res.getString("userPlayMode"));
                            cv.put(DB_Constants.USER_TOTALCASHBALANCE, String.format ("%.0f", signup_res.getDouble("totalCashBalance")));
//                            cv.put(DB_Constants.USER_TOTALCASHBALANCE, signup_res.getString("totalCashBalance"));
                            cv.put(DB_Constants.USER_CASHBALANCE, signup_res.getString("cashBalance"));
                            cv.put(DB_Constants.USER_PROMOBALANCE, signup_res.getString("promoBalance"));
                            cv.put(DB_Constants.USER_TOKENBALANCE, String.format ("%.0f", signup_res.getDouble("tokenBalance")));
//                            cv.put(DB_Constants.USER_TOKENBALANCE, signup_res.getString("tokenBalance"));
//                            cv.put(DB_Constants.USER_TOKENBALANCE, signup_res.getString("tokenBalance"));
                            cv.put(DB_Constants.USER_NUMBER, signup_res.getString("phoneNumber"));
                            cv.put(DB_Constants.USER_DOB, signup_res.getString("dob"));
                            cv.put(DB_Constants.USER_GENDER, signup_res.getString("gender"));
                            cv.put(DB_Constants.USER_SPORTSPRE, signup_res.getString("sportsPreference"));

                            mydb.updateUser(cv);

                            tvTokens.setText(String.format ("%.0f", signup_res.getDouble("tokenBalance")));

                        } catch (JSONException e) {
                            Log.e("error", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("error", error.getMessage());
                    }
                });


    }
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    // age verfy
    public void ageVerfiy(final Context context, String title) {
        final String[] users_proof = { "Driver Licence", "Passport" };
        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.alerts_agev);
        Window window = dialog.getWindow();
        if (window != null) {
            dialog.getWindow().setLayout(((Utilities.getWidth(context) / 100) * 94), LinearLayout.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.CENTER);
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.show();
            dialog.setCancelable(false);
        }

        TextView alert_title = dialog.findViewById(R.id.age_title);
        TextView age_close = dialog.findViewById(R.id.age_close);
        ImageView l_image = dialog.findViewById(R.id.l_image);
        ImageView r_image = dialog.findViewById(R.id.r_image);
        Spinner proof_spin = dialog.findViewById(R.id.proof_spin);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, users_proof);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proof_spin.setAdapter(adapter);
        proof_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(context, "Selected User: "+users_proof[position] , Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        alert_title.setText(title);
//    alert_msg.setText("");
        TextView alert_ok = dialog.findViewById(R.id.age_ok);
        alert_ok.setText("OK");
        // if decline button is clicked, close the custom dialog
        alert_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        age_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Left Image
        l_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Dexter.withContext(Profile.this)
                            .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,

                                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .withListener(new MultiplePermissionsListener() {
                                @Override
                                public void onPermissionsChecked(MultiplePermissionsReport report) {
                                    // check if all permissions are granted
                                    if (report.areAllPermissionsGranted()) {
                                        // do you work now
                                      //  dialog.dismiss();
                                    /*    new ImagePicker.Builder(ProfileEdit.this)
                                                .mode(ImagePicker.Mode.CAMERA)
                                                .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                                                .directory(ImagePicker.Directory.DEFAULT)
                                                .extension(ImagePicker.Extension.JPG)
                                                .scale(600, 600)
                                                .allowMultipleImages(false)
                                                .enableDebuggingMode(true)
                                                .build();*/
                                        openCameraIntent();
                                    }

                                    // check for permanent denial of any permission
                                    if (report.isAnyPermissionPermanentlyDenied()) {
                                        // permission is denied permenantly, navigate user to app settings
                                    }
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                    token.continuePermissionRequest();
                                }
                            })
                            .onSameThread()
                            .check();


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            File cameraFile;
            private void openCameraIntent() {
                Intent pickIntent = new Intent();

                Intent takePhotoIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                this.cameraFile = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                takePhotoIntent.putExtra("output", Uri.fromFile(this.cameraFile));

                Intent chooserIntent = Intent.createChooser(pickIntent, "");
                chooserIntent.putExtra("android.intent.extra.INITIAL_INTENTS", new Intent[]{takePhotoIntent});
                startActivityForResult(chooserIntent, REQUEST_CAPTURE_IMAGE);


            }


        });


        //A

    }



}

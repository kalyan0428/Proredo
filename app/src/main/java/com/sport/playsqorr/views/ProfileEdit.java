package com.sport.playsqorr.views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.sport.playsqorr.BuildConfig;
import com.sport.playsqorr.R;
import com.sport.playsqorr.crop.CropImage;
import com.sport.playsqorr.crop.CropImageView;
import com.sport.playsqorr.database.DB_Constants;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.LocationTrack;
import com.sport.playsqorr.utilities.LocationTracker;
import com.sport.playsqorr.utilities.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.sport.playsqorr.utilities.LocationTrack.getLocationFu;
import static com.sport.playsqorr.utilities.LocationTrack.llat;
import static com.sport.playsqorr.utilities.LocationTrack.llong;
import static com.sport.playsqorr.utilities.UtilitiesAna.trackEvent;


public class ProfileEdit extends AppCompatActivity implements View.OnClickListener, TextWatcher, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    LinearLayout camera_img;


    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;

    public static final String CAMERA_PREF = "camera_pref";
    public static final String ALLOW_KEY = "ALLOWED";
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 10000;

    private static final int REQUEST_CAPTURE_IMAGE = 100;

    ImageView profile_image;

    EditText edt_address, pwd_edt, edt_etFullName, et_dob, et_ph_no, etEmailAddress, etsports;
    TextView edt_submit, tvDOB, tvYourLocation;
    private LocationTracker gpst;
    private double gpslatitude, gpslongitude;

    private int RESULT_LOAD_IMAGE = 101;

   /* String city_txt = "";
    String state_txt = "";
    String country_txt = "";*/

    private DataBaseHelper mydb;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    String ROLE, SESSIONTOKEN,NEWTOKEN, STATE_, COUNTRY_, CITY_, AMOUNT_TOKEN, AVATAR, MYWiNS, ACCNAME, EMAIL, NUMBER, DOB, GEN, SP;

    private Uri fileUri;
    private ImageView ivEditPassword;
    MixpanelAPI mMixpanel;
    ProgressBar pr_images;
    LocationManager lm;
    Uri picUri;
    Bitmap myBitmap;
    Uri cameraPictureUri;

    Bitmap bitmap1;
    String image = "0";
    TextView mRemove;
    Uri selectedImageUri;
    int CROP_IMAGE = 1;
    ProgressDialog progressDoalog;
    String mCurrentPhotoPath;
    private Spinner spnrgender;
    TextView toolbar_title_x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        }
        image = "1";
        progressDoalog = new ProgressDialog(ProfileEdit.this);
        Log.d("selectedImageUri", String.valueOf(selectedImageUri));
        mydb = new DataBaseHelper(this);
        sqLiteDatabase = mydb.getReadableDatabase();
        mMixpanel = MixpanelAPI.getInstance(ProfileEdit.this, getString(R.string.test_MIX_PANEL_TOKEN));
        pr_images = findViewById(R.id.pr_images);
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            EnableGPSAutoMatically();
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
        }
        profile_image = findViewById(R.id.profile_image);
        profile_image.getTag();
        getDataFromLocalDb();
        Utilities.verifyStoragePermissions(ProfileEdit.this);

        init();
        setUserAvatar();
        setPageData();
        Log.d("AVATAR", String.valueOf(profile_image.getTag()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromLocalDb();

        //      setPageData();
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
//                CITY_ = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_CITY)).trim();

                COUNTRY_ = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_COUNTRY)).trim();
                AMOUNT_TOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKENBALANCE));
                AVATAR = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_IMAGE));
                MYWiNS = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_WINS));
                ACCNAME = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NAME));
                EMAIL = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_EMAIL));
                NUMBER = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NUMBER));
                DOB = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_DOB));
                GEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_GENDER));
                SP = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_SPORTSPRE));
            }

            cursor.close();

        } else {
            ROLE = "0";

        }
//        setUserAvatar();
    }

    private void setUserAvatar() {

//        Log.d("AVATAR",AVATAR);
        if (AVATAR.equalsIgnoreCase("null") || AVATAR.equalsIgnoreCase("")) {
            pr_images.setVisibility(View.GONE);
        } else {
            pr_images.setVisibility(View.VISIBLE);

        }

//        pr_images.setVisibility(View.VISIBLE);


        Picasso.with(ProfileEdit.this)
                .load(AVATAR)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .resize(512, 512)
//                .error(R.drawable.progress_animation)
                .error(R.drawable.profile_placeholder)
                .noFade()
                .into(profile_image, new Callback() {
                    @Override
                    public void onSuccess() {

                        pr_images.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        // TODO Auto-generated method stub

                    }
                });

    }

    @SuppressLint("SetTextI18n")
    private void init() {
        gpst = new LocationTracker(this);
        toolbar_title_x = findViewById(R.id.toolbar_title_x);
        toolbar_title_x.setText(getString(R.string._profile));
        pwd_edt = findViewById(R.id.etPassword);
        edt_address = findViewById(R.id.etLocation);
        camera_img = findViewById(R.id.camera_img);
        profile_image = findViewById(R.id.profile_image);
        etEmailAddress = findViewById(R.id.etEmailAddress);
        edt_etFullName = findViewById(R.id.etFullName);

        et_dob = findViewById(R.id.et_dob);
        et_ph_no = findViewById(R.id.et_ph_no);
        edt_submit = findViewById(R.id.edt_submit);
        tvYourLocation = findViewById(R.id.tvYourLocation);
        tvDOB = findViewById(R.id.tvDOB);
        ivEditPassword = findViewById(R.id.ivEditPassword);

        etsports = findViewById(R.id.etsports);
        spnrgender = findViewById(R.id.spnrgender);

        etEmailAddress.setEnabled(false);
        edt_etFullName.setEnabled(false);
        et_dob.setEnabled(false);

        toolbar_title_x.setOnClickListener(this);
        pwd_edt.setOnClickListener(this);
        camera_img.setOnClickListener(this);
        edt_address.setOnClickListener(this);
        edt_submit.setOnClickListener(this);
        et_dob.setOnClickListener(this);
        ivEditPassword.setOnClickListener(this);

        edt_etFullName.addTextChangedListener(this);
        edt_address.addTextChangedListener(this);
        etEmailAddress.addTextChangedListener(this);
        et_dob.addTextChangedListener(this);
        et_ph_no.addTextChangedListener(this);

        String[] statesArray = getResources().getStringArray(R.array.gender_array);
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, statesArray);
        spnrgender.setAdapter(courseAdapter);

    }

    private void setPageData() {
        edt_etFullName.setText(ACCNAME);
        etEmailAddress.setText(EMAIL);

        if ((COUNTRY_ != null && !COUNTRY_.isEmpty() && !COUNTRY_.equalsIgnoreCase("null")) && (STATE_ != null && !STATE_.isEmpty() && !STATE_.equalsIgnoreCase("null"))) {
            edt_address.setText(STATE_ + "," + COUNTRY_);

        } else if ((COUNTRY_ != null && !COUNTRY_.isEmpty() && !COUNTRY_.equalsIgnoreCase("null"))) {

            edt_address.setText(COUNTRY_);

        } else if ((STATE_ != null && !STATE_.isEmpty() && !STATE_.equalsIgnoreCase("null"))) {
            edt_address.setText(STATE_);
        } else {
            edt_address.setText("");
        }

        if ((DOB != null && !DOB.isEmpty() && !DOB.equalsIgnoreCase("null"))) {
            et_dob.setText(parseDateToddMMyyyy(DOB));
        } else {
            et_dob.setText("");
            et_dob.setEnabled(true);
        }

        if (NUMBER != null && !NUMBER.equals("")) {
            et_ph_no.setText(NUMBER);
        } else {
            et_ph_no.setText("");

        }

        if (ROLE != null && ROLE.equalsIgnoreCase("tokens")) {
            tvYourLocation.setText("YOUR LOCATION (OPTIONAL)");
            tvDOB.setText("DATE OF BIRTH");
        } else {
            tvYourLocation.setText("YOUR LOCATION");
            tvDOB.setText("DATE OF BIRTH");
        }


        if ((SP != null && !SP.isEmpty() && !SP.equalsIgnoreCase("null"))) {
            etsports.setText(SP);
        } else {
            etsports.setText("");
        }

        if ((GEN != null && !GEN.isEmpty() && !GEN.equalsIgnoreCase("null"))) {
            etsports.setText(SP);
            if (GEN.equalsIgnoreCase("male")) {
                spnrgender.setSelection(0);
            } else {
                spnrgender.setSelection(1);
            }
        } else {
            etsports.setText("");
        }
        // spnrgender.setSelection(GEN);

    }

    public String parseDateToddMMyyyy(String time) {

        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss"; //1949-12-31T00:00:00
//        String inputPattern = "MM/dd/yyyy"; //1949-12-31T00:00:00
        String outputPattern = "MMMM-dd-yyyy";
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
//
//        String inputPattern = "MM/dd/yyyy";
//        String outputPattern = "MMMM-dd-yyyy";
//        @SuppressLint("SimpleDateFormat")
//        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
//        @SuppressLint("SimpleDateFormat")
//        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;
        String birthDate = "";

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
            String[] dateSplit = str.split("-");

            birthDate = dateSplit[0] + " " + dateSplit[1] + "," + dateSplit[2];

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return birthDate;
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
//                PlayWithTokens.ppto = "tt2";
//                UserLocation.ppco = "tt2";
                break;
            case R.id.ivEditPassword:
                Intent changePwdIntent = new Intent(ProfileEdit.this, ChangePassword.class);
                changePwdIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(changePwdIntent);
                break;
            case R.id.et_dob:
                showCalendar();
                break;
            case R.id.edt_submit:

                if (edt_etFullName.getText().toString().trim().length() > 0) {
                    JSONObject metrics_json = new JSONObject();
                    try {
                        metrics_json.put("fullName", edt_etFullName.getText().toString().trim());
                        if (DOB.equalsIgnoreCase("null")) {
                            metrics_json.put("dob", "");
                        } else {
                            metrics_json.put("dob", DOB);
                        }
                        metrics_json.put("city", CITY_);
                        metrics_json.put("state", STATE_);
                        metrics_json.put("country", COUNTRY_);
                        metrics_json.put("phoneNumber", et_ph_no.getText().toString().trim());
                        metrics_json.put("gender", spnrgender.getSelectedItem().toString().trim());
                        metrics_json.put("sportsPreference", etsports.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    apiCallEditProfile(metrics_json);
                } else {
                    Utilities.showToast(ProfileEdit.this, "Please enter your name");
                }

                break;
            case R.id.etLocation:

                try {
                    Dexter.withContext(ProfileEdit.this)
                            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {


                                    //  if (Helper.isGPSEnabled(MainActivity.this)) {
                                    LocationTrack locationTrack = new LocationTrack(ProfileEdit.this);
//                                    if (locationTrack.canGetLocation) {
//                                        double lat = locationTrack.getLatitude();
//                                        double lon = locationTrack.getLongitude();
                                    getLocationFu(ProfileEdit.this);
                                    double lat = llat;
                                    double lon = llong;
                                        try {
                                            Geocoder gcd = new Geocoder(ProfileEdit.this, Locale.getDefault());
                                            List<Address> addresses = gcd.getFromLocation(lat,
                                                    lon, 1);

                                            if (addresses.size() > 0) {

                                                STATE_ = addresses.get(0).getAdminArea();
                                                CITY_ = addresses.get(0).getLocality();
                                                COUNTRY_ = addresses.get(0).getCountryName();
                                                edt_address.setText(STATE_ + "," + COUNTRY_);
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                 //   }
                                      /*  } else {
                                            Toast.makeText(MainActivity.this, "Location service not enabled", Toast.LENGTH_LONG).show();
                                        }*/


                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response) {
                                    // check for permanent denial of permission
                                    if (response.isPermanentlyDenied()) {
                                        // navigate user to app settings
                                        Utilities.showAlertBoxLoc(ProfileEdit.this, getResources().getString(R.string.enable_location_title), getResources().getString(R.string.enable_location_msg));
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
                break;
            case R.id.camera_img:
                showPictureialog();

                break;
        }
    }

    private void showCalendar() {
        Calendar calendar1 = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileEdit.this, R.style.MyDatePickerDialogTheme, listener, calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH));
        //    datePickerDialog.getDatePicker().setMinDate(calendar1.getTimeInMillis());
        datePickerDialog.getDatePicker().setMaxDate(calendar1.getTimeInMillis());
        datePickerDialog.show();
    }

    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

            DOB = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;

            if (ROLE != null && ROLE.equalsIgnoreCase("cash")) {
                Integer age = getAge(year, monthOfYear + 1, dayOfMonth);
                if (age >= 18) {
                    et_dob.setText((Utilities.getMonthName((monthOfYear + 1)) + " " + dayOfMonth + ", " + year));
                } else {
                    showAlertBox(ProfileEdit.this, getResources().getString(R.string.dob_title), getResources().getString(R.string.dob_msg));
                    et_dob.setText("");
                }
            } else {
                et_dob.setText((Utilities.getMonthName((monthOfYear + 1)) + " " + dayOfMonth + ", " + year));
            }
        }
    };

    private Integer getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(year, month, day);
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }

    public static void showAlertBox(final Context context, String title, String message) {

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

//                ((Activity) context).finish();


            }
        });

    }

    private void showPictureialog() {
        final Dialog dialog = new Dialog(this, R.style.CustomDialogTheme2
                /*android.R.style.Theme_Translucent_NoTitleBar*/);

        // Setting dialogview
        Window window = dialog.getWindow();
        // window.setGravity(Gravity.CENTER);
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.activity_bottomdialog);

        TextView mGallery = (TextView) dialog.findViewById(R.id.pickGallery);
        TextView mCamera = (TextView) dialog.findViewById(R.id.takePhoto);
        mRemove = (TextView) dialog.findViewById(R.id.removePhoto);
        Log.d("selectedImageUri", String.valueOf(selectedImageUri));
        Log.d("AVATAR", String.valueOf(profile_image.getTag()));
//AVATAR.equalsIgnoreCase("null")||
        if (AVATAR.equalsIgnoreCase("null")) {
            Log.d("selectedImageUri", String.valueOf(selectedImageUri));
            if (selectedImageUri == null) {
                mRemove.setVisibility(View.GONE);
            } else {
                mRemove.setVisibility(View.GONE);
//                mRemove.setVisibility(View.VISIBLE);
            }
        } else {

//                    mRemove.setVisibility(View.VISIBLE);
            mRemove.setVisibility(View.GONE);
        }
        mRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // profileImageview.setImageDrawable(null);
                image = "0";
                AVATAR = "null";
                selectedImageUri = null;
                File file = null;
                apiCallImageProfile(file);
                dialog.dismiss();
                profile_image.setImageResource(R.drawable.profile_placeholder);
            }
        });

        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Dexter.withContext(ProfileEdit.this)
                            .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,

                                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .withListener(new MultiplePermissionsListener() {
                                @Override
                                public void onPermissionsChecked(MultiplePermissionsReport report) {
                                    // check if all permissions are granted
                                    if (report.areAllPermissionsGranted()) {
                                        // do you work now
                                        dialog.dismiss();
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
        });

        mGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                try {
                    Dexter.withContext(ProfileEdit.this)
                            .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,

                                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .withListener(new MultiplePermissionsListener() {
                                @Override
                                public void onPermissionsChecked(MultiplePermissionsReport report) {
                                    // check if all permissions are granted
                                    if (report.areAllPermissionsGranted()) {
                                        // do you work now
                                        dialog.dismiss();
                                    /*    new ImagePicker.Builder(ProfileEdit.this)
                                                .mode(ImagePicker.Mode.CAMERA)
                                                .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                                                .directory(ImagePicker.Directory.DEFAULT)
                                                .extension(ImagePicker.Extension.JPG)
                                                .scale(600, 600)
                                                .allowMultipleImages(false)
                                                .enableDebuggingMode(true)
                                                .build();*/
                                        openGallery();
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
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.show();
    }

    File cameraFile;

    private void openCameraIntent() {
/*// Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        Intent pickIntent = new Intent();

        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       cameraFile = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));

        String pickTitle = ""; // Or get from strings.xml Select or take a new Picture
        Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[]{takePhotoIntent});
//        startActivityForResult(chooserIntent, REQUEST_CAPTURE_IMAGE);
        startActivityForResult(getPickImageChooserIntent(), REQUEST_CAPTURE_IMAGE);*/

        Intent pickIntent = new Intent();

        Intent takePhotoIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        this.cameraFile = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
        takePhotoIntent.putExtra("output", Uri.fromFile(this.cameraFile));

        Intent chooserIntent = Intent.createChooser(pickIntent, "");
        chooserIntent.putExtra("android.intent.extra.INITIAL_INTENTS", new Intent[]{takePhotoIntent});
        startActivityForResult(chooserIntent, REQUEST_CAPTURE_IMAGE);


    }

    private void openGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);

       /* Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Intent takePhotoIntent = new Intent();
        cameraFile1 = new File(android.os.Environment.getExternalStorageDirectory(), "temp1.jpg");
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile1));

        String pickTitle = "Select or take a new Picture"; // Or get from strings.xml Select or take a new Picture
        Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
        cameraFile1 = new File(android.os.Environment.getExternalStorageDirectory(), "temp1.jpg");
        chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile1));

        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[]{takePhotoIntent});
        startActivityForResult(chooserIntent, RESULT_LOAD_IMAGE);*/

        /*Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery, RESULT_LOAD_IMAGE);*/
    }

    private void apiCallImageProfile(File file) {
        cursor = mydb.getAllUserInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                System.out.println(cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NAME)));
                SESSIONTOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_SESSIONTOKEN));
                NEWTOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKEN));
                Log.d("SESSIONTOKEN", SESSIONTOKEN);

            }

            cursor.close();

        } else {
            ROLE = "0";

        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        try {
            File file1 = new File("");
            //final MediaType MEDIA_TYPE = MediaType.parse(Utility.getMimeType(mSelectedDocFile));
            builder.addFormDataPart("", file1.getName(), RequestBody.create(MediaType.parse(""), file1));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        RequestBody requestBody = builder.build();

        progressDoalog = new ProgressDialog(ProfileEdit.this);
        progressDoalog.setCancelable(false);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
      /*  String uploadurl="https://staging-backoffice.azurewebsites.net/api/account/profileimage";
        Request request = new Request.Builder()
                .url(uploadurl)
                .addHeader("Content-Type", "application/json")
                .addHeader("sessionToken", SESSIONTOKEN)
                .post(requestBody)
                .build();


        OkHttpClient client = new OkHttpClient.Builder().build();
        Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
//                progressDoalog.dismiss();
                // progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                //  progressBar.setVisibility(View.VISIBLE);
//                progressDoalog.dismiss();

                String jsonData = response.body().string();
                responseString = jsonData;
                ContentValues cv = new ContentValues();
                cv.remove(DB_Constants.USER_IMAGE);
                cv.put(DB_Constants.USER_IMAGE, jsonData);
                ProfileEdit.setUserAvatar1();

                mydb.updateUser(cv);
                preferenceUtils.saveString(PreferenceUtils.PROFILE, responseString );


                Log.d("jsondata", "" + jsonData);

            }
        });*/
        Log.e("response-file--", "" + file);
        AndroidNetworking.upload(APIs.IMAGE_UPLOAD)
                .addMultipartFile("", file)
//                .addMultipartParameter("key","value")
                .setTag("uploadTest")
                .addHeaders("sessionToken", SESSIONTOKEN)
                .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                .addHeaders("Content-Type", "application/json")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
                        Log.e("***Location:-imag- ", String.valueOf(bytesUploaded));
                    }
                })
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("***Location:-imag- ", response.toString());
                        ContentValues cv = new ContentValues();
                        progressDoalog = new ProgressDialog(ProfileEdit.this);
                        progressDoalog.dismiss();
                        cv.put(DB_Constants.USER_IMAGE, response.toString());


                        mydb.updateUser(cv);
//                        preferenceUtils.saveString(PreferenceUtils.PROFILE, response.toString() );

                        Utilities.showToast(getApplicationContext(), "Uploaded");

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("js", "user----error-------" + anError.getErrorBody());
                        try {
                            JSONObject ej = new JSONObject(anError.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                            String au = ej.getString("message");
                            if (au.contains("Unauthorized")) {
                                showAlertBoxAU(ProfileEdit.this, "Error", "Session has expired,please try logining again");
                            } else {
                                Utilities.showToast(ProfileEdit.this, ej.getString("message"));
                            }


                        } catch (Exception e) {

                        }
//                        Utilities.showToast(getApplicationContext(), "Something went worng.Please try again");
                        progressDoalog.dismiss();
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
                mydb.resetLocalData();

                LoginManager.getInstance().logOut();

                Intent in = new Intent(ProfileEdit.this, OnBoarding.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap bitmap = null;
        if (resultCode == Activity.RESULT_OK && requestCode == 200) {

            if (getPickImageResultUri(data) != null) {
                picUri = getPickImageResultUri(data);
                Log.d("picUri", String.valueOf(picUri));
                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                    myBitmap = rotateImageIfRequired(myBitmap, picUri);
                    myBitmap = getResizedBitmap(myBitmap, 500);
                    Log.d("myBitmap", String.valueOf(myBitmap));
                    //CircleImageView croppedImageView = (CircleImageView) findViewById(R.id.img_profile);
                    //croppedImageView.setImageBitmap(myBitmap);
                    profile_image.setImageBitmap(myBitmap);
//                    CropImage.activity(picUri).setGuidelines(CropImageView.Guidelines.ON).start(this);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {

                bitmap = (Bitmap) data.getExtras().get("data");

                myBitmap = bitmap;
                //   CircleImageView croppedImageView = (CircleImageView) findViewById(R.id.img_profile);
                if (profile_image != null) {
                    profile_image.setImageBitmap(myBitmap);

                }

            }

        } else if (requestCode == REQUEST_CAPTURE_IMAGE) { //From Camera


            if (resultCode == RESULT_OK) {


//                Uri imageUri = data.getData();
//                Log.d("imageUri", String.valueOf(imageUri.getPath()));
//                final String path = getPathFromURI(imageUri);


//                cameraPictureUri = Uri.fromFile(cameraFile);
                cameraPictureUri = Uri.fromFile(cameraFile);
                Log.e("cameraPictureUri", String.valueOf(cameraPictureUri) + cameraFile);
//                fileUri = getPickImageResultUri(data);

//                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                Bitmap bitmapImage = BitmapFactory.decodeFile(cameraPictureUri.getPath());
                int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                compressImage(cameraPictureUri.getPath());
//                decodeFile(cameraPictureUri.getPath());
                Log.d("compress", cameraPictureUri.getPath() + cameraFile);
//                Bitmap bm = ShrinkBitmap(cameraPictureUri., 300, 300);

                if (cameraPictureUri != null) {
//                    Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                    String compress = compressImage(cameraPictureUri.getPath());
                    File file = new File(compress);

                    CropImage.activity(Uri.fromFile(file)).setGuidelines(CropImageView.Guidelines.ON).start(this);

                }
                try {
                    bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(new File(cameraPictureUri.getPath())));

                } catch (IOException e) {
                    e.printStackTrace();
                }
                image = "1";
                if (image.equalsIgnoreCase("0") || AVATAR.equalsIgnoreCase("null")) {
                    mRemove.setVisibility(View.GONE);
                } else if (image.equalsIgnoreCase("1") || !AVATAR.equalsIgnoreCase("null")) {
                    mRemove.setVisibility(View.VISIBLE);
                }
                mRemove.setVisibility(View.VISIBLE);
                pr_images.setVisibility(View.GONE);
                //   profile_image.setImageBitmap(bitmap1);

            }

        } else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) { //From Gallery
            Uri imageUri = data.getData();
            Log.d("imageUri", String.valueOf(imageUri.getPath()));
            final String path = getPathFromURI(imageUri);
            if (path != null) {
                File f = new File(path);
                selectedImageUri = Uri.fromFile(f);
                Log.d("selectedImageUri", String.valueOf(selectedImageUri));
                String compress = compressImage(String.valueOf(selectedImageUri));
            }

    /*        cameraPictureUri1 = Uri.fromFile(cameraFile1);
            Log.d("imageUri", String.valueOf(cameraPictureUri1));*/

            if (selectedImageUri != null) {

                String compress = compressImage(selectedImageUri.getPath());
                File file = new File(compress);
                CropImage.activity(Uri.fromFile(file)).setGuidelines(CropImageView.Guidelines.ON).start(this);

            }
//            CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).start(this);
            try {
                bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(new File(selectedImageUri.getPath())));

            } catch (IOException e) {
                e.printStackTrace();
            }
            image = "1";
            if (image.equalsIgnoreCase("0") || AVATAR.equalsIgnoreCase("null")) {
                mRemove.setVisibility(View.GONE);
            } else if (image.equalsIgnoreCase("1") || !AVATAR.equalsIgnoreCase("null")) {
                mRemove.setVisibility(View.VISIBLE);
            }
            mRemove.setVisibility(View.VISIBLE);
            pr_images.setVisibility(View.GONE);
            //   profile_image.setImageBitmap(bitmap1);

//            profile_image.setImageURI(Uri.parse(CropImageActivity.path));
        } else if (requestCode == CROP_IMAGE) {

            Uri imageUri = Uri.parse(mCurrentPhotoPath);
            File file = new File(imageUri.getPath());
            try {
                InputStream ims = new FileInputStream(file);
                Bitmap bitmapImage = BitmapFactory.decodeFile(imageUri.getPath());
                int nh = (int) (bitmapImage.getHeight() * (212.0 / bitmapImage.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 212, nh, true);
//                profile_image.setImageBitmap(scaled);
                profile_image.setImageBitmap(BitmapFactory.decodeStream(ims));
            } catch (FileNotFoundException e) {
                return;
            }

        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            Log.d("imageUri", String.valueOf(result.getUri()));
            if (resultCode == RESULT_OK) {
                profile_image.setImageURI(result.getUri());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight;// = options.outHeight;
        int actualWidth;// = options.outWidth;

        if (options.outHeight > 0 && options.outWidth > 0) {
            actualHeight = options.outHeight;
            actualWidth = options.outWidth;

        } else {
            actualHeight = 50;
            actualWidth = 50;

        }

//      max Height and width values of the compressed image is taken as 816x612
        float maxHeight = 300.0f;
        float maxWidth = 312.0f;

        float imgRatio = actualWidth /
                Math.max(1, actualHeight);

        //  float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image
        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;
            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            Log.d("filename", filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;
    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyMMdd_HHmm").format(new Date());
        String uriSting = (file.getAbsolutePath() + "/" + timeStamp + ".jpg");
        return uriSting;
    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }


    public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();


        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            //  outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
            outputFileUri = FileProvider.getUriForFile(ProfileEdit.this, BuildConfig.APPLICATION_ID + ".provider", new File(getImage.getPath(), "profile.png"));
            //    apiCallImageProfile(outputFileUri);
        }
        return outputFileUri;
    }

    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }


        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    private void apiCallEditProfile(JSONObject metrics_json) {
        Log.e("EDIT PROFILE URL ::", APIs.ACCOUNT_UPDATE_URL);
        Log.e("EDIT PROFILE::", metrics_json.toString());
        AndroidNetworking.put(APIs.ACCOUNT_UPDATE_URL)
                .setPriority(Priority.HIGH)
                .addHeaders("sessionToken", SESSIONTOKEN)
                .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                .addHeaders("Content-Type", "application/json")
                .addJSONObjectBody(metrics_json)
                .build()
                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response.code() == 200) {

                            mMixpanel.track("Profile Complete", null);
                            trackEvent("Profile Complete", null);

                            ContentValues cv = new ContentValues();
                            cv.put(DB_Constants.USER_NAME, edt_etFullName.getText().toString().trim());
                            cv.put(DB_Constants.USER_DOB, DOB);
                            cv.put(DB_Constants.USER_NUMBER, et_ph_no.getText().toString().trim());
                            cv.put(DB_Constants.USER_STATE, STATE_);
                            cv.put(DB_Constants.USER_CITY, CITY_);
                            cv.put(DB_Constants.USER_COUNTRY, COUNTRY_);
                            cv.put(DB_Constants.USER_GENDER, spnrgender.getSelectedItem().toString().trim());
                            cv.put(DB_Constants.USER_SPORTSPRE, etsports.getText().toString().trim());
                            mydb.updateUser(cv);
                            finish();

                        } else {
                            Utilities.showToast(getApplicationContext(), "" + response.message());
//
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("ERROR::", anError.getErrorBody());
                        Utilities.showToast(getApplicationContext(), "" + anError.getErrorBody());
                    }
                });

    }


    private void apiCallImageProfile(Uri file) {

        AndroidNetworking.post(APIs.IMAGE_UPLOAD)
                .setPriority(Priority.HIGH)
                .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer "+ Dashboard.NEWTOKEN)

                .addHeaders("Content-Type", "application/json")
//                .addBodyParameter(metrics_json)
                .addBodyParameter(" ", String.valueOf(file))

                .build()
                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response.code() == 200) {
//                            Utilities.showToast(getApplicationContext(), "HUrray");
                            Log.e("response-imm--", "" + response);

                        } else {
                            Utilities.showToast(getApplicationContext(), "" + response.message());
//
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Utilities.showToast(getApplicationContext(), "" + anError.getErrorBody());
                    }
                });


    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        int nameLength = edt_etFullName.getText().toString().trim().length();
        int locationLength = edt_address.getText().toString().trim().length();
        int dobLength = et_dob.getText().toString().trim().length();
        int phoneLength = et_ph_no.getText().toString().trim().length();

        if (ROLE != null && ROLE.equalsIgnoreCase("tokens")) {
            if (nameLength > 0) {
                edt_submit.setEnabled(true);
                edt_submit.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                edt_submit.setBackgroundResource(R.drawable.btn_bg_red);
            } else {
                edt_submit.setEnabled(false);
                edt_submit.setTextColor(ResourcesCompat.getColor(getResources(), R.color.btn_dis_text, null));
                edt_submit.setBackgroundResource(R.drawable.login_bg_disable);
            }
        } else {
            if (nameLength > 0 && locationLength > 0 && dobLength > 0) {
                edt_submit.setEnabled(true);
                edt_submit.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                edt_submit.setBackgroundResource(R.drawable.btn_bg_red);
            } else {
                edt_submit.setEnabled(false);
                edt_submit.setTextColor(ResourcesCompat.getColor(getResources(), R.color.btn_dis_text, null));
                edt_submit.setBackgroundResource(R.drawable.login_bg_disable);
            }
        }

    }

    @Override
    protected void onDestroy() {
        mMixpanel.flush();
        super.onDestroy();
    }

    private void EnableGPSAutoMatically() {
        GoogleApiClient googleApiClient = null;
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            googleApiClient.connect();
            LocationRequest locationRequest = LocationRequest.create();
//            locationRequest=LocationSettingsRequest.CREATOR.
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            // **************************
            builder.setAlwaysShow(true); // this is the key ingredient
            // **************************

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                    .checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result
                            .getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
//                            toast("Success");
                            // All location settings are satisfied. The client can
                            // initialize location
                            // requests here.
//                            GetDeviceCurrentLocation();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                            toast("GPS is not on");
                            // Location settings are not satisfied. But could be
                            // fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling
                                // startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(ProfileEdit.this, 1000);

                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                            toast("Setting change not allowed");
                            // Location settings are not satisfied. However, we have
                            // no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            });
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}



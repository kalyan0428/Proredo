// "Therefore those skilled at the unorthodox
// are infinite as heaven and earth,
// inexhaustible as the great rivers.
// When they come to an end,
// they begin again,
// like the days and months;
// they die and are reborn,
// like the four seasons."
//
// - Sun Tsu,
// "The Art of War"

package com.sport.playsqorr.crop;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.facebook.login.LoginManager;
import com.sport.playsqorr.R;
import com.sport.playsqorr.database.DB_Constants;
import com.sport.playsqorr.database.DataBaseHelper;

import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.Utilities;
import com.sport.playsqorr.views.OnBoarding;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Built-in activity for image cropping.<br>
 * Use {@link CropImage#activity(Uri)} to create a builder to start this activity.
 */
@SuppressLint("Registered")
public class CropImageActivity extends AppCompatActivity
        implements CropImageView.OnSetImageUriCompleteListener,
        CropImageView.OnCropImageCompleteListener {

    /**
     * The crop image view library widget used in the activity
     */
    private CropImageView mCropImageView;
    String mCurrentPhotoPath,responseString;
    public static String path;
    File file;
    Cursor cursor;
    String SESSIONTOKEN,ROLE,NEWTOKEN;
    ProgressDialog progressDoalog;
    /**
     * Persist URI image to crop URI if specific permissions are required
     */
    private Uri mCropImageUri;
    private TextView btnApply;
    private ImageView ivClose;
    Bitmap bitmap;
    private DataBaseHelper mydb;
//    PreferenceUtils preferenceUtils;
    /**
     * the options that were set for the crop image
     */
    private CropImageOptions mOptions;

    @Override
    @SuppressLint("NewApi")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_image_activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.darkest_gray , null));
        }

        mCropImageView = findViewById(R.id.cropImageView);
        btnApply = findViewById(R.id.btnApply);
        ivClose=findViewById(R.id.ivClose);
        mydb = new DataBaseHelper(this);
//        preferenceUtils=new PreferenceUtils(CropImageActivity.this);


        Bundle bundle = getIntent().getBundleExtra(CropImage.CROP_IMAGE_EXTRA_BUNDLE);
        mCropImageUri = bundle.getParcelable(CropImage.CROP_IMAGE_EXTRA_SOURCE);
        mOptions = bundle.getParcelable(CropImage.CROP_IMAGE_EXTRA_OPTIONS);

        if (savedInstanceState == null) {
            if (mCropImageUri == null || mCropImageUri.equals(Uri.EMPTY)) {
                if (CropImage.isExplicitCameraPermissionRequired(this)) {
                    // request permissions and handle the result in onRequestPermissionsResult()
                    requestPermissions(
                            new String[]{Manifest.permission.CAMERA},
                            CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
                } else {
                    CropImage.startPickImageActivity(this);
                }
            } else if (CropImage.isReadExternalStoragePermissionsRequired(this, mCropImageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                // no permissions required or already grunted, can start crop image activity
                mCropImageView.setImageUriAsync(mCropImageUri);
            }
        }

//    ActionBar actionBar = getSupportActionBar();
//    if (actionBar != null) {
//      CharSequence title = mOptions != null &&
//          mOptions.activityTitle != null && mOptions.activityTitle.length() > 0
//              ? mOptions.activityTitle
//              : getResources().getString(R.string.crop_image_activity_title);
//      actionBar.setTitle(title);
//      actionBar.setDisplayHomeAsUpEnabled(true);
//    }
        progressDoalog = new ProgressDialog(CropImageActivity.this);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImage();
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCropImageView.setOnSetImageUriCompleteListener(this);
        mCropImageView.setOnCropImageCompleteListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCropImageView.setOnSetImageUriCompleteListener(null);
        mCropImageView.setOnCropImageCompleteListener(null);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResultCancel();
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_CANCELED) {
                // User cancelled the picker. We don't have anything to crop
                setResultCancel();
            }


            if (resultCode == Activity.RESULT_OK)
            {
                mCropImageUri = CropImage.getPickImageResultUri(this, data);
//                compressImage(data.getData().getPath());
                // For API >= 23 we need to check specifically that we have permissions to read external
                // storage.
                if (CropImage.isReadExternalStoragePermissionsRequired(this, mCropImageUri)) {
                    // request permissions and handle the result in onRequestPermissionsResult()
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
                } else {
                    // no permissions required or already grunted, can start crop image activity
                    mCropImageView.setImageUriAsync(mCropImageUri);
                }
            }
        }
    }



    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null
                    && grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // required permissions granted, start crop image activity
                mCropImageView.setImageUriAsync(mCropImageUri);
            } else {
                Toast.makeText(this, R.string.crop_image_activity_no_permissions, Toast.LENGTH_LONG).show();
                setResultCancel();
            }
        }

        if (requestCode == CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE) {
            // Irrespective of whether camera permission was given or not, we show the picker
            // The picker will not add the camera intent if permission is not available
            CropImage.startPickImageActivity(this);
        }
    }

    @Override
    public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {
        if (error == null) {
            if (mOptions.initialCropWindowRectangle != null) {
                mCropImageView.setCropRect(mOptions.initialCropWindowRectangle);
            }
//      if (mOptions.initialRotation > -1) {
//        mCropImageView.setRotatedDegrees(mOptions.initialRotation);
//      }
        } else {
            setResult(null, error, 1);
        }
    }

    @Override
    public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
        setResult(result.getUri(), result.getError(), result.getSampleSize());
    }

    // region: Private methods

    /**
     * Execute crop image and save the result tou output uri.
     */
    protected void cropImage() {
        if (mOptions.noOutputImage) {
            setResult(null, null, 1);
        } else {
//            Uri outputUri = getOutputUri();
//           Uri outputUri=  Uri.fromFile(createCropFile());
                 Uri outputUri=  Uri.fromFile(new File(getFilename12()));

            mCropImageView.saveCroppedImageAsync(
                    outputUri,
                    mOptions.outputCompressFormat,
                    mOptions.outputCompressQuality,
                    mOptions.outputRequestWidth,
                    mOptions.outputRequestHeight,
                    mOptions.outputRequestSizeOptions);
             path = outputUri.getPath(); // "/mnt/sdcard/FileName.mp3"
            Log.d("path",path);
            File file = new File(path);
            Uri cameraPictureUri = Uri.fromFile(new File(path));
            Log.d("cameraPictureUri", String.valueOf(cameraPictureUri));
//         String compress =   compressImage(String.valueOf(cameraPictureUri));
            apiCallImageProfile(file);
//            callfilepath(cameraPictureUri);
        }


    }


    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth,
                                                     int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }
    private void apiCallImageProfile(File file)
    {
        cursor = mydb.getAllUserInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                System.out.println(cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NAME)));
                SESSIONTOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_SESSIONTOKEN));
                NEWTOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKEN));
                Log.d("SESSIONTOKEN",SESSIONTOKEN);

            }

            cursor.close();

        } else {
            ROLE = "0";

        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        try {
            File file1 = new File(path);
            //final MediaType MEDIA_TYPE = MediaType.parse(Utility.getMimeType(mSelectedDocFile));
            builder.addFormDataPart("", file1.getName(), RequestBody.create(MediaType.parse(path), file1));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        RequestBody requestBody = builder.build();

        progressDoalog = new ProgressDialog(CropImageActivity.this);
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
        Log.e("response-file--kalyan", "" + file);
        AndroidNetworking.upload(APIs.IMAGE_UPLOAD)
                .addMultipartFile("",file)
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
                        progressDoalog = new ProgressDialog(CropImageActivity.this);
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
                                showAlertBoxAU(CropImageActivity.this, "Error", "Session has expired,please try logining again");
                            } else {
                                Utilities.showToast(CropImageActivity.this, ej.getString("message"));
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

                Intent in = new Intent(CropImageActivity.this, OnBoarding.class);
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

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612
        float maxHeight = 500.0f;
        float maxWidth = 312.0f;
        float imgRatio = actualWidth / actualHeight;
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
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 1, out);
            Log.d("filename",filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;
    }

    public String getFilename()
    {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFoldcorp/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyMMdd_HHmm").format(new Date());
        String uriSting = (file.getAbsolutePath() + "/" +timeStamp + ".jpg");
        return uriSting;
    }
    public String getFilename12()
    {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyCorp/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyMMdd_HHmm").format(new Date());
        String uriSting = (file.getAbsolutePath() + "/" +timeStamp + ".jpg");
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

    /** Rotate the image in the crop image view. */
//  protected void rotateImage(int degrees) {
//    mCropImageView.rotateImage(degrees);
//  }

    /**
     * Get Android uri to save the cropped image into.<br>
     * Use the given in options or create a temp file.
     */
    protected Uri getOutputUri() {
        Uri outputUri = mOptions.outputUri;
        if (outputUri == null || outputUri.equals(Uri.EMPTY)) {
            try {
                String ext =
                        mOptions.outputCompressFormat == Bitmap.CompressFormat.JPEG
                                ? ".jpg"
                                : mOptions.outputCompressFormat == Bitmap.CompressFormat.PNG ? ".png" : ".webp";
                outputUri = Uri.fromFile(File.createTempFile("cropped", ext, getCacheDir()));
            } catch (IOException e) {
                throw new RuntimeException("Failed to create temp file for output image", e);
            }
        }
        return outputUri;
    }
    public static boolean reduceImage(String path, long maxSize) {
        File img = new File(path);
        boolean result = false;
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = null;
        options.inSampleSize=1;
        while (img.length()>maxSize) {
            options.inSampleSize = options.inSampleSize+1;
            bitmap = BitmapFactory.decodeFile(path, options);
            img.delete();
            try
            {
                FileOutputStream fos = new FileOutputStream(path);
//                fos.(path.toLowerCase().endsWith("png")?
//                        Bitmap.CompressFormat.PNG:
//                        Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
                result = true;
            }catch (Exception errVar) {
                errVar.printStackTrace();
            }
        };
        return result;
    }
    /**
     * Result with cropped image data or error if failed.
     */
    protected void setResult(Uri uri, Exception error, int sampleSize) {
        int resultCode = error == null ? RESULT_OK : CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE;
        setResult(resultCode, getResultIntent(uri, error, sampleSize));
        finish();
    }





        /**
         * Cancel of cropping activity.
         */
    protected void setResultCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    /**
     * Get intent instance to be used for the result of this activity.
     */
    protected Intent getResultIntent(Uri uri, Exception error, int sampleSize) {
        CropImage.ActivityResult result =
                new CropImage.ActivityResult(
                        mCropImageView.getImageUri(),
                        uri,
                        error,
                        mCropImageView.getCropPoints(),
                        mCropImageView.getCropRect(),
                        mCropImageView.getRotatedDegrees(),
                        mCropImageView.getWholeImageRect(),
                        sampleSize);
        Intent intent = new Intent();
        intent.putExtras(getIntent());
        intent.putExtra(CropImage.CROP_IMAGE_EXTRA_RESULT, result);
        return intent;
    }

    /**
     * Update the color of a specific menu item to the given color.
     */
    private void updateMenuItemIconColor(Menu menu, int itemId, int color) {
        MenuItem menuItem = menu.findItem(itemId);
        if (menuItem != null) {
            Drawable menuItemIcon = menuItem.getIcon();
            if (menuItemIcon != null) {
                try {
                    menuItemIcon.mutate();
                    menuItemIcon.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                    menuItem.setIcon(menuItemIcon);
                } catch (Exception e) {
                    Log.w("AIC", "Failed to update menu item color", e);
                }
            }
        }
    }
    // endregion
}

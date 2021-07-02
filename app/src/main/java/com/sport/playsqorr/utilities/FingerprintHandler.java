package com.sport.playsqorr.utilities;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;

    public FingerprintHandler(Context context){

        this.context = context;

    }


    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){

        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);

    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {

        update("There was an Auth Error. " + errString, false);

    }

    @Override
    public void onAuthenticationFailed() {

        update("Auth Failed. ", false);

    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

        update("Error: " + helpString, false);

    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

        update("You can now access the app.", true);

    }

    public static void update(String s, boolean b) {

//        TextView paraLabel = (TextView) ((Activity)context).findViewById(R.id.paraLabel);
//        ImageView imageView =findViewById(R.id.fingerprintImage);

//        paraLabel.setText(s);

        /*if(b == false){

//            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

        } else {

//            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
//            imageView.setImageResource(R.drawable.action_done);
//            imageView.setImageDrawable(R.drawable.action_done);//setDrawableLBackgroundColor(getResources().getColor(R.color.medium_gray));
//            imageView.setBackgroundResource(R.drawable.action_done);

        }*/

    }
}

package com.sport.playsqorr.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.sport.playsqorr.R;

public class BitmapUtils {

    public static Bitmap getCroppedBitmap(Bitmap src, Path path, String type, Context context) {
        Bitmap output = Bitmap.createBitmap(src.getWidth(),
                src.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        if (type.equals("pare")) {
            paint.setColor(context.getResources().getColor(R.color.dark_gray));
        } else {
            paint.setColor(context.getResources().getColor(R.color.gray));
        }


        canvas.drawPath(path, paint);

        // Keeps the source pixels that cover the destination pixels,
        // discards the remaining source and destination pixels.
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        canvas.drawBitmap(src, 0, 0, paint);

        return output;
    }

    public static Bitmap getCroppedBitmap(Bitmap src, Path path) {
        Bitmap output = Bitmap.createBitmap(src.getWidth(),
                src.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(0XFF2f2f2f);

        paint.setColor(0XFF000000);


        canvas.drawPath(path, paint);

        // Keeps the source pixels that cover the destination pixels,
        // discards the remaining source and destination pixels.
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(src, 0, 0, paint);

        return output;
    }
    public static Bitmap newGetCroppedBitmap(Bitmap src, Path path) {
        Bitmap output = Bitmap.createBitmap(src.getWidth(),
                src.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(0XFF2f2f2f);

        paint.setColor(0XFF000000);


        canvas.drawPath(path, paint);

        // Keeps the source pixels that cover the destination pixels,
        // discards the remaining source and destination pixels.
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(src, 0, 0, paint);

        return output;
    }
}

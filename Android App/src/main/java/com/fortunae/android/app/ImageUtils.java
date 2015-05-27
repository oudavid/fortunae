package com.fortunae.android.app;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.nio.ByteBuffer;

/**
 * Created by DOu on 5/18/15.
 */
public class ImageUtils {
    public static final int REQUEST_CODE_GALLERY = 1;

    public static int getOrientation(Context context, Uri photoUri) {
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION },null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public static byte[] convert(Bitmap bitmapImage) {
        //calculate how many bytes our image consists of.
        int bytes = bitmapImage.getByteCount();
        //or we can calculate bytes this way. Use a different value than 4 if you don't use 32bit images.
        //int bytes = b.getWidth()*b.getHeight()*4;

        ByteBuffer buffer = ByteBuffer.allocate(bytes); //Create a new buffer
        bitmapImage.copyPixelsToBuffer(buffer); //Move the byte data to the buffer

        return buffer.array(); //Get the underlying array containing the data.
    }
}

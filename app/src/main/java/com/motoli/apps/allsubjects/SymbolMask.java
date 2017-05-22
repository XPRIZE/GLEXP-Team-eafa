package com.motoli.apps.allsubjects;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/3/2016.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: DrawingView */
class SymbolMask {
    byte[] bytes;
    int bytesNum;
    boolean empty;
    int maskHeight;

    int maskWidth;
    HashMap<String,String> mArray;

    public SymbolMask(Bitmap mask) {
        this.empty = true;
        BitmapFactory.Options bmo = new BitmapFactory.Options();
        bmo.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        mask.setConfig(bmo.inPreferredConfig);

        Log.i("SymbolMask", "Creating symbol mask");
        this.maskWidth = mask.getWidth();
        this.maskHeight = mask.getHeight();
        this.bytesNum = mask.getRowBytes() * mask.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(mask.getByteCount());
        mask.copyPixelsToBuffer(byteBuffer);
     //   ByteArrayOutputStream stream = new ByteArrayOutputStream();
    //    mask.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        stream.toByteArray();
        this.bytes = bitmapToByteArray(mask);
        this.empty = true;
        mArray= new HashMap<>();
        int mCount=0;
        for (byte b : this.bytes) {
            if (b != 0) { /*use to be null*/
                this.empty = false;
            }
            if(b > 0){
                int ib=b;

                mArray.put(String.valueOf(mCount),String.valueOf(ib));
            }
            mCount++;
        }
        if (this.empty) {
            Log.i("SymbolMask", "SymbolMask is empty");
        }
    }

    public static byte[] bitmapToByteArray(Bitmap bm) {
        // Create the buffer with the correct size
        int iBytes = bm.getWidth() * bm.getHeight() * 4;
        ByteBuffer buffer = ByteBuffer.allocate(iBytes);

        // Log.e("DBG", buffer.remaining()+""); -- Returns a correct number based on dimensions
        // Copy to buffer and then into byte array
        bm.copyPixelsToBuffer(buffer);

        // Log.e("DBG", buffer.remaining()+""); -- Returns 0
        return buffer.array();
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    boolean contains(int px, int py) {
        int idx = px + (this.maskWidth * py);
        if (this.bytesNum <= 0 || idx < 0 || idx >= this.bytesNum || this.bytes[idx] == 0) {
            return false;
        }
        return true;
    }

     Bitmap returnBitmap(){
         Bitmap mask=BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
         return mask;
     }
}

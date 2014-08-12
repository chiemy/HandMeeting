package com.handmeeting.handmeeting.BitmapTools;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapTools {
	public static int calculateInSampleSize(
			BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	
	        final int heightRatio = Math.round((float) height / (float) reqHeight);
	        final int widthRatio = Math.round((float) width / (float) reqWidth);
	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	    }
	
	    return inSampleSize;
	}
	public static Bitmap decodeSampledBitmapFromResource(byte [] data, int reqWidth, int reqHeight) {

	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    //Ö»»ñµÃÔ­Ê¼Í¼Æ¬µÄ¿í¶ÈºÍ¸ß¶È
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeByteArray(data, 0, data.length,options);
	    options.inPurgeable = true;
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeByteArray(data,0,data.length, options);
	}
	public static Bitmap decodeSampledBitmapFromResource(String filePath, int reqWidth, int reqHeight) {

	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(filePath, options);

	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(filePath, options);
	}
	public static Bitmap decodeSampledBitmapFromResource(InputStream in, int reqWidth, int reqHeight) {
		Bitmap bitmap = null;
		if(in != null){
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(in, null, options);
			
			options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
			
			options.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeStream(in, null, options);
			
		}
	    return bitmap;
	}
}

package com.qust.assistant.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.regex.Pattern;

import androidx.annotation.Nullable;

public class ParamUtil{
	
	private static final Pattern FLOAT_PATTERN = Pattern.compile("[0-9\\\\.]*");
	
	public static boolean isFloat(String str){
		return FLOAT_PATTERN.matcher(str).matches();
	}
	
	/**
	 * dp 转 px
	 */
	public static int dp2px(Context context,float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	/**
	 * px 转 dp
	 */
	public static int px2dp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	@Nullable
	public static Bitmap createBarCode(String code){
		if(code == null) return null;
		try{
			BitMatrix bitMatrix = new MultiFormatWriter().encode(code, BarcodeFormat.CODE_128, 1000, 10);
			int width = bitMatrix.getWidth();
			int height = bitMatrix.getHeight();
			int[] pixels = new int[width * height];
			for(int y = 0; y < height; y++){
				int offset = y * width;
				for(int x = 0; x < width; x++){
					pixels[offset + x] = bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;
				}
			}
			Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
			return bitmap;
		}catch(WriterException ignored){
			return null;
		}
	}
}

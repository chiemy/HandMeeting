package com.handmeeting.handmeeting;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class MyApp extends Application {
	private LruCache<String, Bitmap> cache;
	public String accessToken;
	public String userId;
	@Override
	public void onCreate() {
		super.onCreate();
		final long max = Runtime.getRuntime().maxMemory();
		final long total = Runtime.getRuntime().totalMemory();
		
		cache = new LruCache<String,Bitmap>((int)(max - total) / 2){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
			}
			@Override
			protected void entryRemoved(boolean evicted, String key,
					Bitmap oldValue, Bitmap newValue) {
				super.entryRemoved(evicted, key, oldValue, newValue);
				if(evicted){
					oldValue.recycle();
					remove(key);
				}
			}
		};
	}
	public LruCache<String, Bitmap> getCache(){
		return cache;
	}
}

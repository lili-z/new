package com.example.news.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.news.modle.Callback;

public class BitmapUrl {
	
	public static Bitmap getBitmap(String urlpath) throws Exception{
		URL url=new URL(urlpath);
		HttpURLConnection conn=(HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		InputStream is=conn.getInputStream();
		Bitmap bitmap=BitmapFactory.decodeStream(is);
		return bitmap;
	}

	public static void  getbigBitmap(final String image_url,final Callback callback) {
		new AsyncTask<String, String, Bitmap>(){

			@Override
			protected Bitmap doInBackground(String... params) {
				
				try {
					URL	url = new URL(image_url);
					HttpURLConnection conn=(HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					InputStream is=conn.getInputStream();
					Bitmap bitmap=BitmapFactory.decodeStream(is);
					return bitmap;
				} catch (Exception e) {
					e.printStackTrace();
				}
			return null;
			}
			protected void onPostExecute(Bitmap result) {
				callback.onLoadText(result);
			}
		}.execute();
		
	}

}

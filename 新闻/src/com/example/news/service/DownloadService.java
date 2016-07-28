package com.example.news.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.news.activity.R;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Notification.Builder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;
import android.util.Log;

public class DownloadService extends IntentService{
	public DownloadService(String name) {
		super(name);
	}
	public DownloadService() {
		super("name");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		sendNotification("图片开始下载", "正在下载");
		String path=intent.getStringExtra("url");
		String title=intent.getStringExtra("title");
		try {

			URL url = new URL(path);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			InputStream is = conn.getInputStream();

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length = 0;
			while ((length = is.read(buffer)) != -1) {
				bos.write(buffer, 0, length);
				bos.flush();
			}
			byte[] bytes = bos.toByteArray();
			// 使用BitmapFactory获取图片的原始宽和高
//			Options opts = new Options();
//			// 仅仅加载图片的边界属性
//			opts.inJustDecodeBounds = true;
//			BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
//			// 通过目标宽和高计算图片的压缩比例
//			int w = opts.outWidth;
//			int h = opts.outHeight;
//			int scale = w > h ? w : h;
//			// 给Options属性设置压缩比例
//			opts.inJustDecodeBounds = false;
//			opts.inSampleSize = scale;
			// 重新解析byte[] 获取Bitmap
			Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

			String []s=path.split("-");
			String filename = path.substring(path.lastIndexOf("/") + 1);
			File f = new File(this.getCacheDir(), filename);
			File file = new File(f.getAbsolutePath());
			if (!file.getParentFile().exists()) { // 父目录不存在
				file.getParentFile().mkdirs(); // 创建父目录
			}
			FileOutputStream os = new FileOutputStream(file);
			bitmap.compress(CompressFormat.JPEG, 100, os);

			os.close();
			bos.close();
			//重新出现通知的滚动消息
			cancelNotification();
			sendNotification("图片下载完成", "图片下载完成");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendNotification(String ticker, String text){
		NotificationManager manager = (NotificationManager)
				getSystemService(NOTIFICATION_SERVICE);
		Notification.Builder builder = new Builder(this);
		builder.setTicker(ticker)
		.setContentTitle("图片下载")
		.setContentText(text)
		.setSmallIcon(R.drawable.ic_launcher);
		manager.notify(1, builder.build());
	}
	public void cancelNotification(){
		NotificationManager manager =(NotificationManager)
				getSystemService(NOTIFICATION_SERVICE);
		manager.cancel(1);
	}
}

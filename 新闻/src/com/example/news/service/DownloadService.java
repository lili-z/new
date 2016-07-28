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
		
		sendNotification("ͼƬ��ʼ����", "��������");
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
			// ʹ��BitmapFactory��ȡͼƬ��ԭʼ��͸�
//			Options opts = new Options();
//			// ��������ͼƬ�ı߽�����
//			opts.inJustDecodeBounds = true;
//			BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
//			// ͨ��Ŀ���͸߼���ͼƬ��ѹ������
//			int w = opts.outWidth;
//			int h = opts.outHeight;
//			int scale = w > h ? w : h;
//			// ��Options��������ѹ������
//			opts.inJustDecodeBounds = false;
//			opts.inSampleSize = scale;
			// ���½���byte[] ��ȡBitmap
			Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

			String []s=path.split("-");
			String filename = path.substring(path.lastIndexOf("/") + 1);
			File f = new File(this.getCacheDir(), filename);
			File file = new File(f.getAbsolutePath());
			if (!file.getParentFile().exists()) { // ��Ŀ¼������
				file.getParentFile().mkdirs(); // ������Ŀ¼
			}
			FileOutputStream os = new FileOutputStream(file);
			bitmap.compress(CompressFormat.JPEG, 100, os);

			os.close();
			bos.close();
			//���³���֪ͨ�Ĺ�����Ϣ
			cancelNotification();
			sendNotification("ͼƬ�������", "ͼƬ�������");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendNotification(String ticker, String text){
		NotificationManager manager = (NotificationManager)
				getSystemService(NOTIFICATION_SERVICE);
		Notification.Builder builder = new Builder(this);
		builder.setTicker(ticker)
		.setContentTitle("ͼƬ����")
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

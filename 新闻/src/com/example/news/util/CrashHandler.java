package com.example.news.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import com.example.news.activity.MainActivity;
import com.example.news.activity.NewsActivity;
import com.example.news.app.NewsApplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

public class CrashHandler implements UncaughtExceptionHandler{
	NewsApplication app;
	public CrashHandler (){
		this.app=NewsApplication.getApp();
	}
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		StringWriter writer=new StringWriter();
		PrintWriter pw=new PrintWriter(writer);
		ex.printStackTrace(pw);
		//联网将s发送给服务器
		String s=writer.toString();
		new Thread(){
			public void run() {
				Looper.prepare();
				Toast.makeText(app, "hold住了一个异常，马上重启", 0).show();
				Looper.loop();
			}
		}.start();
		try {
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		AlarmManager manager=(AlarmManager) app.getSystemService(Context.ALARM_SERVICE);
		Intent intent=new Intent(app,MainActivity.class);
		PendingIntent operation=PendingIntent.getActivity(app, 122, intent, Intent.FLAG_ACTIVITY_NEW_TASK);;
		manager.set(AlarmManager.RTC, System.currentTimeMillis()+2000, operation);
		
	}

}

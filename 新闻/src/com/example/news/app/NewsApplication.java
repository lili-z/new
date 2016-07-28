package com.example.news.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.os.Process;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.example.news.entity.News;
import com.example.news.entity.User;
import com.example.news.modle.NewsModle;
import com.example.news.util.CrashHandler;
import com.example.news.util.JsonUtil;


public class NewsApplication extends Application{

	public  List<News>news=new ArrayList<News>();
	public static  List<News>subNews=new ArrayList<News>();
	public static  List<User>users=new ArrayList<User>();
	private RequestQueue queue;
	static NewsApplication app;
	@Override
	public void onCreate() {
		ApiStoreSDK.init(this, "925b818a93076a6395c20e198ba6a28b");
		super.onCreate();
		queue=Volley.newRequestQueue(this);
		app=this;
//		CrashHandler handler=new CrashHandler();
//		Thread.setDefaultUncaughtExceptionHandler(handler);
	}

//	public void finish(){
//	//遍历activity集合然后杀死
//		Process.killProcess(Process.myPid());
//	}
	public static NewsApplication getApp(){
		return app;
	}
	public RequestQueue getQueue(){
		return queue;
	}
	public  List<News>getNews(){
		return news;
	}

	public void setNews(List<News> news) {
		this.news=news;
	}
	public void setSubNews(News n){
		subNews.add(n);
	}
	public static List<News> getSubNews(){
		return subNews;
	}
	public static void addUser(User user){
		users.add(user);
	}
	public static List<User>getUser(){
		return users;
	}
}

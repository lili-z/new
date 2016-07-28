package com.example.news.modle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.example.news.app.NewsApplication;
import com.example.news.entity.Article;
import com.example.news.entity.Image;
import com.example.news.entity.Jok;
import com.example.news.entity.News;
import com.example.news.entity.Root;
import com.example.news.util.JsonUtil;
import com.google.gson.Gson;

public class NewsModle {
	//	private RequestQueue queue;
	//	public NewsModle(){
	//		 queue=NewsApplication.getApp().getQueue();
	//	}
	private static List<News>news=new ArrayList<News>();
	//	public static List<News>getNews(){
	//		return news;
	//	}
	//	public static void getText(final String texturl,final Callback callback) {
	//		new AsyncTask<String , String, String>(){
	//
	//			@Override
	//			protected String doInBackground(String... params) {
	//			
	//				try {
	//					URL	url = new URL(texturl);
	//					HttpURLConnection conn=(HttpURLConnection) url.openConnection();
	//					conn.setRequestMethod("GET");
	//					InputStream is=conn.getInputStream();
	//					BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"));
	//					StringBuilder builder=new StringBuilder();
	//					String line;
	//					while((line=reader.readLine())!=null){
	//						builder.append(line);
	//					}
	//					String text=builder.toString();
	//					return text;
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//				return null;
	//			}
	//			protected void onPostExecute(String result) {
	//				callback.onLoadText(result);
	//			}
	//			
	//		}.execute();
	//	}
	/**
	 * 联网获取新闻
	 * @param callback
	 */
	public void getNews(final CallbackNews callback){
		Parameters parameters=new Parameters();
		parameters.put("keys", "925b818a93076a6395c20e198ba6a28b");
		ApiStoreSDK.execute("http://apis.baidu.com/songshuxiansheng/news/news", 
				ApiStoreSDK.GET, parameters, new ApiCallBack(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				try {
					List<News>	ns=JsonUtil.getData(arg1);

					if(ns!=null){
						callback.loadNews(ns);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * 获取笑话
	 * @param callback
	 */
	public void getJok(final Callback callback){
		Parameters parameters=new Parameters();
		parameters.put("keys", "925b818a93076a6395c20e198ba6a28b");
		parameters.put("page", 1);
		ApiStoreSDK.execute("http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_text", 
				ApiStoreSDK.GET, parameters, new ApiCallBack(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				try {
					if(arg1!=null){
						List<Jok>joks=JsonUtil.getJoks(arg1);
						if(joks!=null){
							callback.onLoadText(joks);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * 联网获取图片
	 * @param callback
	 */
	public void getPic(final CallbackImage callback) {
		Parameters parameters=new Parameters();
		parameters.put("keys", "925b818a93076a6395c20e198ba6a28b");
		//只能返回10条数据
		parameters.put("num",10);

		ApiStoreSDK.execute("http://apis.baidu.com/txapi/mvtp/meinv", 
				ApiStoreSDK.GET, parameters, new ApiCallBack(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				try {
					List<Image>image=JsonUtil.getImages(arg1);
					if(image!=null){
						List<Image>images=image;
						callback.loadImage(images);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * 获取微信头条
	 * @param callback
	 */
	public void getWeixin(final Callback callback) {
		Parameters parameters=new Parameters();
		parameters.put("keys", "925b818a93076a6395c20e198ba6a28b");
		parameters.put("id", "popular");
		parameters.put("page", 1);

		ApiStoreSDK.execute("http://apis.baidu.com/3023/weixin/channel", 
				ApiStoreSDK.GET, parameters, new ApiCallBack(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				try {
					//Log.i("AAA",arg1);
					Gson gson=new Gson();

					Root root=gson.fromJson(arg1, Root.class);

					List<Article>article=root.getData().getArticle();
					if(article!=null){
						callback.onLoadText(article);;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	public void getVideo(final Callback callback) {
		Parameters parameters=new Parameters();
		parameters.put("keys", "925b818a93076a6395c20e198ba6a28b");
		parameters.put("pld", "1");
		//parameters.put("date","2016-07-17");
		

		ApiStoreSDK.execute("http://api.avatardata.cn/TVTime/Query", 
				ApiStoreSDK.GET, parameters, new ApiCallBack(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				try {
					Log.i("AAA",arg1);
//					Gson gson=new Gson();
//
//					Root root=gson.fromJson(arg1, Root.class);
//
//					List<Article>article=root.getData().getArticle();
//					if(article!=null){
//						callback.onLoadText(article);;
//					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	public interface CallbackImage{
		void loadImage(List<Image> images);
	}
	public interface CallbackNews{
		void loadNews(List<News> news);
	}

}

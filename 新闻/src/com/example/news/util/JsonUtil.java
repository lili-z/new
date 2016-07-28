package com.example.news.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.news.entity.Image;
import com.example.news.entity.Jok;
import com.example.news.entity.News;

public class JsonUtil {
	
	public static List<News>getData(String data) throws Exception{
		List<News>news=new ArrayList<News>();
		JSONObject obj=new JSONObject(data);
		JSONArray arr=obj.getJSONArray("retData");
		for(int i=0;i<arr.length();i++){
		News n=new News();
		JSONObject obj2=arr.getJSONObject(i);
		n.setTitle(obj2.getString("title"));
		n.setUrl(obj2.getString("url"));
		n.setAbstracts(obj2.getString("abstract"));
		n.setImage_url(obj2.getString("image_url"));
		news.add(n);
		}
		return news;
		
	}
	public static List<Image>getImages(String data) throws Exception{
		List<Image>images=new ArrayList<Image>();
		JSONObject obj=new JSONObject(data);
		JSONArray arr=obj.getJSONArray("newslist");
		for(int i=0;i<arr.length();i++){
			Image image=new Image();
			JSONObject obj2=arr.getJSONObject(i);
			image.setCtime(obj2.getString("ctime"));
			image.setTitle(obj2.getString("title"));
			image.setDescription(obj2.getString("description"));
			image.setPicUrl(obj2.getString("picUrl"));
			image.setUrl(obj2.getString("url"));
			images.add(image);
		}
		return images;
		
	}
	public static List<Jok> getJoks(String arg1) throws JSONException {
		List<Jok>joks=new ArrayList<Jok>();
		JSONObject obj=new JSONObject(arg1);
		JSONObject obj2=obj.getJSONObject("showapi_res_body");
		JSONArray arr=obj2.getJSONArray("contentlist");
		for(int i=0;i<arr.length();i++){
			Jok jok=new Jok();
			JSONObject obj3=arr.getJSONObject(i);
			jok.setTitle(obj3.getString("title"));
			String text=obj3.getString("text");
			String regex="[</p>]";
			text=text.replaceAll(regex, "");
			jok.setText(text);
			joks.add(jok);
		}
		return joks;
	}

}

package com.example.news.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.news.activity.R;

public class BitmapUtils {
	private Context context;
	private ListView lv;
	Thread workThread;
	boolean isLoop=true;
	private  List<PathHolder>tasks=new ArrayList<BitmapUtils.PathHolder>();
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			
			switch(msg.what){
			case 1:
				 PathHolder hold=(PathHolder) msg.obj;
				 ImageView imageView=(ImageView) lv.findViewWithTag(hold.path);
				 Bitmap bit=hold.bitmap;
				 if(bit!=null&&imageView!=null){
					 imageView.setImageBitmap(hold.bitmap);
				 }else if(imageView!=null){
					 imageView.setImageResource(R.drawable.ic_launcher);
				 }
				 break;
			case 0:
				break;
			}
		}
	};
	
	public BitmapUtils(Context context,ListView lv){
		this.lv=lv;
		this.context=context;
		workThread=new Thread(){
			@Override
			public void run() {
				Log.i("AAA","bit"+isLoop);
			while(isLoop){
				if(!tasks.isEmpty()){
					PathHolder hold=tasks.remove(0);
					String urlpath=hold.path;
					try {
						Bitmap bitmap = BitmapUrl.getBitmap(urlpath);
						
						if(bitmap!=null){
							hold.bitmap=bitmap;
							Message message=Message.obtain();
							message.obj=hold;
							message.what=1;
							handler.sendMessage(message);
						}else{
							handler.sendEmptyMessage(0);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					try {
						synchronized (workThread) {
							workThread.wait();		
						}
					
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				
				super.run();
			}
			}
		};
		workThread.start();
	}
	
	public  void LoadBitmapForUrl(ImageView iv,String urlPath){
		iv.setTag(urlPath);
		PathHolder holder=new PathHolder();
		holder.path=urlPath;
		tasks.add(holder);
		synchronized (workThread) {
			workThread.notify();
		}
	}
	class PathHolder{
		String path;
		Bitmap bitmap;
	}
	public void stopThread(){
		isLoop=false;
		synchronized (workThread) {
			workThread.notify();
		}
	}

}

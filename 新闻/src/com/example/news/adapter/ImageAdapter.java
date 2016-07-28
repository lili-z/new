package com.example.news.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.example.news.activity.R;
import com.example.news.app.NewsApplication;
import com.example.news.entity.Image;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{

	private Context context;
	private List<Image>images;
	LayoutInflater inflater;
	public static ImageListen listener;
	ViewHolder holder;
	RequestQueue queue;
	public ImageAdapter(Context context,List<Image>images){
		this.context=context;
		this.images=images;
		NewsApplication app=NewsApplication.getApp();
		queue=app.getQueue();
		inflater=LayoutInflater.from(context);
	}
	public void setonItemClickListener(ImageListen rvListener){
		if(rvListener!=null){
		this.listener=rvListener;
		}
	}
	
	public interface ImageListen{
		void onItemClickListener(View view,int position);
		void onLongClickListener(View view,int position);
	}
	@Override
	public int getItemCount() {
		return images.size();
		
	}

	@Override
	public void onBindViewHolder(ViewHolder arg0, int arg1) {
		Image image=images.get(arg1);
		if(image.getPicUrl()!=null){
			
		ImageLoader loader=new ImageLoader(queue, new BitmapCache());
		ImageListener listener=ImageLoader.getImageListener(holder.iv, R.drawable.ic_launcher,R.drawable.ic_launcher);
		loader.get(image.getPicUrl(), listener);
		
		ViewGroup.LayoutParams params=arg0.itemView.getLayoutParams();
		List<Integer>heights=new ArrayList<Integer>();
		for(int i=0;i<images.size();i++){
			heights.add(200+new Random().nextInt(60));
		}
		params.height=heights.get(arg1);
		arg0.itemView.setLayoutParams(params);
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		View view=inflater.inflate(R.layout.image_item,arg0,false);
		holder=new ViewHolder(view,listener);
		return holder;
	}
	
	class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener,OnLongClickListener{
		ImageView iv;
		public ViewHolder(View itemView,ImageListen listener) {
			super(itemView);
			if(itemView!=null){
				ImageAdapter.listener=listener;
				iv=(ImageView) itemView.findViewById(R.id.iv_rv);
				itemView.setOnClickListener(this);
				itemView.setOnLongClickListener(this);
				
			}
		}
		@Override
		public void onClick(View v) {
			listener.onItemClickListener(v, getLayoutPosition());
		}
		@Override
		public boolean onLongClick(View v) {
			listener.onLongClickListener(v, getLayoutPosition());
			return false;
		}
		
	}
	public class BitmapCache implements ImageCache {  
		  
	    private LruCache<String, Bitmap> mCache;  
	  
	    public BitmapCache() {  
	        int maxSize = 10 * 1024 * 1024;  
	        mCache = new LruCache<String, Bitmap>(maxSize) {  
	            @Override  
	            protected int sizeOf(String key, Bitmap bitmap) {  
	                return bitmap.getRowBytes() * bitmap.getHeight();  
	            }  
	        };  
	    }  
	    @Override  
	    public Bitmap getBitmap(String url) {  
	        return mCache.get(url);  
	    }  
	    @Override  
	    public void putBitmap(String url, Bitmap bitmap) {  
	        mCache.put(url, bitmap);  
	    }  
	}  
	
}

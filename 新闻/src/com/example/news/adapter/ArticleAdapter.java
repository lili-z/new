package com.example.news.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.news.activity.R;
import com.example.news.app.NewsApplication;
import com.example.news.entity.Article;
import com.example.news.util.BitmapUtils;

public class ArticleAdapter extends BaseAdapter{

	private Context context;
	private List<Article>article;
	private LayoutInflater inflater;
	public BitmapUtils biUtils;
	private RequestQueue queue;
	
	public ArticleAdapter(Context context,List<Article>article,ListView  lv){
		this.context=context;
		this.article=article;
		inflater=LayoutInflater.from(context);
		NewsApplication app=NewsApplication.getApp();
		queue=app.getQueue();
	}
	@Override
	public int getCount() {
		return article.size();
	}

	@Override
	public Article getItem(int position) {
		
		return article.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Article n=article.get(position);
		final ViewHolder holder;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.list_item, null);
			holder=new ViewHolder();
			holder.tvTitle=(TextView) convertView.findViewById(R.id.tv_title_list);
			holder.tvContent=(TextView) convertView.findViewById(R.id.tv_content_list);
			holder.iv=(ImageView) convertView.findViewById(R.id.iv_list);
			convertView.setTag(holder);
		}else{
		holder=(ViewHolder) convertView.getTag();
		}
		holder.tvTitle.setText(n.getTitle());
		holder.tvContent.setText("×÷Õß£º"+n.getAuthor());
		ImageRequest imageRequest=new ImageRequest(n.getImg(), new Listener<Bitmap>() {

			@Override
			public void onResponse(Bitmap response) {
				holder.iv.setImageBitmap(response);
				
			}
		}, 80,80,  Config.RGB_565, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.i("AAA",error.toString());
				
			}
		});
		queue.add(imageRequest);
		return convertView;
	}
	class ViewHolder{
		TextView tvTitle;
		TextView tvContent;
		ImageView iv;
	}
	public void stopThread(){
		biUtils.stopThread();
	}

}

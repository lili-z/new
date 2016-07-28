package com.example.news.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.news.activity.R;
import com.example.news.entity.News;
import com.example.news.util.BitmapUtils;

public class NewsAdapter extends BaseAdapter{

	private Context context;
	private List<News>news;
	private LayoutInflater inflater;
	public BitmapUtils biUtils;
	private onLong onlong;
	
	public NewsAdapter(Context context,List<News>news,ListView  lv){
		this.context=context;
		this.news=news;
		inflater=LayoutInflater.from(context);
		biUtils=new BitmapUtils(context,lv);
	}
	@Override
	public int getCount() {
		return news.size();
	}

	@Override
	public News getItem(int position) {
		
		return news.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		News n=news.get(position);
		ViewHolder holder;
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
		holder.tvContent.setText(n.getAbstracts());
		biUtils.LoadBitmapForUrl(holder.iv,n.getImage_url());
		convertView.setOnLongClickListener(new longclick(position,onlong,1));
		convertView.setOnClickListener(new longclick(position,onlong,0));
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
	public void setonlong(onLong onLong) {
		if(onLong!=null){
		this.onlong=onLong;
		}
	}
	public class longclick implements OnLongClickListener ,OnClickListener{
		public int i;
		onLong onlong;
		int type;
		public longclick(int i,onLong onlong,int type){
			this.i=i;
			this.onlong=onlong;
			this.type=type;
		}
		
		@Override
		public boolean onLongClick(View v) {
			onlong.Longclick(v, i);
			return true;
		}
		
		@Override
		public void onClick(View v) {
			onlong.click(v, i);
			
		}
		
		
		
	}
	public interface onLong{
		void Longclick(View v,int position);
		void click(View v,int position);
	}
	
}

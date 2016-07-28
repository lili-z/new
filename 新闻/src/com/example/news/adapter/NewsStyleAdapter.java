package com.example.news.adapter;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.news.activity.R;


public class NewsStyleAdapter extends RecyclerView.Adapter<NewsStyleAdapter.ViewHolder>{

	private Context context;
	private List<String>styles;
	LayoutInflater inflater;
	public static RvListener listener;
	ViewHolder holder;
	public NewsStyleAdapter(Context context,List<String>styles){
		this.context=context;
		this.styles=styles;
		inflater=LayoutInflater.from(context);
	}
	public void setonItemClickListener(RvListener rvListener){
		this.listener=rvListener;
	}
	
	public interface RvListener{
		void onItemClickListener(View view,int position);
	}
	@Override
	public int getItemCount() {
		return styles.size();
		
	}

	@Override
	public void onBindViewHolder(ViewHolder arg0, int arg1) {
		arg0.tv.setText(styles.get(arg1));
		arg0.tv.setTag(arg1+"");
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		View view=inflater.inflate(R.layout.rv_item_style,arg0,false);
		holder=new ViewHolder(view,listener);
		return holder;
	}
	
//	public void settvColor(int position){
//		for(int i=0;i<styles.size();i++){
//			TextView tv=(TextView) holder.itemView.findViewWithTag(i+"");
//			if(i==position){
//				if(tv!=null){
//				holder.tv.setTextColor(Color.RED);
//				}
//			}
//		}
//	}
	class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener{
		TextView tv;
		public ViewHolder(View itemView,RvListener listener) {
			super(itemView);
			if(itemView!=null){
				NewsStyleAdapter.listener=listener;
				tv=(TextView) itemView.findViewById(R.id.tv_rv_item);
				itemView.setOnClickListener(this);
			}
		}
		@Override
		public void onClick(View v) {
			listener.onItemClickListener(v, getLayoutPosition());
		}
		
	}
int	lastposition;
	
}

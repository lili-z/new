package com.example.news.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.news.activity.NewsDetailActivity;
import com.example.news.activity.R;
import com.example.news.adapter.NewsAdapter;
import com.example.news.adapter.NewsAdapter.onLong;
import com.example.news.app.NewsApplication;
import com.example.news.entity.News;
import com.example.news.modle.NewsModle;
import com.example.news.modle.NewsModle.CallbackNews;

public class NewsFragment extends Fragment{

	private ListView lv;
	static List<News>news=new ArrayList<News>();
	private static NewsAdapter newsAdapter;
	NewsApplication app;
	private boolean isbotom=false;
	int i;
	NewsModle modle;
	private boolean click;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_news,null);
		app=(NewsApplication) getActivity().getApplication();
		modle=new NewsModle();
		getData();
		getData();
		setViews(view);
		
		return view;
	}
	@Override
	public void onDestroyView() {
		newsAdapter=null;
		news.clear();
		super.onDestroyView();
	}
	private  void getData() {
		modle.getNews(new CallbackNews() {
			
			@Override
			public void loadNews(List<News> news) {
				try {
					
					if(news!=null){
						NewsFragment.news.addAll(news);
					if(newsAdapter!=null){
						app.setNews(NewsFragment.news);
						newsAdapter.notifyDataSetChanged();
					}
					if(news.size()>=10*i){
						newsAdapter=new NewsAdapter(getActivity(), NewsFragment.news,lv);
						lv.setAdapter(newsAdapter);
						setListener();
					}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
		
	}
	
	private void setListener() {
newsAdapter.setonlong(new onLong(){

			@Override
			public void Longclick(View v, int position) {
					News n=news.get(position);
					NewsApplication.getApp().setSubNews(n);
					Toast.makeText(getActivity(), "�ղسɹ�", 0).show();
				
			}

			@Override
			public void click(View v, int position) {
				News n=news.get(position);
				Intent intent=new Intent(getActivity(),NewsDetailActivity.class);
				intent.putExtra("path", n.getUrl());
				startActivity(intent);
				
			}
		});
		/**
		 * ��ҳ����
		 */
		lv.setOnScrollListener(new OnScrollListener() {
			
			/**
			 * ��������
			 */
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if(scrollState==OnScrollListener.SCROLL_STATE_IDLE&&isbotom==true){
					i=1;
					getData();//�������̶�������������û������
					getData();
					i++;
				}
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				//�ж��Ƿ񵽴�ײ�
				if(firstVisibleItem+visibleItemCount==totalItemCount){
					isbotom=true;
				}else{
					isbotom=false;
				}
				
			}
		});
	}


	private void setViews(View view) {
		lv=(ListView) view.findViewById(R.id.lv_item);
		
		
	}

	public void setClick(boolean b) {
		click=b;
		
	}
	public static void stop(){
		newsAdapter.stopThread();
	}
}

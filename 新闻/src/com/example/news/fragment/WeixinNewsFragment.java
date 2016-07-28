package com.example.news.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.news.activity.NewsDetailActivity;
import com.example.news.activity.R;
import com.example.news.adapter.ArticleAdapter;
import com.example.news.entity.Article;
import com.example.news.modle.Callback;
import com.example.news.modle.NewsModle;

public class WeixinNewsFragment extends Fragment{

	private NewsModle modle;
	static List<Article>article;
	private ArticleAdapter articleAdapter;
	ListView lv;
	boolean isbotom;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_weixin, null);
		modle=new NewsModle();
		setViews(view);
		setListener();
		getData();
		return view;
	}

	private void getData() {
		modle.getWeixin(new  Callback() {

			@Override
			public void onLoadText(Object obj) {
				if(article==null){
				article=new ArrayList<Article>();
				List<Article>article=(List<Article>)obj;
				WeixinNewsFragment.article.addAll(article);
				articleAdapter=new ArticleAdapter(getActivity(), WeixinNewsFragment.article,lv);
				lv.setAdapter(articleAdapter);
				}else{
					List<Article>article=(List<Article>)obj;
					WeixinNewsFragment.article.addAll(article);
					articleAdapter.notifyDataSetChanged();
					
				}
			}
		} );

	}
	@Override
	public void onDestroyView() {
		article=null;
		articleAdapter=null;
		super.onDestroyView();
	}
	private void setListener() {
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Article a=article.get(position);
				Intent intent=new Intent(getActivity(),NewsDetailActivity.class);
				intent.putExtra("path", a.getUrl());
				startActivity(intent);
				
			}
		});
		lv.setOnScrollListener(new OnScrollListener() {
			
			/**
			 * 加载数据
			 */
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if(scrollState==OnScrollListener.SCROLL_STATE_IDLE&&isbotom==true){
					getData();
				}
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				//判断是否到达底部
				if(firstVisibleItem+visibleItemCount==totalItemCount){
					isbotom=true;
				}else{
					isbotom=false;
				}
				
			}
		});

	}

	private void setViews(View view) {
		lv=(ListView) view.findViewById(R.id.lv);

	}
}

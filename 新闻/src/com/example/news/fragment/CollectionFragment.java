package com.example.news.fragment;


import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.news.activity.NewsDetailActivity;
import com.example.news.activity.R;
import com.example.news.adapter.NewsAdapter;
import com.example.news.adapter.NewsAdapter.onLong;
import com.example.news.app.NewsApplication;
import com.example.news.entity.News;

public class CollectionFragment extends Fragment{

	private List<News> news;
	private ListView lv;
	public NewsAdapter  adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.activity_collection, null);
		news=NewsApplication.getSubNews();
		lv=(ListView) view.findViewById(R.id.lv_collection);
		adapter=new NewsAdapter(getActivity(), news, lv);
		lv.setAdapter(adapter);
		adapter.setonlong(new onLong(){

			@Override
			public void Longclick(View v, int position) {
				news.remove(position);
				adapter.notifyDataSetChanged();
				Toast.makeText(getActivity(), "»°œ˚ ’≤ÿ", 0).show();
			}

			@Override
			public void click(View v, int position) {
				if(news!=null&&news.size()!=0){
					News n=news.get(position);
					Intent intent=new Intent(getActivity(),NewsDetailActivity.class);
					intent.putExtra("path", n.getUrl());
					startActivity(intent);
					}
				
			}
		});
		
		return view;
	}

}

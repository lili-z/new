package com.example.news.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news.activity.R;
import com.example.news.entity.Jok;
import com.example.news.modle.Callback;
import com.example.news.modle.NewsModle;

public class JokFragment extends Fragment implements OnClickListener{

	private TextView tvTitle;
	private TextView tvText;
	Button btNext;
	Button btPrevious;
	NewsModle modle;
	static List<Jok>joks;
	int index;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_jok, null);
		modle=new NewsModle();
		setViews(view);
		setListener();
		getJoks();
		return view;
	}
	private void getJoks() {
		modle.getJok(new Callback() {

			@Override
			public void onLoadText(Object obj) {
			if(JokFragment.joks==null){
				JokFragment.joks=new ArrayList<Jok>();
				List<Jok>joks=(List<Jok>)obj;
				JokFragment.joks.addAll(joks);
				Jok jok=joks.get(0);
				tvTitle.setText(jok.getTitle());
				tvText.setText(jok.getText());
			}else {
				List<Jok>joks=(List<Jok>)obj;
				JokFragment.joks.addAll(joks);
				Jok jok=joks.get(index);
				tvTitle.setText(jok.getTitle());
				tvText.setText(jok.getText());
			}
				
			}
		});
	}
	private void setListener() {
		btNext.setOnClickListener(this);
		btPrevious.setOnClickListener(this);
		
	}
	private void setViews(View view) {
		tvTitle=(TextView) view.findViewById(R.id.tv_title);
		tvText=(TextView) view.findViewById(R.id.tv_text);
		btNext=(Button) view.findViewById(R.id.bt_next);
		btPrevious=(Button) view.findViewById(R.id.bt_prevous);
		
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.bt_next:
			index++;
			if(index==joks.size()){
				getJoks();
				return;
			}
			Jok jok=joks.get(index);
			tvTitle.setText(jok.getTitle());
			tvText.setText(jok.getText());
			break;
		case R.id.bt_prevous:
			index--;
			if(index<0){
				Toast.makeText(getActivity(), "已经是第一条了", 0).show();
				index=0;
				return;
			}
			jok=joks.get(index);
			tvTitle.setText(jok.getTitle());
			tvText.setText(jok.getText());
			break;
		}
		
	}
}

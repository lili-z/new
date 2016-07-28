package com.example.news.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.news.activity.NewsDetailActivity;
import com.example.news.activity.R;
import com.example.news.adapter.ImageAdapter;
import com.example.news.adapter.ImageAdapter.ImageListen;
import com.example.news.entity.Image;
import com.example.news.listener.OnRcvScrollListener;
import com.example.news.modle.NewsModle;
import com.example.news.modle.NewsModle.CallbackImage;
import com.example.news.service.DownloadService;
import com.example.news.util.MyItemDecoration;

public class ImageFragment extends Fragment {

	List<Image>	image=new ArrayList<Image>();
	RecyclerView rv;
	ImageAdapter imageAdapter;
	View view;
	NewsModle modle;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 view=inflater.inflate(R.layout.activity_image, null);
		setViews();
		 modle=new NewsModle();
		 getImage();
		 getImage();
		 getImage();
		return view;
	}

	public void getImage(){
		modle.getPic(new CallbackImage() {
		
			@Override
			public void loadImage(List<Image> images) {
				ImageFragment.this.image.addAll(images);
				if(ImageFragment.this.image.size()>30){
					imageAdapter.notifyDataSetChanged();
					return;
				}
				if(ImageFragment.this.image.size()>=30){
					setRvAdapter();
				}
			}
		});
	}
	

	@Override
	public void onDestroyView() {
		imageAdapter=null;
		image.clear();
		super.onDestroyView();
	}

	private void setViews() {
		rv=(RecyclerView) view.findViewById(R.id.rv_pic);
		
	}



	protected void setRvAdapter() {
		StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
		rv.setLayoutManager(manager);
		if(!image.isEmpty()){
		imageAdapter=new  ImageAdapter(getActivity(), image);
		rv.setAdapter(imageAdapter);
		rv.addItemDecoration(new MyItemDecoration(4));
		rv.setOnScrollListener(new OnRcvScrollListener() {
			@Override
			public void onBottom() {
				getImage();
			}
		});
		
		imageAdapter.setonItemClickListener(new ImageListen() {
			
			@Override
			public void onItemClickListener(View view, int position) {
				Image i=image.get(position);
				Intent intent=new Intent(getActivity(),NewsDetailActivity.class);
				intent.putExtra("path", i.getUrl());
				startActivity(intent);
				
			}

			@Override
			public void onLongClickListener(View view, int position) {
				AlertDialogListener listener=new AlertDialogListener(position);
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("下载？").setPositiveButton("下载", listener)
				.setNegativeButton("取消", listener);
				AlertDialog dialog=builder.create();
				dialog.show();
			}

		});
		
		
		}
	}
	public class AlertDialogListener implements OnClickListener{
		private int position;
		public AlertDialogListener(int position){
			this.position=position;
		}
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch(which){
			case DialogInterface.BUTTON_POSITIVE:
				Intent intent = new Intent(getActivity(), DownloadService.class);
				Image i=image.get(position);
				intent.putExtra("url", i.getPicUrl());
				intent.putExtra("title",i.getTitle());
				getActivity().startService(intent);
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				break;
			}
			
		}
		
	}

}

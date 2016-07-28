package com.example.news.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class MyItemDecoration extends RecyclerView.ItemDecoration{

	private int space;
	public MyItemDecoration(int i){
		this.space=i;
	}
	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
			State state) {
		outRect.left=space;
		outRect.top=space;
		outRect.right=space;
		outRect.bottom=space;
	}
}

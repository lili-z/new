package com.example.news.listener;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public abstract class OnRcvScrollListener extends RecyclerView.OnScrollListener implements OnBottomListener{

	int layoutManagerType;
	private int[] lastPositions;
	private boolean isLoadingMore = false;
	private int lastVisibleItemPosition;
	private int currentScrollState = 0;
	@Override
	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
		super.onScrolled(recyclerView, dx, dy);
		RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
		StaggeredGridLayoutManager staggeredGridLayoutManager
		= (StaggeredGridLayoutManager) layoutManager;
		if (lastPositions == null) {
			lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
		}
		staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
		lastVisibleItemPosition = findMax(lastPositions);
	}
	
	private int findMax(int[] lastPositions) {
		int max = lastPositions[0];
		for (int value : lastPositions) {
			if (value > max) {
				max = value;
			}
		}
		return max;
	}
	@Override
	public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
		super.onScrollStateChanged(recyclerView, newState);
		currentScrollState = newState;
		RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
		int visibleItemCount = layoutManager.getChildCount();
		int totalItemCount = layoutManager.getItemCount();
		if ((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE &&
				(lastVisibleItemPosition) >= totalItemCount - 1)) {
			//调用接口方法
			onBottom();
		}
	}

	
}
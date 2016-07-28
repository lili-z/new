package com.example.news.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.news.fragment.CollectionFragment;
import com.example.news.fragment.ImageFragment;
import com.example.news.fragment.MineFragment;
import com.example.news.fragment.NewFragment;
import com.example.news.fragment.NewsFragment;

public class MainActivity extends FragmentActivity {

	RadioGroup rg;
	FrameLayout fl;
	FrameAdapter adapter;
	RadioButton rb0;
	RadioButton rb1;
	RadioButton rb2;
	RadioButton rb3;
	FragmentTransaction transaction;
	List<Fragment>fragments;
	private int lastPosition;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findView();
		//setFragments();
		setListener();
		setAdapter();
		rg.check(R.id.rb0);
		fragments=new ArrayList<Fragment>();
		fragments.add(new NewFragment());
		fragments.add(new ImageFragment());
		fragments.add(new CollectionFragment());
		fragments.add(new MineFragment());
		FragmentManager manager=getSupportFragmentManager();
		transaction=manager.beginTransaction();
		Fragment fragment=fragments.get(0);
		transaction.add(R.id.fl,fragment);
		transaction.show(fragment);
		transaction.commit();
		lastPosition=0;
	}
	/**
	 * 设置显示的界面
	 */
	private void setFragment(int i) {
		if(i==lastPosition){
			return;
		}
		FragmentManager manager=getSupportFragmentManager();
		transaction=manager.beginTransaction();
		Fragment fragment=fragments.get(i);
		if(!fragment.isAdded()){
			transaction.add(R.id.fl,fragment);
		}
		transaction.hide(fragments.get(lastPosition));
		transaction.show(fragment);
		transaction.commit();
		lastPosition=i;
	}

	private void findView() {
		rg=(RadioGroup) findViewById(R.id.rg_type);
		fl=(FrameLayout) findViewById(R.id.fl);
		rb0=(RadioButton) findViewById(R.id.rb0);
		rb1=(RadioButton) findViewById(R.id.rb1);
		rb2=(RadioButton) findViewById(R.id.rb2);
		rb3=(RadioButton) findViewById(R.id.rb3);

	}

	/**
	 * 对底部菜单栏进行点击事件监听并跳转至相应界面
	 */
	private void setListener() {
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId){
				case R.id.rb0:
					rg.check(R.id.rb0);
					setFragment(0);
					break;
				case R.id.rb1:
					rg.check(R.id.rb1);
					setFragment(1);
					break;
				case R.id.rb2:
					rg.check(R.id.rb2);
					setFragment(2);
					break;
				case R.id.rb3:
					rg.check(R.id.rb3);
					setFragment(3);
					break;
				}

			}
		});
	}



	private void setAdapter() {
		adapter=new FrameAdapter(getSupportFragmentManager());


	}

	private class FrameAdapter extends FragmentPagerAdapter{

		public FrameAdapter(FragmentManager fm) {
			super(fm);

		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment=null;
			switch(position){
			case 0:
				fragment=new NewFragment();
				break;
			case 1:
				fragment=new ImageFragment();
				break;
			case 2:
				fragment=new CollectionFragment();
				break;
			case 3:
				fragment=new MineFragment();
				break;

			}
			return fragment;
		}

		@Override
		public int getCount() {

			return 4;
		}

	}

	@Override
	protected void onDestroy() {
		NewsFragment.stop();
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.one, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

package com.example.news.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.news.activity.R;
import com.example.news.adapter.NewsStyleAdapter;
import com.example.news.adapter.NewsStyleAdapter.RvListener;
import com.example.news.fragment.JokFragment;
import com.example.news.fragment.NewsFragment;
import com.example.news.fragment.OtherFragment;
import com.example.news.fragment.WeixinNewsFragment;

public class NewFragment extends Fragment{

	RecyclerView rv;
	ViewPager vp;
	RadioGroup rg;
	List<Fragment>fragments=new ArrayList<Fragment>();
	FragmentPagerAdapter adapter;
	RelativeLayout rlDeil;
	TextView tvTitle;
	ImageView iv;
	TextView tvContent;
	AlertDialog dialog;
	ScrollView sc;
	TextView tvSay;
	private Button btAdd;
	EditText etSay;
	Button btSay;
	private Button btOk;
	private LinearLayout llAdd;
	private List<String>styles=new ArrayList<String>();
	RecyclerView rc;
	NewsStyleAdapter newsStyleAdapter;
	View view;
	private CheckBox cb0;
	private CheckBox cb1;
	private CheckBox cb2;
	private CheckBox cb3;
	private CheckBox cb4;
	private CheckBox cb5;
	private CheckBox cb6;
	private CheckBox cb7;
	private CheckBox cb8;
	private CheckBox cb9;
	private CheckBox cb10;
	private CheckBox cb11;
	private NewsFragment newsFragment;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.activity_news, null);
		findViews();
		setAdapter();
		setListener();
		return view;
	}

	/**
	 * 监听选择的爱好对数据进行更新
	 * @author wszh
	 *
	 */
public class ClickListener implements android.view.View.OnClickListener{

	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.bt_add:
			llAdd.setVisibility(View.VISIBLE);
			newsFragment.setClick(false);
			break;
		case R.id.bt_ok:
			llAdd.setVisibility(View.INVISIBLE);
			newsStyleAdapter.notifyDataSetChanged();
			adapter.notifyDataSetChanged();
			newsFragment.setClick(true);
			break;
		default:
			CheckBox check=(CheckBox) view;
			String s=check.getText().toString();
			if(check.isChecked()){
				styles.add(s);
				//加载添加的页面
				fragments.add(new OtherFragment());
			}else{
			int i=vp.getCurrentItem();
			int j=styles.indexOf(s);
			if(i==j){
				vp.setCurrentItem(0);
			}
				fragments.remove(styles.indexOf(s));
				styles.remove(s);
			}
			newsStyleAdapter.notifyDataSetChanged();
			adapter.notifyDataSetChanged();
			break;
		}
		
	}
	
}
	/**
	 * 给添加爱好按钮及选择的爱好进行监听
	 */
	public  void setListener() {
		ClickListener l=new ClickListener();
		btAdd.setOnClickListener(l);
		btOk.setOnClickListener(l);
		cb0.setOnClickListener(l);
		cb1.setOnClickListener(l);
		cb2.setOnClickListener(l);
		cb3.setOnClickListener(l);
		cb4.setOnClickListener(l);
		cb5.setOnClickListener(l);
		cb6.setOnClickListener(l);
		cb7.setOnClickListener(l);
		cb8.setOnClickListener(l);
		cb9.setOnClickListener(l);
		cb10.setOnClickListener(l);
		cb11.setOnClickListener(l);
		

	}

	/**
	 * 初始化控件
	 */
	private void findViews() {
		vp=(ViewPager) view.findViewById(R.id.vp);
		btAdd=(Button) view.findViewById(R.id.bt_add);
		btOk=(Button) view.findViewById(R.id.bt_ok);
		llAdd=(LinearLayout) view.findViewById(R.id.ll_add);
		rv=(RecyclerView) view.findViewById(R.id.rv);
		cb0=(CheckBox)view.findViewById(R.id.cb_anmo);
		cb1=(CheckBox)view.findViewById(R.id.cb_dabaojian);
		cb2=(CheckBox)view.findViewById(R.id.cb_guoji);
		cb3=(CheckBox)view.findViewById(R.id.cb_jianshen);
		cb4=(CheckBox)view.findViewById(R.id.cb_meinv);
		cb5=(CheckBox)view.findViewById(R.id.cb_meishi);
		cb6=(CheckBox)view.findViewById(R.id.cb_shehui);
		cb7=(CheckBox)view.findViewById(R.id.cb_tiyu);
		cb8=(CheckBox)view.findViewById(R.id.cb_yangsheng);
		cb9=(CheckBox)view.findViewById(R.id.cb_youxi);
		cb10=(CheckBox)view.findViewById(R.id.cb_yulei);
		cb11=(CheckBox)view.findViewById(R.id.cb_yundong);
	}

	/**
	 * 给顶部导航栏及导航栏对应的界面配置数据
	 */
	private void setAdapter() {
		//重新创建fragment数据不会消失会继续添加。。。。。
		newsFragment=new NewsFragment();
		fragments.add(newsFragment);
		fragments.add(new WeixinNewsFragment());
		fragments.add(new JokFragment());
		fragments.add(new OtherFragment());

		styles.add("推荐");
		styles.add("微信头条");
		styles.add("笑话大全");
		styles.add("随意");
		
		adapter=new innerFragmentAdapter(getFragmentManager());
		vp.setAdapter(adapter);
		vp.setOffscreenPageLimit(3);
		//顶部导航栏配置数据
		LinearLayoutManager manager=new LinearLayoutManager(getActivity());
		manager.setOrientation(LinearLayoutManager.HORIZONTAL);
		rv.setLayoutManager(manager);
		newsStyleAdapter=new NewsStyleAdapter(getActivity(), styles);
		rv.setAdapter(newsStyleAdapter);
		newsStyleAdapter.setonItemClickListener(new RvListener(){

			@Override
			public void onItemClickListener(View view, int position) {
				vp.setCurrentItem(position);
				
			}
		});
	}

	/**
	 * viewpager的adapter
	 * @author wszh
	 *
	 */
	private class innerFragmentAdapter extends FragmentPagerAdapter{

		public innerFragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			
			return fragments.get(arg0);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}
		
	}
	
}

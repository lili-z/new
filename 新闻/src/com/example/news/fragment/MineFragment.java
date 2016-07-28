package com.example.news.fragment;


import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news.activity.R;
import com.example.news.activity.RigestActivity;
import com.example.news.app.NewsApplication;
import com.example.news.entity.User;

public class MineFragment extends Fragment{

	EditText etUserName;
	private EditText etPassword;
	Button btLogin;
	Button btRigest;
	LinearLayout ll;
	RelativeLayout rl;
	private Button btExit;
	TextView tv;
	@Override
	public void onDestroyView() {
		Log.i("AAA","ondestroyview");
		super.onDestroyView();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("AAA","oncreatview");
		View view=inflater.inflate(R.layout.activity_mine, null);
		setViews(view);
		setListener();
		return view;
	}

	private void setListener() {
		Listener l=new Listener();
		btLogin.setOnClickListener(l);
		btRigest.setOnClickListener(l);
		btExit.setOnClickListener(l);
	}

	private void setViews(View view) {
		etUserName=(EditText) view.findViewById(R.id.et_username);
		etPassword=(EditText) view.findViewById(R.id.et_password);
		btLogin=(Button) view.findViewById(R.id.bt_login);
		btRigest=(Button) view.findViewById(R.id.bt_rigest);
		ll=(LinearLayout) view.findViewById(R.id.ll);
		rl=(RelativeLayout) view.findViewById(R.id.rl);
		btExit=(Button)view.findViewById(R.id.bt_exit);
		tv=(TextView) view.findViewById(R.id.tv);
	}
	private class Listener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			switch(v.getId()){
			case R.id.bt_login:
				if(rl.getVisibility()==View.VISIBLE){
					return;
				}
				String name=etUserName.getText().toString();
				String password=etPassword.getText().toString();
				if(name!=null&&name.length()!=0&&password!=null&&password.length()!=0){
					List<User>users=NewsApplication.getUser();
					if(users!=null){
						for(User user:users){
							if(user.getName().equals(name)){
								if(user.getPassword().equals(password)){
								ll.setVisibility(View.INVISIBLE);
								rl.setVisibility(View.VISIBLE);
								tv.setText(name);
								etUserName.setText(null);
								etPassword.setText(null);
									return;
								}else{
									Toast.makeText(getActivity(), "账号或密码错误", 0).show();
									return;
								}
							}
						}
						Toast.makeText(getActivity(), "用户不存在请先注册", 0).show();	
					}else{
						Toast.makeText(getActivity(), "用户不存在请先注册", 0).show();
					}
				}else{
					Toast.makeText(getActivity(), "请输入账号密码", 0).show();
				}
				
				
				break;
			case R.id.bt_rigest:
				if(rl.getVisibility()==View.VISIBLE){
					return;
				}
				etUserName.setText(null);
				etPassword.setText(null);
				Intent intent=new Intent(getActivity(),RigestActivity.class);
				startActivity(intent);
				break;
			case R.id.bt_exit:
				rl.setVisibility(View.INVISIBLE);
				ll.setVisibility(View.VISIBLE);
				tv.setText(null);
				break;
			}
			
		}
		
	}
	
}

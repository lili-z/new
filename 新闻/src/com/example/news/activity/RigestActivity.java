package com.example.news.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.news.app.NewsApplication;
import com.example.news.entity.User;

public class RigestActivity extends Activity{

	private EditText etUserName;
	private EditText etPassword;
	private Button btRigest;
	private Button btBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rigest);
		setViews();
		setListener();
	}

	private void setListener() {
		btRigest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name=etUserName.getText().toString();
				String password=etPassword.getText().toString();
				if(name!=null&&name.length()!=0&&password!=null&&password.length()!=0){
					User user=new User();
					user.setName(name);
					user.setPassword(password);
					NewsApplication.addUser(user);
					finish();
				}else{
					Toast.makeText(RigestActivity.this, "«ÎÕÍ…∆’À∫≈√‹¬Î", 0).show();
				}
				
			}
		});
		btBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void setViews() {
		etUserName=(EditText)findViewById(R.id.et_username);
		etPassword=(EditText) findViewById(R.id.et_password);
		btRigest=(Button) findViewById(R.id.bt_rigest);
		btBack=(Button)findViewById(R.id.bt_back);
		
	}
}

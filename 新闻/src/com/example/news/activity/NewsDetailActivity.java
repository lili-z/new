package com.example.news.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

@SuppressLint("JavascriptInterface")
public class NewsDetailActivity extends Activity {
	String s;
	private WebView mWebView;
	private ProgressBar pb;
	Button btBack;
	Handler mHandler=new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newsdetail);
		 s=getIntent().getStringExtra("path");
		 setViews();
		 loadData();
		 setListener();
	}

	private void setListener() {
		btBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

	private void setViews() {
		btBack=(Button) findViewById(R.id.bt_back);
		 mWebView = (WebView) findViewById(R.id.wv);       
		 pb=(ProgressBar) findViewById(R.id.progressBar);
	}

	private void loadData() {
	        WebSettings webSettings = mWebView.getSettings();       
	        webSettings.setJavaScriptEnabled(true);       
//	        mWebView.addJavascriptInterface(new Object() {       
//	            
//
//				public void clickOnAndroid() {       
//	                mHandler.post(new Runnable() {       
//	                    public void run() {       
//	                        mWebView.loadUrl("javascript:wave()");       
//	                    }       
//	                });       
//	            }       
//	        }, "demo");       
//	        mWebView.loadUrl(s);   
	        mWebView.loadUrl(s);
	        mWebView.setWebViewClient(new WebViewClient(){
	        	  public void onPageStarted(WebView view, String url, Bitmap favicon) {
	                  super.onPageStarted(view, url, favicon);
	                  pb.setVisibility(android.view.View.VISIBLE);
	              }
	              @Override
	              public void onPageFinished(WebView view, String url) {
	                  super.onPageFinished(view, url);
	                  pb.setVisibility(android.view.View.GONE);
	              }
	              @Override
	              public void onReceivedError(WebView view, int errorCode,
	                      String description, String failingUrl) {
	                  super.onReceivedError(view, errorCode, description, failingUrl);
	                  pb.setVisibility(android.view.View.GONE);
	              }	
	        	
	        });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
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

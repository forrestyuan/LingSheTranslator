package com.yfc.lingshetranslator;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;

/**
 * ArticleWebPageActivity 是从ArticleFragment打开答二级页面
 * 此页面展示的内容时详情内容，打开对应列表项的网页内容
 * 
 */
public class ArticleWebPageActivity extends Activity {
	//当前页面显示网页的url地址
	private String weburl = "";
	//当前页面头部显示的文字
	private String webtitle = "";

	
	//WebView 控件---*用来显示网页
	private WebView wv;
	//控件---头部返回按钮
	private ImageView iv_webpage_back;
	//控件---头部显示标题
	private TextView tv_webpage_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//隐藏默认头部
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_article_webpage);
		//Intent 读取 从ArticleFragment传过来的数据
		Intent it = getIntent();
		Bundle extras = it.getExtras();
		weburl = (String) extras.get("weburl");
		webtitle = extras.getString("webtitle");
		
		wv = (WebView) findViewById(R.id.article_webview);
		iv_webpage_back = (ImageView) findViewById(R.id.iv_webpage_back);
		tv_webpage_title = (TextView) findViewById(R.id.tv_webpage_title);
		tv_webpage_title.setText(webtitle);

		// 返回事件
		iv_webpage_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent itback = new Intent(ArticleWebPageActivity.this,MainActivity.class);
				itback.putExtra("fragmentId", 2);
				startActivity(itback);
				ArticleWebPageActivity.this.finish();
			}
		});

		// 网页展示详情页设置
		WebSettings webSettings = wv.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);

		// 加载需要显示的网页
		wv.loadUrl(weburl);
		// 设置Web视图
		wv.setWebViewClient(new HelloWebViewClient());

		// 此方法可以处理webview 在加载时和加载完成时一些操作
		wv.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					// 这里是设置activity的标题， 也可以根据自己的需求做一些其他的操作
					// title.setText("加载完成");
				} else {
					// title.setText("加载中.......");
				}
			}
		});
	}

	public boolean onKeyDown(int keyCoder, KeyEvent event) {
		if (wv.canGoBack() && keyCoder == KeyEvent.KEYCODE_BACK) {
			wv.goBack(); // goBack()表示返回webView的上一页面
			return true;
		}
		return false;
	}

	// Web视图
	private class HelloWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

}

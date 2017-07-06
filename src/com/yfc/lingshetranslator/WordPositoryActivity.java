package com.yfc.lingshetranslator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
/**
 * 
 * @author onelife
 *我的词汇量 对应的页面，从我的 页面中进入
 */
public class WordPositoryActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wordrepository);
		
		ImageView iv_repository_back=(ImageView) findViewById(R.id.iv_repository_back);
		iv_repository_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent it=new Intent(WordPositoryActivity.this,MainActivity.class);
				it.putExtra("fragmentId",4);
				startActivity(it);
				WordPositoryActivity.this.finish();
			}
		});
	}
}

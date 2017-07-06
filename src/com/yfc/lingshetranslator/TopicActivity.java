package com.yfc.lingshetranslator;

import java.util.Properties;

import com.yfc.lingshetranslator.Util.CommonUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
/**
 * 
 * @author onelife
 *启动程序时的封面
 */
public class TopicActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_topic);
		

		
		Button btnlog=(Button) findViewById(R.id.btn_login_go);
		btnlog.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {		
				checkAccount();
			}
		});
	}
	private void checkAccount(){
		SharedPreferences settings=getSharedPreferences("setting",MODE_PRIVATE);
		String name=settings.getString("username", "");
		String pass=settings.getString("password", "");
		if("".equals(name)||"".equals(pass)){
			goLogin();
		}else{
			goMain();
		}
	}
	private void goMain(){
		Intent intent=new Intent(TopicActivity.this,MainActivity.class);
		startActivity(intent);
		TopicActivity.this.finish();
	}
	private void goLogin(){
		Intent intent=new Intent(TopicActivity.this,LoginActivity.class);
		startActivity(intent);
		TopicActivity.this.finish();
	}
}
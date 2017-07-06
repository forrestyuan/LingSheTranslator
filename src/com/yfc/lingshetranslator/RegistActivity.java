package com.yfc.lingshetranslator;

import java.util.Properties;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yfc.lingshetranslator.Util.CommonUtils;

/**
 * 
 * @author onelife
 *注册页面
 *
 */
public class RegistActivity extends Activity {
	//定义布局控件
	private ImageView btn_regist_back;
	private ImageView reg_thumbnail;
	private EditText reg_et_username;
	private EditText reg_et_password;
	private EditText reg_et_phone;
	private EditText reg_et_motto;
	private Button btn_registgo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_regist);
		// 拿到控件
		btn_regist_back = (ImageView) findViewById(R.id.btn_regist_back);
		reg_thumbnail = (ImageView) findViewById(R.id.reg_thumbnail);
		reg_et_username = (EditText) findViewById(R.id.reg_et_username);
		reg_et_password = (EditText) findViewById(R.id.reg_et_password);
		reg_et_phone = (EditText) findViewById(R.id.reg_et_phone);
		reg_et_motto = (EditText) findViewById(R.id.reg_et_motto);
		btn_registgo = (Button) findViewById(R.id.btn_registgo);
		
		//返回登录
		btn_regist_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				goLoginActivity();
			}
		});
		//注册
		btn_registgo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String username=reg_et_username.getText().toString();
				String password=reg_et_password.getText().toString();
				String phone=reg_et_phone.getText().toString();
				String motto=reg_et_motto.getText().toString();
				
				if("".equals(username.trim())||"".equals(password.trim())||"".equals(phone.trim())||"".equals(motto.trim())){
					Toast.makeText(RegistActivity.this,"请把信息输入完整",Toast.LENGTH_LONG).show();
				}else{
					 SharedPreferences settings =getSharedPreferences("setting",MODE_PRIVATE);
					 SharedPreferences.Editor editor=settings.edit();
					 editor.putString("username",reg_et_username.getText().toString());
					 editor.putString("password",reg_et_password.getText().toString());
					 editor.putString("phone",reg_et_phone.getText().toString());
					 editor.putString("motto",reg_et_motto.getText().toString());
					 editor.commit();
					 Toast.makeText(RegistActivity.this,"注册成功", Toast.LENGTH_LONG).show();
					 goLoginActivity();
					
				}
			}
		});
	}
	//调到登陆页面 封装函数
	private void goLoginActivity(){
		Intent intent=new Intent(this,LoginActivity.class);
		startActivity(intent);
		this.finish();
	}
}

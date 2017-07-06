package com.yfc.lingshetranslator;

import java.util.Properties;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yfc.lingshetranslator.Util.CommonUtils;
/**
 * 
 * @author onelife
 *登陆页面
 *输入用户信息，从sharedReference校验数据。
 *
 */
public class LoginActivity extends Activity {
	//定义布局控件
	private EditText login_username;
	private EditText login_password;
	private Button btn_log_sina;
	private Button btn_log_wechat;
	private Button btn_log_qq;
	private Button btn_log;
	private TextView tv_forgetPass;
	private TextView tv_goReg;
	private Context context;
	
	//全局变量
	private long exitTime = 0;
	CommonUtils cu=new CommonUtils();
	
	String username;
	String password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		context=getApplicationContext();
	
		//初始化控件
		login_username = (EditText) findViewById(R.id.login_username);
		login_password = (EditText) findViewById(R.id.login_password);
		btn_log_sina = (Button) findViewById(R.id.btn_log_sina);
		btn_log_wechat = (Button) findViewById(R.id.btn_log_wechat);
		btn_log_qq = (Button) findViewById(R.id.btn_log_qq);
		btn_log = (Button) findViewById(R.id.btn_log);
		tv_forgetPass = (TextView) findViewById(R.id.label_forgetpassword);
		tv_goReg = (TextView) findViewById(R.id.label_goregist);
		
		


		//点击登录校验信息
		btn_log.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				username=login_username.getText().toString();
				password=login_password.getText().toString();
				if("".equals(username.trim())||"".equals(password.trim())){
					cu.tips(context, "先输入用户名或密码");
				}else{
					if(validateAccount(username, password)){
						Intent it=new Intent(LoginActivity.this,MainActivity.class);
						startActivity(it);
						LoginActivity.this.finish();
					}
				}
			}
		});
		//暂不登陆
		tv_forgetPass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it=new Intent(LoginActivity.this,MainActivity.class);
				startActivity(it);
				LoginActivity.this.finish();
			}
		});
		//注册
		tv_goReg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent it=new Intent(LoginActivity.this,RegistActivity.class);
				startActivity(it);
			}
		});
	}
	//辅助方法-----校验用户信息
	private boolean validateAccount(String username,String password){
		SharedPreferences settings=getSharedPreferences("setting",MODE_PRIVATE);
		String name=settings.getString("username", "");
		String pass=settings.getString("password", "");
		if(name.equals(username.trim()))
		{
			if(pass.equals(password.trim())){
				cu.tips(getApplicationContext(), "登录成功");
				return true;
			}else{
				cu.tips(getApplicationContext(), "密码错误");
				return false;
			}
		}else{
			cu.tips(getApplicationContext(), "用户不存在");
			return false;
		}
	}
	//点击两次退出按钮，退出程序
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            cu.tips(getApplicationContext(), "再按一次退出程序");
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            System.exit(0);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}
}

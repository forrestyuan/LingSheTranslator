package com.yfc.lingshetranslator.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yfc.lingshetranslator.LoginActivity;
import com.yfc.lingshetranslator.MainActivity;
import com.yfc.lingshetranslator.R;
import com.yfc.lingshetranslator.WordPositoryActivity;
import com.yfc.lingshetranslator.Util.CommonUtils;

public class MyInfoFragement extends Fragment {
	private View view;// 缓存页面
	private Context context;
	// 顶部
	private TextView tv_left_username;
	private ImageView iv_left_thumbanail;
	private TextView tv_motto;
	// 单词本
	private RelativeLayout left_rl_vocabulary;
	// 词汇量
	private RelativeLayout left_rl_englishwords;
	// 设置
	private RelativeLayout left_rl_setting;
	// 评价
	private ImageView img_good;
	private ImageView img_bad;
	private TextView tv_good_num;
	private TextView tv_bad_num;
	// 退出
	private RelativeLayout left_rl_exit;
	private static CommonUtils cu=new CommonUtils();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		if (view == null) {
			// 加载fragment_music.xml布局文件
			view = inflater.inflate(R.layout.myinfofragment, container, false);
		}
		// 获取父容器
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);// 先移除view
		}

		context=getActivity().getApplicationContext();
		// 获得控件
		tv_left_username = (TextView) view.findViewById(R.id.tv_left_username);
		iv_left_thumbanail = (ImageView) view
				.findViewById(R.id.iv_left_thumbnail);
		tv_motto = (TextView) view.findViewById(R.id.tv_motto);

		left_rl_vocabulary = (RelativeLayout) view
				.findViewById(R.id.left_rl_vocabulary);
		left_rl_englishwords = (RelativeLayout) view
				.findViewById(R.id.left_rl_englishwords);
		left_rl_setting = (RelativeLayout) view
				.findViewById(R.id.left_rl_setting);
		left_rl_exit = (RelativeLayout) view.findViewById(R.id.left_rl_exit);

		img_good = (ImageView) view.findViewById(R.id.img_good);
		img_bad = (ImageView) view.findViewById(R.id.img_bad);
		tv_good_num = (TextView) view.findViewById(R.id.tv_good_num);
		tv_bad_num = (TextView) view.findViewById(R.id.tv_bad_num);
		
		
		initialAccount();
		
		//点赞事件
		img_good.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String str = tv_good_num.getText().toString().replace("(", "").replace(")", "");
				int res = Integer.parseInt(str) + 1;
				str = "(" + res + ")";
				tv_good_num.setText(str);
			}
		});
		//点赞事件
		img_bad.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			String str = tv_bad_num.getText().toString().replace("(", "").replace(")", "");
				int res = Integer.parseInt(str) + 1;
				str = "(" + res + ")";
				tv_bad_num.setText(str);
			}
		});
		//退出登陆
		left_rl_exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent it=new Intent(getActivity(),LoginActivity.class);
				startActivity(it);
				getActivity().finish();
			}
		});
		
		left_rl_englishwords.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it=new Intent(getActivity(),WordPositoryActivity.class);
				startActivity(it);
			}
		});
		return view;
	}
	
	private void initialAccount(){
		SharedPreferences settings=context.getSharedPreferences("setting",Context.MODE_PRIVATE);
		String name=settings.getString("username", "未登录");
		String motto=settings.getString("motto", "这家伙很懒，什么也没留下！！");
		tv_left_username.setText(name);
		tv_motto.setText(motto);
		if("未登录".equals(name)){
			TextView tv=(TextView) (left_rl_exit.getChildAt(0));
			tv.setText("去登录");
		}
	}
}

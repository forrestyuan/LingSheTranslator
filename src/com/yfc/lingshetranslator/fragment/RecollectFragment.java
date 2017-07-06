package com.yfc.lingshetranslator.fragment;

import java.util.HashMap;
import java.util.Properties;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yfc.lingshetranslator.R;
import com.yfc.lingshetranslator.ReciteAcitivity;
import com.yfc.lingshetranslator.Util.CommonUtils;
import com.yfc.lingshetranslator.bean.DevInfo;

public class RecollectFragment extends Fragment {
		private View view;// 缓存页面
		private Context context;
		
		private TextView tv_leftwords;
		private TextView tv_setting_recite;
		private TextView tv_words_type;
		private Button btn_changeplan;
		private TextView tv_already_recite;
		private ProgressBar progressBar_recite;
		private TextView tv_today_hasrecited;
		private Button btn_recite_go;
		private Properties pro;
		private HashMap<String,String> hm;

		CommonUtils cu;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			if (view == null) {
				// 加载fragment_music.xml布局文件
				view = inflater.inflate(R.layout.recitefragment, container, false);
			}
			// 获取父容器
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null) {
				parent.removeView(view);// 先移除view
			}
			context=getActivity();
			init();

			  
			  //修改背诵设置事件（返回的数据在mainacticity里设置添加到对应的properties文件）
			  btn_changeplan.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					
				}
			});
			  //前往背诵页面事件
			  btn_recite_go.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent it=new Intent(getActivity(),ReciteAcitivity.class);
					startActivity(it);
				}
			});
				
			return view;
		}
		public void init(){
			 tv_leftwords=(TextView) view.findViewById(R.id.tv_leftwords);
			 tv_setting_recite=(TextView) view.findViewById(R.id.tv_setting_recite);
			 
			 tv_words_type=(TextView) view.findViewById(R.id.tv_words_type);
			  btn_changeplan=(Button) view.findViewById(R.id.btn_changeplan);
			 
			  tv_already_recite=(TextView) view.findViewById(R.id.tv_already_recite);
			  progressBar_recite=(ProgressBar) view.findViewById(R.id.progressBar_recite);
			 
			 tv_today_hasrecited=(TextView) view.findViewById(R.id.tv_today_hasrecited);
			  btn_recite_go=(Button) view.findViewById(R.id.btn_recite_go);
			  
			  cu=new CommonUtils();
			  hm=DevInfo.initreciteType();//得到背诵的单词的种类列表
			  
		}

	}

package com.yfc.lingshetranslator.fragment;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yfc.lingshetranslator.ArticleWebPageActivity;
import com.yfc.lingshetranslator.R;
import com.yfc.lingshetranslator.Util.CommonUtils;
import com.yfc.lingshetranslator.adapter.ArticleAdapter;
import com.yfc.lingshetranslator.bean.DevInfo;
import com.yfc.lingshetranslator.bean.Newslist;
import com.yfc.lingshetranslator.bean.RootArticle;

public class ArticleFragment extends Fragment {
	private View view;// 缓存页面
	private static Context context;
	
	
    //定义控件
	private Button btn_reload_article;
	private ImageView iv_article_loadicon;
	private static ListView listview_wrapArticle;
	private RadioGroup rg_tab;
	private RadioButton at_radio0, at_radio1, at_radio2, at_radio3, at_radio4,
	at_radio5, at_radio6, at_radio7, at_radio8, at_radio9, at_radio10,
	at_radio11, at_radio12, at_radio13, at_radio14, at_radio15,
	at_radio16;
	
	//辅助变量
	private CommonUtils cu;
	private static List<Newslist> list;
	private String articleUrl = "";
	private static int page = 1;
	private HashMap<String, String> hm;
	// 默认加载文章类型为国内类型
	private String articleType = "创业";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			// 加载fragment_music.xml布局文件
			view = inflater.inflate(R.layout.articlefragment, container, false);
		}
		// 获取父容器
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);// 先移除view
		}
		init();
		context = getActivity().getApplicationContext();
		
		cu = new CommonUtils();
		hm = DevInfo.initArticleType();

		if ((listview_wrapArticle.getChildCount()) <= 0) {
			articleUrl = DevInfo.getArticleUrl(hm.get(articleType), 15);
			getAsynArticleHttp(articleUrl);
		}

		// 点击刷新
		btn_reload_article.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 设置动画
				cu.setAnimation(context, iv_article_loadicon);
				// 刷新
				articleUrl = DevInfo.getArticleUrl(hm.get(articleType), 50);
				getAsynArticleHttp(articleUrl);
			}
		});
		// 点击选择分类
		rg_tab.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				setTab();
				// 选中的btn，设置样式，前去应敌
				RadioButton rb = (RadioButton) view.findViewById(checkedId);
				rb.setTextColor(Color.RED);
				rb.setChecked(true);
				rb.setTextSize(16);
				// 刷新
				articleType = rb.getText().toString();
				articleUrl = DevInfo.getArticleUrl(hm.get(articleType), 50);
				getAsynArticleHttp(articleUrl);
			}
		});

		return view;
	}

	/**
	 * 初始化控件
	 */
	private void init() {
		btn_reload_article = (Button) view
				.findViewById(R.id.btn_reload_article);
		iv_article_loadicon = (ImageView) view
				.findViewById(R.id.iv_article_loadicon);
		listview_wrapArticle = (ListView) view
				.findViewById(R.id.listview_wrapArticle);
		rg_tab = (RadioGroup) view.findViewById(R.id.rg_tab);
		at_radio0 = (RadioButton) view.findViewById(R.id.at_radio0);
		at_radio1 = (RadioButton) view.findViewById(R.id.at_radio1);
		at_radio2 = (RadioButton) view.findViewById(R.id.at_radio2);
		at_radio3 = (RadioButton) view.findViewById(R.id.at_radio3);
		at_radio4 = (RadioButton) view.findViewById(R.id.at_radio4);
		at_radio5 = (RadioButton) view.findViewById(R.id.at_radio5);
		at_radio6 = (RadioButton) view.findViewById(R.id.at_radio6);
		at_radio7 = (RadioButton) view.findViewById(R.id.at_radio7);
		at_radio8 = (RadioButton) view.findViewById(R.id.at_radio8);
		at_radio9 = (RadioButton) view.findViewById(R.id.at_radio9);
		at_radio10 = (RadioButton) view.findViewById(R.id.at_radio10);
		at_radio11 = (RadioButton) view.findViewById(R.id.at_radio11);
		at_radio12 = (RadioButton) view.findViewById(R.id.at_radio12);
		at_radio13 = (RadioButton) view.findViewById(R.id.at_radio13);
		at_radio14 = (RadioButton) view.findViewById(R.id.at_radio14);
		at_radio15 = (RadioButton) view.findViewById(R.id.at_radio15);
		at_radio16 = (RadioButton) view.findViewById(R.id.at_radio16);
	}

	// 选择tab之前，初始化一下,按钮样式
	public void setTab() {
		at_radio0.setChecked(false);
		at_radio0.setTextSize(13);
		at_radio0.setTextColor(Color.BLACK);

		at_radio1.setChecked(false);
		at_radio1.setTextSize(13);
		at_radio1.setTextColor(Color.BLACK);

		at_radio2.setChecked(false);
		at_radio2.setTextSize(13);
		at_radio2.setTextColor(Color.BLACK);

		at_radio3.setChecked(false);
		at_radio3.setTextSize(13);
		at_radio3.setTextColor(Color.BLACK);

		at_radio4.setChecked(false);
		at_radio4.setTextSize(13);
		at_radio4.setTextColor(Color.BLACK);

		at_radio5.setChecked(false);
		at_radio5.setTextSize(13);
		at_radio5.setTextColor(Color.BLACK);

		at_radio6.setChecked(false);
		at_radio6.setTextSize(13);
		at_radio6.setTextColor(Color.BLACK);

		at_radio7.setChecked(false);
		at_radio7.setTextSize(13);
		at_radio7.setTextColor(Color.BLACK);

		at_radio8.setChecked(false);
		at_radio8.setTextSize(13);
		at_radio8.setTextColor(Color.BLACK);

		at_radio9.setChecked(false);
		at_radio9.setTextSize(13);
		at_radio9.setTextColor(Color.BLACK);

		at_radio10.setChecked(false);
		at_radio10.setTextSize(13);
		at_radio10.setTextColor(Color.BLACK);

		at_radio11.setChecked(false);
		at_radio11.setTextSize(13);
		at_radio11.setTextColor(Color.BLACK);

		at_radio12.setChecked(false);
		at_radio12.setTextSize(13);
		at_radio12.setTextColor(Color.BLACK);

		at_radio13.setChecked(false);
		at_radio13.setTextSize(13);
		at_radio13.setTextColor(Color.BLACK);

		at_radio14.setChecked(false);
		at_radio14.setTextSize(13);
		at_radio14.setTextColor(Color.BLACK);

		at_radio15.setChecked(false);
		at_radio15.setTextSize(13);
		at_radio15.setTextColor(Color.BLACK);

		at_radio16.setChecked(false);
		at_radio16.setTextSize(13);
		at_radio16.setTextColor(Color.BLACK);

	}

	/**
	 * 异步请求文章数据
	 * 
	 * @param url
	 */
	public void getAsynArticleHttp(String url) {
		// 创建okHttpClient对象
		OkHttpClient mOkHttpClient = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();
		Call call = mOkHttpClient.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Request arg0, IOException arg1) {
			}

			@Override
			public void onResponse(Response response) throws IOException {
				if (response != null && response.isSuccessful()) {
					final String jsonStr = response.body().string();
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// cu.tips(context,jsonStr);
							String jstr = jsonStr.replaceAll("\\\\", "");
							RootArticle ra = JSON.parseObject(jstr,RootArticle.class);
							list = ra.getNewslist();
							ArticleAdapter adapter = new ArticleAdapter(list,getActivity());
							listview_wrapArticle.setAdapter(adapter);
							// 点击列表项
							listview_wrapArticle
									.setOnItemClickListener(new AdapterView.OnItemClickListener() {
										@Override
										public void onItemClick(AdapterView<?> adapterView,View view, int i, long l) {
											String weburl = list.get(i).getUrl();
											String webtitle = (list.get(i).getTitle().substring(0, 11))+ "...";
											Intent it = new Intent(getActivity(),ArticleWebPageActivity.class);
											it.putExtra("weburl", weburl);
											it.putExtra("webtitle", webtitle);
											startActivity(it);
										}
									});
						}
					});

				}
			}
		});
	}
}

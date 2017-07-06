package com.yfc.lingshetranslator.fragment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yfc.lingshetranslator.R;
import com.yfc.lingshetranslator.Util.CommonUtils;
import com.yfc.lingshetranslator.Util.GetUrlString;
import com.yfc.lingshetranslator.Util.OkManager;
import com.yfc.lingshetranslator.Util.Player;
import com.yfc.lingshetranslator.bean.DevInfo;

public class TranslateFragment extends Fragment {
	private View view;// 缓存页面
	private Context context;
	HashMap<String, String> hm = DevInfo.initLanguage();
	private static int clickLoveTimes = 0;

	// 请求信息
	private String tmpTo = "auto";
	private String from = "auto";
	private String to = "auto";
	private String url = "";
	private String query = "";
	private String tSpeakUrl = "";

	private Button search;
	private Spinner spinner;
	private EditText et_query;
	private Button btn_clearinput_translet;
	private TextView tv_translate_res;
	private Button btn_sound_translet;
	private Button btn_love_translet;
	private Button btn_copy_translet;
	private LinearLayout linearlayout_cihui;
	private LinearLayout linearlayout_res;
	private Button btn_clear_history;
	private LinearLayout linearlayout_history;
	private CommonUtils cu;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		if (view == null) {
			// 加载fragment_music.xml布局文件
			view = inflater.inflate(R.layout.translatefragment, container,false);
		}
		// 获取父容器
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);// 先移除view
		}
		context = getActivity().getApplicationContext();
		// 获取控件
		search = (Button) view.findViewById(R.id.btn_search);
		spinner = (Spinner) view.findViewById(R.id.spinner_to);
		et_query = (EditText) view.findViewById(R.id.et_query);
		btn_clearinput_translet = (Button) view.findViewById(R.id.btn_clearinput_translet);
		tv_translate_res = (TextView) view.findViewById(R.id.tv_translate_res);
		btn_sound_translet = (Button) view.findViewById(R.id.btn_sound_translet);
		btn_love_translet = (Button) view.findViewById(R.id.btn_love_translet);
		btn_copy_translet = (Button) view.findViewById(R.id.btn_copy_translet);
		btn_clear_history = (Button) view.findViewById(R.id.btn_clear_history);
		linearlayout_res = (LinearLayout) view.findViewById(R.id.linearlayout_res);
		linearlayout_cihui = (LinearLayout) view.findViewById(R.id.linearlayout_cihui);
		linearlayout_history = (LinearLayout) view.findViewById(R.id.linearlayout_history);
		cu = new CommonUtils();

		// 起初，搜索记录显示，翻译结果隐藏
		linearlayout_res.setVisibility(View.GONE);
		linearlayout_cihui.setVisibility(View.VISIBLE);

		// 起初自动加载所有历史记录到布局中
		linearlayout_history.removeAllViews();
		cu.addHistoryTextViewToLayout(linearlayout_history, getActivity(),
				"history");

		// 下拉框监听事件
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// 拿到被选择项的值
				tmpTo = (String) spinner.getSelectedItem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		// 搜索按钮事件
		search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				to = (tmpTo == null || "".equals(tmpTo.replace(" ", ""))) ? "auto": hm.get(tmpTo);
				query = et_query.getText().toString();

				// 布局的影藏和显示
				linearlayout_res.setVisibility(View.VISIBLE);
				linearlayout_cihui.setVisibility(View.GONE);
				if (!"".equals(query) && !"".equals(to)) {
					// 调用翻译API
					url = GetUrlString.getUrlString(query, from, to);
					getAsynHttp(url);
				} else {
					Toast.makeText(context, "这位侠客，请输入您要查的文本", Toast.LENGTH_LONG).show();
				}
			}
		});

		// 清空输入文本
		btn_clearinput_translet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				et_query.setText("");
				tv_translate_res.setText("");
				linearlayout_res.setVisibility(View.GONE);
				linearlayout_cihui.setVisibility(View.VISIBLE);
			}
		});
		// 点击爱心
		btn_love_translet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (clickLoveTimes == 0) {
					String ts = tv_translate_res.getText().toString();
					if (!"".equals(ts) && !query.equals("")) {
						clickLoveTimes++;
						btn_love_translet.setBackgroundResource(R.drawable.like_vector_red);
						Properties pro = new Properties();
						pro.put(query, ts);
						cu.saveConfig(context, "like", pro);
						cu.tips(context, "已添加到我的词库^_^");
					} else {
						cu.tips(context, "内容为空，何以添加");
					}
				} else {
					if (!query.equals("")) {
						clickLoveTimes--;
						btn_love_translet.setBackgroundResource(R.drawable.like_vector);
						cu.removePropsItem(context, "like", query);
						cu.tips(context, "已取消添加到我的词库^Q^");
					}
				}
			}
		});
		// 复制翻译文本
		btn_copy_translet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String str = tv_translate_res.getText().toString();
				cu.setclipboardText(context, str);
				Toast.makeText(context, "翻译结果已复制到粘贴板", Toast.LENGTH_LONG).show();
			}
		});
		// 播放音频
		btn_sound_translet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				btn_sound_translet.setBackgroundResource(R.drawable.medium_volum_vector_red);
				new Player(tSpeakUrl).play();
			}
		});

		// 清除历史记录
		btn_clear_history.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(getActivity())
						.setTitle("确定删除所有翻译记录吗？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										boolean isclear = cu.clearProps(
												context, "history");
										if (isclear) {
											linearlayout_history
													.removeAllViews();
											Toast.makeText(context, "翻译记录已经清空",
													Toast.LENGTH_LONG).show();
										} else {
											Toast.makeText(context, "清空失败",
													Toast.LENGTH_LONG).show();
										}
									}
								}).setNegativeButton("返回",new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,int which) {
										// 点击“返回”后的操作,这里不设置没有任何操作
									}
								}).show();
			}
		});
		return view;
	}

	private String jsonStr = "";

	/**
	 * 
	 * @param url
	 *            异步请求数据
	 * @return 返回json字符串
	 */
	public void getAsynHttp(String url) {
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
					jsonStr = response.body().string();
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// 解析json
							JSONObject jsobj = JSON.parseObject(jsonStr);
							if (jsobj != null) {
								tSpeakUrl = jsobj.getString("tSpeakUrl");
								tv_translate_res.setText(jsobj.getJSONArray("translation").getString(0));

								// 添加搜索记录
								String tstr = tv_translate_res.getText().toString();
								if (!("".equals(tstr.replace(" ", "")))) {
									Properties pro = new Properties();
									pro.put(query, tstr);
									cu.saveConfig(context, "history", pro);
									linearlayout_history.removeAllViewsInLayout();
									cu.addHistoryTextViewToLayout(linearlayout_history,getActivity(), "history");
								}
							}
						}
					});
				}

			}

		});
	}

}

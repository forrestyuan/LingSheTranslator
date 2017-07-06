package com.yfc.lingshetranslator;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yfc.lingshetranslator.Util.CommonUtils;
import com.yfc.lingshetranslator.Util.GetUrlString;
import com.yfc.lingshetranslator.Util.Player;
import com.yfc.lingshetranslator.bean.Basic;
import com.yfc.lingshetranslator.bean.DevInfo;
import com.yfc.lingshetranslator.bean.Root;
import com.yfc.lingshetranslator.bean.Web;
/**
 * 
 * @author onelife
 *这个类对应的是 字典功能的搜索二级页面，通过点击 字典页面的 搜索框调到此页面
 *
 */
public class DictionarySearchActivity extends Activity {
	//定义页面布局控件
	private ImageView btn_dictsearch_back;
	private EditText et_dictsearch;
	private ImageView iv_dictsearch_go;
	private ImageView iv_delete;
	private TextView tv_sayOringSound;

	private LinearLayout linear_result;
	private RadioGroup radioGruop1;

	private TextView tv_search_word;
	private ImageView iv_dictsearch2_like;
	private ImageView iv_dictsearch_play;
	private TextView tv_dictsearch_phonetic;
	private LinearLayout linear_dict_basicexplains;
	private LinearLayout linear_dict_webexplains;

	private ScrollView scroll_dicthistory;
	private LinearLayout linear_wrap_history;
	private ImageView iv_trash_history;

	private RadioButton radio0, radio1, radio2, radio3, radio4, radio5, radio6,
	radio7, radio8, radio9, radio10;

	//定义页面全局变量
	//搜索文本内容
	private String searchText = "";
	//翻译语言
	private String to = "";
	//搜索文本语言
	private String from = "auto";
	private String serachTextRes = "";
	private String toSpeakUrl = "";
	private String fromSpeakUrl = "";

	HashMap<String, String> hm = DevInfo.initLanguage();
	CommonUtils cu = new CommonUtils();
	Context context;
	private int clickLoveTimes = 0;
	// 搜索单词前就已选好的翻译语种默认英语
	private String OringinLan = "中文";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dictionarysearch);
		initView();
		context = getApplicationContext();
		// 起初 显示搜索记录，隐藏搜索结果
		scroll_dicthistory.setVisibility(View.VISIBLE);
		linear_result.setVisibility(View.GONE);
		// 起初加载搜索历史记录
		linear_wrap_history.removeAllViews();
		cu.addHistoryTextViewToLayout(linear_wrap_history, context,
				"dictHistory");
		//删除控件隐藏
		iv_delete.setVisibility(View.GONE);

		// 返回搜索主界面（dictionaryfragment）
		btn_dictsearch_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(DictionarySearchActivity.this,MainActivity.class);
				it.putExtra("fragmentId", 0);
				startActivity(it);
				DictionarySearchActivity.this.finish();
			}
		});
		// 监听器，监听输入搜索文本框的内容变化情况
		et_dictsearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() == 0) {
					iv_delete.setVisibility(View.GONE);
					scroll_dicthistory.setVisibility(View.VISIBLE);
					linear_result.setVisibility(View.GONE);
					linear_wrap_history.removeAllViews();
					cu.addHistoryTextViewToLayout(linear_wrap_history, context,
							"dictHistory");
				} else {
					iv_delete.setVisibility(View.VISIBLE);
				}
			}
		});
		// 清空输入框
		iv_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				et_dictsearch.setText("");
			}
		});
		// 点击搜索
		iv_dictsearch_go.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 下面开始请求数据
				to = to.equals("") ? hm.get(OringinLan) : to;
				searchText = et_dictsearch.getText().toString();
				if (!searchText.equals("")) {
					linear_dict_basicexplains.removeAllViews();
					linear_dict_webexplains.removeAllViews();

					tv_search_word.setText(et_dictsearch.getText().toString());
					tv_dictsearch_phonetic.setText("");
					cu.tips(context, "loading.....");
					scroll_dicthistory.setVisibility(View.GONE);
					linear_result.setVisibility(View.VISIBLE);
					String reqUrl = GetUrlString.getUrlString(searchText, from,
							to);
					getAsynDictHttp(reqUrl);
				} else {
					cu.tips(context, "这位侠客，不妨先输入您要查找的词(6_6)");
				}
			}
		});
		// 点击发音
		iv_dictsearch_play.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!"".equals(toSpeakUrl)) {
					iv_dictsearch_play
							.setBackgroundResource(R.drawable.medium_volum_vector_red);
					new Player(toSpeakUrl).play();
				}
			}
		});
		tv_search_word.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!"".equals(fromSpeakUrl)) {
					cu.tips(context, "正在朗读ing");
					new Player(fromSpeakUrl).play();
				}
			}
		});
		// 点击清空历史记录
		iv_trash_history.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				boolean isClear = cu.clearProps(context, "dictHistory");
				if (isClear) {
					cu.tips(context, "侠客,清空指令完满执行");
					linear_wrap_history.removeAllViews();
				} else {
					cu.tips(context, "少侠，小灵蛇正在全力查明清空失败原因");
				}
			}
		});
		// 点击收藏
		iv_dictsearch2_like.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (clickLoveTimes == 0) {
					if (!"".equals(serachTextRes) && !searchText.equals("")) {
						clickLoveTimes++;
						iv_dictsearch2_like
								.setBackgroundResource(R.drawable.like_vector_red);
						Properties pro = new Properties();
						pro.put(searchText, serachTextRes);
						cu.saveConfig(context, "like", pro);
						cu.tips(context, "已添加到我的词库^_^");
					} else {
						cu.tips(context, "内容为空，何以添加");
					}
				} else {
					if (!searchText.equals("")) {
						clickLoveTimes--;
						iv_dictsearch2_like
								.setBackgroundResource(R.drawable.like_vector);
						cu.removePropsItem(context, "like", searchText);
						cu.tips(context, "已取消添加到我的词库^Q^");
					}
				}
			}
		});
		// 点击选择翻译语言
		radioGruop1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkId) {
				// 设置点击后按钮样式之前，所有样式归为，众神归位
				setLanInitStatus();
				// 选中的btn，设置样式，前去应敌
				RadioButton radiobtn = (RadioButton) findViewById(checkId);
				radiobtn.setTextColor(Color.RED);
				radiobtn.setChecked(true);
				radiobtn.setTextSize(16);
				// 下面开始请求数据
				String lanStr = radiobtn.getText().toString();
				to = hm.get(lanStr);
				searchText = et_dictsearch.getText().toString();
				if (!searchText.equals("")) {
					linear_dict_basicexplains.removeAllViews();
					linear_dict_webexplains.removeAllViews();
					tv_dictsearch_phonetic.setText("");
					String reqUrl = GetUrlString.getUrlString(searchText, from,
							to);
					getAsynDictHttp(reqUrl);
					searchText = "";
				} else {
					cu.tips(context, "这位侠客，不妨先输入您要查找的词(6_6)");
				}
			}
		});

	}

	// 初始化控件
	private void initView() {
		// 获得控件
		btn_dictsearch_back = (ImageView) findViewById(R.id.btn_dictsearch_back);
		et_dictsearch = (EditText) findViewById(R.id.et_dictsearch);
		iv_dictsearch_go = (ImageView) findViewById(R.id.iv_dictsearch_go);
		iv_delete = (ImageView) findViewById(R.id.iv_delete);
		tv_sayOringSound = (TextView) findViewById(R.id.tv_sayOringSound);

		linear_result = (LinearLayout) findViewById(R.id.linear_result);
		radioGruop1 = (RadioGroup) findViewById(R.id.radioGroup1);

		tv_search_word = (TextView) findViewById(R.id.tv_search_word);
		iv_dictsearch2_like = (ImageView) findViewById(R.id.iv_dictsearch2_like);
		iv_dictsearch_play = (ImageView) findViewById(R.id.iv_dictsearch_play);
		tv_dictsearch_phonetic = (TextView) findViewById(R.id.tv_dictsearch_phonetic);
		linear_dict_basicexplains = (LinearLayout) findViewById(R.id.linear_dict_basicexplains);
		linear_dict_webexplains = (LinearLayout) findViewById(R.id.linear_dict_webexplains);
		radio0 = (RadioButton) findViewById(R.id.radio0);
		radio1 = (RadioButton) findViewById(R.id.radio1);
		radio2 = (RadioButton) findViewById(R.id.radio2);
		radio3 = (RadioButton) findViewById(R.id.radio3);
		radio4 = (RadioButton) findViewById(R.id.radio4);
		radio5 = (RadioButton) findViewById(R.id.radio5);
		radio6 = (RadioButton) findViewById(R.id.radio6);
		radio7 = (RadioButton) findViewById(R.id.radio7);
		radio8 = (RadioButton) findViewById(R.id.radio8);
		radio9 = (RadioButton) findViewById(R.id.radio9);
		radio10 = (RadioButton) findViewById(R.id.radio10);

		scroll_dicthistory = (ScrollView) findViewById(R.id.scroll_dicthistory);
		linear_wrap_history = (LinearLayout) findViewById(R.id.linear_wrap_history);
		iv_trash_history = (ImageView) findViewById(R.id.iv_trash_history);

	}

	// 点击选择翻译语言时调用
	public void setLanInitStatus() {
		radio0.setTextColor(Color.BLACK);
		radio0.setChecked(false);
		radio0.setTextSize(13);

		radio1.setTextColor(Color.BLACK);
		radio1.setChecked(false);
		radio1.setTextSize(13);

		radio2.setTextColor(Color.BLACK);
		radio2.setChecked(false);
		radio2.setTextSize(13);

		radio3.setTextColor(Color.BLACK);
		radio3.setChecked(false);
		radio3.setTextSize(13);

		radio4.setTextColor(Color.BLACK);
		radio4.setChecked(false);
		radio4.setTextSize(13);

		radio5.setTextColor(Color.BLACK);
		radio5.setChecked(false);
		radio5.setTextSize(13);

		radio6.setTextColor(Color.BLACK);
		radio6.setChecked(false);
		radio6.setTextSize(13);

		radio7.setTextColor(Color.BLACK);
		radio7.setChecked(false);
		radio7.setTextSize(13);

		radio8.setTextColor(Color.BLACK);
		radio8.setChecked(false);
		radio8.setTextSize(13);

		radio9.setTextColor(Color.BLACK);
		radio9.setChecked(false);
		radio9.setTextSize(13);

		radio10.setTextColor(Color.BLACK);
		radio10.setChecked(false);
		radio10.setTextSize(13);
	}

	// 封装请求数据的方法

	/**
	 * @description 将异步请求的数据添加到控件中。
	 * @param url
	 *            异步请求数据
	 */
	public void getAsynDictHttp(String url) {
		// 创建okHttpClient对象
		OkHttpClient mOkHttpClient = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();
		Call call = mOkHttpClient.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Request arg0, IOException arg1) {
			}
			//请求成功后执行
			@Override
			public void onResponse(Response response) throws IOException {
				if (response != null && response.isSuccessful()) {
					final String jsonStr = response.body().string();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							String jstr = jsonStr.replaceAll("-", "_");
							// cu.tips(context,jstr);
							Root root = JSON.parseObject(jstr, Root.class);
							toSpeakUrl = root.getTSpeakUrl();
							fromSpeakUrl = root.getSpeakUrl();
							tv_dictsearch_phonetic.setText("发音");
							String key = et_dictsearch.getText().toString();
							String value = root.getTranslation().get(0);
							// web释义部分
							List<Web> listWeb = root.getWeb();
							if (listWeb != null) {
								for (Web w : listWeb) {
									cu.creatTextViewIntoLayout(context,linear_dict_webexplains,w.getKey(), 20, Color.BLUE, 5, 5,5, 5);
									for (String i : w.getValue())
										cu.creatTextViewIntoLayout(context,linear_dict_webexplains, i, 16,Color.BLACK);
								}
							}
							// 基本解释部分
							Basic basic = root.getBasic();
							if (basic != null) {
								tv_sayOringSound.setText(basic.getPhonetic());
								// cu.tips(context,
								// basic.getUk_phonetic()+"---"+basic.getUs_phonetic());
								if (basic.getExplains() != null)
									for (String exp : basic.getExplains())
										cu.creatTextViewIntoLayout(context,linear_dict_basicexplains, exp,16, Color.BLACK);
							} else {
								if (root.getTranslation() != null) {
									value = root.getTranslation().get(0);
									for (String tran : root.getTranslation()) {
										cu.creatTextViewIntoLayout(context,linear_dict_basicexplains,tran, 16, Color.BLACK);
									}
								} else {
									cu.creatTextViewIntoLayout(context,linear_dict_basicexplains, key, 16,Color.BLACK);
								}
							}
							// 添加搜索记录到dict_history.properties文件下，保存
							Properties pro = new Properties();
							pro.put(key, value);
							cu.saveConfig(context, "dictHistory", pro);

						}
					});
				}
			}
		});
	}
}

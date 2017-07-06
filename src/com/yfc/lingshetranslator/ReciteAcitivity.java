package com.yfc.lingshetranslator;

import java.io.IOException;
import java.util.Properties;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager.Query;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yfc.lingshetranslator.Util.CommonUtils;
import com.yfc.lingshetranslator.Util.Player;
import com.yfc.lingshetranslator.bean.DevInfo;
import com.yfc.lingshetranslator.bean.words;
/**
 * 
 * @author onelife
 *此类对应的是背单词页面的二级页面，在译背页面点击 开始背单词 按钮后调到此类对应的页面
 *
 */
public class ReciteAcitivity extends Activity {
	//读取布局控件
    private ImageView iv_recite_back;
    private TextView tv_english;
    private TextView tv_chinese;
    private Button btn_cut;
    private Button btn_tip;
    private ImageView iv_sound;
    private TextView tv_pron;
    private ImageView iv_thumbnail;
    //定于全局变量 辅助用
    private static  int count=0;
    private static  int total;
    private String picUrl;
    private String audioUrl;
    private String english;
    private String chinese;
    private String detailUrl;
    
    
    CommonUtils cu;
    private static  int tipCount=0;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_runrecite);
		//初始化控件
		tv_english=(TextView) findViewById(R.id.tv_english);
		tv_chinese =(TextView) findViewById(R.id.tv_chinese);
		btn_cut =  (Button) findViewById(R.id.btn_cut);
		btn_tip =  (Button) findViewById(R.id.btn_tip);
		iv_sound =  (ImageView) findViewById(R.id.iv_sound);
		iv_thumbnail =  (ImageView) findViewById(R.id.iv_thumbnail);
		iv_recite_back=(ImageView) findViewById(R.id.iv_recite_back);
		tv_pron=(TextView) findViewById(R.id.tv_pron);
		cu=new CommonUtils();

		tv_chinese.setVisibility(View.GONE);
		//起初加载单词到页面
		final String[] word=words.getWords();
		total=word.length;
		if(total<=0){
			tv_english.setText("少侠，暂无单词");
			iv_sound.setEnabled(false);
		}else{
			total--;
			count++;
			getQueryWordsAsynHttp(word[count]);
			
		}
		//斩词
		btn_cut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				count++;
				total--;
				if(total<=0){
					tv_english.setText("少侠，暂无单词");
					iv_sound.setEnabled(false);
				}else{
					getQueryWordsAsynHttp(word[count]);
					new Player(DevInfo.initAudioUrl().get("斩")).play();
					
				}
			}
		});
		//提示
		btn_tip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(tipCount==0){ //0  表示显示中文翻译
					tv_chinese.setVisibility(View.VISIBLE);
					tipCount++;
				}else{		//否则隐藏翻译
					tv_chinese.setVisibility(View.GONE);
					tipCount--;
				}
			}
		});
		//播放发音
		iv_sound.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Player(audioUrl).play();
			}
		});
		//返回，退出背诵
		iv_recite_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(ReciteAcitivity.this)
				.setTitle("确定停止背诵？").setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,int which) {
							Intent it=new Intent(ReciteAcitivity.this,MainActivity.class);
							it.putExtra("fragmentId",3);
							startActivity(it);
							ReciteAcitivity.this.finish();
						}
					}).setNegativeButton("返回",new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,int which) {
								// 点击“返回”后的操作,这里不设置没有任何操作
						}
					}).show();
			}
		});
		
	}
	//查询单词
	public void getQueryWordsAsynHttp(final String word){
		String url="https://api.shanbay.com/bdc/search/?word="+word;
			// 创建okHttpClient对象
		OkHttpClient mOkHttpClient = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();
		Call call = mOkHttpClient.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Request arg0, IOException arg1) {
			}
			//请求成功后 调用
			@Override
			public void onResponse(Response response) throws IOException {
				if (response != null && response.isSuccessful()) {
					final String jsonStr = response.body().string();
					
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// 解析json
							JSONObject jsobj = JSON.parseObject(jsonStr);
							JSONObject data=jsobj.getJSONObject("data");
							chinese=data.getString("definition");
							audioUrl=data.getString("uk_audio");
							detailUrl=data.getString("url");
							english=word;
							tv_chinese.setVisibility(View.GONE);
							tipCount=1;
							iv_sound.setEnabled(true);
						    tv_english.setText(english);
						    tv_chinese.setText(chinese);
						    tv_pron.setText(data.getString("pron"));
						}
					});
				}
			}
		});
	}


}

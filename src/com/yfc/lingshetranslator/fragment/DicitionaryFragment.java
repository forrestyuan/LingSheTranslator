package com.yfc.lingshetranslator.fragment;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yfc.lingshetranslator.DictionarySearchActivity;
import com.yfc.lingshetranslator.R;
import com.yfc.lingshetranslator.Util.CommonUtils;
import com.yfc.lingshetranslator.Util.Player;
import com.yfc.lingshetranslator.bean.DevInfo;
import com.yfc.lingshetranslator.bean.RootDaily;
import com.yfc.lingshetranslator.bean.Tags;

public class DicitionaryFragment extends Fragment implements IUiListener {
	private View view;// 缓存页面
	private Context context;
	private static CommonUtils cu;

	private RelativeLayout rela_topNav;
	private RelativeLayout Relayout_click_gosearch;
	private ImageView iv_refresh;
	private TextView tv_phrase;
	private TextView tv_phrase_trans;
	private TextView tv_author;
	private ImageView iv_phrase_voice;
	private ImageView iv_phrase_like;
	private ImageView iv_phrase_share;

	private String famousUrl = DevInfo.getFamousWordUrl();
	private JSONObject jsonObj = new JSONObject();
	private static String tspeakUrl = null;

	private static String famous = "永远相信美好的即将发生";
	private static String famousAu = "几米阳光";
	private static String famousEn = "Beautiful flowers will be always ready to bloom under your eyes!";
	private static String picUrl;
	private RootDaily rootDaily;
	private int clickLoveTimes = 0;
	private Tencent mTencent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			// 加载fragment_music.xml布局文件
			view = inflater.inflate(R.layout.dictionaryfragment, container,false);
		}
		// 获取父容器
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);// 先移除view
		}
		context = getActivity().getApplicationContext();
		cu = new CommonUtils();
		// 获取控件
		Relayout_click_gosearch = (RelativeLayout) view.findViewById(R.id.Relayout_click_gosearch);
		iv_refresh = (ImageView) view.findViewById(R.id.iv_refresh);
		tv_phrase = (TextView) view.findViewById(R.id.tv_phrase);
		tv_phrase_trans = (TextView) view.findViewById(R.id.tv_phrase_trans);
		tv_author = (TextView) view.findViewById(R.id.tv_author);
		iv_phrase_voice = (ImageView) view.findViewById(R.id.iv_phrase_voice);
		iv_phrase_like = (ImageView) view.findViewById(R.id.iv_phrase_like);
		iv_phrase_share = (ImageView) view.findViewById(R.id.iv_phrase_share);
		rela_topNav = (RelativeLayout) view.findViewById(R.id.rela_topNav);

		// 腾讯分享开启
		mTencent = (Tencent) Tencent.createInstance(DevInfo.appID,context.getApplicationContext());

		// 进入页面 不请求，加载自己的名言名句到tv中
		tv_phrase.setText(famous);
		tv_phrase_trans.setText(famousEn);
		tv_author.setText(famousAu);
		if (cu.propsItemIsExist(context, "like", tv_phrase.getText().toString())) {
			iv_phrase_like.setBackgroundResource(R.drawable.like_vector_red);
		}
		// 进入搜索页面
		Relayout_click_gosearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(getActivity(),DictionarySearchActivity.class);
				startActivity(it);
			}
		});
		// 点击刷新旋转动画和请求
		iv_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 刷新动画
				cu.setAnimation(context, iv_refresh);
				// 调用名言警句API
				cu.tips(getActivity(), "loading");
				String url = DevInfo.getIcbiUrl();
				// cu.tips(context, url);
				getfamous(url);

			}
		});
		// 点击收藏
		iv_phrase_like.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String key = tv_phrase.getText().toString();
				String value = tv_phrase_trans.getText().toString();
				if (clickLoveTimes == 0) {
					if (!"".equals(key) && !value.equals("")) {
						clickLoveTimes++;
						iv_phrase_like.setBackgroundResource(R.drawable.like_vector_red);
						Properties pro = new Properties();
						pro.put(key, value);
						cu.saveConfig(context, "like", pro);
						cu.tips(context, "已添加到我的词库^_^");
					} else {
						cu.tips(context, "内容为空，何以添加");
					}
				} else {
					if (!key.equals("")) {
						clickLoveTimes--;
						iv_phrase_like.setBackgroundResource(R.drawable.like_vector);
						cu.removePropsItem(context, "like", key);
						cu.tips(context, "已取消添加到我的词库^Q^");
					}
				}
			}
		});
		// 点击发音
		iv_phrase_voice.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (tspeakUrl != null && !"".equals(tspeakUrl.trim())) {
					iv_phrase_voice.setBackgroundResource(R.drawable.medium_volum_vector_red);
					new Player(tspeakUrl).play();
				} else {
					cu.tips(context, "少侠，小灵蛇我正在全力查找音频@_@");
				}
			}
		});
		// 点击分享
		iv_phrase_share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onClickShare();
			}
		});
		return view;
	}

	private static String jsonFamous = "";

	/**
	 * @description 请求名言警句
	 * @param url
	 *            异步请求数据
	 * @return 返回json字符串
	 */
	public void getfamous(String url) {
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
					jsonFamous = response.body().string();
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (!"".equals(jsonFamous)) {
								rootDaily = JSON.parseObject(jsonFamous,
										RootDaily.class);
								if (rootDaily != null) {
									// 将值放进控件
									tspeakUrl = rootDaily.getTts();
									// cu.tips(context, tspeakUrl);
									famousEn = rootDaily.getContent(); // 翻译
									famous = rootDaily.getNote(); // 原句
									picUrl = rootDaily.getPicture();
									String tmp = "";
									List<Tags> tags = rootDaily.getTags();
									if (tags != null) {
										for (Tags tg : tags)
											tmp += tg.getName() + "  ";
									} else {
										tmp = "佚名";
									}
									famousAu = tmp; // 类型
									if (cu.propsItemIsExist(context, "like",famous)) {
										iv_phrase_like.setBackgroundResource(R.drawable.like_vector_red);
									}else{
										iv_phrase_like.setBackgroundResource(R.drawable.like_vector);
									}
									
									tv_phrase.setText(famous);
									tv_phrase_trans.setText(famousEn);
									tv_author.setText(famousAu);
									//设置背景
									Glide.with(getActivity())  
							          .load(picUrl)  
							          .asBitmap()  
							          .into(new SimpleTarget<Bitmap>(180,180){//设置宽高  
							                 @Override  
							                 public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {  
							                     Drawable drawable = new BitmapDrawable(resource);  
							                     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {  
							                    	 rela_topNav.setBackground(drawable);//设置背景  
							                               }  
							                           }  
							                      });  
								} else {
									cu.tips(context,"小灵蛇真的有很努力在加载数据，可还是发现了一点小问题...");
								}
							}
						}
					});
				}
			}
		});
	}

	/**
	 * 请求图片放到首页
	 * 
	 * @param imgUrl
	 */

	// 调用QQ分享

	private class BaseUiListener implements IUiListener {
		public void onComplete(Object response) {
			doComplete(response);
		}

		protected void doComplete(Object values) {
		}

		@Override
		public void onCancel() {
		}

		@Override
		public void onError(UiError arg0) {
			// TODO Auto-generated method stub

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (null != mTencent) {
			Tencent.onActivityResultData(requestCode, resultCode, data, this);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onCancel() {
	}

	@Override
	public void onComplete(Object arg0) {
	}

	@Override
	public void onError(UiError arg0) {
	}

	private void onClickShare() {
		final Bundle params = new Bundle();
		String sayurl = tspeakUrl == null ? "" : tspeakUrl;
		params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
		params.putString(QQShare.SHARE_TO_QQ_TITLE, "灵蛇译神");
		params.putString(QQShare.SHARE_TO_QQ_SUMMARY, famous + "\n" + famousEn);
		params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,"http://www.forrestyuan.cn");
		params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, tspeakUrl);
		params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, picUrl);
		params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "灵蛇译神");
		mTencent.shareToQQ(getActivity(), params, new BaseUiListener());
	}
}

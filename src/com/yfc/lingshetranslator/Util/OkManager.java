package com.yfc.lingshetranslator.Util;

import java.io.IOException;

import android.content.Context;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class OkManager {
	private static String jsonStr = "";

	/**
	 * 
	 * @param url
	 *            异步请求数据
	 * @return 返回json字符串
	 */
	public static String getAsynHttp(String url) {
		jsonStr = "";
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
				}
			}
		});
		return jsonStr;
	}

}
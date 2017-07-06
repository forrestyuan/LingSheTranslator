package com.yfc.lingshetranslator.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yfc.lingshetranslator.ArticleWebPageActivity;
import com.yfc.lingshetranslator.R;
import com.yfc.lingshetranslator.bean.Newslist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 
 * @author onelife
 * 
 *此适配器对应 的时文章列表，将网路请求的JSON数据封装在javabean 里后，
 *通过次适配器将javabean里的信息，读取并填充到列表中
 *
 */
public class ArticleAdapter extends BaseAdapter {
	private List<Newslist> list;
	private static Context context;

	public ArticleAdapter(List<Newslist> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
/*
 * 在下面这个方法里将数据填充的列表中
 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v;
		if (convertView == null) {
			v = LayoutInflater.from(context).inflate(
					R.layout.article_listview_item, parent, false);
		} else {
			v = convertView;
		}
		TextView tv_article_title = (TextView) v
				.findViewById(R.id.tv_article_title);
		ImageView iv_article_thumbnail = (ImageView) v
				.findViewById(R.id.iv_article_thumbnail);
		TextView tv_article_type = (TextView) v
				.findViewById(R.id.tv_article_type);
		TextView tv_article_date = (TextView) v
				.findViewById(R.id.tv_article_date);

		final Newslist news = list.get(position);

		tv_article_title.setText(news.getTitle());
		tv_article_type.setText(news.getDescription());
		tv_article_date.setText(news.getCtime());
		String imgurl = news.getPicUrl();
		Glide.with(context).load(news.getPicUrl()).into(iv_article_thumbnail);

		return v;
	}
}

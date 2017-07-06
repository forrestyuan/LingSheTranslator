package com.yfc.lingshetranslator.Util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.yfc.lingshetranslator.R;

public class CommonUtils {
	/**
	 * @param context
	 *            上下文环境
	 * @param str
	 *            复制的文本
	 */
	public void setclipboardText(Context context, String str) {
		ClipboardManager clip = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		clip.setPrimaryClip(ClipData.newPlainText("str", str));
	}

	/* toast 显示 */
	public void tips(Context ctx, String tips) {
		Toast.makeText(ctx, tips, Toast.LENGTH_SHORT).show();
	}

	/**
	 * @description 动态生成textview保存翻译历史记录
	 * @param ctx
	 *            上下文环境
	 * @param str
	 *            多个字符串参数
	 * @return TextView
	 */
	public TextView creatTextView(Context ctx, String... str) {
		TextView tv = new TextView(ctx);
		tv.setText("搜索词：" + str[0] + "\n\n翻译：" + str[1]);
		tv.setTextSize(12);
		tv.setPadding(5, 10, 5, 10);
		tv.setTextColor(Color.BLACK);
		tv.setBackgroundResource(R.drawable.edit_text_focus);
		tv.setAlpha(0.5f);
		return tv;
	}

	/**
	 * @description 添加textview格式历史记录到布局中
	 * @param layout
	 *            线性布局LinearLayout
	 * @param ctx
	 *            上下文环境
	 */
	public void addHistoryTextViewToLayout(ViewGroup layout, Context ctx,
			String file) {
		int width = layout.getMeasuredWidth();
		Properties prop = loadConfig(ctx, file);
		Iterator<String> it = prop.stringPropertyNames().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = prop.getProperty(key);
			TextView tv = creatTextView(ctx, key, value);
			tv.setWidth(width);
			layout.addView(tv);
		}
	}

	/** 
	  
	*/
	/**
	 * @description 针对字典查询结果 创建一个textView，参数为文本框内容
	 * @param ctx
	 *            上下文环境
	 * @param layout
	 *            需要将TextView 放置的布局
	 * @param str
	 *            放到textview 里的内容(String)
	 * @param size
	 *            字体大小 (int类型)
	 * @param color
	 *            字体颜色 (Color.xxx)
	 * @param padding
	 *            设置textView 的内边距，（可有可无，上右下左）
	 */
	public void creatTextViewIntoLayout(Context ctx, ViewGroup layout,
			String str, int size, int color, int... padding) {
		TextView tv = new TextView(ctx);
		if (padding.length == 0) {
			tv.setPadding(5, 10, 5, 10);
		} else {
			tv.setPadding(padding[3], padding[0], padding[1], padding[2]);
		}
		tv.setText(str);
		tv.setBackgroundColor(Color.WHITE);
		tv.setTextColor(color);
		tv.setTextSize(size);
		layout.addView(tv);
	}

	/**
	 * @description 删除指定file.properites文件下的指定的键值对
	 * @param ctx
	 *            上下文环境
	 * @param file
	 *            指定的file.properties文件
	 * @param key
	 *            删除的键值对的key值
	 */
	public void removePropsItem(Context ctx, String file, String key) {
		Properties prop = loadConfig(ctx, file);
		prop.remove(key);
	}

	/**
	 * @description 得到file.properties 文件中的指定键的值
	 * @param ctx
	 *            上下文环境
	 * @param file
	 *            （account:账号类信息;history:翻译历史记录信息;like:收藏; words:词汇量）
	 * @param key
	 *            查找项的key值
	 * @return 返回加载的配置文件
	 */
	public String getPropsItem(Context ctx, String file, String key) {
		Properties pro = new Properties();
		String res="no";
		try {
			FileInputStream s =ctx.openFileInput(file+".properties");
			pro.load(s);
			res=pro.getProperty(key);
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * @description 读取file.properties文件
	 * @param context
	 *            上下文环境
	 * @param file
	 *            （account:账号类信息;history:翻译历史记录信息;like:收藏; words:词汇量）
	 * @return 返回加载的配置文件
	 */
	public Properties loadConfig(Context ctx, String file) {
		Properties properties = new Properties();
		try {
			FileInputStream s = ctx.openFileInput(file+".properties");
			properties.load(s);
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}
	/**
	 * 统计properties文件中子项的数量
	 * @param ctx	 上下文环境
	 * @param file	 文件名称（无后缀）
	 * @return	返回数量 int	 */
	public int countPropsItemNum(Context ctx, String file) {
		Properties properties = new Properties();
		try {
			FileInputStream s = ctx.openFileInput(file + ".properties");
			properties.load(s);
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return properties.size();
	}
	
	/**
	 * @description 添加键值对到file.properties文件
	 * @param context
	 *            上下文环境
	 * @param file
	 *            （account:账号类信息;history:翻译历史记录信息;like:收藏; words:词汇量）
	 * @param properties
	 *            需要保存的信息properties 文件内容，追加到文件中
	 */
	public void saveConfig(Context ctx, String file, Properties properties) {
		try {
			FileOutputStream s = ctx.openFileOutput(file + ".properties",Context.MODE_APPEND);
			properties.store(s, "");
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/**
 * 判断某个子项是否存在于props文件中
 * @param ctx	上下文环境
 * @param file	文件名称
 * @param key	子项的key值
 * @return
 */
	public boolean propsItemIsExist(Context ctx, String file, String key) {
		Properties pro = new Properties();
		try {
			FileInputStream s2 = ctx.openFileInput(file + ".properties");
			pro.load(s2);
			s2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pro.containsKey(key);
	}

	/**
	 * @decription 删除对应props的文件的所有键值对
	 * @param dataType
	 *            （account:账号类信息;history:翻译历史记录信息;like:收藏; words:词汇量）
	 * 
	 */
	public boolean clearProps(Context ctx, String file) {
		Properties pro = new Properties();
		Properties pro2 = new Properties();
		// pro.put(null,null);
		try {
			FileOutputStream s = ctx.openFileOutput(file + ".properties",
					Context.MODE_PRIVATE);
			pro.store(s, "");
			s.close();

			FileInputStream s2 = ctx.openFileInput(file + ".properties");
			pro2.load(s2);
			s2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pro2.isEmpty();
	}

	public void setAnimation(Context activity, View v) {
		// 刷新动画
		Animation animation = AnimationUtils.loadAnimation(activity,
				R.anim.rotate);
		v.startAnimation(animation);// 开始动画
	}
}

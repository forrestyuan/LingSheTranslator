package com.yfc.lingshetranslator.bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import android.text.format.DateFormat;

/**
 * 这是请求数据和一些相关的开发信息类，保存了APIkey 以及获取url以及语言列表的集合
 * 
 * @author onelife
 * 
 */

public class DevInfo {
	// 通过加密后值
	private static String baiduApikey = "6610e8058cd822b06332a30586349bd3";
	private static String tianxingApikey = "9d23068f4520a18bf46be2a325f7130b";

	private static String icibaKey = "6BBF64671F8D6C9DC6D7F46CC6E38E00";
	private static String icibaUrl = "http://open.iciba.com/dsapi/";

	private static String tianxingUrl = "http://api.tianapi.com/";

	public static String appID = "1106171889";
	public static String APP_KEY = "OrqMPygg0WTzTYXt";
	

	private static int y = 113;
	private static int m = 1;
	private static int d = 1;

	/**
	 * file //数据格式，默认（json），可选xml date //标准化日期格式 如：2013-05-06，
	 * 如：http://open.iciba.com/dsapi/?date=2013-05-03 如果 date为空 则默认取当日的，当日为空
	 * 取前一日的 type(可选) // last 和 next 你懂的，以date日期为准的，last返回前一天的，next返回后一天的
	 * 
	 * JSON 字段解释 { 'sid':'' #每日一句ID 'tts': '' #音频地址 'content':'' #英文内容 'note':
	 * '' #中文内容 'love': '' #每日一句喜欢个数 'translation':'' #词霸小编 'picture': '' #图片地址
	 * 'picture2': '' #大图片地址 'caption':'' #标题 'dateline':'' #时间 's_pv':'' #浏览数
	 * 'sp_pv':'' #语音评测浏览数 'tags':'' #相关标签 'fenxiang_img':'' #合成图片，建议分享微博用的 }
	 * 
	 * @return
	 */
	public static String getIcbiUrl() {
		if ((++d) > 30) {
			d = 1;
			if ((++m) > 12) {
				m = 1;
				if ((++y) > ((new Date().getYear()) - 100)) {
					y = 100;
				}
			}
		}
		Date date = new Date(y, m, d);// 取时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String legalDate = df.format(date);
		String url = "http://open.iciba.com/dsapi/?date=" + legalDate;
		return url;
	}

	/**
	 * 暂停使用
	 * 
	 * @return json字符串 {"code":200, "msg":"success", "newslist":[{ "id":279,
	 *         "content":"人类用知识的活动去了解事物，用实践的活动去改变事物；用前者去掌握宇宙，用后者去创造宇宙。",
	 *         "mrname":"克罗齐" }] }
	 */

	public static String getFamousWordUrl() {
		return "http://api.tianapi.com/txapi/dictum/?key=" + tianxingApikey;
	}

	/**
	 * @description 返回请求文章的URL
	 * @param num
	 *            为变长参数，可以填写也可以不填写，指定返回文章数量
	 * @return String 类型URL
	 */
	public static String getArticleUrl(String articleType, int... num) {
		String type = articleType + "/?key=";
		String url = tianxingUrl + type + tianxingApikey + "&num=";
		if (num.length != 0) {
			url += ("" + num[0]);
		} else {
			url += "10";
		}
		return url;
	}

	/**
	 * @description 翻译语言集合列表
	 * @return 返回翻译语言及和列表 ，hashmap类型
	 */
	public static HashMap<String, String> initLanguage() {
		// 构建翻译集合
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("中文", "zh-CHS");
		hm.put("日文", "ja");
		hm.put("英文", "EN");
		hm.put("韩文", "ko");
		hm.put("法文	", "fr");
		hm.put("阿拉伯文", "ar");
		hm.put("波兰文", "pl");
		hm.put("丹麦文", "da");
		hm.put("德文", "de");
		hm.put("俄文", "ru");
		hm.put("芬兰文", "fl");
		hm.put("荷兰文", "nl");
		hm.put("捷克文", "cs");
		hm.put("罗马尼亚文", "ro");
		hm.put("匈牙利文", "hu");
		hm.put("挪威文", "no");
		hm.put("葡萄牙文", "pt");
		hm.put("瑞典文", "sv");
		hm.put("斯洛伐克文", "sk");
		hm.put("西班牙文", "es");
		hm.put("印地文", "hi");
		hm.put("印度尼西亚文", "id");
		hm.put("意大利文", "it");
		hm.put("泰文", "th");
		hm.put("土耳其文", "tr");
		hm.put("希腊文", "el");
		return hm;
	}

	/**
	 * 得到所对应的文章类型
	 * 
	 * @return
	 */
	public static HashMap<String, String> initArticleType() {
		// 构建文章类型集合
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("社会", "social");
		hm.put("国内", "guonei");
		hm.put("国际	", "world");
		hm.put("娱乐", "huabian");
		hm.put("体育	", "tiyu");
		hm.put("NBA", "nba");
		hm.put("足球", "football");
		hm.put("科技", "keji");
		hm.put("创业", "startup");
		hm.put("军事", "military");
		hm.put("移动", "mobile");
		hm.put("奇闻", "qiwen");
		hm.put("旅游", "travel");
		hm.put("健康	", "health");
		hm.put("VR科技", "vr");
		hm.put("IT资讯", "it");
		hm.put("美女", "meinv");
		return hm;
	}
	
	/**
	 * 得到所对应的背诵类型
	 * 
	 * @return
	 */
	public static HashMap<String, String> initreciteType() {
		// 构建背诵类型集合
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("六级词汇", "sixgrade");
		hm.put("四级词汇", "fourgrade");
		hm.put("雅思词汇	", "yasi");
		hm.put("生活词汇", "daily");
		return hm;
	}
	public static HashMap<String,String >initAudioUrl(){
		// 构建背诵类型集合
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("失败", "http://dx.sc.chinaz.com/Files/DownLoad/sound1/201706/8865.mp3");
		hm.put("失败2", "http://dx.sc.chinaz.com/Files/DownLoad/sound1/201706/8857.mp3");
		hm.put("斩", "http://dx.sc.chinaz.com/Files/DownLoad/sound1/201706/8819.mp3");
		hm.put("背景音乐", "http://dx.sc.chinaz.com/Files/DownLoad/sound1/201705/8757.mp3");
		hm.put("激励","http://zjdx1.sc.chinaz.com/Files/DownLoad/sound1/201703/8404.mp3");
		hm.put("结束背单词", "http://zjdx1.sc.chinaz.com/Files/DownLoad/sound1/201703/8422.mp3");
		return hm;
		
	}
}

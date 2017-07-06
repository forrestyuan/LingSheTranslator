package com.yfc.lingshetranslator.Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class GetUrlString {
	/**
	 * 
	 * @param query
	 *            查询的单词
	 * @param from
	 *            查询的单词的语种
	 * @param to
	 *            翻译的单词的语种
	 * @return 查询的url
	 */
	public static String getUrlString(String query, String from, String to) {
		String appKey = "451f7b48cdc26df4";
		String salt = String.valueOf(System.currentTimeMillis());
		String sign = md5(appKey + query + salt
				+ "FjFDatzr3cH0u7t7OlMRvh73TLkh9RYO");

		Map<String, String> params = new HashMap<String, String>();
		params.put("q", query);
		params.put("from", from);
		params.put("to", to);
		params.put("sign", sign);
		params.put("salt", salt);
		params.put("appKey", appKey);

		return getUrlWithQueryString("https://openapi.youdao.com/api", params);
	}

	/**
	 * 生成32位MD5摘要
	 * 
	 * @param string
	 * @return
	 */
	public static String md5(String string) {
		if (string == null) {
			return null;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		byte[] btInput = string.getBytes();
		try {
			/** 获得MD5摘要算法的 MessageDigest 对象 */
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			/** 使用指定的字节更新摘要 */
			mdInst.update(btInput);
			/** 获得密文 */
			byte[] md = mdInst.digest();
			/** 把密文转换成十六进制的字符串形式 */
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (byte byte0 : md) {
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	/**
	 * 根据api地址和参数生成请求URL
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String getUrlWithQueryString(String url,
			Map<String, String> params) {
		if (params == null) {
			return url;
		}

		StringBuilder builder = new StringBuilder(url);
		if (url.contains("?")) {
			builder.append("&");
		} else {
			builder.append("?");
		}

		int i = 0;
		for (String key : params.keySet()) {
			String value = params.get(key);
			if (value == null) { // 过滤空的key
				continue;
			}

			if (i != 0) {
				builder.append('&');
			}

			builder.append(key);
			builder.append('=');
			builder.append(encode(value));

			i++;
		}

		return builder.toString();
	}

	/**
	 * 进行URL编码
	 * 
	 * @param input
	 * @return
	 */
	public static String encode(String input) {
		if (input == null) {
			return "";
		}

		try {
			return URLEncoder.encode(input, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return input;
	}
}
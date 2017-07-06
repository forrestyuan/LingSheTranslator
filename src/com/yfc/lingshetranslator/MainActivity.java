package com.yfc.lingshetranslator;

import java.util.ArrayList;

import com.yfc.lingshetranslator.Util.Player;
import com.yfc.lingshetranslator.adapter.MyViewPagerAdapter;
import com.yfc.lingshetranslator.bean.DevInfo;
import com.yfc.lingshetranslator.fragment.ArticleFragment;
import com.yfc.lingshetranslator.fragment.DicitionaryFragment;
import com.yfc.lingshetranslator.fragment.MyInfoFragement;
import com.yfc.lingshetranslator.fragment.RecollectFragment;
import com.yfc.lingshetranslator.fragment.TranslateFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
/**
 * 
 *@author onelife
 *这个类对应的主页面，主页面里有四个页面，分别是 字典页面、翻译页面，译背页面，文章页面，我的页面（这五个页面对应的java类都时fragment，在fragment包下）
 *每个页面都包含有对应的二级页面（二级页面都是以Activity的java 文件）
 */
public class MainActivity extends FragmentActivity {
	//定义布局控件
	private ViewPager pager;
	private RadioGroup radiogroup;
	static int which = 0;
	private PagerAdapter mAdapter;
	private ArrayList<Fragment> fragments;
	//设定全局变量
	private Player pl=new Player(DevInfo.initAudioUrl().get("结束背单词"));
	private long exitTime = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		radiogroup = (RadioGroup) findViewById(R.id.tab_menu);
		
		//点击选择页面
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				findViewById(R.id.radio0).setBackgroundColor(Color.WHITE);
				findViewById(R.id.radio1).setBackgroundColor(Color.WHITE);
				findViewById(R.id.radio2).setBackgroundColor(Color.WHITE);
				findViewById(R.id.radio3).setBackgroundColor(Color.WHITE);
				findViewById(R.id.radio4).setBackgroundColor(Color.WHITE);
				pl.stop();
				switch (checkedId) {
				case R.id.radio0:// 词典
					pager.setCurrentItem(0);
					findViewById(R.id.radio0).setBackgroundColor(Color.BLACK);
					break;
				case R.id.radio1:// 长文本翻译
					pager.setCurrentItem(1);
					findViewById(R.id.radio1).setBackgroundColor(Color.BLACK);
					break;
				case R.id.radio4: // 文章
					pager.setCurrentItem(2);
					findViewById(R.id.radio4).setBackgroundColor(Color.BLACK);
					break;
				case R.id.radio2:// 译背
					pager.setCurrentItem(3);
					findViewById(R.id.radio2).setBackgroundColor(Color.BLACK);
					new Thread(new Runnable() {
						@Override
						public void run() {
							pl.play();
						}
					}).start();
					break;
				case R.id.radio3:// 我的
					pager.setCurrentItem(4);
					findViewById(R.id.radio3).setBackgroundColor(Color.BLACK);
					break;
				default:
					break;
				}

			}
		});
		
		pager = (ViewPager) findViewById(R.id.viewPaager1);// 初始化控件，获取ViewPager对象
		fragments = new ArrayList<Fragment>();// 初始化数据

		fragments.add(new DicitionaryFragment());
		fragments.add(new TranslateFragment());
		fragments.add(new ArticleFragment());
		fragments.add(new RecollectFragment());
		fragments.add(new MyInfoFragement());

		initViewPager();
	}

	/**
	 * 初始化ViewPager
	 */
	private void initViewPager() {
		mAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),
				fragments);
		pager.setAdapter(mAdapter);
		// 设置viewpager 页面数量限制，防止每次加载fragment都重新加载。
		pager.setOffscreenPageLimit(5);
		//此处的Intent 是用于接收二级页面返回来的信息，包含返回后调到哪个一个fragment页面
		Intent it = getIntent();
		int fragmentId = it.getIntExtra("fragmentId", 0);
		switch (fragmentId) {
		case 0:
			findViewById(R.id.radio0).setBackgroundColor(Color.BLACK);
			break;
		case 1:
			findViewById(R.id.radio1).setBackgroundColor(Color.BLACK);		
			break;
					
		case 2:
			findViewById(R.id.radio4).setBackgroundColor(Color.BLACK);
			break;
		case 3:
			findViewById(R.id.radio2).setBackgroundColor(Color.BLACK);
			break;
		case 4:
			findViewById(R.id.radio3).setBackgroundColor(Color.BLACK);
			break;

		default:
			break;
		}
		pager.setCurrentItem(fragmentId);// 设置当前显示的是位置在第一个的view
	}
	//点击两次返回键，退出程序
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(this, "再按一次退出程序",Toast.LENGTH_LONG).show();
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            System.exit(0);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}
}

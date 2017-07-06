package com.yfc.lingshetranslator.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 * 
 * @author onelife
 *在MainActivity里有五个fragment 页面，这五个页面通过此适配器填充到MainActivity 类对应的页面中。
 *
 */
public class MyViewPagerAdapter extends FragmentPagerAdapter {
	private ArrayList<Fragment> fragments; // 存储需要添加到ViewPager上的Fragment

	public MyViewPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 自定义的构造函数
	 * 
	 * @param fm
	 * @param fragments
	 *            ArrayList<Fragment>
	 */
	public MyViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();
	}

}

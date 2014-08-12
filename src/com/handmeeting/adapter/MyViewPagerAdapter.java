package com.handmeeting.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class MyViewPagerAdapter extends PagerAdapter{
	List<View> mList;
	public void setList(List<View> list){
		mList = list;
	}
	@Override
	public Object instantiateItem(View container, int position) {
		((ViewGroup) container).addView(mList.get(position));
		return mList.get(position);
	}
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return (view == obj);
	}
	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager) container).removeView(mList.get(position));
	}
	
}

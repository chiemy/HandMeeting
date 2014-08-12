package com.handmeeting.handmeeting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.handmeeting.handmeeting.BitmapTools.DownloadData;
import com.handmeeting.handmeeting.BitmapTools.JsonUtils;
import com.handmeeting.handmeeting.fragment.AccountFragment;
import com.handmeeting.handmeeting.fragment.FindFragment;
import com.handmeeting.urlcontent.UrlContent;

public class MainActivity extends FragmentActivity{
	private RadioButton findRb,meetRb,friendRb,accountRb;
	private RadioGroup navigation;
	private Button cityBt;
	private FragmentManager fragmentManager;
	private Map<String,Fragment> map;
	private int userId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		findRb = (RadioButton) findViewById(R.id.findRb);
		meetRb = (RadioButton) findViewById(R.id.meetRb);
		friendRb = (RadioButton) findViewById(R.id.friendRb);
		accountRb = (RadioButton) findViewById(R.id.accountRb);
		navigation = (RadioGroup) findViewById(R.id.navigationRg);
		navigation.setOnCheckedChangeListener(new RadioGroupListener());
		findRb.setChecked(true);
		
		fragmentManager = getSupportFragmentManager();
		FindFragment findFragment = new FindFragment();
		AccountFragment account = new AccountFragment();
		map = new HashMap<String,Fragment>();
		map.put("find", findFragment);
		map.put("account", account);
		fragmentManager.beginTransaction().add(R.id.contentLayout, map.get("find")).commit();
	}
	public class RadioGroupListener implements OnCheckedChangeListener{
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			switch(checkedId){
			case R.id.findRb:
				transaction.replace(R.id.contentLayout, map.get("find")).commit();
				break;
			case R.id.meetRb:
				
				break;
			case R.id.friendRb:
				
				break;
			case R.id.accountRb:
				transaction.replace(R.id.contentLayout, map.get("account")).commit();
				break;
			}
		}
	}
	
}

package com.handmeeting.handmeeting;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmeeting.handmeeting.BitmapTools.DownloadData;
import com.handmeeting.handmeeting.BitmapTools.JsonUtils;
import com.handmeeting.urlcontent.UrlContent;

public class MeetDetailActivity extends Activity implements OnClickListener{
	private ScrollView scrollView;
	private CheckBox applyIv,shareIv,collectIv;
	private MyHandler handler;
	private TextView titleTv,timeTv,contentTv;
	private ImageView back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meet_detail);
		handler = new MyHandler(this);
		applyIv = (CheckBox) findViewById(R.id.apply);
		shareIv = (CheckBox) findViewById(R.id.share);
		collectIv = (CheckBox) findViewById(R.id.collect);
		back = (ImageView) findViewById(R.id.back);
		
		titleTv = (TextView) findViewById(R.id.title);
		timeTv = (TextView) findViewById(R.id.maoshu);
		contentTv = (TextView) findViewById(R.id.introduce);
		
		applyIv.setOnClickListener(this);
		shareIv.setOnClickListener(this);
		collectIv.setOnClickListener(this);
		back.setOnClickListener(this);
		
		Intent intent = getIntent();
		String meetId = intent.getStringExtra("meetId");
		String userId = ((MyApp)getApplication()).userId;
		String accessToken = ((MyApp)getApplication()).accessToken;
		String url = UrlContent.meetDetail + meetId + "&userId=" + userId + UrlContent.urlTail2 + accessToken;
		new MyThread(url).start();
		
		
	}
	
	static class MyHandler extends Handler{
		private WeakReference<MeetDetailActivity> ref;
		public MyHandler(MeetDetailActivity act){
			ref = new WeakReference<MeetDetailActivity>(act);
		}
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			final MeetDetailActivity act = ref.get();
			if(act != null){
				Map<String,Object> map = (Map<String, Object>) msg.obj;
				String title = (String)map.get("title");
				int charge = (Integer) map.get("charge");
				if(charge == 0){
					Drawable drawable= act.getResources().getDrawable(R.drawable.btn_free);
					act.titleTv.setCompoundDrawablesWithIntrinsicBounds(null, null,drawable, null);
					act.titleTv.setText(title);
				}else{
					act.titleTv.setText(title + " ￥:" + charge);
				}
				String locale = (String) map.get("locale");
				String time = (String) map.get("time");
				String sponsor = (String) map.get("sponsor");
				
				String text = "<img src='ico_position'/>" + locale + 
						"<br><img src='ico_clock'/>" + time + 
						"<br><img src='ico_people'/>" + sponsor + "<p>";
				CharSequence html = Html.fromHtml(text,new ImageGetter(){
					@Override
					public Drawable getDrawable(String source) {
						Drawable drawable = act.getResources().getDrawable(getResourceId(source));
						drawable.setBounds(0, 0,  drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
						return drawable;
					}
				},null);
				act.timeTv.setText(html);
				
				String content = (String) map.get("content");
				act.contentTv.setText(content);
				
			}
		}
		public int getResourceId(String name){
			try {
				Field field = R.drawable.class.getField(name);
				return Integer.parseInt(field.get(null).toString());
			} catch (Exception e) {
				
			}
			return -1;
		}
	}
	class MyThread extends Thread{
		private String url;
		public MyThread(String url){
			this.url = url;
		}
		@Override
		public void run() {
			String json = DownloadData.downloadJsonData(url);
			Map<String,Object> map = JsonUtils.parseJson(json);
			Message msg = handler.obtainMessage();
			msg.obj = map;
			handler.sendMessage(msg);
		}
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.apply:
			
			break;
		case R.id.share:
			
			break;
		case R.id.collect:
			
			break;
		case R.id.back:
			finish();
			break;
		}
	}
	
}

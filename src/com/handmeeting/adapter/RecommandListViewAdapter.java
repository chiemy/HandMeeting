package com.handmeeting.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmeeting.handmeeting.MainActivity;
import com.handmeeting.handmeeting.MeetDetailActivity;
import com.handmeeting.handmeeting.MyApp;
import com.handmeeting.handmeeting.R;
import com.handmeeting.handmeeting.BitmapTools.BitmapTools;
import com.handmeeting.handmeeting.BitmapTools.DownloadData;
import com.handmeeting.handmeeting.fragment.FindFragment.ViewHolder;

public class RecommandListViewAdapter extends BaseAdapter{
	private List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	private Context mContext;
	private Boolean isFastScroll = false;
	private LruCache<String, Bitmap> cache;
	public RecommandListViewAdapter(Context context){
		mContext = context;
		Application app = ((MainActivity)mContext).getApplication();
		MyApp myApp = (MyApp)app;
		cache = myApp.getCache();
	}
	public void setList(List<Map<String,Object>> data){
		list.addAll(data);
	}
	
	public void setScorll(boolean isFastScroll){
		this.isFastScroll = isFastScroll;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if(convertView == null){
			vh = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.recommand_lv_item, parent,false);
			vh.nameTv = (TextView) convertView.findViewById(R.id.meetingName);
			vh.timeTv = (TextView) convertView.findViewById(R.id.meetingTime);
			vh.statusTv = (TextView) convertView.findViewById(R.id.status);
			vh.priceIv = (ImageView) convertView.findViewById(R.id.price);
			vh.meetImg = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		Map<String,Object> item = list.get(position);
		final String meetId = (String) item.get("id");
		
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(mContext,MeetDetailActivity.class);
				intent.putExtra("meetId", meetId);
				mContext.startActivity(intent);
			}
		});
		
		
		if(item != null){
			vh.nameTv.setText((String)item.get("title"));
			vh.timeTv.setText((String)item.get("time"));
			int statusCode = (Integer) item.get("status");
			String statusStr = "";
			switch(statusCode){
			case 1:
				statusStr = "正在报名";
				break;
			case 2:
				statusStr = "报名结束";
				break;
			case 3:
				statusStr = "会议进行中";
				break;
			case 4:
				statusStr = "已取消";
				break;
			}
			vh.statusTv.setText((statusStr));
			vh.statusTv.setTextColor(Color.YELLOW);
			String charge = (String) item.get("charge");
			System.out.println(charge);
			if("0".equals(charge)){
				vh.priceIv.setImageResource(R.drawable.free);
			}else{
				vh.priceIv.setImageResource(R.drawable.charge);
			}
			
			String imgUrl = (String) item.get("img");
			vh.position = position;
			
			Bitmap bitmap = cache.get(imgUrl);
			if(bitmap == null){
				vh.meetImg.setImageResource(R.drawable.long_baseimg);
				if(!isFastScroll){
					new ImageDownloadTask(vh,position).execute(imgUrl);
				}
			}else{
				vh.meetImg.setImageBitmap(bitmap);
			}
		}
		
		return convertView;
	}
	public class ImageDownloadTask extends AsyncTask<String, Void, Bitmap>{
		private ViewHolder mViewHolder;
		private int mPosition;
		public ImageDownloadTask(ViewHolder vh,int position){
			mViewHolder = vh;
			mPosition = position;
		}
		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap = null;
			byte [] data = DownloadData.downLoadData(params[0]);
			if(data != null){
				bitmap = BitmapTools.decodeSampledBitmapFromResource(data, 200, 100);
				cache.put(params[0], bitmap);
			}
			return bitmap;
		}
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if(result != null && mViewHolder.position == mPosition){
				mViewHolder.meetImg.setImageBitmap(result);
			}
		}
	}
}

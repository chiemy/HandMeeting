package com.handmeeting.handmeeting.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.handmeeting.adapter.MyViewPagerAdapter;
import com.handmeeting.adapter.RecommandListViewAdapter;
import com.handmeeting.handmeeting.MyApp;
import com.handmeeting.handmeeting.R;
import com.handmeeting.handmeeting.BitmapTools.BitmapTools;
import com.handmeeting.handmeeting.BitmapTools.DownloadData;
import com.handmeeting.handmeeting.BitmapTools.JsonUtils;
import com.handmeeting.handmeeting.fragment.FindFragment.ImageDownloadTask;
import com.handmeeting.handmeeting.fragment.FindFragment.ListViewListener;
import com.handmeeting.handmeeting.fragment.FindFragment.MyHandler;
import com.handmeeting.handmeeting.fragment.FindFragment.MyScrollListener;
import com.handmeeting.handmeeting.fragment.FindFragment.MyTask;
import com.handmeeting.urlcontent.UrlContent;

public class RecommandFragment extends Fragment implements OnGestureListener,OnTouchListener{
	private ViewPager viewPager;
	private ViewFlipper viewFlipper;
	private RadioGroup group;
	private List<View> list;
	private GestureDetectorCompat mGestureDetector;
	private MyHandler handler;
	private static String accessToken;
	private ListView recommandlv;
	private LruCache<String, Bitmap> cache;
	private List<Map<String,Object>> totalList;
	private RecommandListViewAdapter lvAdapter;
	private boolean isFastScroll;
	private View hideView;
	private MyViewPagerAdapter viewPagerAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new MyTask().execute(UrlContent.defaultAuth);
		handler = new MyHandler(this);
		cache = ((MyApp)getActivity().getApplication()).getCache();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MyScrollListener scrollListener = new MyScrollListener();
		ListViewListener listViewOnClickLis = new ListViewListener();
		
		View recommand = inflater.inflate(R.layout.recommand_frament, null);
		recommandlv = (ListView) recommand.findViewById(R.id.listView1);
		recommandlv.setId(1001);
		hideView = recommand.findViewById(R.id.c_all_salon);
		
		View imageFlip = inflater.inflate(R.layout.image_flip, null);
		View titleView = inflater.inflate(R.layout.listview_header, null);
		viewFlipper = (ViewFlipper) imageFlip.findViewById(R.id.viewFlipper1);
		mGestureDetector = new GestureDetectorCompat(getActivity(),this);
		viewFlipper.setOnTouchListener(this);
		viewFlipper.setLongClickable(true);
		
		recommandlv.addHeaderView(imageFlip);
		recommandlv.addHeaderView(titleView);
		totalList = new ArrayList<Map<String,Object>>();
		lvAdapter = new RecommandListViewAdapter(getActivity());
		recommandlv.setAdapter(lvAdapter);
		recommandlv.setOnScrollListener(scrollListener);
		recommandlv.setOnItemClickListener(listViewOnClickLis);
		
		return recommand;
	}
	
	
	
	public static class ViewHolder{
		public TextView nameTv;
		public TextView timeTv;
		public TextView statusTv;
		public ImageView priceIv;
		public ImageView meetImg;
		public int position;
	}
	

	public class MyTask extends AsyncTask<String, Void, String>{
		private String url;
		@Override
		protected String doInBackground(String... params) {
			url = params[0];
			String json = DownloadData.downloadJsonData(url);
			return json;
		}
		@Override
		protected void onPostExecute(String json) {
			super.onPostExecute(json);
			Map<String,Object> result = null;
			if(json != null){
				result = JsonUtils.parseJson(json);
			}
			if(result != null){
				if(UrlContent.defaultAuth.equals(url)){
					accessToken = ((MyApp)getActivity().getApplication()).accessToken = result.get("accessToken").toString();
					Message msg = Message.obtain();
					msg.what = 1;
					handler.sendMessage(msg);
				}else if((UrlContent.conferenceList + accessToken).equals(url)){
					List<Map<String, Object>> meetList = (List<Map<String, Object>>) result.get("list");
					lvAdapter.setList(meetList);
					lvAdapter.notifyDataSetChanged();
					
					List<Map<String,Object>> first_list = (List<Map<String, Object>>) result.get("first_list");
					for(int i = 0 ; i < first_list.size() ; i++){
						View view = LayoutInflater.from(getActivity()).inflate(R.layout.image_flip_child, null);
						Map<String,Object> map = first_list.get(i);
						ImageView statuIv = (ImageView) view.findViewById(R.id.statuIv);
						int status =  (Integer) map.get("status");
						System.out.println(status);
						switch(status){
						case 1:
							statuIv.setImageResource(R.drawable.apply);
							break;
						case 2:
							statuIv.setImageResource(R.drawable.over);
							break;
						case 3:
							statuIv.setImageResource(R.drawable.underway);
							break;
						case 4:
							statuIv.setImageResource(R.drawable.cancel);
							break;
						}
						ImageView mainIv = (ImageView) view.findViewById(R.id.mainIv);
						mainIv.setImageResource(R.drawable.long_baseimg);
						mainIv.setScaleType(ScaleType.FIT_XY);
						new ImageDownloadTask(mainIv).execute((String)map.get("img"));
						viewFlipper.addView(view);
					}
				}
			}
		}
	}
	
	public class ImageDownloadTask extends AsyncTask<String, Void, Bitmap>{
		private ImageView mImageView;
		public ImageDownloadTask(ImageView iv){
			mImageView = iv;
		}
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
			if(result != null){
				mImageView.setImageBitmap(result);
			}
		}
	}
	
	public static class MyHandler extends Handler{
		private WeakReference<RecommandFragment> ref;
		public MyHandler(RecommandFragment frag){
			ref = new WeakReference<RecommandFragment>(frag);
		}
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			RecommandFragment frag = ref.get();
			if(frag != null){
				if(msg.what == 1){
					frag.new MyTask().execute(UrlContent.conferenceList + accessToken);
				}
			}
		}
	}
	
	class ListViewListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
		}
	}
	
	class MyScrollListener implements OnScrollListener{
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			isFastScroll = scrollState == SCROLL_STATE_FLING;
			lvAdapter.setScorll(isFastScroll);
			if(scrollState == SCROLL_STATE_IDLE){
				lvAdapter.notifyDataSetChanged();
			}
		}
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			if(firstVisibleItem >= 1){
				hideView.setVisibility(View.VISIBLE);
			}else{
				hideView.setVisibility(View.INVISIBLE);
			}
		}
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if(e1.getX() > e2.getX()) {//向左滑动  
			viewFlipper.setInAnimation(getActivity(), R.animator.push_left_in);     
			viewFlipper.setOutAnimation(getActivity(), R.animator.push_left_out);     
			viewFlipper.showNext();     
        }else if(e1.getX() < e2.getX()) {//向右滑动  
    	   viewFlipper.setInAnimation(getActivity(), R.animator.push_right_in);     
    	   viewFlipper.setOutAnimation(getActivity(), R.animator.push_right_out);     
    	   viewFlipper.showPrevious();     
       }else {     
           return false;     
       }
		return true;     
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		viewFlipper.getParent().requestDisallowInterceptTouchEvent(true);
		return mGestureDetector.onTouchEvent(event);
	}

}

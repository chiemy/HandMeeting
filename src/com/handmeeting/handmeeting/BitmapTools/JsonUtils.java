package com.handmeeting.handmeeting.BitmapTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
	public static Map<String,Object> parseJson(String json){
		Map<String,Object> map = null;
		try {
			JSONObject jsonObj = new JSONObject(json);
			String commandID = jsonObj.getString("commandID");
			String code = jsonObj.getString("code");
			if("200".equals(code)){
				JSONObject jsonObj2 = jsonObj.getJSONObject("data");
				if("user/auth.php".equals(commandID)){
					map = new HashMap<String,Object>();
					String accessToken = jsonObj2.getString("accessToken");
					map.put("accessToken", accessToken);
				}else if("meet/conferenceList.php".equals(commandID)){
					map = new HashMap<String,Object>();
					int firstCount = jsonObj2.getInt("first_count");
					map.put("first_count", firstCount);
					
					JSONArray jsonArr = jsonObj2.getJSONArray("first_list");
					List<Map<String,Object>> list2 = new ArrayList<Map<String,Object>>();
					for(int i = 0 ; i < jsonArr.length() ; i++){
						Map<String,Object> map2 = new HashMap<String,Object>();
						JSONObject jsonObj3 = jsonArr.getJSONObject(i);
						String id = jsonObj3.getString("id");
						String img = jsonObj3.getString("img");
						int status = jsonObj3.getInt("status");
						map2.put("id", id);
						map2.put("img", img);
						map2.put("status", status);
						list2.add(map2);
					}
					map.put("first_list", list2);
					
					String count = jsonObj2.getString("count");
					map.put("count", count);
					
					List<Map<String,Object>> list3 = new ArrayList<Map<String,Object>>();
					JSONArray jsonArr2 = jsonObj2.getJSONArray("list");
					for(int i = 0 ; i < jsonArr2.length() ; i++){
						Map<String,Object> map2 = new HashMap<String,Object>();
						JSONObject jsonObj3 = jsonArr2.getJSONObject(i);
						String id = jsonObj3.getString("id");
						String img = jsonObj3.getString("img");
						String sponsor = jsonObj3.getString("sponsor");
						String title = jsonObj3.getString("title");
						String locale = jsonObj3.getString("locale");
						String charge = jsonObj3.getString("charge");
						int status = jsonObj3.getInt("status");
						String conferenceType = jsonObj3.getString("conferenceType");
						String time = jsonObj3.getString("time");
					
						map2.put("id", id);
						map2.put("img", img);
						map2.put("sponsor", sponsor);
						map2.put("title", title);
						map2.put("locale", locale);
						map2.put("charge", charge);
						map2.put("status", status);
						map2.put("conferenceType", conferenceType);
						map2.put("time", time);
						list3.add(map2);
					}
					map.put("list", list3);
				}else if("meet/meetDetail.php".equals(commandID)){
					map = new HashMap<String,Object>();
					String time = jsonObj2.getString("time");
					String sponsor = jsonObj2.getString("sponsor");
					String title = jsonObj2.getString("title");
					String locale = jsonObj2.getString("locale");
					int charge = jsonObj2.getInt("charge");
					String synopsis = jsonObj2.getString("synopsis");
					int conferenceType = jsonObj2.getInt("conferenceType");
					String img = jsonObj2.getString("img");
					String content = jsonObj2.getString("content");
					String isSignup = jsonObj2.getString("isSignup");
					String isCollection  = jsonObj2.getString("isCollection");
					String isShare = jsonObj2.getString("isShare");
					String shareUrl = jsonObj2.getString("shareUrl");
					int status = jsonObj2.getInt("status");
					
					map.put("time", time);
					map.put("sponsor", sponsor);
					map.put("title", title);
					map.put("locale", locale);
					map.put("charge", charge);
					map.put("synopsis", synopsis);
					map.put("conferenceType", conferenceType);
					map.put("img", img);
					map.put("content", content);
					map.put("isSignup", isSignup);
					map.put("isCollection", isCollection);
					map.put("isShare", isShare);
					map.put("shareUrl", shareUrl);
					map.put("status", status);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		return map;
	}
}

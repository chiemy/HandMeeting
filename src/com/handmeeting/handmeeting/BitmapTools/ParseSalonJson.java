package com.handmeeting.handmeeting.BitmapTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseSalonJson
{
	public static List<Map<String, String>> parseForSalon(String str)
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try
		{
			JSONObject jsonObject1 = new JSONObject(str);
			JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
			JSONArray jsonArray = jsonObject2.getJSONArray("list");
			for(int i = 0; i < jsonArray.length(); i ++)
			{
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", jsonArray.getJSONObject(i).getString("id"));
				map.put("time", jsonArray.getJSONObject(i).getString("time"));
				map.put("img", jsonArray.getJSONObject(i).getString("img"));
				map.put("sponsor", jsonArray.getJSONObject(i).getString("sponsor"));
				map.put("title", jsonArray.getJSONObject(i).getString("title"));
				map.put("locale", jsonArray.getJSONObject(i).getString("locale"));
				map.put("charge", jsonArray.getJSONObject(i).getString("charge"));
				map.put("status", jsonArray.getJSONObject(i).getString("status"));
				list.add(map);
			}
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
}

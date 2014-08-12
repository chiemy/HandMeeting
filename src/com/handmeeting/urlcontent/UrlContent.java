package com.handmeeting.urlcontent;

import java.util.UUID;

public class UrlContent
{
	static{
		deviceToken = UUID.randomUUID().toString();
	}
	public static final String deviceToken;
	//第次都要带上的小尾巴
	public static final String urlTail1 = "?deviceToken="+deviceToken+"&clientType=1&city=beijing&accessToken=";
	public static final String urlTail2 = "&deviceToken="+deviceToken+"&clientType=1&city=beijing&accessToken=";
	//获取密匙
	public static final String defaultAuth = "http://61.153.99.148/handmeet/interface/user/auth.php?deviceToken="+ deviceToken +"&clientType=1&city=beijing";
	//发现--推荐
	public static final String conferenceList = "http://61.153.99.148/handmeet/interface/meet/conferenceList.php?pageno=1&pagesize=10"+urlTail2;
	//http://61.153.99.148/handmeet/interface/meet/conferenceList.php?pageno=1&pagesize=10&deviceToken=a8330cbe-8443-422b-a425-036536964ca7&clientType=1&city=beijing&accessToken=FZTM4Z642S84C2MCH2BIXXM2VV0YEELR
	//推荐大会列表
	public static final String meet = "http://61.153.99.148/handmeet/interface/meet/meetOther.php?conferenceType=1&pageno=1&pagesize=10&categoryId=0"+urlTail2;
	public static final String meetDetail = "http://61.153.99.148/handmeet/interface/meet/meetDetail.php?id=";
	//http://61.153.99.148/handmeet/interface/meet/meetOther.php?conferenceType=2&pageno=1&pagesize=10&categoryId=0&deviceToken=a8330cbe-8443-422b-a425-036536964ca7&clientType=1&city=beijing&accessToken=FZTM4Z642S84C2MCH2BIXXM2VV0YEELR
	//发现--沙龙列表
	public static final String meetOther1 = "http://61.153.99.148/handmeet/interface/meet/meetOther.php?conferenceType=2&pageno=1&pagesize=10&categoryId=0"+urlTail2;
	//发现--讲堂列表
	public static final String meetOther2="http://61.153.99.148/handmeet/interface/meet/meetOther.php?conferenceType=3&pageno=1&pagesize=10&categoryId=0"+urlTail2;
	//会议详情
	//public static String meetDetail = "http://61.153.99.148/handmeet/interface/meet/meetDetail.php?id=22&userId=20"
}

package com.handmeeting.handmeeting.BitmapTools;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * @author chen
 *从网络上下载JSON数据
 */
public class DownloadData
{
	public static String downloadJsonData(String url)
	{
		String result = null;
		byte [] data = downLoadData(url);
		if(data != null){
			result = new String(data);
		}
		return result;
	}
	public static byte [] downLoadData(String url){
		byte [] data = null;
		HttpURLConnection conn = null;
		URL u;
		if(url != null && !url.isEmpty())
		{
			try
			{
				u = new URL(url);
				conn = (HttpURLConnection) u.openConnection();
				conn.setConnectTimeout(10 * 1000);//设置连接服务器超时
				conn.setReadTimeout(10 * 1000); //设置读取数据超时
				conn.setDoInput(true);
				conn.setRequestMethod("GET");
				conn.connect();
				if(conn.getResponseCode() == 200)
				{
					BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buffer = new byte[512];
					int len = 0;
					while(-1 !=(len=bis.read(buffer)))
					{
						baos.write(buffer, 0, len);
					}
					data = baos.toByteArray();
					bis.close();
					baos.close();
					return data;
				}
			}
			catch (MalformedURLException e)
			{
				e.printStackTrace();
			}
			catch(SocketTimeoutException e)
			{
				System.out.println("-----连接超时-----");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch(Exception e){
				
			}
			finally
			{
				conn.disconnect();
			}
		}
		else
		{
			System.out.println("----传入url地址为空!------");
		}
		return data;
	}
}	

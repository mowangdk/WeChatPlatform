package turing;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TulingApiProcess {
	/*
	 * 調用圖靈api接口,獲取智能回復內容
	 */
	public String getTulingResult(String content) {
		// 此處為圖靈api接口,參數key需要去註冊申請先以111111代替
		String apiUrl = "http://www.tuling123.com/openapi/api?key=1e761b4943d560bba2f06bca1106959d&info=";

		String param = "";
		// 将参数转化为url编码
		try {
			param = apiUrl + URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/* 发送httpget请求 */
		HttpGet request = new HttpGet(param);
		String result = "";
		try {
			HttpResponse response = HttpClients.createDefault()
					.execute(request);
			//根据返回码判断是否成功
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 请求失败处理
		if (null == result) {
			return "对不起,你说的话真是太高深了 @ —— @";
		}

		JSONObject json;
		try {
			json = new JSONObject(result);
			switch (json.getInt("code")){
			   case 100000:{
				   result  = json.getString("text");
				   break;
			   }
			   case 200000:{
				   result = json.getString("text") +": \n"+ json.getString("url");
				   break;
			   }
			   case 302000:{
				   JSONArray array = json.getJSONArray("list");
				   StringBuffer sb = new StringBuffer();
				   for(int i = 0;i < array.length();i++){
					  JSONObject jsonObject = (JSONObject)array.opt(i);
					  sb.append(jsonObject.getString("article")).append("\n").append(jsonObject.getString("source")).append("\n").append(jsonObject.getString("detailurl")).append("\n\n");
				   }
				   result = json.getString("text")+": \n"+sb.toString();
				   break;
			   }
			   case 305000:{
				   JSONArray array = json.getJSONArray("list");
				   StringBuffer sb = new StringBuffer();
				   for(int i = 0;i < array.length();i++){
					  JSONObject jsonObject = (JSONObject)array.opt(i);
					  sb.append(jsonObject.getString("trainnum")).append("\n").append(jsonObject.getString("start")).append(jsonObject.getString("starttime")).append("\n").append(jsonObject.getString("terminal")).append(jsonObject.getString("endtime")).append("\n").append(jsonObject.getString("detailurl")).append("\n\n");
				   }
				   result = json.getString("text")+": \n"+sb.toString();
				   break; 
			   }
			   case 306000:{
				   JSONArray array = json.getJSONArray("list");
				   StringBuffer sb = new StringBuffer();
				   for(int i = 0;i < array.length();i++){
					  JSONObject jsonObject = (JSONObject)array.opt(i);
					  sb.append(jsonObject.getString("flight")).append("\n").append(jsonObject.getString("starttime")).append("\n").append("endtime").append("\n\n");
				   }
				   result = json.getString("text")+": \n"+sb.toString();
				   break;
			   }
			   case 308000:{
				   JSONArray array = json.getJSONArray("list");
				   StringBuffer sb = new StringBuffer();
				   for(int i = 0;i < array.length();i++){
					  JSONObject jsonObject = (JSONObject)array.opt(i);
					  sb.append(jsonObject.getString("name")).append(i).append("\n").append("info").append("\n").append(jsonObject.getString("detailurl")).append("\n\n");
				   }
				   result = json.getString("text")+": \n"+sb.toString();
				   break;   
			   }
			   default:{
				   result = "亲,服务器出现了一点故障,正在处理中";
				   
			   }
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
}

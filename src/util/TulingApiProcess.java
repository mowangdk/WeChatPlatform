package util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class TulingApiProcess {
	/*
	 * 調用圖靈api接口,獲取智能回復內容
	 * */
	public String getTulingResult(String content){
		//此處為圖靈api接口,參數key需要去註冊申請先以111111代替
		String apiUrl = "http://www.tuling123.com/openapi/api?key=11111111&info=";
		
		String param = "";
		//将参数转化为url编码
		try{
			param = apiUrl + URLEncoder.encode(content,"utf-8");
		} catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}
		
		/*发送httpget请求*/
		HttpGet request = new HttpGet(param);
		String result = "";
		try {
			HttpResponse response = HttpClients.createDefault().execute(request);
			if (response.getStatusLine().getStatusCode() == 200){
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//请求失败处理
		if (null == result){
			return "对不起,你说的话真是太高深了 @ —— @";
		}
		
		JSONObject json = JSONObject.fromObject(result);
		if (100000 == json.getInt("code")){
			result = json.getString("text");
		}
		return result;
	}
}

package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class WeChatServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		// 读取接收到的xml信息
		StringBuffer sb = new StringBuffer();
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String s = "";
		while ((s = br.readLine()) != null) {
			sb.append(s);
		}

		String xml = sb.toString(); // 次为接受到微信端发送过来的xml数据

		String result = "";

		/* 判断是否为微信接入激活验证,只有首次接入才会收到echostr参数,此时需要将它直接返回 */
		
//		进行signature检验
//		signatrueCheck(request.getParameter("timestamp"),request.getParameter("nonce"));
		
		String echostr = request.getParameter("echostr");
		if (null != echostr && echostr.length() > 1) {
			result = echostr;
		} else {
			// 正常的微信处理流程
			result = new WechatProcess().processWechatMag(xml);
		}
		try {
			OutputStream os = response.getOutputStream();
			os.write(result.getBytes("UTF-8"));
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void signatrueCheck(String parameter, String parameter2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
	

}

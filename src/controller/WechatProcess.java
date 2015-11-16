package controller;

import turing.TulingApiProcess;
import util.FormatXmlProcess;
import bean.ReceiveXmlEntity;
import util.ReceiveXmlProcess;


public class WechatProcess {

	public String processWechatMag(String xml) {
		//解析xml数据
		ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml);
		
		//以文本消息为例,调用图灵机器人api接口,获取回复内容
		String result = "";
//		if ("text".endsWith(xmlEntity.getMsgType())){
			result = new TulingApiProcess().getTulingResult(xmlEntity.getContent());
//		}
		
		result = new FormatXmlProcess().formatXmlAnswer(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), result);
		return result;
	}

}

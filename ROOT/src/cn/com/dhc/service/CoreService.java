package cn.com.dhc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.com.dhc.entity.response.Article;
import cn.com.dhc.entity.response.NewsMessage;
import cn.com.dhc.entity.response.TextMessage;
import cn.com.dhc.util.MessageUtil;

public class CoreService {
	
	public static String processRequest(HttpServletRequest request){
		String respMessage = null;
		String respContent = "请求处理异常，请稍后重试！";
		
		try {
			Map<String,String> requestMap = MessageUtil.parseXml(request);
			String fromUserName = requestMap.get("FromUserName");
			String toUserName = requestMap.get("ToUserName");
			String msgType = requestMap.get("MsgType");
			
			TextMessage textMessage = new TextMessage();
			textMessage.setFromUserName(toUserName);
			textMessage.setToUserName(fromUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			System.out.println("消息类型:"+msgType);
			if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
				//文本消息
				respContent = "你发送的是文本消息:\n"+requestMap.get("Content")+"\n更多消息请点击<a href=\"http://www.baidu.com\">百度</a>";
				if("图文".equals(requestMap.get("Content"))){
					NewsMessage message = new NewsMessage();
					List<Article> list = new ArrayList<Article>();
					
					Article art1 = new Article();
					art1.setTitle("神舟飞船发射成功");
					art1.setDescription("2016年10月17日上午7点半，中国神舟11号载人航天飞船在甘肃酒泉卫星发射基地成功发射升空！");
					art1.setPicUrl("http://xuxianshun.imwork.net/img/1.jpg");
					art1.setUrl("http://tech.qq.com/a/20161017/020186.htm");					
					list.add(art1);
					
					Article art2 = new Article();
					art2.setTitle("\"神十一\"整流罩残骸已找到 准确降落陕西榆林");
					art2.setDescription("10月17日，搭载两名航天员的神舟十一号载人飞船由长征二号F遥十一运载火箭成功发射升空，其整流罩残骸和黑匣子在陕西省榆林市榆阳区一处空旷地区找到。");
					art2.setPicUrl("http://xuxianshun.imwork.net/img/2.jpg");
					art2.setUrl("http://mil.sohu.com/20161017/n470461235.shtml?qq-pf-to=pcqq.discussion");					
					list.add(art2);
					
					Article art3 = new Article();
					art3.setTitle("民调：希拉里“春风得意”，领先特朗普11个百分点");
					art3.setDescription("据NBC新闻/《华尔街日报(博客,微博)》的民调显示，自美选二辩结束以来，美国民主党总统候选人希拉里领先共和党总统候选人特朗普的优势扩大至两位数");
					art3.setPicUrl("http://xuxianshun.imwork.net/img/3.jpg");
					art3.setUrl("http://forex.hexun.com/2016-10-17/186446585.html");					
					list.add(art3);
					
					
					message.setArticleCount(list.size());
					message.setArticles(list);
					message.setCreateTime(new Date().getTime());
					message.setFromUserName(toUserName);
					message.setToUserName(fromUserName);
					message.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					respContent = MessageUtil.newsMessageToXml(message);
					
					return respContent;
				}
				if("爱".equals(requestMap.get("Content"))){
					respContent = "我爱老婆和小棉袄!\ue057";
					textMessage.setContent(respContent);
					return MessageUtil.messageToXml(textMessage);
				}
					
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)){
				//图片消息
				respContent = "你发送的是图片消息";
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)){
				//链接消息
				respContent = "你发送的是/链接消息";
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)){
				//语音消息
				respContent = "你发送的是语音消息";
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)){
				//地理位置
				respContent = "你发送的是地理位置";
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)){
				//视频
				respContent = "你发送的是视频";
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO)){
				//小视频
				respContent = "你发送的是小视频";
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)){
				//服务器的事件推送
				String eventType = requestMap.get("Event");
				System.out.println("event类型:"+eventType);
				if(eventType.equals(MessageUtil.REQ_MESSAGE_TYPE_CLICK)){
					//自定义菜单点击消息
					respContent = "你点击了自定义菜单";
				}else if(eventType.equals(MessageUtil.REQ_MESSAGE_TYPE_SUBSCRIBE)){
					//关注公众号事件，
					respContent = "谢谢关注！你可以任意发言";
				}else if(eventType.equals(MessageUtil.REQ_MESSAGE_TYPE_UNSUBSCRIBE)){
					//取消关注事件,不需要回复，因为已经取消订阅了
				}else if(eventType.equals("SCAN")){
					respContent = "从扫描进入！";
				}
				
			}
			
			textMessage.setContent(respContent+"/::)\ue428"+MessageUtil.emoji(0x2708));
			respMessage = MessageUtil.messageToXml(textMessage);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respMessage;
	}
	
}

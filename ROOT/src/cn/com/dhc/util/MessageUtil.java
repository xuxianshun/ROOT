package cn.com.dhc.util;

import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.com.dhc.entity.response.Article;
import cn.com.dhc.entity.response.NewsMessage;
import cn.com.dhc.entity.response.TextMessage;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 微信消息处理共通
 * @author Administrator
 *
 */
public class MessageUtil {

	public static final String RESP_MESSAGE_TYPE_TEXT="text";
	public static final String RESP_MESSAGE_TYPE_MUSIC="music";
	public static final String RESP_MESSAGE_TYPE_NEWS="news";
	
	public static final String REQ_MESSAGE_TYPE_TEXT="text";
	public static final String REQ_MESSAGE_TYPE_IMAGE="image";
	public static final String REQ_MESSAGE_TYPE_LINK="link";
	public static final String REQ_MESSAGE_TYPE_LOCATION="location";
	public static final String REQ_MESSAGE_TYPE_VOICE="voice";
	public static final String REQ_MESSAGE_TYPE_EVENT="event";
	public static final String REQ_MESSAGE_TYPE_SUBSCRIBE="subscribe";
	public static final String REQ_MESSAGE_TYPE_UNSUBSCRIBE="unsubscribe";
	public static final String REQ_MESSAGE_TYPE_CLICK="click";
	public static final String REQ_MESSAGE_TYPE_VIDEO="video";
	public static final String REQ_MESSAGE_TYPE_SHORTVIDEO="shortvideo";
	
	/** 
     * 扩展xstream，使其支持CDATA块 
     *  
     */  
    private static XStream xstream = new XStream(new XppDriver() {  
        public HierarchicalStreamWriter createWriter(Writer out) {  
            return new PrettyPrintWriter(out) {  
                // 对所有xml节点的转换都增加CDATA标记  
                boolean cdata = true;  
                
                public void startNode(String name, Class clazz) {  
                    super.startNode(name, clazz);  
                }  
  
                protected void writeText(QuickWriter writer, String text) {  
                    if (cdata) {  
                        writer.write("<![CDATA[");  
                        writer.write(text);  
                        writer.write("]]>");  
                    } else {  
                        writer.write(text);  
                    }  
                }  
            };  
        }  
    });  
	
	/*
	 * 解析微信发来的请求（XML） 
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> parseXml(HttpServletRequest request)throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		SAXReader reader = new SAXReader();
		Document document = reader.read(request.getInputStream());
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		for(Element e:elementList){
			map.put(e.getName(), e.getText());
		}
		return map;
	}
	
	/*
	 * 文本消息对象转化为xml
	 */
	public static String textMessageToXml(TextMessage message){
		xstream.alias("xml",message.getClass());
		return xstream.toXML(message);
		
	}
	
	/**
	 * 图文消息转化为xml
	 * @param message
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage message){
		xstream.alias("xml", message.getClass());
		xstream.alias("item", Article.class);
		return xstream.toXML(message);
	}
	
	/**
	 * 通用对象转化为xml
	 * @param message
	 * @return
	 */
	public static String messageToXml(Object message){
		xstream.alias("xml", message.getClass());
		return xstream.toXML(message);
	}
	
	/**
	 * 返回创建时间format之后的字符串
	 * @param createTime
	 * @return
	 */
	public static String formatTime(String createTime){
		long lngTime = Long.parseLong(createTime)*1000L;
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(lngTime));
	}
	
	/**
	 * emoji表情转换(例如飞机0x2708)
	 */
	public static String emoji(int hexEmoji){
		return String.valueOf(Character.toChars(hexEmoji));
	}
	
	/**
	 * 返回字符串的长度（使用UTF-8）计算
	 * @param content
	 * @return
	 */
	public static int getByteSize(String content){
		int size = 0;
		if(content!=null&&!"".equals(content)){
			try {
				size = content.getBytes("utf-8").length;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return size;
	}
}

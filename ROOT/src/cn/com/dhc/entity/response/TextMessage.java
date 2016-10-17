package cn.com.dhc.entity.response;
/**
 * 公众号回复给用户的文本消息
 * @author Administrator
 *
 */
public class TextMessage extends BaseMessage {
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	
}

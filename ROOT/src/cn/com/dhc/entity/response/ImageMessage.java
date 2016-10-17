package cn.com.dhc.entity.response;

/**
 * 回复图片消息
 * @author Administrator
 *
 */
public class ImageMessage extends BaseMessage {
	
	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}
	
}

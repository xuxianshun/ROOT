package cn.com.dhc.entity.response;
/**
 * 回复
 * 图片消息中的图片类
 * @author Administrator
 *
 */
public class Image {
	//通过素材管理中的接口上传多媒体文件，得到的id
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	
}

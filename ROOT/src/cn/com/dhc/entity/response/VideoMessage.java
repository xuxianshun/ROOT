package cn.com.dhc.entity.response;
/**
 * 回复视频消息
 * @author Administrator
 *
 */
public class VideoMessage extends BaseMessage {
	private Video Video;

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		Video = video;
	}
}

package cn.com.dhc.entity;

import java.util.List;

/**
 * 翻译结果对象
 * @author Administrator
 *
 */
public class TranslateResult {

	/*
	 * 翻译源语言
	 */
	private String from;
	/*
	 * 翻译目标语言
	 */
	private String to;
	/*
	 * 翻译结果
	 */
	private List<Result> trans_result;
	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public List<Result> getTrans_result() {
		return trans_result;
	}
	public void setTrans_result(List<Result> trans_result) {
		this.trans_result = trans_result;
	}
	
}

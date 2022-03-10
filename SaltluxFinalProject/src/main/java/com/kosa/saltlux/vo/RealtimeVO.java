package com.kosa.saltlux.vo;

public class RealtimeVO {
	private String keyword;
	private int btfRatio;
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getBtfRatio() {
		return btfRatio;
	}
	public void setBtfRatio(int btfRatio) {
		this.btfRatio = btfRatio;
	}
	
	@Override
	public String toString() {
		return "realtimeVO [keyword=" + keyword + ", btfRatio=" + btfRatio + "]";
	}
	
}

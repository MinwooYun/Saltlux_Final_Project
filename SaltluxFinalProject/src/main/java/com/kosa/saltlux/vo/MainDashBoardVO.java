package com.kosa.saltlux.vo;

public class MainDashBoardVO {
	private String keyword;
	private String category;
	private int btf;
	
	@Override
	public String toString() {
		return "MainDashBoardVO [keyword=" + keyword + ", category=" + category + ", btf=" + btf + "]";
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getBtf() {
		return btf;
	}

	public void setBtf(int btf) {
		this.btf = btf;
	}
}

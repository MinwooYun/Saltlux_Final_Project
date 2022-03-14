package com.kosa.saltlux.vo;

import java.util.Date;

public class NewsVO {
	private String newsNo;
	private String title;
	private String contents;
	private String imageURL;
	private String thumbnailURL;
	private String press;
	private String category;
	private String newsDate;
	private String nouns;
	private String fragments;
	
	public String getNewsNo() {
		return newsNo;
	}
	public void setNewsNo(String newsNo) {
		this.newsNo = newsNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public String getThumbnailURL() {
		return thumbnailURL;
	}
	public void setThumbnailURL(String thumbnailURL) {
		this.thumbnailURL = thumbnailURL;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getNewsDate() {
		return newsDate;
	}
	public void setNewsDate(String newsDate) {
		this.newsDate = newsDate;
	}
	public String getNouns() {
		return nouns;
	}
	public void setNouns(String nouns) {
		this.nouns = nouns;
	}
	
	public String getFragments() {
		return fragments;
	}
	public void setFragments(String fragments) {
		this.fragments = fragments;
	}
	@Override
	public String toString() {
		return "newsVO [newsNo=" + newsNo + ", title=" + title + ", contents=" + contents + ", imageURL=" + imageURL
				+ ", thumbnailURL=" + thumbnailURL + ", press=" + press + ", category=" + category + ", newsDate="
				+ newsDate + ", nouns=" + nouns + "]";
	}
	
	
}

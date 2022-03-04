package com.kosa.saltlux.vo;

import java.sql.Date;

public class newsVO {
	private int newsNo;
	private String title;
	private String contents;
	private String imageURL;
	private String thumbnailURL;
	private String press;
	private String category;
	private Date newsDate;
	private String nouns;
	public int getNewsNo() {
		return newsNo;
	}
	public void setNewsNo(int newsNo) {
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
	public Date getNewsDate() {
		return newsDate;
	}
	public void setNewsDate(Date newsDate) {
		this.newsDate = newsDate;
	}
	public String getNouns() {
		return nouns;
	}
	public void setNouns(String nouns) {
		this.nouns = nouns;
	}
	
	@Override
	public String toString() {
		return "newsVO [newsNo=" + newsNo + ", title=" + title + ", contents=" + contents + ", imageURL=" + imageURL
				+ ", thumbnailURL=" + thumbnailURL + ", press=" + press + ", category=" + category + ", newsDate="
				+ newsDate + ", nouns=" + nouns + "]";
	}
	
	
}

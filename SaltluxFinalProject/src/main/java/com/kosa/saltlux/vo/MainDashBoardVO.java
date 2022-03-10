package com.kosa.saltlux.vo;

public class MainDashBoardVO {
	private int year;
	private int month;
	private int day;
	private int hour;
	
	private String keyword;
	private String category;
	
	private int btf;
	private float ntf1;
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
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
	public float getNtf1() {
		return ntf1;
	}
	public void setNtf1(float ntf1) {
		this.ntf1 = ntf1;
	}
	
	@Override
	public String toString() {
		return "mainDashBoard [year=" + year + ", month=" + month + ", day=" + day + ", hour=" + hour + ", keyword="
				+ keyword + ", category=" + category + ", btf=" + btf + ", ntf1=" + ntf1 + "]";
	}
	
}

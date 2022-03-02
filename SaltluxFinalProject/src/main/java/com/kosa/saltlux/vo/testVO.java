package com.kosa.saltlux.vo;

import java.sql.Date;

public class testVO {
	private int productNo;
	private String sallerUserId;
	private String productName;
	private int productPrice;
	private String productDescription;
	private String productSaleStatusId;
	private String productCategoryName;
	private Date purchaseDate;
	public int getProductNo() {
		return productNo;
	}
	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}
	public String getSallerUserId() {
		return sallerUserId;
	}
	public void setSallerUserId(String sallerUserId) {
		this.sallerUserId = sallerUserId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getProductSaleStatusId() {
		return productSaleStatusId;
	}
	public void setProductSaleStatusId(String productSaleStatusId) {
		this.productSaleStatusId = productSaleStatusId;
	}
	public String getProductCategoryName() {
		return productCategoryName;
	}
	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	@Override
	public String toString() {
		return "testVO [productNo=" + productNo + ", sallerUserId=" + sallerUserId + ", productName=" + productName
				+ ", productPrice=" + productPrice + ", productDescription=" + productDescription
				+ ", productSaleStatusId=" + productSaleStatusId + ", productCategoryName=" + productCategoryName
				+ ", purchaseDate=" + purchaseDate + "]";
	}
	
	
}

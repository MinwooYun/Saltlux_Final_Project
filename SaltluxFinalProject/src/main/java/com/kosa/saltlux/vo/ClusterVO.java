package com.kosa.saltlux.vo;

public class ClusterVO {
	private int clusterNum;
	private String articleIndex;
	private int cnt;
	
	public int getClusterNum() {
		return clusterNum;
	}
	public void setClusterNum(int clusterNum) {
		this.clusterNum = clusterNum;
	}
	public String getArticleIndex() {
		return articleIndex;
	}
	public void setArticleIndex(String articleIndex) {
		this.articleIndex = articleIndex;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
	@Override
	public String toString() {
		return "ClusterVO [clusterNum=" + clusterNum + ", articleIndex=" + articleIndex + ", cnt=" + cnt + "]";
	}
}

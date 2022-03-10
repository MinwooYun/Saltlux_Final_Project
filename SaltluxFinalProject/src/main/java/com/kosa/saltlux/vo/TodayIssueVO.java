package com.kosa.saltlux.vo;

import java.util.List;

public class TodayIssueVO {
	private int clusterNum;
	private List<Integer> docList;
	
	public int getClusterNum() {
		return clusterNum;
	}
	public void setClusterNum(int clusterNum) {
		this.clusterNum = clusterNum;
	}
	public List<Integer> getDocList() {
		return docList;
	}
	public void setDocList(List<Integer> docList) {
		this.docList = docList;
	}
	
	@Override
	public String toString() {
		return "todayIssueVO [clusterNum=" + clusterNum + ", docList=" + docList + "]";
	}
	
}

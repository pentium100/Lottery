package com.itg.dao;

import javax.persistence.*;

@Entity
@Table(name = "MenuItems")
public class MenuItem {
	
	
	private int parentID;
	
	
	private int ID;

	private String menuText;
	private String reportId;
	private String queryString;
	private String url;
	private String defQueryString;
	public String getDefQueryString() {
		return defQueryString;
	}
	public void setDefQueryString(String defQueryString) {
		this.defQueryString = defQueryString;
	}
	private boolean leaf;

	//主键,注解方式

	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	
	@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
	public int getID() {
		return ID;
	}
	public void setID(int id) {
		ID = id;
	}
	
	public int getParentID() {
		return parentID;
	}
	public void setParentID(int parentID) {
		this.parentID = parentID;
	}
	public String getMenuText() {
		return menuText;
	}
	public void setMenuText(String menuText) {
		this.menuText = menuText;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	

}

package com.example.news.entity;

public class Image {
	private String ctime;
    private String title;
    private String description;
    private String picUrl;
    private String url;
	public Image(String ctime, String title, String description, String picUrl,
			String url) {
		super();
		this.ctime = ctime;
		this.title = title;
		this.description = description;
		this.picUrl = picUrl;
		this.url = url;
	}
	public Image() {
		
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Image [ctime=" + ctime + ", title=" + title + ", description="
				+ description + ", picUrl=" + picUrl + ", url=" + url + "]";
	}
    
    
    
}

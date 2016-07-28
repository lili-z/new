package com.example.news.entity;

public class News {


	private String title;
	private String url;
	private String abstracts;
	private String image_url;
	public News(String title, String url, String abstracts, String image_url) {
		super();
		this.title = title;
		this.url = url;
		this.abstracts = abstracts;
		this.image_url = image_url;
	}
	public News() {
		super();
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAbstracts() {
		return abstracts;
	}
	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	@Override
	public String toString() {
		return  title ;
	}
	
}

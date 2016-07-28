package com.example.news.entity;

import java.util.List;

public class Data {
	private String channel;

	private List<Article> article ;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public List<Article> getArticle() {
		return article;
	}

	public void setArticle(List<Article> article) {
		this.article = article;
	}

	
}

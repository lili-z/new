package com.example.news.entity;

public class Jok {

	String title;
	String text;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "Jok [title=" + title +  "]";
	}
	
}

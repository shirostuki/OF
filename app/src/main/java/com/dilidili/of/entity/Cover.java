package com.dilidili.of.entity;public class Cover extends BaseBean{	private long height;	private String src;	private long width;	public long getHeight() {		return this.height;	}	public void setHeight(long height) {		this.height = height;	}	public String getSrc() {		return this.src;	}	public void setSrc(String src) {		this.src = src;	}	public long getWidth() {		return this.width;	}	public void setWidth(long width) {		this.width = width;	}	public String toString() {		return "Cover [height = " + height + ", src = " + src + ", width = " + width + "]";	}}
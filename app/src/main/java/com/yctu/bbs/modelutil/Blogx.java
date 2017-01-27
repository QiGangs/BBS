package com.yctu.bbs.modelutil;

public class Blogx {
private String id;
private String title;
private String date;
private String username;
private String headpic;

public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}

public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getHeadpic() {
	return headpic;
}
public void setHeadpic(String headpic) {
	this.headpic = headpic;
}
@Override
public String toString() {
	return "Blogx [title=" + title + ", date=" + date + ", username=" + username + ", headpic=" + headpic + "]";
}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}




}

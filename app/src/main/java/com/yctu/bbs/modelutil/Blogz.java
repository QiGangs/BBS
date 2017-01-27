package com.yctu.bbs.modelutil;

public class Blogz {
private String account;
private String username;
private int id;
private String title;
private String content;
private String date;
private int statue;
private String headpic;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public int getStatue() {
	return statue;
}
public void setStatue(int statue) {
	this.statue = statue;
}
public String getAccount() {
	return account;
}
public void setAccount(String account) {
	this.account = account;
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
	return "Blogz [account=" + account + ", username=" + username + ", id=" + id + ", title=" + title + ", content="
			+ content + ", date=" + date + ", statue=" + statue + ", headpic=" + headpic + "]";
}


}

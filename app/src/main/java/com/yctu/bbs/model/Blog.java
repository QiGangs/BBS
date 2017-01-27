package com.yctu.bbs.model;

/**
 * Created by qigang on 2017/1/15.
 */
public class Blog {
    private int id;
    private String title;
    private String content;
    private User user;
    private String date;
    private int statue;
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
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
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
    @Override
    public String toString() {
        return "Blog [id=" + id + ", title=" + title + ", content=" + content + ", user=" + user + ", date=" + date
                + ", statue=" + statue + "]";
    }


}

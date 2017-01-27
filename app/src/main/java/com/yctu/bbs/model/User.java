package com.yctu.bbs.model;

/**
 * Created by qigang on 2017/1/14.
 */

public class User {
    private int id;
    private String account;
    private String password;
    private String username;
    private String headpic;
    private String aword;
    private int statue;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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
    public String getAword() {
        return aword;
    }
    public void setAword(String aword) {
        this.aword = aword;
    }
    public int getStatue() {
        return statue;
    }
    public void setStatue(int statue) {
        this.statue = statue;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", account=" + account + ", password=" + password + ", username=" + username
                + ", headpic=" + headpic + ", aword=" + aword + ", statue=" + statue + "]";
    }

}

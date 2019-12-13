package com.itheima.health.pojo;

import java.io.Serializable;

public class Consumer implements Serializable {

    private Integer id;
    private String nickName;
    private String avatarUrl;
    private Integer gender;
    private String phoneNumber;
    private String openid;

    public Consumer() {
    }

    public Consumer(String nickName, String avatarUrl, Integer gender, String openid, String randPass) {
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
        this.gender = gender;
        this.openid = openid;
        this.randPass = randPass;
    }

    private String randPass;
    private String session_key;

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getRandPass() {
        return randPass;
    }

    public void setRandPass(String randPass) {
        this.randPass = randPass;
    }
//    other1 other2
}

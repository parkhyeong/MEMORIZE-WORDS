package com.example.admin_voca;

import java.io.Serializable;

public class User_E implements Serializable {
    private int id;
    private String uId;
    private String uPw;
    private String uName;
    private String uTel;
    private String uBirth;
    private String uNickName;
    private String uEmail;
    private String uTargetScore;
    public User_E() {
        super();
    }
    public User_E(String uId, String uPw, String uName, String uTel,
                  String uBirth, String uNickName, String uEmail, String uTargetScore){
        super();
        this.uId = uId;
        this.uPw = uPw;
        this.uName = uName;
        this.uTel = uTel;
        this.uBirth = uBirth;
        this.uNickName = uNickName;
        this.uEmail = uEmail;
        this.uTargetScore = uTargetScore;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUid() {
        return uId;
    }
    public void setUid(String uId) {
        this.uId = uId;
    }
    public String getUpw() {
        return uPw;
    }
    public void setUpw(String uPw) {
        this.uPw = uPw;
    }
    public String getUname() {
        return uName;
    }
    public void setUname(String uName) {
        this.uName = uName;
    }
    public String getUtel() {
        return uTel;
    }
    public void setUtel(String uTel) {
        this.uTel = uTel;
    }
    public String getUbirth() {
        return uBirth;
    }
    public void setUbirth(String uBirth) {
        this.uBirth = uBirth;
    }
    public String getUnickname() {
        return uNickName;
    }
    public void setUnickname(String uNickName) {
        this.uNickName = uNickName;
    }
    public String getUemail() {
        return uEmail;
    }
    public void setUemail(String uEmail) {
        this.uEmail = uEmail;
    }
    public String getUtargetscore() {
        return uTargetScore;
    }
    public void setUtargetscore(String uTargetScore) {
        this.uTargetScore = uTargetScore;
    }
    @Override
    public String toString() {
        return "User_E [id=" +id+", uId=" +uId+ ", uPw=" +uPw+ ", uName=" +uName+", uTel=" +uTel+", uBirth="+uBirth+
                ", uNickName=" +uNickName+", uEmail=" +uEmail+", uTargetScore="+uTargetScore+ "]";
    }
}

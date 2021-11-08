package com.example.bottomapp;

public class User {

    private String docId, uid, email, login, pass, avatar, vk, inst, yt;


    public User (String docId, String uid, String email, String login, String pass, String avatar, String vk, String inst, String yt) {
        this.docId = docId;
        this.uid = uid;
        this.email = email;
        this.login = login;
        this.pass = pass;
        this.avatar = avatar;
        this.vk = vk;
        this.inst = inst;
        this.yt = yt;
    }

    public void setUser (User user) {
        this.docId = user.docId;
        this.uid = user.uid;
        this.email = user.email;
        this.login = user.login;
        this.pass = user.pass;
        this.avatar = user.avatar;
        this.vk = user.vk;
        this.inst = user.inst;
        this.yt = user.yt;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getVk() {
        return vk;
    }

    public void setVk(String vk) {
        this.vk = vk;
    }

    public String getInst() {
        return inst;
    }

    public void setInst(String inst) {
        this.inst = inst;
    }

    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }
}

package com.example.bottomapp;

import java.util.ArrayList;

public class PlayerItem {
    public int beatId, authorId;
    public String beatURL, beatTitle, beatAuthor, beatCoverURL;


    public PlayerItem(int authorId, String beatTitle, String beatURL, String beatCoverURL) {
        this.authorId = authorId;
        this.beatAuthor = beatAuthor;
        this.beatTitle = beatTitle;
        this.beatURL = beatURL;
        this.beatCoverURL = beatCoverURL;
    }

    public ArrayList<String> toArrayList() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(beatURL);
        arrayList.add(beatTitle);
        arrayList.add(beatAuthor);
        arrayList.add(beatCoverURL);
        return arrayList;
    }

    public int getBeatId() {
        return beatId;
    }

    public void setBeatId(int beatId) {
        this.beatId = beatId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getBeatURL() {
        return beatURL;
    }

    public void setBeatURL(String beatURL) {
        this.beatURL = beatURL;
    }

    public String getBeatTitle() {
        return beatTitle;
    }

    public void setBeatTitle(String beatTitle) {
        this.beatTitle = beatTitle;
    }

    public String getBeatAuthor() {
        return beatAuthor;
    }

    public void setBeatAuthor(String beatAuthor) {
        this.beatAuthor = beatAuthor;
    }

    public String getBeatCoverURL() {
        return beatCoverURL;
    }

    public void setBeatCoverURL(String beatCoverURL) {
        this.beatCoverURL = beatCoverURL;
    }
}

package com.example.bottomapp;

import java.util.ArrayList;
import java.util.List;

public class Beat {
    private String beatId, authorId, beatURL, beatTitle, beatDescription, coverURL, duration;
    private List<String> liked;

    public Beat(String beatId, String authorId, String beatURL, String beatTitle, String beatDescription, String coverURL, String duration, List<String> liked) {
        this.beatId = beatId;
        this.authorId = authorId;
        this.beatURL = beatURL;
        this.beatTitle = beatTitle;
        this.beatDescription = beatDescription;
        this.coverURL = coverURL;
        this.duration = duration;
        this.liked = liked;
    }

    public ArrayList<String> toArrayList() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(beatId);
        arrayList.add(authorId);
        arrayList.add(beatURL);
        arrayList.add(beatTitle);
        arrayList.add(beatDescription);
        arrayList.add(coverURL);
        arrayList.add(duration);
        arrayList.add(liked.toString());
        return arrayList;
    }

    public String getBeatId() {
        return beatId;
    }

    public void setBeatId(String beatId) {
        this.beatId = beatId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
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

    public String getBeatDescription() {
        return beatDescription;
    }

    public void setBeatDescription(String beatDescription) {
        this.beatDescription = beatDescription;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<String> getLiked() {
        return liked;
    }

    public void setLiked(List<String> liked) {
        this.liked = liked;
    }
}

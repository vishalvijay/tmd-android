package com.v4creations.tmd.model;

public class RssItem {
    private String title, contentSnippet;

    public RssItem(String title, String contentSnippet) {
        this.title = title;
        this.contentSnippet = contentSnippet;
    }

    public String getTitle() {
        return title;
    }

    public String getContentSnippet() {
        return contentSnippet;
    }
}

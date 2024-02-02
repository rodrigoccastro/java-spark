package com.axreng.backend.model;

import com.axreng.backend.enums.SearchStatus;
import com.google.gson.JsonArray;

public class Search {
    private String keyword;
    private String id;
    private SearchStatus status;
    private JsonArray urls;

    public Search() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SearchStatus getStatus() {
        return status;
    }

    public void setStatus(SearchStatus status) {
        this.status = status;
    }

    public JsonArray getUrls() {
        return urls;
    }

    public void setUrls(JsonArray urls) {
        this.urls = urls;
    }

}

package com.axreng.backend.repository;

import com.axreng.backend.enums.SearchStatus;
import com.axreng.backend.model.Search;
import com.google.gson.JsonArray;

import java.security.SecureRandom;
import java.util.*;

public class Repository {

    // data keywords and ids
    private Map<String, String> mapKeywords = Collections.synchronizedMap(new HashMap<>());

    // data ids and opbject search
    private Map<String, Search> mapContent = Collections.synchronizedMap(new HashMap<>());

    private final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int ID_LENGTH = 8;

    private String generateRandomID() {
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            int randomIndex = random.nextInt(ALPHANUMERIC.length());
            char randomChar = ALPHANUMERIC.charAt(randomIndex);
            sb.append(randomChar);
        }
        var id = sb.toString();
        // ensure that it does not generate an already existing id
        while (mapContent.containsKey(id)) {
            id = generateRandomID();
        }
        return id;
    }

    // the method is synchronized to guarantee there are no two insertions for the same keyword from multiple requests
    // even if there is a map, which would not allow two records, we would have problems with the old id
    public synchronized String addKeyword(String keyword) {
        if (!mapKeywords.containsKey(keyword)) {
            // generate search id: it must be an automatically generated 8-character alphanumeric code.
            String id = generateRandomID();
            mapKeywords.put(keyword, id);
            Search item = new Search();
            item.setKeyword(keyword);
            item.setId(id);
            item.setStatus(SearchStatus.ACTIVE);
            item.setUrls(new JsonArray());
            mapContent.put(id, item);
            return id;
        } else {
            return mapKeywords.get(keyword);
        }
    }

    public synchronized void restartData(String id) {
        Search item = mapContent.get(id);
        item.setStatus(SearchStatus.ACTIVE);
        item.setUrls(new JsonArray());
    }

    public boolean containsKeyword(String keyword) {
        return mapKeywords.containsKey(keyword);
    }

    public String getIdByKeyword(String keyword) {
        return mapKeywords.get(keyword);
    }

    public JsonArray getListUrlsById(String id) {
        return mapContent.get(id).getUrls();
    }

    public boolean containsId(String id) {
        return mapContent.containsKey(id);
    }

    public SearchStatus getStatusSearch(String id) {
        return mapContent.get(id).getStatus();
    }

    public void setStatusDone(String id) {
        mapContent.get(id).setStatus(SearchStatus.DONE);
    }
}

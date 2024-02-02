package com.axreng.backend.service;

import com.axreng.backend.enums.SearchStatus;
import com.axreng.backend.repository.Repository;
import com.axreng.backend.util.UrlWrapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;

public class ServiceFind {

    public JsonObject execute(Repository repository, String baseUrl, String keyword) throws Exception {

        // validate key word between 4 and 32 and convert to lowercase because the search needs case-insensitive
        keyword = validateKeyword(keyword).toLowerCase();

        // checks if the keyword does NOT exist in manageData
        if (!repository.containsKeyword(keyword)) {
            // start new search and response id
            String id = executeNewSearch(repository, baseUrl, keyword);
            return responseService(id);
        }

        // in this case, the keyword already exists
        String id = repository.getIdByKeyword(keyword);
        var status = repository.getStatusSearch(id);

        // verify if search is active
        if (status == SearchStatus.ACTIVE) {
            // do nothing, because another request has already started the search
            return responseService(id);
        }

        // in this case the keyword already exists, but the search has already ended
            // This task does not specify what you need to do if you have
            // already performed a previous search for the term
            // if server already executed search for especific keyword
            // Option 1: I could not run new search and show old results
            // Option 2: I could run new search after especific config time...
            // Option 3: I could run new search if status is done in any time...
            // I preferred to do a new search

        // start new search and response with id for request
        executeRestartSearch(repository, baseUrl, keyword, id);
        return responseService(id);
    }

    private String executeNewSearch(Repository repository, String baseUrl, String keyword) {

        // add keyword in manageData and get id
        String id = repository.addKeyword(keyword);

        // start search async and return id
        new Thread(() -> { search(repository, baseUrl, keyword, id); }).start();
        return id;
    }

    private void executeRestartSearch(Repository repository, String baseUrl, String keyword, String id) {
        repository.restartData(id);
        new Thread(() -> { search(repository, baseUrl, keyword, id); }).start();
    }

    private String validateKeyword(String keyword) throws Exception {
        // between 4 and 32
        if (keyword==null || keyword.trim().length()<4 || keyword.trim().length()>32) {
            throw new Exception("The search term must have a minimum of 4 and a maximum of 32!");
        }
        return keyword;
    }

    private JsonObject responseService(String id) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        return jsonObject;
    }

    private void search(Repository repository, String baseUrl, String keyword, String id)  {
        try {
            UrlWrapper urlWrapper = new UrlWrapper(baseUrl);
            ServiceUrl serviceUrl = new ServiceUrl(urlWrapper);

            // get list of urls by base url
            List<String> allUrls = serviceUrl.extractLinksByBaseUrl();

            // get object of array from this id
            JsonArray arr = repository.getListUrlsById(id);

            for (String url : allUrls) {
                // I put this treatment because I don't want the search to stop if I have a problem with a URL
                try {
                    // return the body string of a URL
                    String pageContent = serviceUrl.getPageContent();
                    // verify if body contains the especific keyword
                    if (pageContent.toLowerCase().contains(keyword)) {
                        arr.add(url);
                    }
                } catch (Exception ex) {
                    System.out.println("Error in search in url "+url+" : "+ex.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Error in search: "+e.getMessage());
        }

        // change status in manage data
        repository.setStatusDone(id);
    }


}

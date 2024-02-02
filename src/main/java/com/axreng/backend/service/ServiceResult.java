package com.axreng.backend.service;

import com.axreng.backend.enums.SearchStatus;
import com.axreng.backend.repository.Repository;
import com.google.gson.JsonObject;

public class ServiceResult {
    public JsonObject execute(Repository repository, String id) throws Exception {
        if (!repository.containsId(id)) {
            throw new Exception("Id " + id + " not exists in server!");
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        SearchStatus status = repository.getStatusSearch(id);
        jsonObject.addProperty("status", status.getLabel());
        jsonObject.add("urls", repository.getListUrlsById(id));
        return jsonObject;
    }
}

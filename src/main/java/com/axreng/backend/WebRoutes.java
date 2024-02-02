package com.axreng.backend;

import com.axreng.backend.repository.Repository;
import com.axreng.backend.service.ServiceFind;
import com.axreng.backend.service.ServiceResult;
import com.axreng.backend.util.Util;

import static spark.Spark.*;

public class WebRoutes {

    public static void configureRoutes(Repository repository, String baseUrl) {

        // define error
        exception(Exception.class, (e, req, res) -> {
            res.status(500);
            res.body("Internal Server Error: " + e.getMessage());
        });

        // start search
        post("/crawl", (req, res) ->
                new ServiceFind().execute(repository,
                        Util.getValidateBaseUrl(baseUrl),
                        Util.getKeywordFromBody(req, "keyword"))
        );

        // get results
        get("/crawl/:id", (req, res) ->
                new ServiceResult().execute(repository,
                        Util.getKeywordFromParams(req, "id"))
        );
    }
}

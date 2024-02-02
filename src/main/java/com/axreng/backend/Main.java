package com.axreng.backend;

import com.axreng.backend.repository.Repository;

public class Main {

    // url from environment variable BASE_URL
    public static String baseUrl = System.getenv("BASE_URL");

    // object responsible for storing the data os searches
    public static Repository repository = new Repository();

    public static void main(String[] args) {

        // Configure web routes
        WebRoutes.configureRoutes(repository, baseUrl);

    }

}

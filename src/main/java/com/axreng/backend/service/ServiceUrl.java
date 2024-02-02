package com.axreng.backend.service;

import com.axreng.backend.util.UrlWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ServiceUrl {

    private final UrlWrapper urlWrapper;
    public ServiceUrl(UrlWrapper urlWrapper) {
        this.urlWrapper = urlWrapper;
    }

    // return list of urls by base url
    public List<String> extractLinksByBaseUrl() throws IOException {
        List<String> allUrls = new ArrayList<>();
        allUrls.add(urlWrapper.getUrl());

        try (InputStream inputStream = urlWrapper.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            // get line by line
            while ((line = reader.readLine()) != null) {
                // get href by href in this line
                while (line.contains(" href=\"")) {
                    int startIndex = line.indexOf(" href=\"") + 7;
                    int endIndex = line.indexOf("\"", startIndex);
                    String link = line.substring(startIndex, endIndex);

                    // if link has the same base URL and verify if already added
                    if (link.startsWith(urlWrapper.getUrl()) && !allUrls.contains(link)) {
                        allUrls.add(link);
                    } else {
                        // if the link does not have another base URL and check if it has already been added
                        if (!link.startsWith("http") && !allUrls.contains(urlWrapper.getUrl()+link)) {
                            allUrls.add(urlWrapper.getUrl() + link);
                        }
                    }
                    // discard part of the line already analyzed
                    line = line.substring(endIndex);
                }
            }
        }

        return allUrls;
    }

    // return the body string of a URL
    public String getPageContent() throws IOException {
        StringBuilder content = new StringBuilder();
        try (InputStream inputStream = urlWrapper.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
            }
        }
        return content.toString();
    }

}

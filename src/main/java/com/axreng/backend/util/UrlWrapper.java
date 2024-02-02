package com.axreng.backend.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

// i need create this class because i had one problem to mock URL class because is final class...
public class UrlWrapper  {
    private URL url;
    private String urlStr;

    public UrlWrapper(String urlStr) throws IOException {
        this.urlStr = urlStr;
        this.url = new URL(urlStr);
    }

    public InputStream getInputStream() throws IOException {
        return url.openConnection().getInputStream();
    }

    public String getUrl() {
        return urlStr;
    }

}


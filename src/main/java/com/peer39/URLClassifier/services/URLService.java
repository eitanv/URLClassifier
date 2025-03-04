package com.peer39.URLClassifier.services;

public interface URLService {

    /**
     * @param url The website's address
     * @return The raw HTML content of the page
     */
    String getTextFromUrl(String url);
}


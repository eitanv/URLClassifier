package com.peer39.URLClassifier.services;

import com.peer39.URLClassifier.model.KeywordCategory;

import java.util.Collection;
import java.util.List;

public interface CategoryClassifierService {

    /**
     * @param content    The text content of the URL
     * @param categories The list of keyword categories containing phrases to match
     * @return List of category names matching the URL content
     */
    List<String> classifyURL(String content, List<KeywordCategory> categories);

    /**
     * @param URLContents The text content of the URLs
     * @param categories  The list of keyword categories containing phrases to match
     * @return True if any URL content matches any category phrases
     */
    boolean isURLinCategory(Collection<String> URLContents, List<KeywordCategory> categories);
}

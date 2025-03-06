package com.peer39.URLClassifier.services;

import com.peer39.URLClassifier.model.KeywordCategory;

import java.util.List;

public interface CategoryClassifierService {

    //boolean isURLinCatrgory(List<String> urls, List<KeywordCategory> categories);

    List<String> classifyURL(String content, List<KeywordCategory> categories);

}

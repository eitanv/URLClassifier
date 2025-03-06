package com.peer39.URLClassifier.model;

import lombok.Data;

import java.util.List;

@Data
public class KeywordCategory {

    String categoryName;

    List<Keyword> keywords;

}

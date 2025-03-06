package com.peer39.URLClassifier.services.implementations;

import com.peer39.URLClassifier.model.KeywordCategory;
import com.peer39.URLClassifier.services.CategoryClassifierService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryClassifierServiceImpl implements CategoryClassifierService {

    public boolean isURLinCatrgory(String url, KeywordCategory category) {
        return true;
    }

    @Override
    public List<String> classifyURL(String content, List<KeywordCategory> categories) {
        List<String> matchedCategories = categories.stream().filter(category -> category.getKeywords().stream() //Get all the keywords of the category
                        .anyMatch(keyword -> content.contains(keyword.getPhrase()))) //Find if the url content contains any phrase
                .map(KeywordCategory::getCategoryName)//Get the name of the category to a list
                .collect(Collectors.toList());
        return matchedCategories;
    }
}

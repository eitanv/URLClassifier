package com.peer39.URLClassifier.services.implementations;

import com.peer39.URLClassifier.model.KeywordCategory;
import com.peer39.URLClassifier.services.CategoryClassifierService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryClassifierServiceImpl implements CategoryClassifierService {

    /**
     * Mention implementation complexity assuming the text length is N, number of categories is M, max keyword length is K:
     * Answer: O(N * M * K) for each URL
     * Can be improved to O(N * M) by using a Trie data structure for the keywords
     */
    @Override
    public List<String> classifyURL(String content, List<KeywordCategory> categories) {
        return categories.stream().filter(category -> category.getKeywords().stream() //Get all the keywords of the category
                        .anyMatch(keyword -> content.contains(keyword.getPhrase()))) //Find if the url content contains any phrase
                .map(KeywordCategory::getCategoryName)//Get the name of the category to a list
                .collect(Collectors.toList());
    }

    @Override
    public boolean isURLinCategory(Collection<String> URLContents, List<KeywordCategory> categories) {
        for (String content : URLContents) {
            List<String> matchedCategories = classifyURL(content, categories); //Get the matching categories
            if (!matchedCategories.isEmpty()) { //If there are any matching categories return true
                return true;
            }
        }
        return false;
    }
}

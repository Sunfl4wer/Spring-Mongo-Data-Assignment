package com.homework.superblog.service;


import java.util.EnumSet;
import java.util.List;

import org.bson.types.ObjectId;

import com.homework.superblog.common.GenericResponse;
import com.homework.superblog.model.Article;
import com.homework.superblog.model.Category;

public interface ArticleService {

  GenericResponse getPageArticles(int page, int size);
  GenericResponse createArticle(Article article);
  GenericResponse updateArticle(ObjectId articleId, Article article);
  GenericResponse updateCategories(ObjectId articleId, EnumSet<Category> categories);
  GenericResponse updateTags(ObjectId articleId, List<String> tags);
  GenericResponse deleteArticle(ObjectId articleId);
  GenericResponse getArticlesByTitleRegex(String regex);
}

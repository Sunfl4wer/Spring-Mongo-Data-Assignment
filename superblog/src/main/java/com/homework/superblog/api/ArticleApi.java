package com.homework.superblog.api;

import java.util.EnumSet;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homework.superblog.common.GenericResponse;
import com.homework.superblog.model.Article;
import com.homework.superblog.model.Category;
import com.homework.superblog.service.ArticleService;

@RestController
@RequestMapping("/articles")
public class ArticleApi {

  @Autowired
  private ArticleService articleService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public GenericResponse getPageArticles(@RequestParam("size") int size, @RequestParam("page") int page) {
    return articleService.getPageArticles(page, size);
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public GenericResponse getArticlesByTitleUsingKey(@RequestParam("title-key") String titleKey) {
    return articleService.getArticlesByTitleRegex(titleKey);
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public GenericResponse createArticle(@RequestBody Article article) {
    return articleService.createArticle(article);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public GenericResponse updateArticle(@PathVariable ObjectId id, @RequestBody Article article) {
    return articleService.updateArticle(id, article);
  }

  @RequestMapping(value = "/{id}/categories", method = RequestMethod.PUT)
  public GenericResponse updateCategories(@PathVariable ObjectId id, @RequestBody EnumSet<Category> categories) {
    return articleService.updateCategories(id, categories);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public GenericResponse updateTags(@PathVariable ObjectId id, @RequestBody List<String> tags) {
    return articleService.updateTags(id, tags);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public GenericResponse deleteArticle(@PathVariable ObjectId id) {
    return articleService.deleteArticle(id);
  }
}

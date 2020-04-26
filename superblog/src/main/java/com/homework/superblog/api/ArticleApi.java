package com.homework.superblog.api;

import java.util.EnumSet;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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

  @RequestMapping(value = "/page", method = RequestMethod.GET)
  public GenericResponse getPageArticles(@RequestParam int page, @RequestParam int size ) {
    return articleService.getPageArticles(page, size);
  }

  @RequestMapping(value = "/getByTitleUsingKey", method = RequestMethod.GET)
  public GenericResponse getArticlesByTitleUsingKey(@RequestParam String key) {
    return articleService.getArticlesByTitleRegex(key);
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public GenericResponse createArticle(@RequestBody Article article) {
    return articleService.createArticle(article);
  }

  @RequestMapping(value = "", method = RequestMethod.PUT)
  public GenericResponse updateArticle(@RequestParam("id") ObjectId id, @RequestBody Article article) {
    return articleService.updateArticle(id, article);
  }

  @RequestMapping(value = "/updateCategories", method = RequestMethod.PUT)
  public GenericResponse updateCategories(@RequestParam("id") ObjectId id, @RequestBody EnumSet<Category> categories) {
    return articleService.updateCategories(id, categories);
  }

  @RequestMapping(value = "/updateTags", method = RequestMethod.PUT)
  public GenericResponse updateTags(@RequestParam("id") ObjectId id, @RequestBody List<String> tags) {
    return articleService.updateTags(id, tags);
  }

  @RequestMapping(value = "", method = RequestMethod.DELETE)
  public GenericResponse deleteArticle(@RequestParam("id") ObjectId id) {
    return articleService.deleteArticle(id);
  }
}

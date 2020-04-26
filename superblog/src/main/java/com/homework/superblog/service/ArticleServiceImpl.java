package com.homework.superblog.service;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import com.homework.superblog.model.Category;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.homework.superblog.common.ErrorCode;
import com.homework.superblog.common.GenericResponse;
import com.homework.superblog.common.LogExecutionStatus;
import com.homework.superblog.model.Article;
import com.homework.superblog.repository.ArticleRepository;

@Service
public class ArticleServiceImpl implements ArticleService{

  @Autowired
  private ArticleRepository articleRepository;

  @Override
  @LogExecutionStatus
  public GenericResponse getPageArticles(int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
    Page<Article> articles = articleRepository.findAll(pageable);
    if (!articles.isEmpty()) {
      return new GenericResponse(ErrorCode.SUCCESS.getCode(), articles);
    } else {
      return new GenericResponse(ErrorCode.NOT_FOUND_DATA.getCode(), articles);
    }
  }

  @Override
  @LogExecutionStatus
  public GenericResponse createArticle(Article article) {
    return new GenericResponse(ErrorCode.CREATED.getCode(), articleRepository.save(article));
  }

  @Override
  @LogExecutionStatus
  public GenericResponse updateArticle(ObjectId articleId, Article updateArticle) {
    Optional<Article> receivedObject = articleRepository.findById(articleId);

    if (receivedObject.isPresent()) {
      Article article = receivedObject.get();
      article.setTitle(updateArticle.getTitle());
      article.setContent(updateArticle.getContent());
      article.setAuthorId(updateArticle.getAuthorId());
      article.setCategories(updateArticle.getCategories());
      article.setTags(updateArticle.getTags());
      articleRepository.save(article);
      return new GenericResponse(ErrorCode.SUCCESS.getCode(), articleId);
    } else {
      return new GenericResponse(ErrorCode.NOT_FOUND_DATA.getCode(), articleId);
    }
  }

  @Override
  @LogExecutionStatus
  public GenericResponse updateCategories(ObjectId articleId, EnumSet<Category> categories) {
    Optional<Article> receivedObject = articleRepository.findById(articleId);

    if (receivedObject.isPresent()) {
      Article updatedArticle = receivedObject.get();
      updatedArticle.setCategories(categories);
      articleRepository.save(updatedArticle);
      return new GenericResponse(ErrorCode.SUCCESS.getCode(), articleId);
    } else {
      return new GenericResponse(ErrorCode.NOT_FOUND_DATA.getCode(), articleId);
    }
  }

  @Override
  @LogExecutionStatus
  public GenericResponse updateTags(ObjectId articleId, List<String> tags) {
    Optional<Article> receivedObject = articleRepository.findById(articleId);

    if (receivedObject.isPresent()) {
      Article updatedArticle = receivedObject.get();
      updatedArticle.setTags(tags);
      articleRepository.save(updatedArticle);
      return new GenericResponse(ErrorCode.SUCCESS.getCode(), articleId);
    } else {
      return new GenericResponse(ErrorCode.NOT_FOUND_DATA.getCode(), articleId);
    }
  }

  @Override
  @LogExecutionStatus
  public GenericResponse getArticlesByTitleRegex(String regex) {
    List<Article> articles = articleRepository.findByTitleRegex(regex);
    if (!articles.isEmpty()) {
      return new GenericResponse(ErrorCode.SUCCESS.getCode(), articles);
    } else {
      return new GenericResponse(ErrorCode.NOT_FOUND_DATA.getCode(), regex);
    }
  }

  @Override
  @LogExecutionStatus
  public GenericResponse deleteArticle(ObjectId articleId) {
    if (articleRepository.findById(articleId).isEmpty()) {
      return new GenericResponse(ErrorCode.NOT_FOUND_DATA.getCode(), articleId);
    } else {
      articleRepository.deleteById(articleId);
      return new GenericResponse(ErrorCode.SUCCESS.getCode(), articleId);
    }
  }
}

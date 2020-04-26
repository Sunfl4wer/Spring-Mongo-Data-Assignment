package com.homework.superblog.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.homework.superblog.model.Article;

public interface ArticleRepository extends MongoRepository<Article, ObjectId> {

  @Query("{'title' : {$regex : ?0, $options: 'i'}}")
  List<Article> findByTitleRegex(final String key);
}

package com.homework.superblog.repository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.homework.superblog.model.Comment;

public class CustomCommentRepositoryImpl implements CustomCommentRepository {

  @Autowired
  MongoTemplate mongoTemplate;

  public CustomCommentRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void approveComment(ObjectId id) {
    
    Query query = new Query(Criteria.where("id").is(id));
    Update update = new Update();
    update.set("approved", true);
    mongoTemplate.updateFirst(query, update, Comment.class);
  }
}

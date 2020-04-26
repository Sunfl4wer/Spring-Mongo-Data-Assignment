package com.homework.superblog.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.homework.superblog.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, ObjectId>,
                                           CustomCommentRepository {

  List<Comment> findByArticleId(ObjectId articleId);

  @Query("{'articleId' : ?0, 'approved' : ?1}")
  Page<Comment> findByArticleIdAndApproved(ObjectId articleId, boolean approved, Pageable pagable);

  List<Comment> findByArticleIdAndApproved(ObjectId articleId, boolean approved);
}

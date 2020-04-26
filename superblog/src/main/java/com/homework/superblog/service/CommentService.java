package com.homework.superblog.service;

import org.bson.types.ObjectId;

import com.homework.superblog.common.GenericResponse;
import com.homework.superblog.model.Comment;

public interface CommentService {

  GenericResponse createComment(Comment comment);
  GenericResponse deleteComment(ObjectId commentId);
  GenericResponse getByArticleId(ObjectId articleId);
  GenericResponse getPageByArticleIdAndApproved(ObjectId articleId, boolean approved, int page, int size);
  GenericResponse approveComment(ObjectId commentId);
  GenericResponse getByArticleIdAndApproved(ObjectId articleId, boolean approved);
}

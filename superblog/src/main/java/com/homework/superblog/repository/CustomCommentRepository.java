package com.homework.superblog.repository;

import org.bson.types.ObjectId;

public interface CustomCommentRepository {

  void approveComment(ObjectId commentId);
}

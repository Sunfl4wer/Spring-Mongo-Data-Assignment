package com.homework.superblog.service;


import java.util.List;
import java.util.Optional;

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
import com.homework.superblog.model.Comment;
import com.homework.superblog.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {
  
  @Autowired
  CommentRepository commentRepository;
  
  @Override
  @LogExecutionStatus
  public GenericResponse createComment(Comment comment) {
    return new GenericResponse(ErrorCode.CREATED.getCode(), commentRepository.save(comment));
  }

  @Override
  @LogExecutionStatus
  public GenericResponse deleteComment(ObjectId commentId) {
    Optional<Comment> receivedObject = commentRepository.findById(commentId);
    if (!receivedObject.isEmpty()) {
      commentRepository.delete(receivedObject.get());
      return new GenericResponse(ErrorCode.SUCCESS.getCode(), commentId);
    } else {
      return new GenericResponse(ErrorCode.NOT_FOUND_DATA.getCode(), commentId);
    }
  }

  @Override
  @LogExecutionStatus
  public GenericResponse getByArticleId(ObjectId articleId) {
    List<Comment> comments = commentRepository.findByArticleId(articleId);
    if (comments.isEmpty()) {
      return new GenericResponse(ErrorCode.NOT_FOUND_DATA.getCode(), articleId);
    } else {
      return new GenericResponse(ErrorCode.SUCCESS.getCode(), comments);
    }
  }

  @Override
  @LogExecutionStatus
  public GenericResponse getPageByArticleIdAndApproved(ObjectId articleId, boolean approved, int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
    Page<Comment> comments = commentRepository.findByArticleIdAndApproved(articleId, approved, pageable);
    if (comments.isEmpty()) {
      return new GenericResponse(ErrorCode.NOT_FOUND_DATA.getCode(), new String("No comment with articleId:" + articleId.toString() 
                                                                      + "and approved: " + Boolean.toString(approved) + "found."));
    } else {
      return new GenericResponse(ErrorCode.SUCCESS.getCode(), comments);
    }
  }

  @Override
  @LogExecutionStatus
  public GenericResponse approveComment(ObjectId commentId) {
    Optional<Comment> receivedObject = commentRepository.findById(commentId);
    if (!receivedObject.isEmpty()) {
      commentRepository.approveComment(commentId);
      Optional<Comment> object = commentRepository.findById(commentId);
      Comment comment = object.get();
      return new GenericResponse(ErrorCode.SUCCESS.getCode(), comment);
    } else {
      return new GenericResponse(ErrorCode.NOT_FOUND_DATA.getCode(), commentId);
    }
  }

  @Override
  @LogExecutionStatus
  public GenericResponse getByArticleIdAndApproved(ObjectId articleId, boolean approved) {
    List<Comment> comments = commentRepository.findByArticleIdAndApproved(articleId, approved);
    if (comments.isEmpty()) {
      return new GenericResponse(ErrorCode.NOT_FOUND_DATA.getCode(), new String("No comment with articleId:" + articleId.toString() 
                                                                      + "and approved: " + Boolean.toString(approved) + "found."));
    } else {
      return new GenericResponse(ErrorCode.SUCCESS.getCode(), comments);
    }
  }
}

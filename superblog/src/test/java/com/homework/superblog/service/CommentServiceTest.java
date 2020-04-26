package com.homework.superblog.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.homework.superblog.common.GenericResponse;
import com.homework.superblog.model.Comment;
import com.homework.superblog.repository.CommentRepository;

@SpringBootTest
public class CommentServiceTest {

  @Autowired
  CommentRepository commentRepository;

  @Autowired
  CommentService commentService;

  @BeforeEach
  public void init() {
    commentRepository.save(Comment.builder().content("First").articleId(new ObjectId("5ea2cc2756708a1128983986"))
                                            .email("email1@gmail.com").approved(false).build());
    commentRepository.save(Comment.builder().content("Second").articleId(new ObjectId("5ea2cc2756708a1128983986"))
                                            .email("email2@gmail.com").approved(false).build());
    commentRepository.save(Comment.builder().content("Third").articleId(new ObjectId("5ea2cc2756708a1128983986"))
                                            .email("email3@gmail.com").approved(false).build());
  }

  @AfterEach
  public void cleaning() {
    commentRepository.deleteAll();
  }

  @DisplayName("createComment test function")
  @Test
  void createCommentTest() {
    
    //given
    Comment comment =  Comment.builder().content("Forth").articleId(new ObjectId("5ea2cc2756708a1128983986"))
                                            .email("email4@gmail.com").approved(false).build();

    //when
    GenericResponse responseS = commentService.createComment(comment);
    Object objS = responseS.getData();
    int codeS = responseS.getErrorCode();

    //then
    Assertions.assertNotNull(objS);
    Assertions.assertEquals(codeS, 201);
    Assertions.assertTrue(objS instanceof Comment);
    if (objS instanceof Comment) {
      Comment getComment = (Comment) objS;
      Assertions.assertEquals(getComment.getArticleId(), comment.getArticleId());
      Assertions.assertEquals(getComment.getContent(), comment.getContent());
      Assertions.assertEquals(getComment.getEmail(), comment.getEmail());
      Assertions.assertEquals(getComment.isApproved(), comment.isApproved());
    }
  }

  @DisplayName("deleteComment test function")
  @Test
  void deleteCommentTest() {
    
    //given
    Comment comment =  commentRepository.save(Comment.builder().content("Forth").articleId(new ObjectId("5ea2cc2756708a1128983986"))
                                            .email("email4@gmail.com").approved(false).build());
    GenericResponse responseS = commentService.deleteComment(comment.getId());
    GenericResponse responseN = commentService.deleteComment(comment.getId());

    //when 
    int codeS = responseS.getErrorCode();
    int codeN = responseN.getErrorCode();
    Object objS = responseS.getData();
    Object objN = responseN.getData();

    //then
    Assertions.assertTrue(objS instanceof ObjectId);
    Assertions.assertTrue(objN instanceof ObjectId);
    Assertions.assertEquals(codeS, 200);
    Assertions.assertEquals(codeN, 404);
    Assertions.assertEquals(objS, comment.getId());
    Assertions.assertEquals(objN, comment.getId());
  }

  @DisplayName("getByArticleId test function")
  @Test
  void getByArticleIdTest() {
    
    //given
    GenericResponse responseS = commentService.getByArticleId(new ObjectId("5ea2cc2756708a1128983986"));
    GenericResponse responseN = commentService.getByArticleId(new ObjectId("5ea2cc2756708a1128983980"));
    
    //when
    Object objS = responseS.getData();
    Object objN = responseN.getData();
    int codeS = responseS.getErrorCode();
    int codeN = responseN.getErrorCode();

    //then
    Assertions.assertNotNull(objS);
    Assertions.assertNotNull(objN);
    Assertions.assertTrue(objS instanceof List<?>);
    Assertions.assertTrue(objN instanceof ObjectId);
    Assertions.assertEquals(codeS, 200);
    Assertions.assertEquals(codeN, 404);
    if (objS instanceof List<?>) {
      List<?> comments = (List<?>) objS;
      Assertions.assertEquals(comments.size(), 3);
    }
    Assertions.assertEquals(objN, new ObjectId("5ea2cc2756708a1128983980"));
  }

  @DisplayName("getPageByArticleIdAndApproved test function")
  @Test
  void getPageByArticleIdAndApprovedTest() {
    
    //given
    GenericResponse responseS = commentService.getPageByArticleIdAndApproved(new ObjectId("5ea2cc2756708a1128983986"), 
                                                                          false, 0, 2);
    GenericResponse responseN = commentService.getPageByArticleIdAndApproved(new ObjectId("5ea2cc2756708a1128983985"), 
                                                                          false, 0, 2);
    //when
    Object objS = responseS.getData();
    Object objN = responseN.getData();
    int codeS = responseS.getErrorCode();
    int codeN = responseN.getErrorCode();

    //then
    Assertions.assertNotNull(objS);
    Assertions.assertNotNull(objN);
    Assertions.assertTrue(objS instanceof Page<?>);
    Assertions.assertTrue(objN instanceof String);
    Assertions.assertEquals(codeS, 200);
    Assertions.assertEquals(codeN, 404);
    if (objS instanceof List<?>) {
      List<?> comments = (List<?>) objS;
      Assertions.assertEquals(comments.size(), 2);
    }
  }
    
  @DisplayName("getByArticleIdAndApproved test function")
  @Test
  void getByArticleIdAndApprovedTest() {
    
    //given
    GenericResponse responseS = commentService.getByArticleIdAndApproved(new ObjectId("5ea2cc2756708a1128983986"), 
                                                                          false);
    GenericResponse responseN = commentService.getByArticleIdAndApproved(new ObjectId("5ea2cc2756708a1128983985"), 
                                                                          false);
    //when
    Object objS = responseS.getData();
    Object objN = responseN.getData();
    int codeS = responseS.getErrorCode();
    int codeN = responseN.getErrorCode();

    //then
    Assertions.assertNotNull(objS);
    Assertions.assertNotNull(objN);
    Assertions.assertTrue(objS instanceof List<?>);
    Assertions.assertTrue(objN instanceof String);
    Assertions.assertEquals(codeS, 200);
    Assertions.assertEquals(codeN, 404);
    if (objS instanceof List<?>) {
      List<?> comments = (List<?>) objS;
      Assertions.assertEquals(comments.size(), 3);
    }
  }

  @DisplayName("approveComment test function")
  @Test
  void approveComment() {
    
    //given
    Comment comment =  commentRepository.save(Comment.builder().content("Forth").articleId(new ObjectId("5ea2cc2756708a1128983986"))
                                            .email("email4@gmail.com").approved(false).build());
    GenericResponse responseS = commentService.approveComment(comment.getId());
    GenericResponse responseN = commentService.approveComment(new ObjectId("5ea2cc2756708a1128983985"));

    //when
    Object objS = responseS.getData();
    Object objN = responseN.getData();
    int codeS = responseS.getErrorCode();
    int codeN = responseN.getErrorCode();

    //then
    Assertions.assertNotNull(objS);
    Assertions.assertNotNull(objN);
    Assertions.assertTrue(objS instanceof Comment);
    Assertions.assertTrue(objN instanceof ObjectId);
    Assertions.assertEquals(codeS, 200);
    Assertions.assertEquals(codeN, 404);
    if (objS instanceof Comment) {
      Comment commentS = (Comment) objS;
      Assertions.assertEquals(commentS.getId(), comment.getId());
      Assertions.assertEquals(commentS.isApproved(), true);
    }
    Assertions.assertEquals(objN, new ObjectId("5ea2cc2756708a1128983985"));
  }

}

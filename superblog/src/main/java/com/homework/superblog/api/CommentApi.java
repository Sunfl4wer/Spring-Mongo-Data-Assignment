package com.homework.superblog.api;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homework.superblog.common.GenericResponse;
import com.homework.superblog.model.Comment;
import com.homework.superblog.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentApi {

  @Autowired
  CommentService commentService;

  @RequestMapping(value = "", method = RequestMethod.POST)
  public GenericResponse createComment(@RequestBody Comment comment) {
    return commentService.createComment(comment);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public GenericResponse deleteComment(@PathVariable ObjectId id) {
    return commentService.deleteComment(id);
  }

  @RequestMapping(value = "", method = RequestMethod.GET, params = "articleId")
  public GenericResponse getByArticleId(@RequestParam("articleId") ObjectId articleId) {
    return commentService.getByArticleId(articleId);
  }

  @RequestMapping(value = "", method = RequestMethod.GET, params = {"articleId", "approved", "size", "page"})
  public GenericResponse getPageByArticleIdAndApproved(@RequestParam("articleId") ObjectId articleId,
                                                  @RequestParam("approved") boolean approved,
                                                  @RequestParam("size") int size,
                                                  @RequestParam("page") int page) {
    return commentService.getPageByArticleIdAndApproved(articleId, approved, page, size);
  }

  @RequestMapping(value = "", method = RequestMethod.GET, params = {"articleId", "approved"})
  public GenericResponse getByArticleIdAndApproved(@RequestParam("articleId") ObjectId articleId,
                                                   @RequestParam("approved") boolean approved) {
    return commentService.getByArticleIdAndApproved(articleId, approved);
  }

  @RequestMapping(value = "/approve", method = RequestMethod.POST, params = "id")
  public GenericResponse approveComment(@RequestParam("id") ObjectId id) {
    return commentService.approveComment(id);
  }
}

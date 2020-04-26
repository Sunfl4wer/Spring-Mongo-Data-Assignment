package com.homework.superblog.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
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
import com.homework.superblog.model.Article;
import com.homework.superblog.model.Category;
import com.homework.superblog.repository.ArticleRepository;

@SpringBootTest
public class ArticleApiTest {

  @Autowired
  ArticleRepository articleRepository;

  @Autowired
  ArticleApi articleApi;

  @BeforeEach
  public void init() {
    articleRepository.save(Article.builder().title("First article").authorId(new ObjectId("5ea2cc2756708a1128983985"))
                                           .content("Some text for the article")
                                           .categories(EnumSet.of(Category.Literature))
                                           .tags(new ArrayList<String>(Arrays.asList("test","article"))).build());
    articleRepository.save(Article.builder().title("Second article").authorId(new ObjectId("5ea2cc2756708a1128983985"))
                                           .content("Some text for the article")
                                           .categories(EnumSet.of(Category.Science))
                                           .tags(new ArrayList<String>(Arrays.asList("test","article"))).build());
  }

  @AfterEach
  public void cleaning() {
    articleRepository.deleteAll();
  }

  @DisplayName("getPageArticles test function")
  @Test
  void getPageArticlesTest() {
    
    //given
    GenericResponse responseS = articleApi.getPageArticles(0, 1);
    GenericResponse responseN = articleApi.getPageArticles(5, 1);

    //when 
    int codeS = responseS.getErrorCode();
    int codeN = responseN.getErrorCode();
    Object objS = responseS.getData();
    Object objN = responseN.getData();

    //then
    Assertions.assertNotNull(objS);
    Assertions.assertNotNull(objN);
    Assertions.assertEquals(codeS, 200);
    Assertions.assertEquals(codeN, 404);
    Assertions.assertTrue(objS instanceof Page<?>);
    Assertions.assertTrue(objN instanceof Page<?>);
  }
  
  @DisplayName("createArticle test function")
  @Test
  void createArticleTest() {
    
    //given
    Article article = Article.builder().title("Third article").authorId(new ObjectId("5ea2cc2756708a1128983985"))
                                           .content("Some text for the article")
                                           .categories(EnumSet.of(Category.Literature))
                                           .tags(new ArrayList<String>(Arrays.asList("test","article"))).build();
    GenericResponse responseS = articleApi.createArticle(article);

    //when 
    int codeS = responseS.getErrorCode();
    Object objS = responseS.getData();

    //then
    Assertions.assertNotNull(objS);
    Assertions.assertEquals(codeS, 201);
    Assertions.assertTrue(objS instanceof Article);
    if (objS instanceof Article) {
      Article newArticle = (Article) objS;
      Assertions.assertEquals(newArticle, article);
    }
  }

  @DisplayName("deleteArticle test function")
  @Test
  void deleteArticleTest() {
    
    //given
    Article article = Article.builder().title("Third article").authorId(new ObjectId("5ea2cc2756708a1128983985"))
                                           .content("Some text for the article")
                                           .categories(EnumSet.of(Category.Literature))
                                           .tags(new ArrayList<String>(Arrays.asList("test","article"))).build();
    articleRepository.save(article);
    GenericResponse responseS = articleApi.deleteArticle(article.getId());
    GenericResponse responseN = articleApi.deleteArticle(article.getId());

    //when 
    int codeS = responseS.getErrorCode();
    Object objS = responseS.getData();
    int codeN = responseN.getErrorCode();
    Object objN = responseN.getData();

    //then
    Assertions.assertNotNull(objS);
    Assertions.assertEquals(codeS, 200);
    Assertions.assertTrue(objS instanceof ObjectId);
    Assertions.assertNotNull(objN);
    Assertions.assertEquals(codeN, 404);
    Assertions.assertTrue(objN instanceof ObjectId);
  }

  @DisplayName("updateArticle test function")
  @Test
  void updateArticleTest() {
    
    //given
    Article article = Article.builder().title("Third article").authorId(new ObjectId("5ea2cc2756708a1128983985"))
                                           .content("Some text for the article")
                                           .categories(EnumSet.of(Category.Literature))
                                           .tags(new ArrayList<String>(Arrays.asList("test","article"))).build();
    articleRepository.save(article);
    Article updateArticle = Article.builder().title("Third article updated").authorId(new ObjectId("5ea2cc2756708a1128983985"))
                                           .content("Some text for the article")
                                           .categories(EnumSet.of(Category.Literature))
                                           .tags(new ArrayList<String>(Arrays.asList("test","article"))).build();

    GenericResponse responseS = articleApi.updateArticle(article.getId(), updateArticle);
    GenericResponse responseN = articleApi.updateArticle(new ObjectId("5ea2cc2756708a1128983985"), updateArticle);

    //when 
    int codeS = responseS.getErrorCode();
    Object objS = responseS.getData();
    int codeN = responseN.getErrorCode();
    Object objN = responseN.getData();

    //then
    Assertions.assertNotNull(objS);
    Assertions.assertEquals(codeS, 200);
    Assertions.assertTrue(objS instanceof ObjectId);
    Assertions.assertNotNull(objN);
    Assertions.assertEquals(codeN, 404);
    Assertions.assertTrue(objN instanceof ObjectId);
    Assertions.assertEquals(articleRepository.findById(article.getId()).get().getTitle(), updateArticle.getTitle());
  }

  @DisplayName("updateCategory test function")
  @Test
  void updateCategoryTest() {
    
    //given
    Article article = Article.builder().title("Third article").authorId(new ObjectId("5ea2cc2756708a1128983985"))
                                           .content("Some text for the article")
                                           .categories(EnumSet.of(Category.Literature))
                                           .tags(new ArrayList<String>(Arrays.asList("test","article"))).build();
    articleRepository.save(article);
    EnumSet<Category> updateCategories = EnumSet.of(Category.Literature,Category.Social);

    GenericResponse responseS = articleApi.updateCategories(article.getId(), updateCategories);
    GenericResponse responseN = articleApi.updateCategories(new ObjectId("5ea2cc2756708a1128983985"), updateCategories);

    //when 
    int codeS = responseS.getErrorCode();
    Object objS = responseS.getData();
    int codeN = responseN.getErrorCode();
    Object objN = responseN.getData();

    //then
    Assertions.assertNotNull(objS);
    Assertions.assertEquals(codeS, 200);
    Assertions.assertTrue(objS instanceof ObjectId);
    Assertions.assertNotNull(objN);
    Assertions.assertEquals(codeN, 404);
    Assertions.assertTrue(objN instanceof ObjectId);
    Assertions.assertEquals(articleRepository.findById(article.getId()).get().getCategories(), updateCategories);
  }

  @DisplayName("updateTags test function")
  @Test
  void updateTagsTest() {
    
    //given
    Article article = Article.builder().title("Third article").authorId(new ObjectId("5ea2cc2756708a1128983985"))
                                           .content("Some text for the article")
                                           .categories(EnumSet.of(Category.Literature))
                                           .tags(new ArrayList<String>(Arrays.asList("test","article"))).build();
    articleRepository.save(article);
    List<String> updateTags = new ArrayList<String>(Arrays.asList("update","test","article"));
    GenericResponse responseS = articleApi.updateTags(article.getId(), updateTags);
    GenericResponse responseN = articleApi.updateTags(new ObjectId("5ea2cc2756708a1128983985"), updateTags);

    //when 
    int codeS = responseS.getErrorCode();
    Object objS = responseS.getData();
    int codeN = responseN.getErrorCode();
    Object objN = responseN.getData();

    //then
    Assertions.assertNotNull(objS);
    Assertions.assertEquals(codeS, 200);
    Assertions.assertTrue(objS instanceof ObjectId);
    Assertions.assertNotNull(objN);
    Assertions.assertEquals(codeN, 404);
    Assertions.assertTrue(objN instanceof ObjectId);
    Assertions.assertEquals(articleRepository.findById(article.getId()).get().getTags(), updateTags);
  }

  @DisplayName("getArticlesByTitleUsingKey test function")
  @Test
  void getArticlesByTitleUsingKey() {
    
    //given
    GenericResponse responseS = articleApi.getArticlesByTitleUsingKey("first");
    GenericResponse responseN = articleApi.getArticlesByTitleUsingKey("tre");

    //when 
    int codeS = responseS.getErrorCode();
    Object objS = responseS.getData();
    int codeN = responseN.getErrorCode();
    Object objN = responseN.getData();

    //then
    Assertions.assertNotNull(objS);
    Assertions.assertEquals(codeS, 200);
    Assertions.assertTrue(objS instanceof List<?>);
    if (objS instanceof List<?>) {
      List<?> articles = (List<?>) objS;
      Assertions.assertEquals(articles.size(), 1);
    }
    Assertions.assertNotNull(objN);
    Assertions.assertEquals(codeN, 404);
    Assertions.assertTrue(objN instanceof String);
    Assertions.assertEquals(objN, "tre");
  }
}

package com.homework.superblog.model;

import java.util.EnumSet;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.homework.superblog.common.ObjectID_Serializer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "articles")
@Builder
public class Article {

  @Id
  @Getter
  @JsonSerialize(using=ObjectID_Serializer.class)
  private ObjectId id;

  @Setter
  @Getter
  private String title;
  
  @Setter
  @Getter
  private String content;
  
  @Setter
  @Getter
  @JsonSerialize(using=ObjectID_Serializer.class)
  private ObjectId authorId;

  @Setter
  @Getter
  private EnumSet<Category> categories;

  @Setter
  @Getter
  private List<String> tags;
}


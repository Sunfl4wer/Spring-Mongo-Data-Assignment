package com.homework.superblog.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.homework.superblog.common.ObjectID_Serializer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "comments")
@Builder
public class Comment {

  @Id
  @Getter
  @JsonSerialize(using=ObjectID_Serializer.class)
  private ObjectId id;

  @Getter
  @Setter
  private String email;

  @Getter
  @Setter
  private String content;

  @Getter
  @Setter
  @JsonSerialize(using=ObjectID_Serializer.class)
  private ObjectId articleId;

  @Getter
  @Setter
  private boolean approved;
}

package com.homework.superblog.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.homework.superblog.common.ObjectID_Serializer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "admin_users")
@Builder
public class User {

  @Id
  @Getter
  @JsonSerialize(using=ObjectID_Serializer.class)
  private ObjectId id;
  
  @Setter
  @Getter
  private String name;

  @Setter
  @Getter
  private String email;
  
  @Setter
  @Getter
  private Address address;

  @Setter
  @Getter
  private Authorized authorized;

  @Setter
  @Getter
  private boolean enabled;
}

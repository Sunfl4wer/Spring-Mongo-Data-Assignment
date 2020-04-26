package com.homework.superblog.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.homework.superblog.model.User;

public interface UserRepository extends MongoRepository<User, String>,
                                        CustomUserRepository {
  User findByEmail(String email);
  @Query("{'address.country' : ?0}")
  List<User> findByCountry(final String country);

  List<User> findByNameStartsWith(final String name);
}
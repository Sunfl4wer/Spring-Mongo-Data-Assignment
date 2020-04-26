package com.homework.superblog.service;

import com.homework.superblog.common.GenericResponse;
import com.homework.superblog.model.Authorized;
import com.homework.superblog.model.User;

public interface UserService {

  GenericResponse getAllUsers();
  GenericResponse createUser(User user);
  GenericResponse deleteUserByEmail(String email);
  GenericResponse getUserByEmail(String email);
  GenericResponse banUserByEmail(String email);
  GenericResponse updateAuthorized(String email, Authorized authorized);
  GenericResponse getUsersByNameStartsWith(String nameKeyWords);
}

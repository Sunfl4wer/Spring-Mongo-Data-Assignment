package com.homework.superblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.superblog.common.ErrorCode;
import com.homework.superblog.common.GenericResponse;
import com.homework.superblog.common.LogExecutionStatus;
import com.homework.superblog.model.Authorized;
import com.homework.superblog.model.User;
import com.homework.superblog.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Override
  @LogExecutionStatus
  public GenericResponse getAllUsers() {
    List<User> users = userRepository.findAll();
    if (users.isEmpty()) {
      return new GenericResponse(ErrorCode.NOT_FOUND_DATA.getCode(), null);
    } else {
      return new GenericResponse(ErrorCode.SUCCESS.getCode(), users);
    }
  }

  @Override
  @LogExecutionStatus
  public GenericResponse createUser(User user) {
    if(userRepository.findByEmail(user.getEmail()) != null) {
      return new GenericResponse(ErrorCode.EXISTING_DATA.getCode(), user.getEmail());
    }
      return new GenericResponse(ErrorCode.CREATED.getCode(), userRepository.save(user));
  }

  @Override
  @LogExecutionStatus
  public GenericResponse getUserByEmail(String email) {
    User user = userRepository.findByEmail(email);
    if ( user != null) {
      return new GenericResponse(ErrorCode.SUCCESS.getCode(), user);
    } else {
      return new GenericResponse(ErrorCode.NOT_FOUND_DATA.getCode(), email);
    }
  }

  @Override
  @LogExecutionStatus
  public GenericResponse getUsersByNameStartsWith(String name) {
    List<User> users = userRepository.findByNameStartsWith(name);
    if (users.isEmpty()) {
      return new GenericResponse(ErrorCode.NOT_FOUND_DATA.getCode(), name);
    } else {
      return new GenericResponse(ErrorCode.SUCCESS.getCode(), users);
    }
  }

  @Override
  @LogExecutionStatus
  public GenericResponse banUserByEmail(String email) {
    User user = userRepository.findByEmail(email);

    if (user != null) {
      userRepository.banUserByEmail(email);
      return new GenericResponse(ErrorCode.SUCCESS.getCode(), userRepository.findByEmail(email));
    } else {
      return new GenericResponse(ErrorCode.NOT_FOUND_DATA.getCode(), email);
    }
  }

  @Override
  @LogExecutionStatus
  public GenericResponse updateAuthorized(String email, Authorized authorized) {
    User updatedUser = userRepository.findByEmail(email);
    
    if (updatedUser != null) {
      updatedUser.setAuthorized(authorized);
      return new GenericResponse(ErrorCode.SUCCESS.getCode(), userRepository.save(updatedUser));
    } else {
      return new GenericResponse(ErrorCode.NOT_FOUND_DATA.getCode(), email);
    }
  }
}

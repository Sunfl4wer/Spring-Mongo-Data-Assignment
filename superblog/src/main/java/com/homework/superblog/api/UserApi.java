package com.homework.superblog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homework.superblog.common.GenericResponse;
import com.homework.superblog.model.Authorized;
import com.homework.superblog.model.User;
import com.homework.superblog.service.UserService;

@RestController
@RequestMapping("/users")
public class UserApi {

  @Autowired
  UserService userService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public GenericResponse getAllUsers() {
    return userService.getAllUsers();
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public GenericResponse getUserByEmail(@RequestParam("email") String email) {
    return userService.getUserByEmail(email);
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public GenericResponse getUsersByNameStartsWith(@RequestParam("name-starts-with") String prefix) {
    return userService.getUsersByNameStartsWith(prefix);
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public GenericResponse createUser(@RequestBody User user) {
    return userService.createUser(user);
  }

  @RequestMapping(value = "/ban", method = RequestMethod.PUT)
  public GenericResponse banUserByEmail(@RequestParam("email") String email) {
    return userService.banUserByEmail(email);
  }

  @RequestMapping(value = "/{email}/authorized", method = RequestMethod.PUT)
  public GenericResponse updateAuthorized(@PathVariable String email, @RequestBody Authorized updateAuthorized) {
    return userService.updateAuthorized(email, updateAuthorized);
  }
}

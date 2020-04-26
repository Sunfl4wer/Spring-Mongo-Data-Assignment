package com.homework.superblog.service;

import java.util.EnumSet;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.homework.superblog.common.GenericResponse;
import com.homework.superblog.model.Address;
import com.homework.superblog.model.Authorized;
import com.homework.superblog.model.Role;
import com.homework.superblog.model.User;
import com.homework.superblog.model.Permission;
import com.homework.superblog.repository.UserRepository;
import com.homework.superblog.service.UserService;

@SpringBootTest
public class UserServiceTests {

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserService userService;

  @BeforeEach
  public void init() {
    userRepository.save(User.builder().id(new ObjectId()).name("Chi Pheo")
                                      .email("loveyouno@gmail.com")
                                      .address(new Address("1 Vu Dai","Ha Nam","Viet Nam"))
                                      .authorized(new Authorized(EnumSet.of(Role.END_USER),EnumSet.of(Permission.POST_COMMENT)))
                                      .enabled(true).build());
    userRepository.save(User.builder().id(new ObjectId()).name("Thi No")
                                      .email("loveyoupheo@gmail.com")
                                      .address(new Address("1 Vu Dai","Ha Nam","Viet Nam"))
                                      .authorized(new Authorized(EnumSet.of(Role.END_USER),EnumSet.of(Permission.POST_COMMENT)))
                                      .enabled(true).build());
    userRepository.save(User.builder().id(new ObjectId()).name("Cau Vang")
                                      .email("goldenboy@gmail.com")
                                      .address(new Address("77 Vu Dai","Ha Nam","Viet Nam"))
                                      .authorized(new Authorized(EnumSet.of(Role.END_USER),EnumSet.of(Permission.POST_COMMENT)))
                                      .enabled(true).build());
    userRepository.save(User.builder().id(new ObjectId()).name("Lao Hac")
                                      .email("lonelyman@gmail.com")
                                      .address(new Address("77 Vu Dai","Ha Nam","Viet Nam"))
                                      .authorized(new Authorized(EnumSet.of(Role.END_USER),EnumSet.of(Permission.POST_COMMENT)))
                                      .enabled(true).build());
  }

  @AfterEach
  public void cleaning() {
    userRepository.deleteAll();
  }

  @DisplayName("getAllUsers test function")
  @Test
  void getAllUsersTest() {
    
    //Given
    GenericResponse responseSuccess = userService.getAllUsers();
    userRepository.deleteAll();
    GenericResponse responseNotFoundData = userService.getAllUsers();

    //When
    int codeSuccess = responseSuccess.getErrorCode();
    Object objectSuccess = responseSuccess.getData();
    int codeNotFoundData = responseNotFoundData.getErrorCode();
    Object objectNotFoundData = responseNotFoundData.getData();

    //then
    assertEquals(codeSuccess,200);
    assertEquals(codeNotFoundData,404);
    assertTrue(objectSuccess instanceof List<?>);
    if (objectSuccess instanceof List<?>) {
      List<?> users = (List<?>) objectSuccess;
      assertEquals(users.size(),4);
    }
    assertNull(objectNotFoundData);
  }

  @DisplayName("getUserByEmail test function")
  @Test
  void getUserByEmailTest() {
    //given
    GenericResponse responseSuccess = userService.getUserByEmail("loveyouno@gmail.com");
    GenericResponse responseNotFoundData = userService.getUserByEmail("huanrose@gmail.com");

    //when
    int codeSuccess = responseSuccess.getErrorCode();
    int codeNotFoundData = responseNotFoundData.getErrorCode();
    Object objectSuccess = responseSuccess.getData();
    Object objectNotFoundData = responseNotFoundData.getData();

    //then
    assertEquals(codeSuccess,200);
    assertEquals(codeNotFoundData,404);
    assertNotNull(objectSuccess);
    assertEquals(objectNotFoundData,"huanrose@gmail.com");
    assertTrue(objectSuccess instanceof User);
    if (objectSuccess instanceof User) {
      User user = (User) objectSuccess;
      assertTrue(user.isEnabled());
      assertEquals(user.getName(),"Chi Pheo");
      assertTrue(user.getAddress() instanceof Address);
      assertEquals(user.getAddress().getStreetAddress(),"1 Vu Dai");
      assertEquals(user.getAddress().getCity(),"Ha Nam");
      assertEquals(user.getAddress().getCountry(),"Viet Nam");
      assertEquals(user.getAuthorized().getPermissions(),EnumSet.of(Permission.POST_COMMENT));
      assertEquals(user.getAuthorized().getRoles(),EnumSet.of(Role.END_USER));
    }
  }

  @DisplayName("getByNameStartsWith test function")
  @Test
  void getByNameStartsWith() {
    
    //given
    GenericResponse responseS = userService.getUsersByNameStartsWith("C");
    GenericResponse responseNFD = userService.getUsersByNameStartsWith("Lk");
    Object objectS = responseS.getData();
    Object objectNFD = responseNFD.getData();

    //when
    int codeS = responseS.getErrorCode();
    int codeNFD = responseNFD.getErrorCode();

    //then
    if (objectS instanceof List<?>) {
      List<?> users = (List<?>) objectS;
      assertNotNull(users);
      assertEquals(users.size(), 2);
    }
    if (objectNFD instanceof String) {
      String prefix = (String) objectNFD;
      assertNotNull(prefix);
      assertEquals(prefix, "Lk");
    }
    assertEquals(codeS, 200);
    assertEquals(codeNFD, 404);
  }
  
  @DisplayName("createUser test function")
  @Test
  void createUserTest() {
    
    //given
    User newUser = User.builder().id(new ObjectId()).name("Marcus Aurelius")
                                      .email("king@gmail.com")
                                      .address(new Address("0 MA","Roma","Italy"))
                                      .authorized(new Authorized(EnumSet.of(Role.SUPER_ADMIN),EnumSet.of(Permission.CREATE_ARTICLE,
                                                                                                         Permission.DELETE_ARTICLE)))
                                      .enabled(true).build();

    //when
    GenericResponse responseS = userService.createUser(newUser);
    GenericResponse responseE = userService.createUser(newUser);
    Object objectS = responseS.getData();
    Object objectE = responseE.getData();
    int codeS = responseS.getErrorCode();
    int codeE = responseE.getErrorCode();
   
    //then
    if (objectS instanceof User) {
      User user = (User) objectS;
      assertNotNull(user);
      assertEquals(user.getName(), "Marcus Aurelius");
      assertEquals(user.getEmail(), "king@gmail.com");
      assertEquals(user.getAddress().getCity(), "Roma");
      assertEquals(user.getAddress().getStreetAddress(), "0 MA");
      assertEquals(user.getAddress().getCountry(), "Italy");
    }
    assertEquals(codeS, 201);
    assertEquals(codeE, 303);
    assertTrue(objectE instanceof String);
  }

  @DisplayName("deleteUserByEmail test function")
  @Test
  void deleteUserByEmailTest() {
    
    //given
    GenericResponse responseS = userService.deleteUserByEmail("loveyouno@gmail.com");
    GenericResponse responseE = userService.deleteUserByEmail("loveyouno@gmail.com");

    //when
    Object objectS = responseS.getData();
    Object objectE = responseE.getData();
    int codeS = responseS.getErrorCode();
    int codeE = responseE.getErrorCode();
   
    //then
    if (objectS instanceof String) {
      String str = (String) objectS;
      assertNotNull(str);
      assertEquals(str, "User deleted.");
    }
    assertEquals(codeS, 200);
    assertEquals(codeE, 404);
    assertTrue(objectE instanceof String);
    assertEquals(objectE, "loveyouno@gmail.com");
  }

  @DisplayName("banUserByEmail test function")
  @Test
  void banUserByEmailTest() {
    //given
    GenericResponse responseS = userService.banUserByEmail("loveyouno@gmail.com");
    GenericResponse responseNFD = userService.banUserByEmail("noone@yahoo.com");

    //when
    Object objectS = responseS.getData();
    Object objectNFD = responseNFD.getData();
    int codeS = responseS.getErrorCode();
    int codeNFD = responseNFD.getErrorCode();

    //then
    if (objectS instanceof User) {
      User user = (User) objectS;
      assertNotNull(user);
      assertEquals(user.getEmail(), "loveyouno@gmail.com");
      assertFalse(user.isEnabled());
    }
    assertTrue(objectNFD instanceof String);
    assertEquals(objectNFD, "noone@yahoo.com");
    assertEquals(codeS, 200);
    assertEquals(codeNFD, 404);
  }

  @DisplayName("updateAuthorized test function")
  @Test
  void updateAuthorizedTest() {
    
    //given
    Authorized updateAuthorized = new Authorized(EnumSet.of(Role.SUPER_ADMIN),EnumSet.of(Permission.CREATE_ARTICLE));
    GenericResponse responseS = userService.updateAuthorized("loveyoupheo@gmail.com", updateAuthorized);
    GenericResponse responseN = userService.updateAuthorized("lopheo@gmail.com", updateAuthorized);

    //when
    Object objS = responseS.getData();
    Object objN = responseN.getData();
    int codeS = responseS.getErrorCode();
    int codeN = responseN.getErrorCode();

    //then
    assertNotNull(objS);
    assertNotNull(objN);
    assertEquals(objN, "lopheo@gmail.com");
    assertTrue(objS instanceof User);
    assertTrue(objN instanceof String);
    assertEquals(codeS, 200);
    assertEquals(codeN, 404);
    if (objS instanceof User) {
      User user = (User) objS;
      assertEquals(user.getAuthorized().getRoles(), updateAuthorized.getRoles());
      assertEquals(user.getAuthorized().getPermissions(), updateAuthorized.getPermissions());
    }
  }
}

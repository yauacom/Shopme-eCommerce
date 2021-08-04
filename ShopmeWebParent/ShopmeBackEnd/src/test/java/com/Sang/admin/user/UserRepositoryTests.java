package com.Sang.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.Sang.ShopmeCommon.entity.Role;
import com.Sang.ShopmeCommon.entity.User;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TestEntityManager testEntityManager;

  @Test
  public void testCreatedUser(){
    Role roleAdmin = testEntityManager.find(Role.class, 1);
    User userSang = new User(
        "yauacom@yahoo.com",
        "123",
        "Sang",
        "Vo"
    );
    userSang.addRole(roleAdmin);

    User saveUser = userRepository.save(userSang);
    assertThat(saveUser.getId()).isGreaterThan(0);
  }

  @Test
  public void testCreatedUserWithTwoRoles() {
    User userRavi = new User(
        "ravi@gmail.com",
        "123",
        "Ravi",
        "Kumar"
        );
    Role roleEditor = new Role(3);
    Role roleAssistant = new Role(5);
    userRavi.addRole(roleEditor);
    userRavi.addRole(roleAssistant);

    User savedUser = userRepository.save(userRavi);
    assertThat(savedUser.getId()).isGreaterThan(0);
  }

  @Test
  public void testListAllUsers() {
    Iterable<User> listUsers = userRepository.findAll();
    listUsers.forEach(System.out::println);
  }

  @Test
  public void testGetUserById() {
    User userSang = userRepository.findById(1).get();
    System.out.println(userSang);
    assertThat(userSang).isNotNull();
  }

  @Test
  public void testUpdateUserDetails() {
    User userSang = userRepository.findById(1).get();
    userSang.setEnabled(true);
    userSang.setEmail("test@random.com");

    userRepository.save(userSang);
  }

  @Test
  public void testUpdateUserRoles() {
    User userRavi = userRepository.findById(2).get();
    Role roleEditor = new Role(3);
    Role roleSalesperson = new Role(2);

    userRavi.getRoles().remove(roleEditor);
    userRavi.addRole(roleSalesperson);

    userRepository.save(userRavi);
  }

  @Test
  public void testUpdateUser() {
    int userId = 2;
    userRepository.deleteById(userId);
  }

  @Test
  public void testGetUserByEmail() {
    String email = "ravi@gmail.com";
    User user = userRepository.getUserByEmail(email);

    assertThat(user).isNotNull();

  }
}

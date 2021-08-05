package com.Sang.admin.user;

import com.Sang.ShopmeCommon.entity.Role;
import com.Sang.ShopmeCommon.entity.User;
import java.util.List;
import java.util.NoSuchElementException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository,
      RoleRepository roleRepository,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public List<User> listAll() {
    return (List<User>) userRepository.findAll();
  }

  public List<Role> listRoles() {
    return (List<Role>) roleRepository.findAll();
  }

  public User save(User user) {
    boolean isUpdatingUser = (user.getId() != null);
    if (isUpdatingUser) {
      User existingUser = userRepository.findById(user.getId()).get();
      if (user.getPassword().isEmpty()) {
        user.setPassword(existingUser.getPassword());
      } else {
        encodedPassword(user);
      }
    } else {
      encodedPassword(user);
    }


    return userRepository.save(user);
  }

  private void encodedPassword(User user){
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
  }

  public boolean isEmailUnique(Integer id, String email) {
    User userByEmail = userRepository.getUserByEmail(email);
    if(userByEmail == null) return true;

    boolean isCreatingNew = (id == null);
    if (isCreatingNew) {
      if (userByEmail!= null) return false;
    } else {
      if (userByEmail.getId() != id) {
        return false;
      }
    }

    return true;
  }


  public void delete(Integer id) throws UserNotFoundException {
    Long countById = userRepository.countById(id);

    if (countById == null || countById == 0) {
      throw new UserNotFoundException("Could not find any user with ID " + id);
    }

    userRepository.deleteById(id);
  }

  public User get(Integer id) throws UserNotFoundException {
    try {
      return userRepository.findById(id).get();

    } catch (NoSuchElementException ex) {
      throw new UserNotFoundException("Could not find any user with ID " + id);
    }
  }

  public void updateUserEnabledStatus(Integer id, boolean enabled) {
    userRepository.updateEnabledStatus(id, enabled);
  }

}

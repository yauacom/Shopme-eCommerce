package com.Sang.admin.user;

import com.Sang.ShopmeCommon.entity.Role;
import com.Sang.ShopmeCommon.entity.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private UserRepository userRepository;
  private RoleRepository roleRepository;

  @Autowired
  public UserService(UserRepository userRepository,
      RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  public List<User> listAll() {
    return (List<User>) userRepository.findAll();
  }

  public List<Role> listRoles() {
    return (List<Role>) roleRepository.findAll();
  }

  public void save(User newUser) {
    userRepository.save(newUser);
  }
}

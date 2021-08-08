package com.Sang.admin.security;

import com.Sang.ShopmeCommon.entity.User;
import com.Sang.admin.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ShopmeUserDetailsService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.getUserByEmail(email);
    if (user != null) return new ShopmeUserDetails(user);
    throw new UsernameNotFoundException("Could not find user with email: " + email);
  }
}

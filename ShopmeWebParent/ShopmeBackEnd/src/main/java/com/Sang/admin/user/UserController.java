package com.Sang.admin.user;

import com.Sang.ShopmeCommon.entity.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users")
  public String listAll(Model model) {
    List<User> listUsers = userService.listAll();
    model.addAttribute("listUsers", listUsers);

    return "users";
  }
}

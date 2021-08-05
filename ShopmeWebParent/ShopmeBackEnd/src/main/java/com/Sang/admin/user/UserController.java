package com.Sang.admin.user;

import com.Sang.ShopmeCommon.entity.Role;
import com.Sang.ShopmeCommon.entity.User;
import com.Sang.admin.FileUploadUtil;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

  private final UserService userService;

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

  @GetMapping("/users/new")
  public String newUser(Model model) {
    List<Role> listRoles = userService.listRoles();
    User user = new User();
    user.setEnabled(true);

    model.addAttribute("user", user);
    model.addAttribute("listRoles", listRoles);
    model.addAttribute("pageTitle", "Create New User");
    return "user_form";
  }

  @PostMapping("/users/save")
  public String saveUser(
      User newUser,
      RedirectAttributes redirectAttributes,
      @RequestParam("image") MultipartFile multipartFile
    ) throws IOException {
    if(!multipartFile.isEmpty()) {
    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
      newUser.setPhotos(fileName);
      User savedUser = userService.save(newUser);
    String uploadDir = "ShopmeWebParent/ShopmeBackEnd/user-photos/" + savedUser.getId();

    FileUploadUtil.cleanDir(uploadDir);
    FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
    } else {
      if (newUser.getPhotos().isEmpty()) {
        newUser.setPhotos(null);
      }
      userService.save(newUser);
    }

    redirectAttributes.addFlashAttribute("message", "The user has been saved successfully");

    return "redirect:/users";
  }

  @GetMapping("/users/edit/{id}")
  public String editUser(@PathVariable(name = "id") Integer id,
      Model model,
      RedirectAttributes redirectAttributes) {
    try {
      User user = userService.get(id);
      List<Role> listRoles = userService.listRoles();
      model.addAttribute("listRoles", listRoles);
      model.addAttribute("user", user);
      model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
      return "user_form";
    } catch (UserNotFoundException ex) {
      redirectAttributes.addFlashAttribute("message", ex.getMessage());
      return "redirect:/users";
    }
  }

  @GetMapping("/users/delete/{id}")
  public String deleteUser(@PathVariable(name = "id") Integer id,
      Model model,
      RedirectAttributes redirectAttributes) {
    try {
      userService.delete(id);
      redirectAttributes.addFlashAttribute("message","The User ID " + id + " has been deleted successfully.");
    } catch (UserNotFoundException ex) {
      redirectAttributes.addFlashAttribute("message", ex.getMessage());
    }
    return "redirect:/users";
  }

  @GetMapping("/users/{id}/enabled/{status}")
  public String updateUserEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,  RedirectAttributes redirectAttributes) {
    userService.updateUserEnabledStatus(id, enabled);
    String status = enabled ? "enabled" : "disabled";
    String message = "The user ID " + id + " has been " + status;
    redirectAttributes.addFlashAttribute("message", message);
    return "redirect:/users";
  }
}

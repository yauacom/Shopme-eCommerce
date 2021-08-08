package com.Sang.admin.category.controller;

import com.Sang.ShopmeCommon.entity.Category;
import com.Sang.admin.category.service.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @GetMapping("/categories")
  public String listAllCategories(Model model) {
    List<Category> listCategories = categoryService.listAll();
    model.addAttribute("listCategories", listCategories);

    return "categories/categories";
  }
}

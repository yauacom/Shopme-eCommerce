package com.Sang.admin.category.service;

import com.Sang.ShopmeCommon.entity.Category;
import com.Sang.admin.category.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  public List<Category> listAll() {
    return (List<Category>) categoryRepository.findAll();
  }

  public List<Category> listCategoriesUsedInForm() {
    List<Category> listCategoriesUsedInForm = new ArrayList<>();

    Iterable<Category> categoriesInDatabase = categoryRepository.findAll();

    for (Category category : categoriesInDatabase) {
      if (category.getParent() == null) {
        listCategoriesUsedInForm.add(new Category(category.getName()));

        Set<Category> children = category.getChildren();

        for (Category subCategory : children) {
          System.out.println("--" + subCategory.getName());
          String name = "--" + subCategory.getName();
          listCategoriesUsedInForm.add(new Category(name));

          listChildren(listCategoriesUsedInForm, subCategory, 1);
        }
      }
    }



    return listCategoriesUsedInForm;
  }

  private void listChildren( List<Category> listCategoriesUsedInForm, Category parent, int subLevel) {
    int newSubLevel = subLevel + 1;
    Set<Category> children = parent.getChildren();

    for (Category subCategory : children) {
      String name = "";
      for (int i = 0; i < newSubLevel; i++ ) {
        name += "--";
      }
      name += subCategory.getName();
      listCategoriesUsedInForm.add(new Category(name));
      listChildren(listCategoriesUsedInForm, subCategory, newSubLevel);
    }
  }
}

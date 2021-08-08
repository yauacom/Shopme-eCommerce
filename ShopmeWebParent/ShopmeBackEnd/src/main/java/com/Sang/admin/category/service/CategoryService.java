package com.Sang.admin.category.service;

import com.Sang.ShopmeCommon.entity.Category;
import com.Sang.admin.category.repository.CategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  public List<Category> listAll() {
    return (List<Category>) categoryRepository.findAll();
  }
}

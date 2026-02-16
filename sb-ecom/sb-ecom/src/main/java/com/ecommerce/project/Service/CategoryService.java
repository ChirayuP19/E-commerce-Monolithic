package com.ecommerce.project.Service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    CategoryResponse getAllCategory();

   CategoryDTO CreateCategory(CategoryDTO categoryDTO);

   String DeleteCategory(Long id);

   Category updateCategoryById(Category category,Long id);

}

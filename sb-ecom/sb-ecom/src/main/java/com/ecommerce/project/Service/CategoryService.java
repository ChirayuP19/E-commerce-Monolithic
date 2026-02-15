package com.ecommerce.project.Service;

import com.ecommerce.project.model.Category;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CategoryService {

    List<Category> getAllCategory();

   Category CreateCategory(Category category);

   String DeleteCategory(Long id);

   Category updateCategoryById(Category category,Long id);

}

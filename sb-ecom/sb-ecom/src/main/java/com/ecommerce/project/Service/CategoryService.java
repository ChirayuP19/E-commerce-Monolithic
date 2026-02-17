package com.ecommerce.project.Service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    CategoryResponse getAllCategory(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder);

   CategoryDTO CreateCategory(CategoryDTO categoryDTO);

   CategoryDTO DeleteCategory(Long id);

   CategoryDTO updateCategoryById(CategoryDTO categoryDTO,Long id);

}

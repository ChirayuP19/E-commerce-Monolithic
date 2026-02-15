package com.ecommerce.project.Service;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category CreateCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public String DeleteCategory(Long id) {
        return categoryRepository.findById(id)
                .map(x->{
                    categoryRepository.deleteById(id);
                    return "Category with Id " +id +" removed successfully";
                }).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",id));
    }

    @Override
    public Category updateCategoryById(Category category,Long id) {
        Optional<Category> byId = categoryRepository.findById(id);

        Category savedCategory = byId.orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",id));

        savedCategory.setCategoryName(category.getCategoryName());
        Category save = categoryRepository.save(savedCategory);
        return save;


    }
}

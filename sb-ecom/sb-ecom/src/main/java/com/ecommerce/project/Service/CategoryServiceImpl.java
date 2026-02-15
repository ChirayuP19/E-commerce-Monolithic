package com.ecommerce.project.Service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

//    private final List<Category> categories=new ArrayList<>();
//    private Long nextId=1L;

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
                }).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"ID INVALID"));
    }

    @Override
    public Category updateCategoryById(Category category,Long id) {
        List<Category> categories = categoryRepository.findAll();

        Optional<Category> categoryOptional = categories.stream()
                .filter(x -> x.getCategoryId().equals(id))
                .findFirst();

        if(categoryOptional.isPresent()){
            Category existingCategory = categoryOptional.get();
            existingCategory.setCategoryName(category.getCategoryName());
            Category saved = categoryRepository.save(existingCategory);
            return saved;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category Id Invalid");
    }
}

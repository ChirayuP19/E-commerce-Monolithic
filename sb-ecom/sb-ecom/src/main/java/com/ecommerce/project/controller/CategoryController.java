package com.ecommerce.project.controller;

import com.ecommerce.project.Service.CategoryService;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/public/categories")
     public ResponseEntity<CategoryResponse> getAllCategories(){
        return new ResponseEntity<>(categoryService.getAllCategory(),HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        return new ResponseEntity<>(categoryService.CreateCategory(categoryDTO),HttpStatus.CREATED);
    }

    @DeleteMapping("/public/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
           return new ResponseEntity<>(categoryService.DeleteCategory(id), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/public/categories/{id}")
    public ResponseEntity<?> updateByID(@PathVariable Long id,
                                        @RequestBody Category category){
            return new ResponseEntity<>(categoryService.updateCategoryById(category,id),HttpStatus.OK);
    }

}

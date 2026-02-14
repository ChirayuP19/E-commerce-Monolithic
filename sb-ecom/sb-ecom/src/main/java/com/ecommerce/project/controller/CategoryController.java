package com.ecommerce.project.controller;

import com.ecommerce.project.Service.CategoryService;
import com.ecommerce.project.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/api/public/categories")
     public List<Category> getAllCategories(){
        return categoryService.getAllCategory();
    }

    @PostMapping("/api/public/categories")
    public String createCategory(@RequestBody Category category){
        categoryService.CreateCategory(category);
        return "Category added successfully";
    }

    @DeleteMapping("/api/public/categories")
    public String deleteCategory(@RequestParam Long id){
       return categoryService.DeleteCategory(id);
    }

}

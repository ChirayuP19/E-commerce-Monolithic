package com.ecommerce.project.controller;

import com.ecommerce.project.Service.CategoryService;
import com.ecommerce.project.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("")
     public ResponseEntity<List<Category>> getAllCategories(){
        return new ResponseEntity<>(categoryService.getAllCategory(),HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> createCategory(@RequestBody Category category){
        categoryService.CreateCategory(category);
        return new ResponseEntity<>("Category added successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
       try {
           return new ResponseEntity<>(categoryService.DeleteCategory(id), HttpStatus.NO_CONTENT);
       } catch (ResponseStatusException e) {
           return new ResponseEntity<>(e.getReason(),e.getStatusCode());
       }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateByID(@PathVariable Long id,
                                        @RequestBody Category category){
        try {
            return new ResponseEntity<>(categoryService.updateCategoryById(category,id),HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }
    }

}

package com.ecommerce.project.Service;

import com.ecommerce.project.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final List<Category> categories=new ArrayList<>();
    private Long nextId=1L;

    @Override
    public List<Category> getAllCategory() {
        return categories;
    }

    @Override
    public void CreateCategory(@RequestBody Category category) {
        category.setCategoryId(nextId++);
    categories.add(category);
    }

    @Override
    public String DeleteCategory(Long id) {
        Category category = categories.stream()
                .filter(x -> x.getCategoryId().equals(id))
                .findFirst()
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"ID INVALID"));
        categories.remove(category);
        return "Category with Id "+id+" remove Successfully";
    }

    @Override
    public String updateCategoryById(Category category,Long id) {
        Optional<Category> found = Optional.ofNullable(categories.stream()
                .filter(x -> x.getCategoryId().equals(id))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND")));
        found.ifPresent(category1 -> category1.setCategoryName(category.getCategoryName()));
        return "Category id "+id+" updated Successfully";
    }
}

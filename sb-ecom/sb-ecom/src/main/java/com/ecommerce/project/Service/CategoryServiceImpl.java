package com.ecommerce.project.Service;

import com.ecommerce.project.model.Category;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

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
                .findFirst().orElse(null);
        if(category==null)
            return "Category ID Not Found in DATABASE";

        categories.remove(category);
        return "Category with Id "+id+" remove Successfully";
    }
}

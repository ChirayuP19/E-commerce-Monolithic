package com.ecommerce.project.Service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty())
            throw new APIException("No category records exist.");

        List<CategoryDTO> categoryDTOS=categories
                .stream()
                .map(x->modelMapper.map(x, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse=new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }

    @Override
    public CategoryDTO CreateCategory(@RequestBody CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if(savedCategory != null){
            throw new APIException("Category with the name "+category.getCategoryName()+" is already exist !!!");
        }
        Category save = categoryRepository.save(category);
        return modelMapper.map(save,CategoryDTO.class);
    }

    @Override
    public CategoryDTO DeleteCategory(Long id) {
        return categoryRepository.findById(id)
                .map(x->{
                    categoryRepository.deleteById(id);
                    return modelMapper.map(x,CategoryDTO.class);
                }).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",id));
    }

    @Override
    public CategoryDTO updateCategoryById(CategoryDTO categoryDTO,Long id) {

        Optional<Category> byId = categoryRepository.findById(id);

        CategoryDTO savedCategoryDTO=modelMapper.map(byId,CategoryDTO.class);
        Category savedCategory = byId.orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",id));

        savedCategory.setCategoryName(categoryDTO.getCategoryName());
        Category save = categoryRepository.save(savedCategory);
        return modelMapper.map(save,CategoryDTO.class);
    }
}

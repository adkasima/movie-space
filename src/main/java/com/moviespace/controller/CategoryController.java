package com.moviespace.controller;

import com.moviespace.controller.request.CategoryRequest;
import com.moviespace.controller.response.CategoryResponse;
import com.moviespace.entity.Category;
import com.moviespace.mapper.CategoryMapper;
import com.moviespace.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/moviespace/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/all")
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        return categories.stream()
                .map(CategoryMapper::toCategoryResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryByID(@PathVariable  Long id) {
        Optional<Category> optCategory = categoryService.getById(id);
        if (optCategory.isPresent()) {
            return CategoryMapper.toCategoryResponse(optCategory.get());
        }
        return null;
    }

    @PostMapping("/create")
    public CategoryResponse createCategory(@RequestBody CategoryRequest request) {
        Category newCategory = CategoryMapper.toCategory(request);
        Category savedCategory = categoryService.createCategory(newCategory);
        return CategoryMapper.toCategoryResponse(savedCategory);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

}

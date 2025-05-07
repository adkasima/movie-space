package com.moviespace.service;

import com.moviespace.entity.Category;
import com.moviespace.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> update(Long id, Category updateCategory) {
        Optional<Category> optCategory = categoryRepository.findById(id);

        if (optCategory.isPresent()) {

            Category category = optCategory.get();

            category.setName(updateCategory.getName());
            categoryRepository.save(category);
            return Optional.of(category);
        }

        return Optional.empty();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<Category> getById(Long id) {
        return categoryRepository.findById(id);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}

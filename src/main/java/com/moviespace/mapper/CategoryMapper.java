package com.moviespace.mapper;

import com.moviespace.controller.request.CategoryRequest;
import com.moviespace.controller.response.CategoryResponse;
import com.moviespace.entity.Category;

public class CategoryMapper {

    public static Category toCategory(CategoryRequest categoryRequest) {
        return Category
                .builder()
                .name(categoryRequest.name())
                .build();
    }

    public static CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse
                .builder()
                .id(category.getId())
                .name(category.getName())
                .build();

    }
}

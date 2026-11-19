package com.skbazaarhub.product.service;

import com.skbazaarhub.common.dto.CategoryDto;
import com.skbazaarhub.common.exception.ResourceNotFoundException;
import com.skbazaarhub.product.entity.CategoryEntity;
import com.skbazaarhub.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public CategoryDto getCategoryById(Long id) {
        CategoryEntity entity = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));
        return mapToDto(entity);
    }

    public CategoryDto createCategory(CategoryDto dto) {
        CategoryEntity entity = CategoryEntity.builder()
                .name(dto.getName())
                .slug(dto.getName().toLowerCase().replaceAll("[^a-z0-9]", "-"))
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .parentId(dto.getParentId())
                .build();
        return mapToDto(categoryRepository.save(entity));
    }

    private CategoryDto mapToDto(CategoryEntity entity) {
        return CategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .slug(entity.getSlug())
                .description(entity.getDescription())
                .imageUrl(entity.getImageUrl())
                .parentId(entity.getParentId())
                .build();
    }
}

package com.projectrefocus.service.categories;

import com.projectrefocus.service.categories.dto.CategoryDto;
import com.projectrefocus.service.categories.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoriesController {

    private final CategoryService categoryService;

    public CategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<CategoryDto> getCategories() {
        return categoryService.getAllCategories();
    }
}

package com.beetle.recipe.controller;

import com.beetle.recipe.model.entity.Category;
import com.beetle.recipe.model.entity.UnitOfMeasure;
import com.beetle.recipe.repository.CategoryRepository;
import com.beetle.recipe.repository.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @GetMapping({"", "/"})
    public String getIndexPage() {
        Category category = categoryRepository.findByDescription("Italian").orElse(null);
        UnitOfMeasure unitOfMeasure = unitOfMeasureRepository.findByMeasure("Teaspoon").orElse(null);

        System.out.println("category is " + (category != null ? category.getId() : null));
        System.out.println("unit of measure is " + (unitOfMeasure != null ? unitOfMeasure.getId() : null));

        return "index";
    }

}

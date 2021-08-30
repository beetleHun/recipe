package com.beetle.recipe.controller;

import com.beetle.recipe.service.RecipeService;
import org.springframework.stereotype.Controller;

@Controller("/ingredients")
public class IngredientController {

    private final RecipeService recipeService;

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

}

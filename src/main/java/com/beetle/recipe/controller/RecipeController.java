package com.beetle.recipe.controller;

import com.beetle.recipe.model.entity.Recipe;
import com.beetle.recipe.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"", "/", "/index"})
    public String getRecipes(Model model) {
        log.debug("Getting recipes");
        List<Recipe> recipes = recipeService.listRecipes();
        model.addAttribute("recipes", recipes);

        return "recipes/index";
    }

    @GetMapping("/{id}")
    public String getRecipe(@PathVariable(name = "id") Long id, Model model) {
        log.debug("Getting recipe by id '{}'", id);
        Recipe recipe = recipeService.getById(id);
        model.addAttribute("recipe", recipe);

        return "recipes/details";
    }

}

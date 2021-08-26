package com.beetle.recipe.service;

import com.beetle.recipe.commands.RecipeCommand;
import com.beetle.recipe.model.entity.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> listRecipes();

    Recipe getById(Long id);

    RecipeCommand save(RecipeCommand recipeCommand);

}

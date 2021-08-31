package com.beetle.recipe.service;

import com.beetle.recipe.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand getCommandById(Long id);

    IngredientCommand save(IngredientCommand ingredientCommand);

}

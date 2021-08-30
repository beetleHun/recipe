package com.beetle.recipe.service;

import com.beetle.recipe.commands.IngredientCommand;
import com.beetle.recipe.converters.IngredientToIngredientCommand;
import com.beetle.recipe.model.entity.Ingredient;
import com.beetle.recipe.repository.IngredientRepository;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand getCommandById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient with id '" + id + "not found!"));

        return ingredientToIngredientCommand.convert(ingredient);
    }

}

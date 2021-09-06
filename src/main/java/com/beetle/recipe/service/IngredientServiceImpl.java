package com.beetle.recipe.service;

import com.beetle.recipe.commands.IngredientCommand;
import com.beetle.recipe.converters.IngredientCommandToIngredient;
import com.beetle.recipe.converters.IngredientToIngredientCommand;
import com.beetle.recipe.model.entity.Ingredient;
import com.beetle.recipe.repository.IngredientRepository;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientCommandToIngredient ingredientCommandToIngredient,
                                 IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand getCommandById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient with id '" + id + "not found!"));

        return ingredientToIngredientCommand.convert(ingredient);
    }

    @Override
    public IngredientCommand save(IngredientCommand ingredientCommand) {
        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
        Ingredient saved = ingredientRepository.save(ingredient);

        return ingredientToIngredientCommand.convert(saved);
    }

}

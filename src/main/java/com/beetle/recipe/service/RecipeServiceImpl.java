package com.beetle.recipe.service;

import com.beetle.recipe.commands.RecipeCommand;
import com.beetle.recipe.converters.RecipeCommandToRecipe;
import com.beetle.recipe.converters.RecipeToRecipeCommand;
import com.beetle.recipe.model.entity.Recipe;
import com.beetle.recipe.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public List<Recipe> listRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipeRepository.findAll().forEach(recipes::add);

        return recipes;
    }

    @Override
    public Recipe getById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recipe with id '" + id + "' does not exist!"));
    }

    @Override
    public RecipeCommand getCommandById(Long id) {
        return recipeToRecipeCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public RecipeCommand save(RecipeCommand command) {
        log.debug("Saving {} recipe", command.getId() == null ? "new" : "");
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);

        return recipeToRecipeCommand.convert(savedRecipe);
    }

}

package com.beetle.recipe.service;

import com.beetle.recipe.model.entity.Recipe;
import com.beetle.recipe.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
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

}

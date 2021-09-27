package com.beetle.recipe.service;

import com.beetle.recipe.commands.RecipeCommand;
import com.beetle.recipe.model.entity.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecipeService {

    List<Recipe> listRecipes();

    Recipe getById(Long id);

    RecipeCommand getCommandById(Long id);

    RecipeCommand save(RecipeCommand recipeCommand);

    void delete(Long id);

    void updateImage(Long id, MultipartFile file);

}

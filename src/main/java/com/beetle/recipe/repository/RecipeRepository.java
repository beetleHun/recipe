package com.beetle.recipe.repository;

import com.beetle.recipe.model.entity.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}

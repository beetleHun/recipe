package com.beetle.recipe.repository;

import com.beetle.recipe.model.entity.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}

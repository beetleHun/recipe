package com.beetle.recipe.service;

import com.beetle.recipe.commands.RecipeCommand;
import com.beetle.recipe.converters.RecipeCommandToRecipe;
import com.beetle.recipe.converters.RecipeToRecipeCommand;
import com.beetle.recipe.model.entity.Recipe;
import com.beetle.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @InjectMocks
    private RecipeServiceImpl service;

    @Test
    void getRecipes() {
        service.listRecipes();
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void getById_WhenDoesNotExist_ThenExceptionIsThrown() {
        final Long id = 1L;

        when(recipeRepository.findById(eq(id))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.getById(id));
        verify(recipeRepository).findById(eq(id));
    }

    @Test
    void getById_WhenHappyPath() {
        final Long id = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeRepository.findById(eq(id))).thenReturn(Optional.of(recipe));

        Recipe found = assertDoesNotThrow(() -> service.getById(id));
        verify(recipeRepository).findById(eq(id));
        assertEquals(id, found.getId());
    }

    @Test
    void getCommandById_WhenHappyPath() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        when(recipeRepository.findById(eq(1L))).thenReturn(Optional.of(recipe));

        service.getCommandById(1L);

        verify(recipeToRecipeCommand).convert(recipe);
    }

    @Test
    void save_WhenCalled_ThenRecipeCommandToRecipeIsCalled() {
        RecipeCommand recipeCommand = new RecipeCommand();
        service.save(recipeCommand);

        verify(recipeCommandToRecipe).convert(eq(recipeCommand));
    }

    @Test
    void save_WhenCalled_ThenRepositoryIsCalled() {
        Recipe recipe = new Recipe();
        RecipeCommand recipeCommand = new RecipeCommand();

        when(recipeCommandToRecipe.convert(eq(recipeCommand))).thenReturn(recipe);

        service.save(recipeCommand);

        verify(recipeRepository).save(eq(recipe));
    }

    @Test
    void save_WhenCalled_ThenRecipeToRecipeCommandIsCalled() {
        Recipe recipe = new Recipe();
        Recipe saved = new Recipe();
        saved.setId(1L);
        RecipeCommand recipeCommand = new RecipeCommand();

        when(recipeCommandToRecipe.convert(eq(recipeCommand))).thenReturn(recipe);
        when(recipeRepository.save(eq(recipe))).thenReturn(saved);

        service.save(recipeCommand);

        verify(recipeToRecipeCommand).convert(eq(saved));
    }

}

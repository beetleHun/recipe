package com.beetle.recipe.service;

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

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Test
    void getRecipes() {
        recipeService.listRecipes();
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void getById_WhenDoesNotExist_ThenExceptionIsThrown() {
        final Long id = 1L;

        when(recipeRepository.findById(eq(id))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> recipeService.getById(id));
        verify(recipeRepository).findById(eq(id));
    }

    @Test
    void getById_WhenHappyPath() {
        final Long id = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeRepository.findById(eq(id))).thenReturn(Optional.of(recipe));

        Recipe found = assertDoesNotThrow(() -> recipeService.getById(id));
        verify(recipeRepository).findById(eq(id));
        assertEquals(id, found.getId());
    }

}

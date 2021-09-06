package com.beetle.recipe.service;

import com.beetle.recipe.commands.IngredientCommand;
import com.beetle.recipe.converters.IngredientCommandToIngredient;
import com.beetle.recipe.converters.IngredientToIngredientCommand;
import com.beetle.recipe.model.entity.Ingredient;
import com.beetle.recipe.repository.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    private IngredientToIngredientCommand ingredientToIngredientCommand;

    @InjectMocks
    private IngredientServiceImpl service;

    @Test
    void getCommandById_WhenIngredientNotFound_ThenExceptionIsThrown() {
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.getCommandById(1L));
    }

    @Test
    void getCommandById_WhenHappyPath() {
        final Long id = 1L;
        Ingredient ingredient = new Ingredient();
        ingredient.setId(id);

        when(ingredientRepository.findById(eq(id))).thenReturn(Optional.of(ingredient));

        assertDoesNotThrow(() -> service.getCommandById(id));

        verify(ingredientRepository).findById(eq(id));
        verify(ingredientToIngredientCommand).convert(eq(ingredient));
    }

    @Test
    void save_WhenCalled_ThenRecipeCommandToRecipeIsCalled() {
        IngredientCommand ingredientCommand = new IngredientCommand();
        service.save(ingredientCommand);

        verify(ingredientCommandToIngredient).convert(eq(ingredientCommand));
    }

    @Test
    void save_WhenCalled_ThenRepositoryIsCalled() {
        Ingredient ingredient = new Ingredient();
        IngredientCommand ingredientCommand = new IngredientCommand();

        when(ingredientCommandToIngredient.convert(eq(ingredientCommand))).thenReturn(ingredient);

        service.save(ingredientCommand);

        verify(ingredientRepository).save(eq(ingredient));
    }

    @Test
    void save_WhenCalled_ThenRecipeToRecipeCommandIsCalled() {
        Ingredient ingredient = new Ingredient();
        Ingredient saved = new Ingredient();
        saved.setId(1L);
        IngredientCommand ingredientCommand = new IngredientCommand();

        when(ingredientCommandToIngredient.convert(eq(ingredientCommand))).thenReturn(ingredient);
        when(ingredientRepository.save(eq(ingredient))).thenReturn(saved);

        service.save(ingredientCommand);

        verify(ingredientToIngredientCommand).convert(eq(saved));
    }

}

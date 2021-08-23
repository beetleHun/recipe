package com.beetle.recipe.controller;

import com.beetle.recipe.model.entity.Recipe;
import com.beetle.recipe.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @Mock
    private Model model;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController controller;

    @Test
    void getRecipes() {
        Recipe recipe = new Recipe();
        recipe.setDescription("test");
        List<Recipe> recipes = Collections.singletonList(recipe);

        when(recipeService.listRecipes()).thenReturn(recipes);

        String index = controller.getRecipes(model);

        verify(recipeService, times(1)).listRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), eq(recipes));
        assertEquals("recipes/index", index);
    }

    @Test
    void getRecipes_WithArgumentCaptor() {
        Recipe recipe = new Recipe();
        recipe.setDescription("test");
        List<Recipe> recipes = Collections.singletonList(recipe);

        when(recipeService.listRecipes()).thenReturn(recipes);
        ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);

        String index = controller.getRecipes(model);

        verify(recipeService, times(1)).listRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        assertEquals("recipes/index", index);
        assertEquals(1, argumentCaptor.getValue().size());
        assertEquals(recipes, argumentCaptor.getValue());
    }

    @Test
    void testMockMVC() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/index"));
    }

    @Test
    void getRecipe() throws Exception {
        final Long id = 1L;
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get("/recipes/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/details"));

        verify(recipeService).getById(eq(id));
    }

}

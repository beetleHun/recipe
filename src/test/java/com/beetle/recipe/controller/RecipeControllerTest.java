package com.beetle.recipe.controller;

import com.beetle.recipe.commands.IngredientCommand;
import com.beetle.recipe.commands.RecipeCommand;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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

        String index = controller.list(model);

        verify(recipeService, times(1)).listRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), eq(recipes));
        assertEquals("recipes/list", index);
    }

    @Test
    void getRecipes_WithArgumentCaptor() {
        Recipe recipe = new Recipe();
        recipe.setDescription("test");
        List<Recipe> recipes = Collections.singletonList(recipe);

        when(recipeService.listRecipes()).thenReturn(recipes);
        ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);

        String index = controller.list(model);

        verify(recipeService, times(1)).listRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        assertEquals("recipes/list", index);
        assertEquals(1, argumentCaptor.getValue().size());
        assertEquals(recipes, argumentCaptor.getValue());
    }

    @Test
    void getRecipes_testWithMockMVC() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/list"));
    }

    @Test
    void getRecipe_WhenDoesNotExist_ThenModelDoesNotContainAny() throws Exception {
        final Long id = 1L;
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get("/recipes/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/details"))
                .andExpect(model().attributeDoesNotExist("recipe"));

        verify(recipeService).getById(eq(id));
    }

    @Test
    void getRecipe_WhenHappyPath() throws Exception {
        final Long id = 1L;
        MockMvc mockMvc = standaloneSetup(controller).build();

        when(recipeService.getById(eq(id))).thenReturn(new Recipe());

        mockMvc.perform(get("/recipes/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/details"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService).getById(eq(id));
    }


    @Test
    void getNew_WhenHappyPath() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get("/recipes/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/edit"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void getExisting() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller).build();
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeService.getCommandById(eq(1L))).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipes/" + recipeCommand.getId() + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/edit"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService).getCommandById(eq(1L));
    }

    @Test
    void save() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller).build();
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeService.save(any(RecipeCommand.class))).thenReturn(recipeCommand);

        mockMvc.perform(post("/recipes"))
                .andExpect(view().name("redirect:/recipes/" + recipeCommand.getId()))
                .andExpect(redirectedUrl("/recipes/" + recipeCommand.getId()));
    }

    @Test
    void delete() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(post("/recipes/" + 1 + "/delete"))
                .andExpect(view().name("redirect:/recipes/"))
                .andExpect(redirectedUrl("/recipes/"));
    }

    @Test
    void listIngredients_WhenDoesNotExist_ThenModelDoesNotContainAny() throws Exception {
        final Long id = 1L;
        MockMvc mockMvc = standaloneSetup(controller).build();

        when(recipeService.getCommandById(eq(id))).thenReturn(new RecipeCommand());

        mockMvc.perform(get("/recipes/" + id + "/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredients/list"))
                .andExpect(model().attributeDoesNotExist("ingredients"));

        verify(recipeService).getCommandById(eq(id));
    }

    @Test
    void listIngredients_WhenHappyPath() throws Exception {
        final Long id = 1L;
        RecipeCommand command = new RecipeCommand();
        command.setId(id);
        command.setIngredients(Collections.singleton(new IngredientCommand()));

        MockMvc mockMvc = standaloneSetup(controller).build();

        when(recipeService.getCommandById(eq(id))).thenReturn(command);

        mockMvc.perform(get("/recipes/" + id + "/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredients/list"))
                .andExpect(model().attributeExists("ingredients"));

        verify(recipeService).getCommandById(eq(id));
    }

}

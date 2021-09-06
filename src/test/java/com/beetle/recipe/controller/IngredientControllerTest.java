package com.beetle.recipe.controller;

import com.beetle.recipe.commands.IngredientCommand;
import com.beetle.recipe.commands.UnitOfMeasureCommand;
import com.beetle.recipe.service.IngredientService;
import com.beetle.recipe.service.UnitOfMeasureService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
class IngredientControllerTest {

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    @InjectMocks
    private IngredientController controller;

    @Test
    void getIngredient_WhenDoesNotExist_ThenModelDoesNotContainAny() throws Exception {
        final Long id = 1L;
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get("/ingredients/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredients/details"))
                .andExpect(model().attributeDoesNotExist("ingredient"));

        verify(ingredientService).getCommandById(eq(id));
    }

    @Test
    void getIngredient_WhenHappyPath() throws Exception {
        final Long id = 1L;
        MockMvc mockMvc = standaloneSetup(controller).build();

        when(ingredientService.getCommandById(eq(id))).thenReturn(new IngredientCommand());

        mockMvc.perform(get("/ingredients/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredients/details"))
                .andExpect(model().attributeExists("ingredient"));

        verify(ingredientService).getCommandById(eq(id));
    }

    @Test
    void getExisting_WhenDoesNotExist_ThenModelDoesNotContainAny() throws Exception {
        final Long id = 1L;
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get("/ingredients/" + id + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredients/edit"))
                .andExpect(model().attributeDoesNotExist("ingredient"))
                .andExpect(model().attributeDoesNotExist("units"));

        verify(ingredientService).getCommandById(eq(id));
    }

    @Test
    void getExisting_WhenHappyPath() throws Exception {
        final Long id = 1L;
        MockMvc mockMvc = standaloneSetup(controller).build();

        when(ingredientService.getCommandById(eq(id))).thenReturn(new IngredientCommand());
        when(unitOfMeasureService.getAllCommands()).thenReturn(Collections.singletonList(new UnitOfMeasureCommand()));

        mockMvc.perform(get("/ingredients/" + id + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredients/edit"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("units"));

        verify(ingredientService).getCommandById(eq(id));
        verify(unitOfMeasureService).getAllCommands();
    }

    @Test
    void save() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller).build();
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1L);

        when(ingredientService.save(any(IngredientCommand.class))).thenReturn(ingredientCommand);

        mockMvc.perform(post("/ingredients"))
                .andExpect(view().name("redirect:ingredients/" + ingredientCommand.getId()))
                .andExpect(redirectedUrl("ingredients/" + ingredientCommand.getId()));
    }

}

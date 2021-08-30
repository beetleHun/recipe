package com.beetle.recipe.controller;

import com.beetle.recipe.service.IngredientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/{id}")
    public String get(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("ingredient", ingredientService.getCommandById(id));

        return "ingredients/details";
    }

}

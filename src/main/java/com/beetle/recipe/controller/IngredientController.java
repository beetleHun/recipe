package com.beetle.recipe.controller;

import com.beetle.recipe.commands.IngredientCommand;
import com.beetle.recipe.commands.UnitOfMeasureCommand;
import com.beetle.recipe.service.IngredientService;
import com.beetle.recipe.service.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/{id}")
    public String get(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("ingredient", ingredientService.getCommandById(id));

        return "ingredients/details";
    }

    @GetMapping("{id}/edit")
    public String getExisting(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("ingredient", ingredientService.getCommandById(id));

        List<UnitOfMeasureCommand> unitOfMeasures = unitOfMeasureService.getAllCommands();

        if (!unitOfMeasures.isEmpty()) {
            model.addAttribute("units", unitOfMeasures);
        }

        return "ingredients/edit";
    }

    @PostMapping
    public String save(@ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand saved = ingredientService.save(ingredientCommand);

        return "redirect:ingredients/" + saved.getId();
    }

}

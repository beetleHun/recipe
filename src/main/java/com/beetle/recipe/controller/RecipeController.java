package com.beetle.recipe.controller;

import com.beetle.recipe.commands.IngredientCommand;
import com.beetle.recipe.commands.RecipeCommand;
import com.beetle.recipe.model.entity.Recipe;
import com.beetle.recipe.service.IngredientService;
import com.beetle.recipe.service.RecipeService;
import com.beetle.recipe.service.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public RecipeController(RecipeService recipeService, IngredientService ingredientService,
                            UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping({"", "/", "/index"})
    public String list(Model model) {
        log.debug("Getting recipes");
        List<Recipe> recipes = recipeService.listRecipes();
        model.addAttribute("recipes", recipes);

        return "recipes/list";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable(name = "id") Long id, Model model) {
        log.debug("Getting recipe by id '{}'", id);
        Recipe recipe = recipeService.getById(id);
        model.addAttribute("recipe", recipe);

        return "recipes/details";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipes/edit";
    }

    @GetMapping("/{id}/edit")
    public String getExisting(@PathVariable Long id, Model model) {
        RecipeCommand recipeCommand = recipeService.getCommandById(id);
        model.addAttribute("recipe", recipeCommand);

        return "recipes/edit";
    }

    @GetMapping("/{id}/ingredients")
    public String listIngredients(@PathVariable(value = "id") Long id, Model model) {
        RecipeCommand recipe = recipeService.getCommandById(id);

        if (recipe != null && recipe.getIngredients() != null && !recipe.getIngredients().isEmpty()) {
            model.addAttribute("ingredients", recipe.getIngredients());
        }

        return "ingredients/list";
    }

    @GetMapping("/{id}/ingredient/new")
    public String getNewIngredient(@PathVariable(value = "id") Long id, Model model) {
        IngredientCommand newIngredient = new IngredientCommand();
        newIngredient.setRecipeId(id);

        model.addAttribute("ingredient", newIngredient);
        model.addAttribute("units", unitOfMeasureService.getAllCommands());

        return "ingredients/new";
    }

    @PostMapping
    public String save(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand saved = recipeService.save(recipeCommand);

        return "redirect:/recipes/" + saved.getId();
    }

    @PostMapping("/{id}/ingredient/new")
    public String addIngredient(@PathVariable(name = "id") Long id,
                                @ModelAttribute IngredientCommand ingredientCommand) {
        ingredientService.saveNewIngredientToRecipe(id, ingredientCommand);

        return "redirect:/recipes/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        recipeService.delete(id);

        return "redirect:/recipes/";
    }

}

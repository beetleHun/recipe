package com.beetle.recipe;

import com.beetle.recipe.model.entity.Ingredient;
import com.beetle.recipe.model.entity.Note;
import com.beetle.recipe.model.entity.Recipe;
import com.beetle.recipe.model.enums.Difficulty;
import com.beetle.recipe.repository.CategoryRepository;
import com.beetle.recipe.repository.RecipeRepository;
import com.beetle.recipe.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public Bootstrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        log.debug("Creating guacamole");
        Recipe guacamole = new Recipe();
        guacamole.setDescription("The best guacamole");
        guacamole.addCategory(categoryRepository.findByDescription("Mexican").orElseThrow());
        guacamole.setDifficulty(Difficulty.PADAWAN);
        guacamole.setPrepTime(10);
        guacamole.setCookTime(0);
        guacamole.setServings(4);
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamole.setSource("Simply Recipes");

        Note guacamoleNote = new Note();
        guacamoleNote.setRecipe(guacamole);
        guacamoleNote.setNote("Be careful handling chilis! If using, it's best to wear food-safe gloves. If no gloves are available, wash your hands thoroughly after handling, and do not touch your eyes or the area near your eyes for several hours afterwards.");
        guacamole.setNote(guacamoleNote);

        guacamole.setDirections("Cut the avocado:\n" +
                "Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.;" +
                "Mash the avocado flesh:\n" +
                "Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.);" +
                "Add remaining ingredients to taste:\n" +
                "Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chilis. Chili peppers vary individually in their spiciness. So, start with a half of one chili pepper and add more to the guacamole to your desired degree of heat.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.;" +
                "Serve immediately:\n" +
                "If making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.)\n" +
                "\n" +
                "Garnish with slices of red radish or jigama strips. Serve with your choice of store-bought tortilla chips or make your own homemade tortilla chips.\n" +
                "\n" +
                "Refrigerate leftover guacamole up to 3 days.");

        Ingredient avocado = new Ingredient();
        avocado.setRecipe(guacamole);
        avocado.setAmount(BigDecimal.valueOf(2));
        avocado.setDescription("avocados");
        avocado.setUnitOfMeasure(unitOfMeasureRepository.findByMeasure("Ripe").orElseThrow());
        guacamole.addIngredient(avocado);

        Ingredient salt = new Ingredient();
        salt.setRecipe(guacamole);
        salt.setAmount(BigDecimal.valueOf(0.25));
        salt.setDescription("salt");
        salt.setUnitOfMeasure(unitOfMeasureRepository.findByMeasure("Teaspoon").orElseThrow());
        guacamole.addIngredient(salt);

        log.debug("Saving guacamole");
        recipeRepository.save(guacamole);

        log.debug("Creating grilledChickenTaco");
        Recipe grilledChickenTaco = new Recipe();
        grilledChickenTaco.setDescription("Spicy grilled chicken tacos");
        grilledChickenTaco.addCategory(categoryRepository.findByDescription("Mexican").orElseThrow());
        grilledChickenTaco.addCategory(categoryRepository.findByDescription("Fast Food").orElseThrow());
        grilledChickenTaco.setDifficulty(Difficulty.JEDI);
        grilledChickenTaco.setPrepTime(20);
        grilledChickenTaco.setCookTime(15);
        grilledChickenTaco.setServings(6);
        grilledChickenTaco.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        guacamole.setSource("Simply Recipes");

        Note grilledChickenTacoNote = new Note();
        grilledChickenTacoNote.setRecipe(grilledChickenTaco);
        grilledChickenTacoNote.setNote("Look for ancho chili powder with the Mexican ingredients at your grocery store, or buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");
        grilledChickenTaco.setNote(grilledChickenTacoNote);

        grilledChickenTaco.setDirections("Prepare a gas or charcoal grill for medium-high, direct heat;" +
                "Make the marinade and coat the chicken:\n" +
                "In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.;" +
                "Grill the chicken:\n" +
                "Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.;" +
                "Warm the tortillas:\n" +
                "Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.;" +
                "Assemble the tacos:\n" +
                "Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");

        Ingredient chiliPowder = new Ingredient();
        chiliPowder.setRecipe(grilledChickenTaco);
        chiliPowder.setAmount(BigDecimal.valueOf(2));
        chiliPowder.setDescription("chili powder");
        chiliPowder.setUnitOfMeasure(unitOfMeasureRepository.findByMeasure("Tablespoon").orElseThrow());
        grilledChickenTaco.addIngredient(chiliPowder);

        Ingredient oregano = new Ingredient();
        oregano.setRecipe(grilledChickenTaco);
        oregano.setAmount(BigDecimal.valueOf(1));
        oregano.setDescription("oregano");
        oregano.setUnitOfMeasure(unitOfMeasureRepository.findByMeasure("Teaspoon").orElseThrow());
        grilledChickenTaco.addIngredient(oregano);

        log.debug("Saving grilledChickenTaco");
        recipeRepository.save(grilledChickenTaco);
    }

}

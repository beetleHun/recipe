package com.beetle.recipe;

import com.beetle.recipe.model.entity.Recipe;
import com.beetle.recipe.model.enums.Difficulty;
import com.beetle.recipe.repository.CategoryRepository;
import com.beetle.recipe.repository.RecipeRepository;
import com.beetle.recipe.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

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
        guacamole.setDescription("The best guacamole keeps it simple: just ripe avocados, salt, a squeeze of lime, onions, chilis, cilantro, and some chopped tomato. Serve it as a dip at your next party or spoon it on top of tacos for an easy dinner upgrade.");
        guacamole.addCategory(categoryRepository.findByDescription("Mexican").orElseThrow());
        guacamole.setDifficulty(Difficulty.PADAWAN);
        guacamole.setPrepTime(10);
        guacamole.setCookTime(0);
        guacamole.setServings(4);

        log.debug("Saving guacamole");
        recipeRepository.save(guacamole);

        log.debug("Creating grilledChickenTaco");
        Recipe grilledChickenTaco = new Recipe();
        grilledChickenTaco.setDescription("Spicy grilled chicken tacos! Quick marinade, then grill. Ready in about 30 minutes. Great for a quick weeknight dinner, backyard cookouts, and tailgate parties.");
        grilledChickenTaco.addCategory(categoryRepository.findByDescription("Mexican").orElseThrow());
        grilledChickenTaco.setDifficulty(Difficulty.JEDI);
        grilledChickenTaco.setPrepTime(20);
        grilledChickenTaco.setCookTime(15);
        grilledChickenTaco.setServings(6);

        log.debug("Saving grilledChickenTaco");
        recipeRepository.save(grilledChickenTaco);
    }

}

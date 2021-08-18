package com.beetle.recipe.repository;

import com.beetle.recipe.model.entity.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
    }

    // With DirtiesContext the spring context will load twice as after the first test it has to roll back every possible change
    @Test
    // @DirtiesContext
    void findByMeasure() {
        Optional<UnitOfMeasure> teaspoonOpt = unitOfMeasureRepository.findByMeasure("Teaspoon");
        assertFalse(teaspoonOpt.isEmpty());
        assertEquals("Teaspoon", teaspoonOpt.get().getMeasure());
    }

    @Test
    void findByMeasureAlmostSame() {
        Optional<UnitOfMeasure> teaspoonOpt = unitOfMeasureRepository.findByMeasure("Cup");
        assertFalse(teaspoonOpt.isEmpty());
        assertEquals("Cup", teaspoonOpt.get().getMeasure());
    }

}

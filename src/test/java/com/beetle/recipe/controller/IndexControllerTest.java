package com.beetle.recipe.controller;

import com.beetle.recipe.repository.CategoryRepository;
import com.beetle.recipe.repository.UnitOfMeasureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @InjectMocks
    private IndexController controller;

    @Test
    void getIndexPage() {
        String indexPage = controller.getIndexPage();

        verify(categoryRepository, times(1)).findByDescription(eq("Italian"));
        verify(unitOfMeasureRepository, times(1)).findByMeasure(eq("Teaspoon"));
        assertEquals("index", indexPage);
    }

}

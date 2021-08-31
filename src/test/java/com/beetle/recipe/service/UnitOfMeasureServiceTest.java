package com.beetle.recipe.service;

import com.beetle.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.beetle.recipe.model.entity.UnitOfMeasure;
import com.beetle.recipe.repository.UnitOfMeasureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureServiceTest {

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Mock
    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @InjectMocks
    private UnitOfMeasureServiceImpl service;

    @Test
    void getAllCommands() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        when(unitOfMeasureRepository.findAll()).thenReturn(Collections.singletonList(unitOfMeasure));

        service.getAllCommands();

        verify(unitOfMeasureRepository).findAll();
        verify(unitOfMeasureToUnitOfMeasureCommand).convert(eq(unitOfMeasure));
    }

}

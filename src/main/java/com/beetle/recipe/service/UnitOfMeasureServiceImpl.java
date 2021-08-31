package com.beetle.recipe.service;

import com.beetle.recipe.commands.UnitOfMeasureCommand;
import com.beetle.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.beetle.recipe.repository.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                                    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public List<UnitOfMeasureCommand> getAllCommands() {
        List<UnitOfMeasureCommand> uofs = new ArrayList<>();
        unitOfMeasureRepository.findAll().forEach(unitOfMeasure -> uofs.add(unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure)));

        return uofs;
    }

}

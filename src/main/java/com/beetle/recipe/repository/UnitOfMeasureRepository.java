package com.beetle.recipe.repository;

import com.beetle.recipe.model.entity.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    Optional<UnitOfMeasure> findByMeasure(String measure);

}

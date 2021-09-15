package com.beetle.recipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by jt on 6/21/17.
 */
@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private Long recipeId;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand unitOfMeasure;

    public String toString() {
        if (amount == null || unitOfMeasure == null) {
            return "";
        }

        BigDecimal readableAmount = amount.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
        String measure = unitOfMeasure.getMeasure() == null
                ? ""
                : unitOfMeasure.getMeasure().toLowerCase() + (readableAmount.compareTo(BigDecimal.ONE) > 0 ? "s" : "");

        return readableAmount.toPlainString() + " " + measure + " of " + description;
    }

}

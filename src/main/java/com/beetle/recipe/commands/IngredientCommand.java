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

    public String getReadableAmount() {
        BigDecimal readableAmount = amount.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();

        return readableAmount + " " + unitOfMeasure.getMeasure().toLowerCase() +
                (readableAmount.compareTo(BigDecimal.ONE) > 0 ? "s" : "") +
                " of " + description;
    }

}

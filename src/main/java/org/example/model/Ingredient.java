package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {

    private String nameIngredient;
    private int numberOfIngredient;
    private String unitOfMeasurement;

    @Override
    public String toString() {
        return nameIngredient + " - " + numberOfIngredient + " " + unitOfMeasurement;
    }
}

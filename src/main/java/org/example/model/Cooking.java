package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cooking {

    private String cooking;

    @Override
    public String toString() {
        return cooking;
    }
}

package org.example;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class PharmacyBranch {
    private String name;
    private double xCoordinate;
    private double yCoordinate;
    private Map<String, Integer> inventory;

    public boolean hasMedicine(String medicine, int quantity) {
        return inventory.getOrDefault(medicine, 0) >= quantity;
    }
}

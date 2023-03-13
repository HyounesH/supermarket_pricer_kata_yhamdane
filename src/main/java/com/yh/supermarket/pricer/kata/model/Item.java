package com.yh.supermarket.pricer.kata.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Item {
    private String hsCode;
    private String name;
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return hsCode.equals(item.hsCode);
    }

    @Override
    public int hashCode() {
        return hsCode.hashCode();
    }
}

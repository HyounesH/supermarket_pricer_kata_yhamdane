package com.yh.supermarket.pricer.kata.model;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
public class Basket {
    private String id;
    private BigDecimal total;
    private Map<Item, Integer> items = new HashMap<>();

    public Basket(String id) {
        this.id = id;
    }

    public void addItem(@NonNull Item item, @NonNull Integer quantity) {
        if (quantity < 1) return;
        if (total == null) {
            total = BigDecimal.ZERO;
        }
        if (items.containsKey(item)) {
            items.put(item, items.get(item) + quantity);
        } else {
            items.put(item, quantity);
        }
        total = total.add(item.getPrice().multiply(BigDecimal.valueOf(quantity)));
    }


}

package com.yh.supermarket.pricer.kata.model;

import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class Basket {
    private String id;
    private BigDecimal total;
    private Map<Item, Integer> items = new HashMap<>();

    public Basket(String id) {
        this.id = id;
    }

    public Basket() {

    }

    public void addItem(Item item, Integer quantity) {
        Assert.notNull(item, "item should not be null");
        Assert.notNull(quantity, "quantity should not be null");
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Item, Integer> items) {
        this.items = items;
    }
}

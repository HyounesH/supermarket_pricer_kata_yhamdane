package com.yh.supermarket.pricer.kata.dto;

import org.springframework.util.Assert;


public class BasketItemDto {

    private String itemId;
    private Integer quantity;

    public BasketItemDto(String itemId, Integer quantity) {
        Assert.notNull(itemId, "itemId should not be null");
        Assert.notNull(quantity, "quantity should not be null");
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public BasketItemDto() {
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "BasketItemDto{" +
                "itemId='" + itemId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

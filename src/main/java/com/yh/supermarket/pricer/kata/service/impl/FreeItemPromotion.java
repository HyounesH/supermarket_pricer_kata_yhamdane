package com.yh.supermarket.pricer.kata.service.impl;

import com.yh.supermarket.pricer.kata.enums.PromotionTypeEnum;
import com.yh.supermarket.pricer.kata.model.Item;
import com.yh.supermarket.pricer.kata.model.Promotion;
import com.yh.supermarket.pricer.kata.service.PromotionStrategy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Service
public class FreeItemPromotion implements PromotionStrategy {
    /**
     * This method applies free item promotion (e.g: buy two, get one free )  on the provided quantity of items
     *
     * @param item
     * @param itemQuantity
     * @param promotion
     * @return The total price after applying promotion discount
     */
    @Override
    public BigDecimal applyPromotion(Item item, Integer itemQuantity, Promotion promotion) {
        Assert.notNull(item, "the item should not be null");
        Assert.notNull(promotion, "the promotion should not be null");
        Assert.notNull(itemQuantity, "itemQuantity should not be null");
        Assert.notNull(promotion.getEligibleQuantityForDiscount(), "eligibleQuantityForDiscount of promotion should not be null");
        // check if the item quantity is not eligible for discount
        if (itemQuantity < promotion.getEligibleQuantityForDiscount()) {
            return item.getPrice().multiply(BigDecimal.valueOf(itemQuantity));
        }
        // Calculate the free item quantity
        int freeItemQuantity = itemQuantity / (promotion.getEligibleQuantityForDiscount() + 1);
        // Calculate the final total price
        return item.getPrice().multiply(BigDecimal.valueOf( (itemQuantity - freeItemQuantity)));
    }

    @Override
    public PromotionTypeEnum getPromotionType() {
        return PromotionTypeEnum.FREE_ITEM;
    }
}

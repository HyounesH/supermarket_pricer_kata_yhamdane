package com.yh.supermarket.pricer.kata.service.impl;

import com.yh.supermarket.pricer.kata.enums.PromotionTypeEnum;
import com.yh.supermarket.pricer.kata.model.Item;
import com.yh.supermarket.pricer.kata.model.Promotion;
import com.yh.supermarket.pricer.kata.service.PromotionStrategy;

import java.math.BigDecimal;

public class CouponDiscountPromotion implements PromotionStrategy {

    /**
     * This method applies percentage coupon promotion on items  (e.g: 60% discount for itemA )
     *
     * @param item
     * @param itemQuantity
     * @param promotion
     * @return The total price after applying promotion discount
     */
    @Override
    public BigDecimal applyPromotion(Item item, Integer itemQuantity, Promotion promotion) {
        // Calculate the item price after applying the percentage % discount
        final BigDecimal itemPriceAfterDiscount = item.getPrice()
                .subtract(item.getPrice().multiply(BigDecimal.valueOf(promotion.getCouponDiscountPercentage()).divide(BigDecimal.valueOf(100))));
        // Calculate the final total price
        return itemPriceAfterDiscount.multiply(BigDecimal.valueOf(itemQuantity));
    }
    @Override
    public PromotionTypeEnum getPromotionType() {
        return PromotionTypeEnum.COUPON_DISCOUNT;
    }
}

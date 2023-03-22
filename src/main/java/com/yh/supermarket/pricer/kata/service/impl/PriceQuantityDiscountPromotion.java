package com.yh.supermarket.pricer.kata.service.impl;

import com.yh.supermarket.pricer.kata.enums.PromotionTypeEnum;
import com.yh.supermarket.pricer.kata.model.Item;
import com.yh.supermarket.pricer.kata.model.Promotion;
import com.yh.supermarket.pricer.kata.service.PromotionStrategy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Service
public class PriceQuantityDiscountPromotion implements PromotionStrategy {
    /**
     * This method applies promotion price quantity discount (e.g: three items for a dollar)  on the provided quantity of items
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
        Assert.notNull(promotion.getPromotionPrice(), "promotionPrice should not be null");
        if (itemQuantity <= 0) return BigDecimal.ZERO;
        // check if the item quantity is not eligible for discount
        if (itemQuantity < promotion.getEligibleQuantityForDiscount()) {
            return item.getPrice().multiply(BigDecimal.valueOf(itemQuantity));
        }
        // Calculate the discount quantity which the promotion price will be applicable on.
        Integer discountQuantity = itemQuantity / promotion.getEligibleQuantityForDiscount();
        // Calculate the quantity which the promotion price will not be applicable on (always normalQuantity < promotion.getEligibleQuantityForDiscount())
        Integer normalPriceQuantity = itemQuantity % promotion.getEligibleQuantityForDiscount();

        // Calculate the final total price
        return promotion.getPromotionPrice().multiply(BigDecimal.valueOf(discountQuantity)).add(item.getPrice().multiply(BigDecimal.valueOf(normalPriceQuantity)));
    }

    @Override
    public PromotionTypeEnum getPromotionType() {
        return PromotionTypeEnum.PRICE_QUANTITY_DISCOUNT;
    }
}

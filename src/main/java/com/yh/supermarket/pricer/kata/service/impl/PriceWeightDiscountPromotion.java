package com.yh.supermarket.pricer.kata.service.impl;

import com.yh.supermarket.pricer.kata.enums.PromotionTypeEnum;
import com.yh.supermarket.pricer.kata.model.Item;
import com.yh.supermarket.pricer.kata.model.Promotion;
import com.yh.supermarket.pricer.kata.service.PromotionStrategy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Service
public class PriceWeightDiscountPromotion implements PromotionStrategy {
    /**
     * This method applies promotion on weighted items (e.g: 2-pounds-for-one-dollar )
     *
     * @param item
     * @param itemQuantity
     * @param promotion
     * @return The total price after applying promotion discount
     */
    @Override
    public BigDecimal applyPromotion(final Item item, final Integer itemQuantity, final Promotion promotion) {
        Assert.notNull(item, "the item should not be null");
        Assert.notNull(promotion, "the promotion should not be null");
        Assert.notNull(itemQuantity, "itemQuantity should not be null");
        Assert.notNull(promotion.getEligibleQuantityForDiscount(), "eligibleQuantityForDiscount of promotion should not be null");
        Assert.notNull(promotion.getPromotionUnit(), "promotionUnit of promotion should not be null");
        Assert.notNull(promotion.getPromotionPrice(), "promotionPrice of promotion should not be null");
        Assert.notNull(item.getPrice(), "the item's price should not be null");
        Integer itemQuantityInDiscountUnit = itemQuantity;
        BigDecimal finalItemPrice = item.getPrice();
        if (item.getUnit() != promotion.getPromotionUnit()) {
            //todo: apply conversion on itemQuantity to discount unit and convert the price also
        }
        // check if the item quantity on promotion unit is not eligible for discount
        if (itemQuantityInDiscountUnit < promotion.getEligibleQuantityForDiscount()) {
            return item.getPrice().multiply(BigDecimal.valueOf(itemQuantity));
        }
        // Calculate the discount weight quantity which the promotion price will be applicable on.
        final Integer discountWeightQuantity = itemQuantityInDiscountUnit / promotion.getEligibleQuantityForDiscount();
        // Calculate the quantity which the promotion price will not be applicable on (always normalPriceWeightQuantity < promotion.getEligibleQuantityForDiscount())
        final Integer normalPriceWeightQuantity = itemQuantityInDiscountUnit % promotion.getEligibleQuantityForDiscount();

        // Calculate the final total price
        return promotion.getPromotionPrice().multiply(BigDecimal.valueOf(discountWeightQuantity)).add(finalItemPrice.multiply(BigDecimal.valueOf(normalPriceWeightQuantity)));
    }

    @Override
    public PromotionTypeEnum getPromotionType() {
        return PromotionTypeEnum.PRICE_WEIGHT_DISCOUNT;
    }
}

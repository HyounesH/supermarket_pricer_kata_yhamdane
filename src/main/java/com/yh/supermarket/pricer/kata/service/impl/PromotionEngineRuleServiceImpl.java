package com.yh.supermarket.pricer.kata.service.impl;

import com.yh.supermarket.pricer.kata.enums.PromotionTypeEnum;
import com.yh.supermarket.pricer.kata.enums.UnitEnum;
import com.yh.supermarket.pricer.kata.model.Item;
import com.yh.supermarket.pricer.kata.model.Promotion;
import com.yh.supermarket.pricer.kata.service.PromotionEngineRuleService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PromotionEngineRuleServiceImpl implements PromotionEngineRuleService {
    @Override
    public BigDecimal applyPromotion(@NonNull Item item, @NonNull Integer itemQuantity, @NonNull Promotion promotion) {
        if (promotion.getType().equals(PromotionTypeEnum.FREE_ITEM)) {
            return applyFreeItemPromotion(item, itemQuantity, promotion.getEligibleQuantityForDiscount());
        } else if (promotion.getType().equals(PromotionTypeEnum.PRICE_QUANTITY_DISCOUNT)) {
            return applyPromotionForDiscountPerQuantity(item, itemQuantity, promotion.getEligibleQuantityForDiscount(), promotion.getPromotionPrice());
        } else if (promotion.getType().equals(PromotionTypeEnum.PRICE_WEIGHT_DISCOUNT)) {
            return applyPromotionForDiscountPerWeights(item, itemQuantity, promotion.getUnitEnum(), promotion.getEligibleQuantityForDiscount(), promotion.getPromotionPrice());
        } else if (promotion.getType().equals(PromotionTypeEnum.COUPON_DISCOUNT)) {
            return applyCouponPromotionDiscount(item, itemQuantity, promotion.getCouponDiscountPercentage());
        } else {
            return item.getPrice().multiply(BigDecimal.valueOf(itemQuantity));
        }
    }


    private BigDecimal applyFreeItemPromotion(@NonNull Item item, @NonNull Integer itemQuantity, @NonNull Integer eligibleQuantityToGetFreeItem) {

        if (itemQuantity < eligibleQuantityToGetFreeItem) {
            return item.getPrice().multiply(BigDecimal.valueOf(itemQuantity));
        }
        int discountQuantity = itemQuantity / (eligibleQuantityToGetFreeItem+1);
        BigDecimal promotionDiscount = item.getPrice().multiply(BigDecimal.valueOf(discountQuantity));
        return calculateTotalPriceAfterDiscount(item.getPrice(), itemQuantity, promotionDiscount);
    }


    private BigDecimal applyPromotionForDiscountPerQuantity(@NonNull Item item, @NonNull Integer itemQuantity, @NonNull Integer eligibleQuantityForDiscount, @NonNull BigDecimal price) {
        if (itemQuantity < eligibleQuantityForDiscount) {
            return item.getPrice().multiply(BigDecimal.valueOf(itemQuantity));
        }
        Integer discountQuantity = itemQuantity / eligibleQuantityForDiscount;
        Integer normalPriceQuantity = itemQuantity % eligibleQuantityForDiscount;
        return calculateTotalPriceAfterDiscount(item.getPrice(), price, discountQuantity, normalPriceQuantity);
    }


    private BigDecimal applyPromotionForDiscountPerWeights(@NonNull Item item, @NonNull Integer itemQuantity, @NonNull UnitEnum discountUnit, @NonNull Integer eligibleWeightForDiscount, @NonNull BigDecimal price) {
        Integer itemQuantityInDiscountUnit = itemQuantity;
        BigDecimal finalItemPrice = item.getPrice();
        if (item.getUnit() != discountUnit) {
            //todo: apply conversion to discount unit
        }
        if (itemQuantityInDiscountUnit < eligibleWeightForDiscount) {
            return item.getPrice().multiply(BigDecimal.valueOf(itemQuantity));
        }
        Integer discountWeightQuantity = itemQuantityInDiscountUnit / eligibleWeightForDiscount;
        Integer normalPriceWeightQuantity = itemQuantityInDiscountUnit % eligibleWeightForDiscount;

        return this.calculateTotalPriceAfterDiscount(finalItemPrice, price, discountWeightQuantity, normalPriceWeightQuantity);

    }

    private BigDecimal applyCouponPromotionDiscount(@NonNull Item item, @NonNull Integer itemQuantity, @NonNull Integer couponPercentage) {
        BigDecimal itemPriceAfterDiscount = item.getPrice().subtract(item.getPrice().multiply(BigDecimal.valueOf(couponPercentage).divide(BigDecimal.valueOf(100))));
        return itemPriceAfterDiscount.multiply(BigDecimal.valueOf(itemQuantity));
    }

    private BigDecimal calculateTotalPriceAfterDiscount(BigDecimal itemPrice, BigDecimal promotionPrice, Integer discountApplicableQuantity, Integer normalPriceQuantity) {
        return promotionPrice.multiply(BigDecimal.valueOf(discountApplicableQuantity)).add(itemPrice.multiply(BigDecimal.valueOf(normalPriceQuantity)));
    }

    private BigDecimal calculateTotalPriceAfterDiscount(BigDecimal itemPrice, Integer quantity, BigDecimal discount) {
        return itemPrice.multiply(BigDecimal.valueOf(quantity)).subtract(discount);
    }
}

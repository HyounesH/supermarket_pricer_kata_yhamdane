package com.yh.supermarket.pricer.kata.factory;

import com.yh.supermarket.pricer.kata.enums.PromotionTypeEnum;
import com.yh.supermarket.pricer.kata.service.PromotionStrategy;
import com.yh.supermarket.pricer.kata.service.impl.CouponDiscountPromotion;
import com.yh.supermarket.pricer.kata.service.impl.FreeItemPromotion;
import com.yh.supermarket.pricer.kata.service.impl.PriceQuantityDiscountPromotion;
import com.yh.supermarket.pricer.kata.service.impl.PriceWeightDiscountPromotion;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PromotionStrategyFactory {
    private Map<PromotionTypeEnum, PromotionStrategy> promotionStrategies;

    private final FreeItemPromotion freeItemPromotion = new FreeItemPromotion();
    private final PriceQuantityDiscountPromotion priceQuantityDiscountPromotion = new PriceQuantityDiscountPromotion();
    private final PriceWeightDiscountPromotion priceWeightDiscountPromotion = new PriceWeightDiscountPromotion();
    private final CouponDiscountPromotion couponDiscountPromotion = new CouponDiscountPromotion();

    public PromotionStrategyFactory() {
        Set<PromotionStrategy> promotionStrategySet = new HashSet<>();
        promotionStrategySet.add(freeItemPromotion);
        promotionStrategySet.add(priceQuantityDiscountPromotion);
        promotionStrategySet.add(priceWeightDiscountPromotion);
        promotionStrategySet.add(couponDiscountPromotion);
        createPromotionStrategy(promotionStrategySet);
    }

    public PromotionStrategy findStrategy(PromotionTypeEnum promotionType) {
        return promotionStrategies.get(promotionType);
    }

    private void createPromotionStrategy(Set<PromotionStrategy> promotionStrategySet) {
        promotionStrategies = new EnumMap<>(PromotionTypeEnum.class);
        promotionStrategySet.forEach(
                strategy -> promotionStrategies.put(strategy.getPromotionType(), strategy));
    }
}

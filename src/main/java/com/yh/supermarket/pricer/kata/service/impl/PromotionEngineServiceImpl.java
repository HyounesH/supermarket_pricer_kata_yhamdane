package com.yh.supermarket.pricer.kata.service.impl;

import com.yh.supermarket.pricer.kata.factory.PromotionStrategyFactory;
import com.yh.supermarket.pricer.kata.model.Basket;
import com.yh.supermarket.pricer.kata.model.Item;
import com.yh.supermarket.pricer.kata.model.Promotion;
import com.yh.supermarket.pricer.kata.service.PromotionEngineService;
import com.yh.supermarket.pricer.kata.service.PromotionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class PromotionEngineServiceImpl implements PromotionEngineService {
    private final Logger log = LoggerFactory.getLogger(PromotionEngineServiceImpl.class);

    private final PromotionStrategyFactory promotionStrategyFactory;

    public PromotionEngineServiceImpl(PromotionStrategyFactory promotionStrategyFactory) {
        this.promotionStrategyFactory = promotionStrategyFactory;
    }

    @Override
    public void applyPromotionsOnBasket(Map<Item, Promotion> itemPromotionMap, Basket basket) {
        log.debug("PromotionEngineServiceImpl:: apply promotion for basket with id {}", basket.getId());
        BigDecimal basketTotal = BigDecimal.ZERO;
        for (Map.Entry<Item, Integer> itemEntry : basket.getItems().entrySet()) {
            if (itemPromotionMap.containsKey(itemEntry.getKey())) {
                Promotion promotion = itemPromotionMap.get(itemEntry.getKey());
                PromotionStrategy strategy = promotionStrategyFactory.findStrategy(promotion.getType());
                BigDecimal basketEntryPrice = strategy.applyPromotion(itemEntry.getKey(), itemEntry.getValue(), promotion);
                basketTotal = basketTotal.add(basketEntryPrice);
            }
        }
        basket.setTotal(basketTotal);
    }
}

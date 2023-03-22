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
import java.util.Objects;
import java.util.Set;

@Service
public class PromotionEngineServiceImpl implements PromotionEngineService {
    private final Logger log = LoggerFactory.getLogger(PromotionEngineServiceImpl.class);

    private final PromotionStrategyFactory promotionStrategyFactory;

    public PromotionEngineServiceImpl(PromotionStrategyFactory promotionStrategyFactory) {
        this.promotionStrategyFactory = promotionStrategyFactory;
    }

    @Override
    public void applyPromotionsOnBasket(Map<Item, Set<Promotion>> itemPromotionMap, Basket basket) {
        log.debug("PromotionEngineServiceImpl:: apply promotion for basket with id {}", basket.getId());
        BigDecimal basketTotal = BigDecimal.ZERO;
        for (Map.Entry<Item, Integer> itemEntry : basket.getItems().entrySet()) {
            if (itemPromotionMap.containsKey(itemEntry.getKey()) && Objects.nonNull(itemPromotionMap.get(itemEntry.getKey()))) {
                Set<Promotion> promotionSet = itemPromotionMap.get(itemEntry.getKey());
                // In case there is multiple promotion for one item we take the promotion with the highest discount if the discount condition are applicable
                BigDecimal itemTotalPrice = null;
                for (Promotion promotion : promotionSet) {
                    PromotionStrategy strategy = promotionStrategyFactory.findStrategy(promotion.getType());
                    BigDecimal basketEntryPrice = strategy.applyPromotion(itemEntry.getKey(), itemEntry.getValue(), promotion);
                    if (Objects.isNull(itemTotalPrice)) {
                        itemTotalPrice = basketEntryPrice;
                    } else {
                        itemTotalPrice = itemTotalPrice.compareTo(basketEntryPrice) > 0 ? basketEntryPrice : itemTotalPrice;
                    }
                }
                basketTotal = basketTotal.add(itemTotalPrice);
            }
        }
        basket.setTotal(basketTotal);
    }
}

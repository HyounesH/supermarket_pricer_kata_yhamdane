package com.yh.supermarket.pricer.kata.service.impl;

import com.yh.supermarket.pricer.kata.model.Basket;
import com.yh.supermarket.pricer.kata.model.Item;
import com.yh.supermarket.pricer.kata.model.Promotion;
import com.yh.supermarket.pricer.kata.service.PromotionEngineRuleService;
import com.yh.supermarket.pricer.kata.service.PromotionEngineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@Slf4j
public class PromotionEngineServiceImpl implements PromotionEngineService {

    private final PromotionEngineRuleService promotionEngineRuleService;

    @Autowired
    public PromotionEngineServiceImpl(PromotionEngineRuleService promotionEngineRuleService) {
        this.promotionEngineRuleService = promotionEngineRuleService;
    }

    @Override
    public void applyPromotionsOnBasket(Map<Item, Promotion> itemPromotionMap, Basket basket) {
        log.debug("PromotionEngineServiceImpl:: apply promotion for basket with id {}", basket.getId());
        BigDecimal basketTotal = BigDecimal.ZERO;
        for (Map.Entry<Item, Integer> itemEntry : basket.getItems().entrySet()) {
            if (itemPromotionMap.containsKey(itemEntry.getKey())) {
                BigDecimal basketEntryPrice = this.promotionEngineRuleService.applyPromotion(itemEntry.getKey(), itemEntry.getValue(), itemPromotionMap.get(itemEntry.getKey()));
                basketTotal = basketTotal.add(basketEntryPrice);
            }
        }
        basket.setTotal(basketTotal);
    }
}

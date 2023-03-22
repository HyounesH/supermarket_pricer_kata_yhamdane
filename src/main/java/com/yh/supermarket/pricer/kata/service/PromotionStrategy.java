package com.yh.supermarket.pricer.kata.service;

import com.yh.supermarket.pricer.kata.enums.PromotionTypeEnum;
import com.yh.supermarket.pricer.kata.model.Item;
import com.yh.supermarket.pricer.kata.model.Promotion;

import java.math.BigDecimal;

public interface PromotionStrategy  {
    BigDecimal applyPromotion(Item item, Integer itemQuantity, Promotion promotion);
    PromotionTypeEnum getPromotionType();
}

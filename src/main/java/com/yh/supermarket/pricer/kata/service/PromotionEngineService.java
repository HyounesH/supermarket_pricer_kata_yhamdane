package com.yh.supermarket.pricer.kata.service;

import com.yh.supermarket.pricer.kata.model.Basket;
import com.yh.supermarket.pricer.kata.model.Item;
import com.yh.supermarket.pricer.kata.model.Promotion;

import java.util.Map;

public interface PromotionEngineService {

    void applyPromotionsOnBasket(Map<Item, Promotion> itemPromotionMap, Basket basket);
}

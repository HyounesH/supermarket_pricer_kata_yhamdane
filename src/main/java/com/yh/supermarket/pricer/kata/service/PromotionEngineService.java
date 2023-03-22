package com.yh.supermarket.pricer.kata.service;

import com.yh.supermarket.pricer.kata.model.Basket;
import com.yh.supermarket.pricer.kata.model.Item;
import com.yh.supermarket.pricer.kata.model.Promotion;

import java.util.Map;
import java.util.Set;

public interface PromotionEngineService {

    void applyPromotionsOnBasket(Map<Item, Set<Promotion>> itemPromotionMap , Basket basket);
}

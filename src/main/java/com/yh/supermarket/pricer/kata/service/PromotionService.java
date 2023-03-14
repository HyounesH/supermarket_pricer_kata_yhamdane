package com.yh.supermarket.pricer.kata.service;

import com.yh.supermarket.pricer.kata.model.Item;
import com.yh.supermarket.pricer.kata.model.Promotion;

import java.util.List;
import java.util.Map;

public interface PromotionService {
    List<Promotion> findAllPromotions();

    Promotion addPromotion(Promotion promotion);

    Map<Item, Promotion> getPromotionMapByItem();
}

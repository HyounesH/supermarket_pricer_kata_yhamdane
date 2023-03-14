package com.yh.supermarket.pricer.kata.service.impl;

import com.yh.supermarket.pricer.kata.dto.BasketItemDto;
import com.yh.supermarket.pricer.kata.model.Basket;
import com.yh.supermarket.pricer.kata.model.Item;
import com.yh.supermarket.pricer.kata.model.Promotion;
import com.yh.supermarket.pricer.kata.service.BasketService;
import com.yh.supermarket.pricer.kata.service.ItemService;
import com.yh.supermarket.pricer.kata.service.PromotionEngineService;
import com.yh.supermarket.pricer.kata.service.PromotionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class BasketServiceImpl implements BasketService {

    private final ItemService itemService;
    private final PromotionService promotionService;

    private final PromotionEngineService promotionEngineService;

    public BasketServiceImpl(ItemService itemService, PromotionService promotionService, PromotionEngineService promotionEngineService) {
        this.itemService = itemService;
        this.promotionService = promotionService;
        this.promotionEngineService = promotionEngineService;
    }

    @Override
    public BigDecimal calculateBasketTotal(List<BasketItemDto> basketItems, boolean promotion) throws Exception {
        Basket basket = this.buildBasketFromBasketItemDtoList(basketItems);
        if (Boolean.TRUE.equals(promotion)) {
            return calculateBasketTotalWithPromotion(basket);
        }
        return calculateBasketTotalWithoutPromotion(basket);
    }


    private BigDecimal calculateBasketTotalWithoutPromotion(Basket basket) {
        return basket.getTotal();
    }

    private BigDecimal calculateBasketTotalWithPromotion(Basket basket) {
        Map<Item, Promotion> promotionMapByItem = this.promotionService.getPromotionMapByItem();
        promotionEngineService.applyPromotionsOnBasket(promotionMapByItem, basket);
        return basket.getTotal();
    }


    private Basket buildBasketFromBasketItemDtoList(List<BasketItemDto> basketItemDtoList) throws Exception {
        Basket basket = new Basket();
        for (BasketItemDto basketItemDto : basketItemDtoList) {
            Item item = this.itemService.getItemById(basketItemDto.getItemId());
            basket.addItem(item, basketItemDto.getQuantity());
        }
        return basket;
    }

}

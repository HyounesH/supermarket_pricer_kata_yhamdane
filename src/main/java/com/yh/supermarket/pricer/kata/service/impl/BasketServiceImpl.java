package com.yh.supermarket.pricer.kata.service.impl;

import com.yh.supermarket.pricer.kata.dto.BasketItemDto;
import com.yh.supermarket.pricer.kata.exceptions.ApplicationException;
import com.yh.supermarket.pricer.kata.model.Basket;
import com.yh.supermarket.pricer.kata.model.Item;
import com.yh.supermarket.pricer.kata.model.Promotion;
import com.yh.supermarket.pricer.kata.service.BasketService;
import com.yh.supermarket.pricer.kata.service.ItemService;
import com.yh.supermarket.pricer.kata.service.PromotionEngineService;
import com.yh.supermarket.pricer.kata.service.PromotionService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /**
     * Calculate the basket total amount
     *
     * @param basketItems           : basket items
     * @param isPromotionApplicable : boolean param to decide to apply promotion or not
     * @return
     * @throws ApplicationException the type of exception to throw in case there is an exception
     */
    @Override
    public BigDecimal calculateBasketTotal(List<BasketItemDto> basketItems, boolean isPromotionApplicable) throws ApplicationException {
        Basket basket = this.buildBasketFromBasketItemDtoList(basketItems);
        if (Boolean.TRUE.equals(isPromotionApplicable)) {
            return calculateBasketTotalWithPromotion(basket);
        }
        return calculateBasketTotalWithoutPromotion(basket);
    }

    /**
     * Calculate the basket total amount without promotion discount
     *
     * @param basket
     * @return return the total amount
     */
    private BigDecimal calculateBasketTotalWithoutPromotion(Basket basket) {
        return basket.getTotal();
    }

    /**
     * this method calculate the basket total after applying promotion
     *
     * @param basket : contains set of items with it quantity
     * @return return the total amount of basket after promotion discount
     */
    private BigDecimal calculateBasketTotalWithPromotion(Basket basket) {
        Map<Item, Set<Promotion>> promotionMapByItem = this.promotionService.getPromotionMapByItem();
        promotionEngineService.applyPromotionsOnBasket(promotionMapByItem, basket);
        return basket.getTotal();
    }

    /**
     * Build the basket dto from BasketItemDto list in order to calculate to total amount of basket
     *
     * @param basketItemDtoList
     * @return
     * @throws ApplicationException when an item in the basketItemDto list is not found then throw the ApplicationException
     */
    private Basket buildBasketFromBasketItemDtoList(List<BasketItemDto> basketItemDtoList) throws ApplicationException {
        Assert.notNull(basketItemDtoList, "the basketItemDto list should not be null");
        Basket basket = new Basket();
        for (BasketItemDto basketItemDto : basketItemDtoList) {
            Item item = this.itemService.getItemById(basketItemDto.getItemId());
            basket.addItem(item, basketItemDto.getQuantity());
        }
        return basket;
    }

}

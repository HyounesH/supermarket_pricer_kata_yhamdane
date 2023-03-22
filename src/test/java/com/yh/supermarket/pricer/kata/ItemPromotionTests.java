package com.yh.supermarket.pricer.kata;

import com.yh.supermarket.pricer.kata.enums.PromotionTypeEnum;
import com.yh.supermarket.pricer.kata.enums.UnitEnum;
import com.yh.supermarket.pricer.kata.factory.PromotionStrategyFactory;
import com.yh.supermarket.pricer.kata.model.Basket;
import com.yh.supermarket.pricer.kata.model.Item;
import com.yh.supermarket.pricer.kata.model.Promotion;
import com.yh.supermarket.pricer.kata.service.impl.PromotionEngineServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ItemPromotionTests {

    public static final String BASKET_NAME = "Basket1";

    private PromotionEngineServiceImpl promotionService;

    @BeforeEach()
    public void initTests() {
        PromotionStrategyFactory promotionStrategyFactory = new PromotionStrategyFactory();
        this.promotionService = new PromotionEngineServiceImpl(promotionStrategyFactory);
    }

    @Test
    public void test_three_for_one_dollar_promotion() {
        Item waterBottleItem = new Item("1300554E", "water bottle", BigDecimal.valueOf(0.37));

        Basket basket = new Basket(BASKET_NAME);
        basket.addItem(waterBottleItem, 4);

        BigDecimal expectedPriceBeforeDiscount = waterBottleItem.getPrice().multiply(BigDecimal.valueOf(4));

        Assertions.assertEquals(expectedPriceBeforeDiscount, basket.getTotal());

        Promotion promotion = new Promotion(1, PromotionTypeEnum.PRICE_QUANTITY_DISCOUNT, 3, BigDecimal.valueOf(1));

        Assertions.assertEquals(1, promotion.getId());

        Map<Item, Set<Promotion>> promotionMap = new HashMap<>();
        Set<Promotion> promotions = new HashSet<>();
        promotions.add(promotion);
        promotionMap.put(waterBottleItem, promotions);

        this.promotionService.applyPromotionsOnBasket(promotionMap, basket);

        Assertions.assertEquals(BigDecimal.valueOf(1.37), basket.getTotal());
    }

    @Test
    public void test_2_pounds_for_one_dollar_promotion() {

        Item beanCanItem = new Item("1400154E", "bean can", BigDecimal.valueOf(0.65), UnitEnum.POUND);

        Assertions.assertEquals(UnitEnum.POUND, beanCanItem.getUnit());
        Basket basket = new Basket(BASKET_NAME);
        basket.addItem(beanCanItem, 5);

        BigDecimal expectedPriceBeforeDiscount = beanCanItem.getPrice().multiply(BigDecimal.valueOf(5));

        Assertions.assertEquals(expectedPriceBeforeDiscount, basket.getTotal());

        Promotion promotion = new Promotion(1, PromotionTypeEnum.PRICE_WEIGHT_DISCOUNT, 2, BigDecimal.valueOf(1), UnitEnum.POUND);

        Map<Item, Set<Promotion>> promotionMap = new HashMap<>();
        Set<Promotion> promotions = new HashSet<>();
        promotions.add(promotion);
        promotionMap.put(beanCanItem, promotions);

        this.promotionService.applyPromotionsOnBasket(promotionMap, basket);

        Assertions.assertEquals(BigDecimal.valueOf(2.65), basket.getTotal());

    }

    @Test
    public void test_buy_2_get_1_free_promotion() {
        Item pastaPackItem = new Item("1200354E", "pasta pack", BigDecimal.valueOf(1.10));
        Basket basket = new Basket(BASKET_NAME);
        basket.addItem(pastaPackItem, 7);

        BigDecimal expectedPriceBeforeDiscount = pastaPackItem.getPrice().multiply(BigDecimal.valueOf(7));

        Assertions.assertEquals(expectedPriceBeforeDiscount, basket.getTotal());

        Promotion promotion = new Promotion(1, PromotionTypeEnum.FREE_ITEM, 2);

        Map<Item, Set<Promotion>> promotionMap = new HashMap<>();
        Set<Promotion> promotions = new HashSet<>();
        promotions.add(promotion);
        promotionMap.put(pastaPackItem, promotions);

        this.promotionService.applyPromotionsOnBasket(promotionMap, basket);

        Assertions.assertEquals(BigDecimal.valueOf(5.50), basket.getTotal());

    }

    @Test
    public void test_70_percent_coupon_promotion() {
        Item pastaPackItem = new Item("1200354E", "pasta pack", BigDecimal.valueOf(1.10));
        Basket basket = new Basket(BASKET_NAME);
        basket.addItem(pastaPackItem, 10);

        BigDecimal expectedPriceBeforeDiscount = pastaPackItem.getPrice().multiply(BigDecimal.valueOf(10));

        Assertions.assertEquals(expectedPriceBeforeDiscount, basket.getTotal());

        Promotion promotion = new Promotion(1, 70, PromotionTypeEnum.COUPON_DISCOUNT);

        Map<Item, Set<Promotion>> promotionMap = new HashMap<>();
        Set<Promotion> promotions = new HashSet<>();
        promotions.add(promotion);
        promotionMap.put(pastaPackItem, promotions);

        this.promotionService.applyPromotionsOnBasket(promotionMap, basket);

        Assertions.assertEquals(BigDecimal.valueOf(3.30).doubleValue(), basket.getTotal().doubleValue());
    }

    @Test
    public void test_multiple_promotions() {
        Item pastaPackItem = new Item("1200354E", "pasta pack", BigDecimal.valueOf(1.10));
        Item beanCanItem = new Item("1400154E", "bean can", BigDecimal.valueOf(0.65), UnitEnum.POUND);
        Item waterBottleItem = new Item("1300554E", "water bottle", BigDecimal.valueOf(0.37));

        Basket basket = new Basket(BASKET_NAME);
        basket.addItem(pastaPackItem, 10);
        basket.addItem(beanCanItem, 15);
        basket.addItem(waterBottleItem, 5);
        BigDecimal expectedPriceBeforeDiscount = waterBottleItem.getPrice().multiply(BigDecimal.valueOf(5))
                .add(pastaPackItem.getPrice().multiply(BigDecimal.TEN))
                .add(beanCanItem.getPrice().multiply(BigDecimal.valueOf(15)));
        Assertions.assertEquals(expectedPriceBeforeDiscount, basket.getTotal());

        Promotion promotionCoupon = new Promotion(1, 70, PromotionTypeEnum.COUPON_DISCOUNT);
        Promotion promotionFreeItem = new Promotion(2, PromotionTypeEnum.FREE_ITEM, 4);
        Promotion promotionDiscount = new Promotion(3, PromotionTypeEnum.PRICE_WEIGHT_DISCOUNT, 2, BigDecimal.valueOf(1), UnitEnum.POUND);
        Map<Item, Set<Promotion>> promotionMap = new HashMap<>();

        Set<Promotion> pastaPackPromotionSet = new HashSet<>();
        pastaPackPromotionSet.add(promotionCoupon);

        Set<Promotion> waterBottlePromotionSet = new HashSet<>();
        waterBottlePromotionSet.add(promotionFreeItem);

        Set<Promotion> beanCanPromotionSet = new HashSet<>();
        beanCanPromotionSet.add(promotionDiscount);


        promotionMap.put(pastaPackItem, pastaPackPromotionSet);
        promotionMap.put(waterBottleItem, waterBottlePromotionSet);
        promotionMap.put(beanCanItem, beanCanPromotionSet);

        this.promotionService.applyPromotionsOnBasket(promotionMap, basket);

        Assertions.assertEquals(BigDecimal.valueOf(12.43).doubleValue(), basket.getTotal().doubleValue());
    }


}

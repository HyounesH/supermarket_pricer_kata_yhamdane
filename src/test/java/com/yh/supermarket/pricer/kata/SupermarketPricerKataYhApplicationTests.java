package com.yh.supermarket.pricer.kata;

import com.yh.supermarket.pricer.kata.model.Basket;
import com.yh.supermarket.pricer.kata.model.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.stream.Stream;

@SpringBootTest
class SupermarketPricerKataYhApplicationTests {

    public static final String BASKET_NAME = "Basket1";

    @Test
    public void test_bask_total_with_no_items() {
        Basket basket = new Basket(BASKET_NAME);
        Assertions.assertEquals(BASKET_NAME, basket.getId());
        Assertions.assertNull(basket.getTotal());
    }

    @Test
    public void test_basket_total_with_items() {
        Basket basket = new Basket(BASKET_NAME);
        Item beanCanItem = new Item("1400154E", "bean can", BigDecimal.valueOf(1.30));
        Item waterBottleItem = new Item("1300554E", "water bottle", BigDecimal.valueOf(0.81));
        Item pastaPackItem = new Item("1200354E", "pasta pack", BigDecimal.valueOf(1.14));
        basket.addItem(beanCanItem, 1);
        basket.addItem(waterBottleItem, 1);
        basket.addItem(pastaPackItem, 1);
        BigDecimal total = beanCanItem.getPrice().add(pastaPackItem.getPrice().add(waterBottleItem.getPrice()));
        Assertions.assertEquals(total.doubleValue(), basket.getTotal().doubleValue());
    }

    @Test
    public void test_basket_total_after_increasing_quantity_of_items() {
        Basket basket = new Basket(BASKET_NAME);
        Item beanCanItem = new Item("1400154E", "bean can", BigDecimal.valueOf(1.30));
        Item waterBottleItem = new Item("1300554E", "water bottle", BigDecimal.valueOf(0.81));
        Item pastaPackItem = new Item("1200354E", "pasta pack", BigDecimal.valueOf(1.14));
        basket.addItem(beanCanItem, 1);
        basket.addItem(waterBottleItem, 1);
        basket.addItem(pastaPackItem, 1);

        BigDecimal total = beanCanItem.getPrice().add(pastaPackItem.getPrice().add(waterBottleItem.getPrice()));
        Assertions.assertEquals(total, basket.getTotal());


        basket.addItem(waterBottleItem, 4);

        Assertions.assertNotEquals(total.doubleValue(), basket.getTotal().doubleValue());

        total = total.add(waterBottleItem.getPrice().multiply(BigDecimal.valueOf(4)));

        Assertions.assertEquals(total, basket.getTotal());
    }

    @Test
    public void test_basket_items() {
        Basket basket = new Basket(BASKET_NAME);
        Item beanCanItem = new Item("1400154E", "bean can", BigDecimal.valueOf(1.30));
        Item waterBottleItem = new Item("1300554E", "water bottle", BigDecimal.valueOf(0.81));
        Item pastaPackItem = new Item("1200354E", "pasta pack", BigDecimal.valueOf(1.14));
        basket.addItem(beanCanItem, 1);
        basket.addItem(waterBottleItem, 1);
        basket.addItem(pastaPackItem, 1);

        Assertions.assertNotNull(basket.getItems());
        String[] expectedItemsNames = Stream.of(beanCanItem.getName(), waterBottleItem.getName(), pastaPackItem.getName()).sorted().toArray(String[]::new);
        String[] basketItemsNames = basket.getItems().keySet().stream().map(Item::getName).sorted().toArray(String[]::new);
        Assertions.assertArrayEquals(expectedItemsNames, basketItemsNames);


        String[] expectedItemsHsCode = Stream.of(beanCanItem.getId(), waterBottleItem.getId(), pastaPackItem.getId()).sorted().toArray(String[]::new);
        String[] basketItemsHsCode = basket.getItems().keySet().stream().map(Item::getId).sorted().toArray(String[]::new);

        Assertions.assertArrayEquals(expectedItemsHsCode, basketItemsHsCode);
    }
}

package com.yh.supermarket.pricer.kata.controller;

import com.yh.supermarket.pricer.kata.dto.BasketItemDto;
import com.yh.supermarket.pricer.kata.model.Item;
import com.yh.supermarket.pricer.kata.model.Promotion;
import com.yh.supermarket.pricer.kata.service.BasketService;
import com.yh.supermarket.pricer.kata.service.ItemService;
import com.yh.supermarket.pricer.kata.service.PromotionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
@Slf4j
public class MainController {
    private final PromotionService promotionService;
    private final ItemService itemService;

    private final BasketService basketService;

    @Autowired
    public MainController(PromotionService promotionService, ItemService itemService, BasketService basketService) {
        this.promotionService = promotionService;
        this.itemService = itemService;
        this.basketService = basketService;
    }

    @GetMapping("/promotion/all")
    public ResponseEntity<List<Promotion>> findAllPromotions() {
        log.debug("MainController:: find all available promotions");
        return ResponseEntity.ok(this.promotionService.findAllPromotions());
    }

    @PostMapping("/promotion/add")
    public ResponseEntity<Promotion> addPromotion(@RequestBody Promotion promotion) {
        log.debug("MainController:: create new promotion [{}]", promotion);
        return new ResponseEntity(this.promotionService.addPromotion(promotion), HttpStatus.CREATED);
    }


    @GetMapping("/item/all")
    public ResponseEntity<List<Item>> findAllItems() {
        log.debug("MainController:: find all available items");
        return ResponseEntity.ok(this.itemService.findAllItems());
    }

    @PostMapping("/item/add")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        log.debug("MainController:: create new item [{}]", item);
        return new ResponseEntity(this.itemService.addItem(item), HttpStatus.CREATED);
    }

    @PostMapping("/basket/calculate-total-without-promotion")
    public ResponseEntity<BigDecimal> calculateBasketTotalWithoutPromotion(@RequestBody List<BasketItemDto> basketItems) {
        log.debug("MainController:: calculate basket total without promotion : [{}]", basketItems);
        return ResponseEntity.ok(basketService.calculateBasketTotal(basketItems, false));
    }

    @PostMapping("/basket/calculate-total-with-promotion")
    public ResponseEntity<BigDecimal> calculateBasketTotalWitPromotion(@RequestBody List<BasketItemDto> basketItems) {
        log.debug("MainController:: calculate basket total with promotion : [{}]", basketItems);
        return ResponseEntity.ok(basketService.calculateBasketTotal(basketItems, true));
    }
}
package com.yh.supermarket.pricer.kata.service.impl;

import com.yh.supermarket.pricer.kata.model.Item;
import com.yh.supermarket.pricer.kata.model.Promotion;
import com.yh.supermarket.pricer.kata.repository.PromotionRepository;
import com.yh.supermarket.pricer.kata.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepository promotionRepository;

    private List<Promotion> availablePromotions = new ArrayList<>();

    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    /**
     * find all available promotions
     *
     * @return list of available promotions
     */

    @Override
    public List<Promotion> findAllPromotions() {
        this.availablePromotions = this.promotionRepository.findAll();
        return availablePromotions;
    }

    /**
     * Save promotion in database
     *
     * @param promotion
     * @return the promotion saved in database
     */

    @Override
    public Promotion addPromotion(Promotion promotion) {
        return promotionRepository.saveAndFlush(promotion);
    }

    /**
     * map list of promotion by item
     *
     * @return map list of promotion by item
     */
    @Override
    public Map<Item, Promotion> getPromotionMapByItem() {
        if (CollectionUtils.isEmpty(this.availablePromotions)) {
            this.availablePromotions = findAllPromotions();
        }
        return this.availablePromotions.stream().collect(Collectors.toMap(Promotion::getItem, Function.identity()));
    }
}

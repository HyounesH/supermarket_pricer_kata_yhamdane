package com.yh.supermarket.pricer.kata.model;

import com.yh.supermarket.pricer.kata.enums.PromotionTypeEnum;
import com.yh.supermarket.pricer.kata.enums.UnitEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Promotion {
    private Integer id;
    private PromotionTypeEnum type;
    private Integer eligibleQuantityForDiscount;
    private BigDecimal promotionPrice;
    private UnitEnum unitEnum;
    private Integer couponDiscountPercentage;

    public Promotion(Integer id, PromotionTypeEnum promotionType, Integer eligibleQuantityForDiscount, BigDecimal promotionPrice) {
        this.id = id;
        this.type = promotionType;
        this.eligibleQuantityForDiscount = eligibleQuantityForDiscount;
        this.promotionPrice = promotionPrice;
    }

    public Promotion(Integer id, PromotionTypeEnum type, Integer eligibleQuantityForDiscount, BigDecimal promotionPrice, UnitEnum unitEnum) {
        this.id = id;
        this.type = type;
        this.eligibleQuantityForDiscount = eligibleQuantityForDiscount;
        this.promotionPrice = promotionPrice;
        this.unitEnum = unitEnum;
    }

    public Promotion(Integer id, PromotionTypeEnum type, Integer eligibleQuantityForDiscount) {
        this.id = id;
        this.type = type;
        this.eligibleQuantityForDiscount = eligibleQuantityForDiscount;
    }

    public Promotion(Integer id, Integer couponDiscountPercentage, PromotionTypeEnum type) {
        this.id = id;
        this.type = type;
        this.couponDiscountPercentage = couponDiscountPercentage;
    }


}

package com.yh.supermarket.pricer.kata.model;

import com.opencsv.bean.CsvBindByPosition;
import com.yh.supermarket.pricer.kata.enums.PromotionTypeEnum;
import com.yh.supermarket.pricer.kata.enums.UnitEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity(name = "promotion")
@ToString
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByPosition(position = 0)
    private Integer id;

    @JoinColumn(name = "item_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Item item;
    @Transient
    @CsvBindByPosition(position = 1)
    private String itemId;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @CsvBindByPosition(position = 2)
    private PromotionTypeEnum type;
    @Column(name = "eligible_quantity_for_discount")
    @CsvBindByPosition(position = 3)
    private Integer eligibleQuantityForDiscount;
    @Column(name = "promotion_price")
    @CsvBindByPosition(position = 4)
    private BigDecimal promotionPrice;

    @Column(name = "promotion_unit")
    @Enumerated(EnumType.STRING)
    @CsvBindByPosition(position = 5)
    private UnitEnum promotionUnit;
    @Column(name = "coupon_discount_percentage")
    @CsvBindByPosition(position = 6)
    private Integer couponDiscountPercentage;


    public Promotion(Integer id, PromotionTypeEnum promotionType, Integer eligibleQuantityForDiscount, BigDecimal promotionPrice) {
        this.id = id;
        this.type = promotionType;
        this.eligibleQuantityForDiscount = eligibleQuantityForDiscount;
        this.promotionPrice = promotionPrice;
    }

    public Promotion(Integer id, PromotionTypeEnum type, Integer eligibleQuantityForDiscount, BigDecimal promotionPrice, UnitEnum promotionUnit) {
        this.id = id;
        this.type = type;
        this.eligibleQuantityForDiscount = eligibleQuantityForDiscount;
        this.promotionPrice = promotionPrice;
        this.promotionUnit = promotionUnit;
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

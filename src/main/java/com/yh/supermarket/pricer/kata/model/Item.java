package com.yh.supermarket.pricer.kata.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByPosition;
import com.yh.supermarket.pricer.kata.enums.UnitEnum;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity(name = "item")
public class Item {
    @Id
    @Column(name = "id")
    @CsvBindByPosition(position = 0)
    private String id;
    @Column(name = "name")
    @CsvBindByPosition(position = 1)
    private String name;
    @Column(name = "price")
    @CsvBindByPosition(position = 2)
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    @Column(name = "unit")
    @CsvBindByPosition(position = 3)
    private UnitEnum unit;

    @OneToOne(mappedBy = "item")
    private Promotion promotion;

    public Item() {

    }

    public Item(String id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Item(String id, String name, BigDecimal price, UnitEnum unit) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @JsonIgnore
    public Promotion getPromotion() {
        return promotion;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", unit=" + unit +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public UnitEnum getUnit() {
        return unit;
    }

    public void setUnit(UnitEnum unit) {
        this.unit = unit;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
}

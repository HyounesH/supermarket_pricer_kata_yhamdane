package com.yh.supermarket.pricer.kata.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BasketItemDto {
    @NonNull
    private String itemId;
    @NonNull
    private Integer quantity;
}

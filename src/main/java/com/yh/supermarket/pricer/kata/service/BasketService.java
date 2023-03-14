package com.yh.supermarket.pricer.kata.service;

import com.yh.supermarket.pricer.kata.dto.BasketItemDto;
import com.yh.supermarket.pricer.kata.exceptions.ApplicationException;

import java.math.BigDecimal;
import java.util.List;

public interface BasketService {
    BigDecimal calculateBasketTotal(List<BasketItemDto> basketItems, boolean promotion) throws ApplicationException;
}

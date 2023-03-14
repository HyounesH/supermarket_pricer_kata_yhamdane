package com.yh.supermarket.pricer.kata.service;


import com.yh.supermarket.pricer.kata.model.Item;

import java.util.List;

public interface ItemService {
    List<Item> findAllItems();

    Item addItem(Item item);

    Item getItemById(String id) throws Exception;
}

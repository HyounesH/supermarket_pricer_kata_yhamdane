package com.yh.supermarket.pricer.kata.service.impl;

import com.yh.supermarket.pricer.kata.exceptions.ApplicationException;
import com.yh.supermarket.pricer.kata.model.Item;
import com.yh.supermarket.pricer.kata.repository.ItemRepository;
import com.yh.supermarket.pricer.kata.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> findAllItems() {
        return this.itemRepository.findAll();
    }

    @Override
    public Item addItem(Item item) {
        return this.itemRepository.saveAndFlush(item);
    }

    @Override
    public Item getItemById(String id) throws ApplicationException {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (itemOptional.isPresent()) return itemOptional.get();
        throw new ApplicationException("item-not-found", String.format("Item with id %s not found", id), HttpStatus.NOT_FOUND);
    }
}

package com.yh.supermarket.pricer.kata.repository;

import com.yh.supermarket.pricer.kata.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
}

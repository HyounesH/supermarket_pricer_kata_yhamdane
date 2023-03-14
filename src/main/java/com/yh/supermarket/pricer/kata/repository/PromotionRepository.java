package com.yh.supermarket.pricer.kata.repository;

import com.yh.supermarket.pricer.kata.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
}

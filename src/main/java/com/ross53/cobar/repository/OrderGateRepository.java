package com.ross53.cobar.repository;

import com.ross53.cobar.domain.OrderGate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderGateRepository extends JpaRepository<OrderGate, Integer>{

}

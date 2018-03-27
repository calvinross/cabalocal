package com.ross53.cobar.service;


import com.ross53.cobar.domain.OrderGate;
import com.ross53.cobar.repository.OrderGateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderGateService {

    @Autowired
    private OrderGateRepository orderGateRepository;

    private static final int gateIndex = 1;


    public OrderGate getOrderGate(){

        return orderGateRepository.findOne(gateIndex);
    }

    public OrderGate saveOrderGate(OrderGate orderGate)
    {
        return orderGateRepository.save(orderGate);
    }
}

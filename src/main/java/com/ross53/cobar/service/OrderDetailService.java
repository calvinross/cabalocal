package com.ross53.cobar.service;


import com.ross53.cobar.domain.OrderDetail;
import com.ross53.cobar.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailService {

    @Autowired
    OrderDetailRepository orderDetailRepository;

    public OrderDetail saveOrderDetail(OrderDetail orderDetail){

        return orderDetailRepository.save(orderDetail);
    }
}

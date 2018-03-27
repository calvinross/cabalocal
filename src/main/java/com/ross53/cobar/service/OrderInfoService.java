package com.ross53.cobar.service;

import com.ross53.cobar.domain.OrderInfo;
import com.ross53.cobar.enums.OrderStatus;
import com.ross53.cobar.repository.OrderInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderInfoService {

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    public OrderInfo SaveOrderInfo(OrderInfo orderInfo){

        return orderInfoRepository.save(orderInfo);
    }

    public List<OrderInfo> findAll(){
        return orderInfoRepository.findAll();
    }

    public List<OrderInfo> findAllByOrderDateDesc(){
        return orderInfoRepository.findAllByOrderDateDesc();
    }

    public OrderInfo findByOrderId(Integer orderId){
        return  orderInfoRepository.findByOrderId(orderId);
    }

    public List<OrderInfo> findByOrderStatus(OrderStatus orderStatus){

        return orderInfoRepository.findByOrderStatus(orderStatus);
    }

    public OrderInfo findByOrderNumber(String orderNumber){

        return orderInfoRepository.findByOrderNumber(orderNumber);
    }
}

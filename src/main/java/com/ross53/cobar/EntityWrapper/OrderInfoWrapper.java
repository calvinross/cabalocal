package com.ross53.cobar.EntityWrapper;

import com.ross53.cobar.domain.OrderInfo;

import java.util.ArrayList;
import java.util.List;

public class OrderInfoWrapper {

    private List<OrderInfo> orderinfo = new ArrayList<OrderInfo>();

    public List<OrderInfo> getOrderinfo() {
        return orderinfo;
    }

    public void setOrderinfo(List<OrderInfo> orderinfo) {
        this.orderinfo = orderinfo;
    }
}

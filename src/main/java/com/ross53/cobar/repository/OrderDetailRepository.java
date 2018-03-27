package com.ross53.cobar.repository;

import com.ross53.cobar.domain.OrderDetail;
import com.ross53.cobar.domain.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer>{
}

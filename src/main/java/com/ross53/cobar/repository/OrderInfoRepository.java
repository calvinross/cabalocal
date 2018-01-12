package com.ross53.cobar.repository;

import com.ross53.cobar.domain.OrderInfo;
import com.ross53.cobar.domain.PositionInfo;
import com.ross53.cobar.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderInfoRepository extends JpaRepository<OrderInfo,Integer>{

    @Query("SELECT o FROM OrderInfo o WHERE o.id = :id")
        //public PositionInfo findByTypeNumber(Integer positionType, String positionNumber);
    OrderInfo findByOrderId(@Param("id")Integer id);

    @Query("SELECT o FROM OrderInfo o WHERE o.status = :orderStatus  ORDER BY o.orderNumber ASC")
    List<OrderInfo> findByOrderStatus(@Param("orderStatus")OrderStatus orderStatus);

    @Query("SELECT o FROM OrderInfo o WHERE o.orderNumber = :orderNumber")
    OrderInfo findByOrderNumber(@Param("orderNumber")String orderNumber);
}

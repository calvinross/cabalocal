package com.ross53.cobar.repository;

import com.ross53.cobar.domain.PositionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PositionInfoRepository extends JpaRepository<PositionInfo,Integer> {

    @Query("SELECT psInfo FROM PositionInfo psInfo WHERE psInfo.positionType = :positionType and psInfo.positionNumber= :positionNumber")
    //public PositionInfo findByTypeNumber(Integer positionType, String positionNumber);
    PositionInfo findByTypeNumber(@Param("positionType")Integer positionType, @Param("positionNumber")String PositionNumber);

    @Query("SELECT psInfo FROM PositionInfo psInfo WHERE psInfo.orderNumber =:orderNumber and psInfo.itemId=:itemId")
    PositionInfo findByOrderNumberItemId(@Param("orderNumber")String orderNumber, @Param("itemId")Integer itemId);

    @Query("SELECT o FROM PositionInfo o WHERE o.positionType = :positionType and o.positionStatus = 0 ORDER BY o.positionId ASC")
    List<PositionInfo> findByType(@Param("positionType")Integer positionType);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update position_info pinfo set position_status=0", nativeQuery = true)
    void updatePositionAll();
}

package com.ross53.cobar.service;

import com.ross53.cobar.controller.PositionInfoController;
import com.ross53.cobar.domain.OrderInfo;
import com.ross53.cobar.domain.PositionInfo;
import com.ross53.cobar.repository.PositionInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.Position;
import java.util.List;

@Service
public class PositionInfoService {

    private final static Logger logger = LoggerFactory.getLogger(PositionInfoController.class);

    @Autowired
    PositionInfoRepository repository;

    @Autowired
    private OrderInfoService orderService;


    public List<PositionInfo> getPositionAll(){
        return repository.findAll();
    }

    /**
     * release one position
     * */
    public PositionInfo releasePosition(Integer positionType, String positionNumber){

        logger.info("into service: {}",positionType);

        PositionInfo psInfo = repository.findByTypeNumber(positionType,positionNumber);

        if(psInfo != null) {
            psInfo.setPositionStatus(0);
            repository.save(psInfo);
        }

        return  psInfo;
    }


    /**
     * release all positions
     * */
    public void updatePositionAll(){

        repository.updatePositionAll();

    }

    /**
     * save position
     * */
    public PositionInfo savePosition(PositionInfo positionInfo){
        repository.save(positionInfo);

        return positionInfo;
    }


    /**
     * get current position based on order number and item id from position_info table;
     * this method for complete item api using;
     * */
    public PositionInfo getPositionInfo(String orderNumber,Integer itemId){

        PositionInfo positionInfo = repository.findByOrderNumberItemId(orderNumber,itemId);

        return positionInfo;
    }

    /**
     * assign position for current item, and lock the position until item delivered to buyer
     * for take away order, the position type is 1 means locker, dine in order's position type is 0 means pre-table
     * */
    public PositionInfo getPositionByOrderNumberItemId(String orderNumber, Integer itemId){

        List<PositionInfo> positionInfos;
        PositionInfo positionInfo;
        OrderInfo orderinfo = orderService.findByOrderNumber(orderNumber);

        if(orderinfo == null)
        {
            PositionInfo emptyPosition = new PositionInfo();
            emptyPosition.setPositionId(999);
            emptyPosition.setPositionType(999);
            emptyPosition.setPositionStatus(999);
            emptyPosition.setPositionNumber("");

            return emptyPosition;
        }

        // -1 means take away order;locker;
        if(orderinfo.getTableNumber() == -1){
            positionInfos = repository.findByType(1);
        }else
        {
            positionInfos = repository.findByType(0);
        }

        //return position if there is unused position else return null;
        if (positionInfos.size()>0)
        {
            positionInfo = positionInfos.get(0);
            //lock this position by update status using orderNumber and itemId;
            positionInfo.setPositionStatus(1);
            positionInfo.setOrderNumber(orderNumber);
            positionInfo.setItemId(itemId);
            logger.info(positionInfo.toString());
            repository.save(positionInfo);

        }else
        {
            PositionInfo emptyPosition = new PositionInfo();
            emptyPosition.setPositionId(999);
            emptyPosition.setPositionType(999);
            emptyPosition.setPositionStatus(999);
            emptyPosition.setPositionNumber("");

            positionInfo = emptyPosition;
        }

        return  positionInfo;
    }


}



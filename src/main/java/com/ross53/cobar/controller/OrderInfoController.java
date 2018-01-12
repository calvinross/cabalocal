package com.ross53.cobar.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ross53.cobar.EntityWrapper.OrderInfoWrapper;
import com.ross53.cobar.domain.*;
import com.ross53.cobar.enums.ItemStatus;
import com.ross53.cobar.enums.OrderStatus;
import com.ross53.cobar.exception.SignatureValidateFailedException;
import com.ross53.cobar.service.CompletedItemService;
import com.ross53.cobar.service.OrderDetailService;
import com.ross53.cobar.service.OrderInfoService;
import com.ross53.cobar.service.PositionInfoService;
import com.ross53.cobar.utils.BuildRequestWithValidateHeaderUtil;
import com.ross53.cobar.utils.ResultUtil;
import com.ross53.cobar.utils.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Order")
public class OrderInfoController {

    @Autowired
    OrderInfoService orderInfoService;

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    CompletedItemService completedItemService;

    @Autowired
    PositionInfoService positionInfoService;

    Logger logger = LoggerFactory.getLogger(OrderInfoController.class);

    @Value("${clouduri.itemcompleteuri}")
    private String cloudItemCompleteUrl;


    @GetMapping(value = "/List")
    public ModelAndView getOrderInfoList(Map<String,Object> map){

        List<OrderInfo> orderInfos = orderInfoService.findAll();
        map.put("orderInfo",orderInfos);

        return  new ModelAndView("/orderinfo",map);
    }


    /**
     * this api is for labview to get start order and order by order number asc to processing...
     * */
    @GetMapping(value = "/Doing")
    public Result<OrderInfo> getStartOrderByASC(){

        try {
            List<OrderInfo> orderInfos = orderInfoService.findByOrderStatus(OrderStatus.START);

            if(orderInfos!=null && (orderInfos.size()>0))
            {
                OrderInfo orderInfo = orderInfos.get(0);

                orderInfo.setStatus(OrderStatus.COOKING);

                orderInfoService.SaveOrderInfo(orderInfo);

                return ResultUtil.successful(orderInfo);

            }else{
                throw new Exception("there is no new orders for doing");
            }

        }catch (Exception e){

            return ResultUtil.failled(-1,"get order failed : "+e.getMessage());
        }
    }

    /**
     * this api is get order from cloud and save order as START status
     * **/
    @PostMapping(value = "/postOrderInfo")
    public Result<OrderInfo> postOrderInfo(@RequestBody final String orderInfo,
                                       @RequestHeader(value = "signature") String signature,
                                       @RequestHeader(value="timestamp") String timestamp) throws Exception {

        try{
            System.out.println(orderInfo.toString()+";"+timestamp.toString());
            if(!ValidateUtil.validateSignature(signature,orderInfo.toString()+";"+timestamp.toString())){
                throw new SignatureValidateFailedException(100,"signature validated failed");
            }else {
                ObjectMapper m = new ObjectMapper();
                //m.registerModule(new Hibernate5Module());
                OrderInfo oinfo = m.readValue(orderInfo, OrderInfo.class);
                //orderInfo.setStatus(OrderStatus.START);
                return ResultUtil.successful(orderInfoService.SaveOrderInfo(oinfo));
            }

        }catch (Exception e){

           return ResultUtil.failled(-1,"save order failed : "+e.getMessage());
        }
    }


    @PostMapping(value = "/PostOrder")
    public Result<OrderInfoWrapper> postOrder(@RequestBody final String OrderInfoWrapper,
                                              @RequestHeader(value = "signature") String signature,
                                              @RequestHeader(value="timestamp") String timestamp) throws Exception {

        try{
            if(!ValidateUtil.validateSignature(signature,OrderInfoWrapper.toString()+";"+timestamp.toString())){
                throw new SignatureValidateFailedException(100,"signature validated failed");
            }else {
                ObjectMapper m = new ObjectMapper();
                //m.registerModule(new Hibernate5Module());
                OrderInfoWrapper orderInfoWrapper = m.readValue(OrderInfoWrapper, OrderInfoWrapper.class);
                //orderInfo.setStatus(OrderStatus.START);
                for(OrderInfo info : orderInfoWrapper.getOrderinfo()){
                    orderInfoService.SaveOrderInfo(info);
                }
                return ResultUtil.successful(orderInfoWrapper);
            }

        }catch (Exception e){

            return ResultUtil.failled(-1,"save order failed : "+e.getMessage());
        }
    }



    /**
     * this api is only for test;
     * **/
    @PostMapping(value = "/postTestOrder")
    public Result<OrderInfo> postOrderLocal(@RequestBody OrderInfo orderInfo) throws Exception {

        try{

                orderInfo.setStatus(OrderStatus.START);
                return ResultUtil.successful(orderInfoService.SaveOrderInfo(orderInfo));
            }catch (Exception e){

            return ResultUtil.failled(-1,"save order failed : "+e.getMessage());
        }
    }

    @GetMapping(value = "/getOrder/List")
    public Result<List<OrderInfo>> getOrders(){

        try{

            List<OrderInfo> orderInfo = orderInfoService.findAll();

            return ResultUtil.successful(orderInfo);

        }catch (Exception e){

            return ResultUtil.failled(-1,"get order failed : "+e.getMessage());
        }
    }

    @GetMapping(value = "/getOrder/{orderId}")
    public Result<OrderInfo> getOrderByOrderID(@PathVariable("orderId") Integer orderId){

        try{

            OrderInfo orderInfo = orderInfoService.findByOrderId(orderId);


            return ResultUtil.successful(orderInfo);

        }catch (Exception e){

            return ResultUtil.failled(-1,"get order failed : "+e.getMessage());
        }
    }

    /**
     * this api for labview to return completed item
     * first:  update order_detail and order status
     * second: call cloud api to inform cloud which item was completed and which position
     * */
    @PostMapping(value = "/Complete/{orderNumber}/{itemId}")
    public Result<String> completeOrderFromLabview(@PathVariable("orderNumber") String orderNumber,
                                                  @PathVariable("itemId") Integer itemId) throws Exception {

        //update order and order_detail
        OrderInfo orderInfo = orderInfoService.findByOrderNumber(orderNumber);

        if(orderInfo == null){
            throw new Exception("not found this order,please check your orderNumber");
        }


        OrderDetail od = null;
        boolean orderStatus = true;
        boolean isCorrectItem = false;
        for (OrderDetail d : orderInfo.getOrderDetail()) {
            if (itemId.equals(d.getItemId())) {
                d.setCompleteCount(d.getCompleteCount() + 1);
                if (d.getCompleteCount() >= d.getCount()) {
                    d.setItemStatus(ItemStatus.COMPLETED);
                } else {
                    d.setItemStatus(ItemStatus.COMPLETEPART);
                }
                od = d;
                isCorrectItem = true;
            }
            if (d.getItemStatus()  != ItemStatus.COMPLETED) {
                orderStatus = false;
            }
        }
        if (od != null) {

            orderDetailService.saveOrderDetail(od);
        }
        if (orderStatus) {
            orderInfo.setStatus(OrderStatus.COMPLETE);
            orderInfo.setOrderStatus(true);
            orderInfoService.SaveOrderInfo(orderInfo);
        }

        if (!isCorrectItem) {

            logger.info("There is no such item id: " + itemId + " in this order number :" + orderNumber);
            throw new Exception("not found this item, please check your itemId");
        }

        CompleteItem completeItem = new CompleteItem();
        completeItem.setCompletedQuantity(1);
        completeItem.setItemId(itemId);
        completeItem.setItemStatus(ItemStatus.COMPLETED.getIndex());
        completeItem.setOrderNumber(orderNumber);
        //need to change; set position type based on dine in or take away;
        if(orderInfo.getTableNumber()==-1) {
            completeItem.setPosition(1);
        }else {
            completeItem.setPosition(0);
        }

        PositionInfo positionInfo = positionInfoService.getPositionInfo(orderNumber,itemId);
        completeItem.setPositionNumber(Integer.valueOf(positionInfo.getPositionNumber()));
        completeItem.setOrderId(orderInfo.getId());

        completeItem = completedItemService.saveCompletetedItem(completeItem);

        // TODO: please use cloud URL address instead of below uri variable
        // build HttpHeader and add key: signature and timestamp;
        final String uri = cloudItemCompleteUrl;//"http://brand.fugumobile.cn/q/cobarapi/order/update";

        logger.info("cloudItemCompleteUrl:"+cloudItemCompleteUrl);

        HttpEntity<String> request =
                 BuildRequestWithValidateHeaderUtil.getItemCompleteUpdateRestTemplate(completeItem);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        ResponseEntity<String> resp= restTemplate.postForEntity(uri, request, String.class);

        if (resp.getStatusCode() != HttpStatus.OK) {
            logger.error("requestError: complete response body is:" + resp.getBody());
            return ResultUtil.failled(-1,"Item Complete Update Failed;");
        }

        return ResultUtil.successful("Item Complete updated;");
    }


}

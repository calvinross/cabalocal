package com.ross53.cobar.utils;

import com.ross53.cobar.domain.CompleteItem;
import com.ross53.cobar.enums.ItemStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BuildRequestWithValidateHeaderUtilTest {

    @Test
    public void getItemCompleteUpdateRestTemplate() throws Exception {

        CompleteItem completeItem = new CompleteItem();
        completeItem.setCompletedQuantity(1);
        completeItem.setItemId(2017101010);
        completeItem.setItemStatus(ItemStatus.COMPLETED.getIndex());
        completeItem.setOrderNumber("20170728130506507");
        //need to change; set position type based on dine in or take away;
        completeItem.setPosition(1);
        completeItem.setPositionNumber(10);
        completeItem.setOrderId(6);
        HttpEntity<String> result = BuildRequestWithValidateHeaderUtil.getItemCompleteUpdateRestTemplate(completeItem);

        assertNotNull(result);
    }

}
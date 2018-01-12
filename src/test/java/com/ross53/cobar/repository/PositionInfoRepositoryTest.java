package com.ross53.cobar.repository;

import com.ross53.cobar.domain.PositionInfo;
import com.sun.org.apache.xpath.internal.operations.String;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PositionInfoRepositoryTest {

    private final static Logger logger = LoggerFactory.getLogger(PositionInfoRepositoryTest.class);

    @Autowired
    PositionInfoRepository repository;

    @Test
    public void findOneTest(){
        PositionInfo positionInfo = repository.findOne(1);
        logger.info("PositionInfo Testing...");
        Assert.assertEquals("1",positionInfo.getPositionId().toString());
    }

}
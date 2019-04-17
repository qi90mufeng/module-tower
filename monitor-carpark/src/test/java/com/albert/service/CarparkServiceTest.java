package com.albert.service;

import com.albert.document.dto.carpark.ParkBaseInfoReqDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@PropertySource("classpath:application.properties")
public class CarparkServiceTest {

    @Autowired
    private CarparkService carparkService;

    @Test
    public void initParkType() {
        carparkService.initParkType();
    }

    @Test
    public void saveCarpark(){
        String parkinfo = "{'truck_contract':{'count':0, 'monthAmt':0},'truck_temp':{'count':20, 'hourAmt':10, " +
                "'dailyMax':120},'car_contract':{'count':30, 'monthAmt':150},'car_temp':{'count':100, 'hourAmt':5, 'dailyMax':60}}";

        ParkBaseInfoReqDTO reqDTO = ParkBaseInfoReqDTO.builder()
                .parkNum("HZ-XH-001")
                .parkName("杭州西湖湖滨银泰停车场")
                .address("杭州西湖湖滨银泰")
                .totalNum(1000L)
                .status(1)
                .parksInfo(parkinfo)
                .build();

        carparkService.saveCarpark(reqDTO);
    }

    @Test
    public void queryCarpark(){
        carparkService.queryCarpark("5caddea37ee25251c0668a1e");
    }

    @Test
    public void configCarpark(){
        carparkService.configCarpark("5caddea37ee25251c0668a1e", 1);
    }
}

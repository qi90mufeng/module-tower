package com.albert.controller;

import com.albert.document.UserInfo;
import com.albert.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fujin
 * @version v 0.1
 * @date 2019-01-07
 */
@RestController
public class UserController {

    @Autowired
    private PositionService positionService;

    @PostMapping("initNearBy")
    public String initNearBy(){
        return positionService.initNearBy();
    }


    @PostMapping("clearNearBy")
    public String clearNearBy(){
        return positionService.clearNearBy();
    }

    @GetMapping("findNearByPeople")
    public Page<UserInfo> findNearByPeople(@RequestParam("longitude") String longitude,
                                           @RequestParam("latitude") String latitude,
                                           @RequestParam("distance") double distance,
                                            @RequestParam("pageNum") Integer pageNum,
                                           @RequestParam("pageSize") Integer pageSize){
        return positionService.findNearByPeople(longitude, latitude, distance, pageNum, pageSize);
    }
}

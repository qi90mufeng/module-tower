package com.albert.controller;

import com.albert.repository.primary.PrimaryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author fujin
 * @date 2019/03/07
 * @since 1.0
 */
@RestController
@Api(tags = {"第一数据源-test-api文档"})
public class PrimayController {

    @Autowired
    private PrimaryRepository primaryRepository;

    @ApiOperation(value = "获取数量")
    @PostMapping(value = "/primary/getCount")
    public String getPrimaryByKey(){
        return primaryRepository.count() + "";
    }
}

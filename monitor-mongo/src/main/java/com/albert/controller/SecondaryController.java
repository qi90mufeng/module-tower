package com.albert.controller;

import com.albert.repository.secondary.SecondaryRepository;
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
@Api(tags = {"第二数据源-local-api文档"})
public class SecondaryController {

    @Autowired
    private SecondaryRepository secondaryRepository;

    @ApiOperation(value = "获取数量")
    @PostMapping(value = "/secondary/getCount")
    public String getSecondaryByKey(){
        return secondaryRepository.count() + "";
    }
}

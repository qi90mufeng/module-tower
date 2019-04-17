package com.albert.controller;

import com.albert.document.GoodsInfo;
import com.albert.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fujin
 * @version v 0.1
 * @date 2019-04-17
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsRepository goodsRepository;

    @GetMapping(value = "getData")
    public String getData(){
        Iterable<GoodsInfo> all = goodsRepository.findAll();
        return "" + all;
    }

    @GetMapping("save")
    public String save(){
        GoodsInfo goodsInfo = new GoodsInfo(System.currentTimeMillis(),
                "商品"+System.currentTimeMillis(),"这是一个测试商品");
        goodsRepository.save(goodsInfo);
        return "success";
    }

}

package com.albert.controller;

import com.albert.document.GoodsInfo;
import com.albert.document.UserInfo;
import com.albert.repository.GoodsRepository;
import com.albert.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ////////////////////////////////////////////////////////////////////
 * //                          _ooOoo_                               //
 * //                         o8888888o                              //
 * //                         88" . "88                              //
 * //                         (| ^_^ |)                              //
 * //                         O\  =  /O                              //
 * //                      ____/`---'\____                           //
 * //                    .'  \\|     |//  `.                         //
 * //                   /  \\|||  :  |||//  \                        //
 * //                  /  _||||| -:- |||||-  \                       //
 * //                  |   | \\\  -  /// |   |                       //
 * //                  | \_|  ''\---/''  |   |                       //
 * //                  \  .-\__  `-`  ___/-. /                       //
 * //                ___`. .'  /--.--\  `. . ___                     //
 * //              ."" '<  `.___\_<|>_/___.'  >'"".                  //
 * //            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
 * //            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
 * //      ========`-.____`-.___\_____/___.-`____.-'========         //
 * //                           `=---='                              //
 * //      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
 * //         佛祖保佑       永无BUG     永不修改                      //
 * ////////////////////////////////////////////////////////////////////
 *
 * @author fujin
 * @version v 0.1
 * @date 2019-01-07
 */
@RestController
public class EsController {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private PositionService positionService;

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
                                           @RequestParam("distance") double distance){
        return positionService.findNearByPeople(longitude, latitude, distance);
    }
}

package com.albert.controller;

import com.albert.document.dto.carpark.ParkBaseInfoReqDTO;
import com.albert.document.dto.carpark.ParkBaseInfoRspDTO;
import com.albert.global.BEErrors;
import com.albert.global.BusinessException;
import com.albert.global.ResultBean;
import com.albert.service.CarparkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fujin
 * @date 2019/04/09
 * @since 1.0.0
 */
@RestController(value = "/carkpark")
@Api(tags = {"停车场-test-api文档"})
public class CarparkController {

    @Autowired
    private CarparkService carparkService;

    @ApiOperation(value = "初始化停车位类型")
    @PostMapping(value = "/init/parkType")
    public ResultBean initParkType(){
        try{
            carparkService.initParkType();
            return ResultBean.success();
        }catch (BusinessException e){
            return ResultBean.error(e.getCode(), e.getDesc());
        }catch (Exception e){
            return ResultBean.error(BEErrors.UNKNOW_ERROR);
        }
    }

    @ApiOperation(value = "新增停车位类型")
    @PostMapping(value = "/add/parkType")
    public ResultBean addPparkType(@RequestParam(value = "parkCode") String  parkCode,
                               @RequestParam(value = "parkDesc") String parkDesc){
        try{
            carparkService.addPparkType(parkCode, parkDesc);
            return ResultBean.success();
        }catch (BusinessException e){
            return ResultBean.error(e.getCode(), e.getDesc());
        }catch (Exception e){
            return ResultBean.error(BEErrors.UNKNOW_ERROR);
        }
    }

    @ApiOperation(value = "初始化/编辑停车场信息")
    @PostMapping(value = "/save/carpark")
    public ResultBean saveCarpark(@RequestBody ParkBaseInfoReqDTO parkBaseInfoReqDTO){
        try{
            carparkService.saveCarpark(parkBaseInfoReqDTO);
            return ResultBean.success();
        }catch (BusinessException e){
            return ResultBean.error(e.getCode(), e.getDesc());
        }catch (Exception e){
            return ResultBean.error(BEErrors.UNKNOW_ERROR);
        }
    }

    @ApiOperation(value = "停车场信息展示")
    @PostMapping(value = "/query/carpark")
    public ResultBean<ParkBaseInfoRspDTO> queryCarpark(@RequestParam(value = "id") String  id){
        try{
            return new ResultBean<>(BEErrors.SUCCESS, carparkService.queryCarpark(id));
        }catch (BusinessException e){
            return ResultBean.error(e.getCode(), e.getDesc());
        }catch (Exception e){
            return ResultBean.error(BEErrors.UNKNOW_ERROR);
        }
    }

//    @ApiOperation(value = "停车场信息列表")
//    @PostMapping(value = "/query/carpark/list")
//    public ResultBean<PageInfo<ParkBaseInfoRspDTO>> queryCarparkList(@RequestParam(value = "status") String  status){
//        try{
//            return new ResultBean<>(BEErrors.SUCCESS, carparkService.queryCarparkList(status));
//        }catch (BusinessException e){
//            return ResultBean.error(e.getCode(), e.getDesc());
//        }catch (Exception e){
//            return ResultBean.error(BEErrors.UNKNOW_ERROR);
//        }
//    }

    @ApiOperation(value = "启用/停用停车场信息")
    @PostMapping(value = "/config/carpark")
    public ResultBean configCarpark(@RequestParam(value = "id") String  id,
                                @RequestParam(value = "status") Integer status){
        try{
            carparkService.configCarpark(id, status);
            return ResultBean.success();
        }catch (BusinessException e){
            return ResultBean.error(e.getCode(), e.getDesc());
        }catch (Exception e){
            return ResultBean.error(BEErrors.UNKNOW_ERROR);
        }
    }

    @ApiOperation(value = "驶入车位")
    @PostMapping(value = "/offer")
    public String offerCarpark(@RequestParam(value = "plateNumber") String  plateNumber,
                               @RequestParam(value = "lotType") Integer lotType){
        return carparkService.offerCarpark(plateNumber, lotType);
    }

    @ApiOperation(value = "驶离车位，计算费用")
    @PostMapping(value = "/poll")
    public String pollCarpark(@RequestParam(value = "plateNumber") String  plateNumber){
        return carparkService.pollCarpark(plateNumber);
    }

    @ApiOperation(value = "车位实时剩余统计")
    @PostMapping(value = "/free/park/statis")
    public String freeParkStatis(){
        return carparkService.freeParkStatis();
    }
}

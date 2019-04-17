package com.albert.service;

import com.albert.constant.CarparkTypeEnum;
import com.albert.document.carpark.ParkLotTypePo;
import com.albert.document.carpark.ParkBaseInfoPo;
import com.albert.document.dto.carpark.ParkBaseInfoReqDTO;
import com.albert.document.dto.carpark.ParkBaseInfoRspDTO;
import com.albert.global.BEErrors;
import com.albert.global.BusinessException;
import com.albert.repository.CarparkTypeRepository;
import com.albert.repository.ParkBaseInfoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarparkService {

    //停车场基本信息(序号、停车场名称、车位总数量、车位剩余数量)
    private static List parkBasInfoList;

    //车位数量信息(序号、车位类型、总数量、剩余数量)
    private static String parkSpaceAmount;

    //车位类型信息 (序号、车位类型、价格)
    private static String parkSpaceTypeInfo;

    //停车记录 （序号、车位类型、驶入时间、驶出时间、停车总费用 )
    private static String parkDetailInfo;

    public String offerCarpark(String plateNumber, Integer lotType) {
        //新增停车记录

        return null;
    }

    public String pollCarpark(String plateNumber) {
        //驶离车位

        return null;
    }

    public String freeParkStatis() {
        //实时剩余车位统计
        return null;
    }

    public void dailyPriceStatis(){
        //当日累计收入

    }
    @Autowired
    private CarparkTypeRepository carparkTypeRepository;
    @Autowired
    private ParkBaseInfoRepository parkBaseInfoRepository;

    /**
     * 初始化停车位类型
     */
    public void initParkType() {
        List<ParkLotTypePo> pos = new ArrayList<>();
        for (CarparkTypeEnum temp: CarparkTypeEnum.values()) {
            ParkLotTypePo carparkTypePo = new ParkLotTypePo();
            carparkTypePo.setCode(temp.getCode());
            carparkTypePo.setDesc(temp.getDesc());
            pos.add(carparkTypePo);
        }
        carparkTypeRepository.insert(pos);
    }

    /**
     * 新增停车位类型
     */
    public void addPparkType(String parkCode, String parkDesc) {
        ParkLotTypePo carparkTypePo = new ParkLotTypePo();
        carparkTypePo.setCode(parkCode);
        carparkTypePo.setDesc(parkDesc);
        carparkTypeRepository.insert(carparkTypePo);
    }

    /**
     * 初始化/编辑停车场信息
     */
    public void saveCarpark(ParkBaseInfoReqDTO parkBaseInfoReqDTO) {
        //校验数据，判断能否插入
        checkIfCanInsertOrUpdate(parkBaseInfoReqDTO);
        //转换数据
        ParkBaseInfoPo parkBaseInfoPo = ParkBaseInfoPo.builder()
                .id(parkBaseInfoReqDTO.getId())
                .parkNum(parkBaseInfoReqDTO.getParkNum())
                .parkName(parkBaseInfoReqDTO.getParkName())
                .address(parkBaseInfoReqDTO.getAddress())
                .totalNum(parkBaseInfoReqDTO.getTotalNum())
                .status(parkBaseInfoReqDTO.getStatus())
                .parksInfo(parkBaseInfoReqDTO.getParksInfo())
                .build();
        if (StringUtils.hasText(parkBaseInfoPo.getId())){
            Optional<ParkBaseInfoPo> pb = parkBaseInfoRepository.findById(parkBaseInfoPo.getId());
            if(pb.isPresent()){
                //更新数据
                parkBaseInfoRepository.save(parkBaseInfoPo);
                return;
            }
            throw new BusinessException(BEErrors.NO_CARPARK_DATA);
        }else{
            //插入数据
            parkBaseInfoRepository.insert(parkBaseInfoPo);
        }
    }

    /**
     * 停车场信息展示
     */
    public ParkBaseInfoRspDTO queryCarpark(String id) {
        Optional<ParkBaseInfoPo> parkBaseInfo = parkBaseInfoRepository.findById(id);
        parkBaseInfo.ifPresent(p -> ParkBaseInfoRspDTO.builder()
                .parkNum(p.getParkNum())
                .parkName(p.getParkName())
                .address(p.getAddress())
                .totalNum(p.getTotalNum())
                .status(p.getStatus())
                .parksInfo(p.getParksInfo())
                .build());
        return null;
    }
    
    /**
     * 启用/停用停车场信息
     */
    public void configCarpark(String id, Integer status) {
        Optional<ParkBaseInfoPo> pb = parkBaseInfoRepository.findById(id);
        if (pb.isPresent()){
            //更新数据
            ParkBaseInfoPo parkBaseInfoPo = new ParkBaseInfoPo();
            BeanUtils.copyProperties(pb, parkBaseInfoPo);
            parkBaseInfoPo.setStatus(status);
            parkBaseInfoRepository.save(parkBaseInfoPo);
            return;
        }
        throw new BusinessException(BEErrors.NO_CARPARK_DATA);
    }



    //--------------------------------------private method--------------------------------------
    private void checkIfCanInsertOrUpdate(ParkBaseInfoReqDTO parkBaseInfoReqDTO) {
        //todo 暂时不做判断
    }


}

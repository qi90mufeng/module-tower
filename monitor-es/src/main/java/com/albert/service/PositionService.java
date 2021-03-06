package com.albert.service;

import com.albert.document.UserInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PositionService {

    /**
     * 查找附近的人
     * @param longitude 经度
     * @param latitude 纬度
     * @param distance 距离
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return
     */
    Page<UserInfo> findNearByPeople(String longitude, String latitude, double distance, Integer pageNum, Integer pageSize);

    /**
     * 初始化附近人信息
     * @return
     */
    String initNearBy();

    /**
     * 清除附近人信息
     * @return
     */
    String clearNearBy();

    /**
     * 高亮搜索用户
     * @param userName
     * @return
     */
    List<UserInfo> searchHighlightOfNearBy(String userName);
}

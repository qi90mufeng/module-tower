package com.albert.service.impl;

import com.albert.data.ChineseNameData;
import com.albert.document.UserInfo;
import com.albert.repository.UserRepository;
import com.albert.service.PositionService;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {

    private static final String PERSON_INDEX_NAME = "testgoods";
    private static final String PERSON_INDEX_TYPE = "userPosition";
    private static final Integer INIT_COUNT = 100000;
    private static final double lat = 39.929986;
    private static final double lon = 116.395645;

    @Autowired
    private TransportClient client;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<UserInfo> findNearByPeople(String longitude, String latitude, double distance, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);

        GeoDistanceQueryBuilder builder =
                QueryBuilders.geoDistanceQuery("location")//查询字段
                        .point(lat, lon)//设置经纬度
                        .distance(distance, DistanceUnit.METERS)//设置距离和单位（米）
                        .geoDistance(GeoDistance.ARC);
        GeoDistanceSortBuilder sortBuilder =
                SortBuilders.geoDistanceSort("location",lat,lon)
                        .point(lat, lon)
                        .unit(DistanceUnit.METERS)
                        .order(SortOrder.ASC);//排序方式
        //构造查询条件
        NativeSearchQueryBuilder nativeSearchQueryBuilder =
                new NativeSearchQueryBuilder()
                        .withFilter(builder)
                        .withSort(sortBuilder)
                        .withPageable(pageable);

        Page<UserInfo> page =
                elasticsearchTemplate.queryForPage(
                        nativeSearchQueryBuilder.build(), UserInfo.class);
        return page;
    }

    @Override
    public String initNearBy() {
        List<UserInfo> list = new ArrayList<>();
        for (int i = 0; i < INIT_COUNT; i++){
            UserInfo info = new UserInfo();
            info.setId((long)i);
            ChineseNameData.getChineseName(info);
            ChineseNameData.setLocation(info,lon,lat);
            //client.prepareIndex("testgoods", "userPosition").setSource(info);
            list.add(info);
            //userRepository.save(info);
        }
        userRepository.saveAll(list);
        return "success";
    }

    @Override
    public String clearNearBy() {
        userRepository.deleteAll();
        return "success";
    }

}

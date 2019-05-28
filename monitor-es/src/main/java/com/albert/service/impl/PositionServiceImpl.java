package com.albert.service.impl;

import com.albert.data.ChineseNameData;
import com.albert.document.UserInfo;
import com.albert.repository.UserRepository;
import com.albert.service.PositionService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PositionServiceImpl implements PositionService {

    private static final String PERSON_INDEX_NAME = "testgoods";
    private static final String PERSON_INDEX_TYPE = "userPosition";
    private static final Integer INIT_COUNT = 100000;
    private static final double lat = 39.929986;
    private static final double lon = 116.395645;

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
            ChineseNameData.getUserAge(info);
            ChineseNameData.setLocation(info,lon,lat);
            //client.prepareIndex("testgoods", "userPosition").setSource(info);
            ChineseNameData.getAddress(info);
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

    @Override
    public List<UserInfo> searchHighlightOfNearBy(String userName){
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //高亮显示规则
        highlightBuilder.preTags("<span style='color:green'>");
        highlightBuilder.postTags("</span>");
        //指定高亮字段
        highlightBuilder.field("userName");
        //查询用户
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        QueryBuilder queryBuilder2 = QueryBuilders.matchPhraseQuery("userName", userName);
        boolQueryBuilder.must(queryBuilder2);

        //将距离存入sort中
        GeoDistanceSortBuilder sort = SortBuilders.geoDistanceSort("location", Double.valueOf(lat) , Double.valueOf(lon))
                .unit(DistanceUnit.KILOMETERS).order(SortOrder.ASC);

        SearchResponse response = elasticsearchTemplate.getClient().prepareSearch("testuser")
                .setQuery(boolQueryBuilder)
                .highlighter(highlightBuilder)
                .addSort(sort)
                .setFrom(0)
                .setSize(50)
                .execute().actionGet();
        SearchHits searchHits = response.getHits();
        //TODO 暂未加入日志框架，先使用sout
        System.out.println("记录数-->" + searchHits.getTotalHits());

        List<UserInfo> list = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            UserInfo entity = new UserInfo();
            Map<String, Object> entityMap = hit.getSourceAsMap();
            System.out.println(hit.getHighlightFields());
            //高亮字段
            if (!StringUtils.isEmpty(hit.getHighlightFields().get("userName"))) {
                Text[] text = hit.getHighlightFields().get("userName").getFragments();
                entity.setUserName(text[0].toString());
            }
            if (!CollectionUtils.isEmpty(entityMap)) {
                //高亮字段
                if (StringUtils.isEmpty(entity.getUserName()) && !StringUtils.isEmpty(entityMap.get("userName"))) {
                    entity.setUserName(String.valueOf(entityMap.get("userName")));
                }
                //普通字段
                if (!StringUtils.isEmpty(entityMap.get("id"))) {
                    entity.setId(Long.valueOf(String.valueOf(entityMap.get("id"))));
                }
                if (!StringUtils.isEmpty(entityMap.get("sex"))) {
                    entity.setSex(String.valueOf(entityMap.get("sex")));
                }
                if (!StringUtils.isEmpty(entityMap.get("age"))) {
                    entity.setAge(Integer.valueOf(String.valueOf(entityMap.get("age"))));
                }
                if (!StringUtils.isEmpty(entityMap.get("location"))) {
                    //获取距离值，并保留两位小数点  geoDis就是距离，距离存储在sortvalues中，距离单位由sort中的uni决定
                    //BigDecimal geoDis = new BigDecimal((Double) hit.getSortValues()[0]).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                    entity.setLocation(String.valueOf(entityMap.get("location")));
                }
            }
            list.add(entity);
        }
       return list;
    }
}

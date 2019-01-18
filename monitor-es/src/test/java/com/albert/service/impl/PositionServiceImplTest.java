package com.albert.service.impl;

import com.albert.data.ChineseNameData;
import com.albert.document.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@PropertySource("classpath:application.properties")
public class PositionServiceImplTest {
    private static final double lat = 39.929986;
    private static final double lon = 116.395645;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void template(){
        UserInfo info = new UserInfo();
        ChineseNameData.getChineseName(info);
        ChineseNameData.setLocation(info,lon, lat );
        elasticsearchTemplate.putMapping(UserInfo.class,info);
    }
}

package com.albert.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

/**
 * 现在不加type，生成时，自动加type为类名，userinfo
 *
 * 不仅限于java添加mapping和setting，可以用es操作
 */
@Document(indexName = "testuser")
@Mapping(mappingPath = "/es/userinfo-mapping.json")
@Setting(settingPath = "/es/userinfo-setting.json")
public class UserInfo {
    //自定义分析器
    private static final String insert = "ik_pinyin_analyzer";
    private static final String search = "ik_pinyin_analyzer_for_search";
    @Id
    private Long id;
    //用户名 如果要搜索出的结果尽可能全，可以使用ik_max_word，如果需要结果尽可能精确，可以使用ik_smart
    @Field(type = FieldType.Text, analyzer = insert, searchAnalyzer = search)
    private String userName;
    //性别
    private String sex;
    //年龄
    private Integer age;
    /**
      * 地理位置经纬度
      * lat纬度，lon经度 "40.715,-74.011"
      * 如果用数组则相反[-73.983, 40.719]
      */
    @GeoPointField
    private String location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

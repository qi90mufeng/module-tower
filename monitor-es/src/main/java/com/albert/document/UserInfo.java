package com.albert.document;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

@Document(indexName = "testuser",type = "userPosition")
public class UserInfo {
    private Long id;
    //用户名
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

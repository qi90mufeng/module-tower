package com.albert.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


/**
 * 现在不加type，生成时，自动加type为类名，即esregion
 */
@Document(indexName = "indexregion")
public class EsRegion {

    @Id
    private Integer id;
    @Field(type = FieldType.Text, analyzer = "ik_pinyin_analyzer", searchAnalyzer = "ik_pinyin_analyzer")
    private String cityName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}

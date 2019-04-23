## 版本匹配
| 项目     | spring boot   | elasticsearch |
| -------- | ------------- | ------------- |
| 当前版本 | 2.0.4.RELEASE | 5.6.10        |
| 版本     | 2.0.6.RELEASE | 5.6.12        |
|          |               |               |



## 数据结构

```
org.springframework.boot
```



## elasticsearch学习

### 官网网址 5.6版本

> https://www.elastic.co/guide/en/elasticsearch/reference/5.6/mapping.html

### 基本概念

1、单个 Elasticsearch 实例称为一个节点（node），一组节点构成一个集群（cluster）。

2、index

> 索引

3、document

> Index 里面单条的记录称为 Document（文档）

4、field

> https://www.elastic.co/guide/en/elasticsearch/reference/5.6/mapping-types.html



不推荐使用type，原因：

1、



1、数据结构

```JAVA
public enum FieldType {
    Text,
    Integer,
    Long,
    Date,
    Float,
    Double,
    Boolean,
    Object,
    Auto,
    Nested,
    Ip,
    Attachment,
    Keyword;

    private FieldType() {
    }
}
```

2、事务



#### 3、分词器

> 安装ik分词器
>
> cmd   D:\Program Files\program\elasticsearch-5.6.10 下执行
>
>  	./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v5.6.10/elasticsearch-analysis-ik-5.6.10.zip
>
> 安装拼音搜索
>
> ​       ./bin/elasticsearch-plugin install <https://github.com/medcl/elasticsearch-analysis-pinyin/releases/download/v5.6.10/elasticsearch-analysis-pinyin-5.6.10.zip>

##### 3.1、自定义分词器

> 在6.0版本之前, 添加新的分词器之前, 必须先关闭索引, 添加完成后, 要及时打开索引进行搜索等操作, 而6.0版本后无需此关闭/打开.
>
> 注意 my_pinyin 中的参数设置，会产生不同分词效果
>
> GET indexregion/_mapping
>
>
> POST indexregion/_close
>
> POST indexregion/_open
>
> PUT /indexregion
> {
> ​    "index": {
> ​        "analysis": {
> ​            "analyzer": {
> ​                "ik_pinyin_analyzer": {
> ​                    "type": "custom",
> ​                    "tokenizer": "ik_smart",
> ​                    "filter": ["my_pinyin", "word_delimiter"]
> ​                }
> ​            },
> ​            "filter": {
> ​                "my_pinyin": {
> ​                    "type": "pinyin",
> ​                    "keep_first_letter": true,
> ​                    "keep_separate_first_letter" : true,
> ​                    "keep_full_pinyin" : true
> ​                }
> ​            }
> ​        }
> ​    }
> }

```json
通过项目 regionRepository.saveAll(collect)生成数据后，查看mapping

GET indexregion/_mapping

{
  "indexregion": {
    "mappings": {
      "esregion": {
        "properties": {
          "cityName": {
            "type": "text",
            "analyzer": "ik_pinyin_analyzer"
          },
          "id": {
            "type": "long"
          }
        }
      }
    }
  }
}
```



```java
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
```

>_id相同，重复插入数据，会更新es



##### 3.2、ES的默认分词器



单集群配置



多集群配置



## 计划


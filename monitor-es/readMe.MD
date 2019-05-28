[TOC]

# 版本匹配

| 项目 | spring boot   | elasticsearch | lucene版本 |
| ---- | ------------- | ------------- | ---------- |
| 版本 | 2.1.5.RELEASE | 6.4.3         | 7.4.0      |
| 版本 | 2.0.6.RELEASE | 5.6.12        |            |
| 版本 | 2.0.4.RELEASE | 5.6.10        |            |



# 官网网址 5.6版本、 6.4 版本

> github源码
>
> https://github.com/elastic/elasticsearch
>
>
>
> 官网资料区
>
> https://www.elastic.co/guide/en/elasticsearch/reference/5.6/mapping.html
>
> https://www.elastic.co/guide/en/elasticsearch/reference/6.4/mapping.html
>
>
>
> Java query dsl 
>
> https://www.elastic.co/guide/en/elasticsearch/client/java-api/6.4/index.html
>
>
>
> es 社区
>
> https://elasticsearch.cn/

## 一、特点及使用场景

### 1、特点

全文检索

结构化检索

数据统计

分析

接近实时处理

分布式搜索(可部署数百台服务器)，处理PB级别的数据

搜索纠错，自动完成

### 2、使用场景

日志搜索

数据聚合

数据监控

报表统计分析



## 二、配置安装

> 以下安装版本：5.6.10
>
> 6.4.3 安装一致

### 1、下载

> elastic官网下载elasticsearch-5.6.10.zip

### 2、解压zip

### 3、更改elasticsearch.yml

```ym
# ======================== Elasticsearch Configuration =========================
#
# NOTE: Elasticsearch comes with reasonable defaults for most settings.
#       Before you set out to tweak and tune the configuration, make sure you
#       understand what are you trying to accomplish and the consequences.
#
# The primary way of configuring a node is via this file. This template lists
# the most important settings you may want to configure for a production cluster.
#
# Please consult the documentation for further information on configuration options:
# https://www.elastic.co/guide/en/elasticsearch/reference/index.html
#
# ---------------------------------- Cluster -----------------------------------
#
# Use a descriptive name for your cluster:
#
cluster.name: first-cluster
#
# ------------------------------------ Node ------------------------------------
#
# Use a descriptive name for the node:
#
node.name: first-node
#
# Add custom attributes to the node:
#
#node.attr.rack: r1
#
# ----------------------------------- Paths ------------------------------------
#
# Path to directory where to store the data (separate multiple locations by comma):
#
#path.data: /path/to/data
#
# Path to log files:
#
#path.logs: /path/to/logs
#
# ----------------------------------- Memory -----------------------------------
#
# Lock the memory on startup:
#
#bootstrap.memory_lock: true
#
# Make sure that the heap size is set to about half the memory available
# on the system and that the owner of the process is allowed to use this
# limit.
#
# Elasticsearch performs poorly when the system is swapping the memory.
#
# ---------------------------------- Network -----------------------------------
#
# Set the bind address to a specific IP (IPv4 or IPv6):
#
network.host: 127.0.0.1
#
# Set a custom port for HTTP:
#
http.port: 9200
#
# For more information, consult the network module documentation.
#
# --------------------------------- Discovery ----------------------------------
#
# Pass an initial list of hosts to perform discovery when new node is started:
# The default list of hosts is ["127.0.0.1", "[::1]"]
#
#discovery.zen.ping.unicast.hosts: ["host1", "host2"]
#
# Prevent the "split brain" by configuring the majority of nodes (total number of master-eligible nodes / 2 + 1):
#
#discovery.zen.minimum_master_nodes: 3
#
# For more information, consult the zen discovery module documentation.
#
# ---------------------------------- Gateway -----------------------------------
#
# Block initial recovery after a full cluster restart until N nodes are started:
#
#gateway.recover_after_nodes: 3
#
# For more information, consult the gateway module documentation.
#
# ---------------------------------- Various -----------------------------------
#
# Require explicit names when deleting indices:
#
#action.destructive_requires_name: true
```

### 4、运行

​	es的bin目录下，打开cmd，执行elasticsearch.bat

### 5、检测是否启动成功

```
http://127.0.0.1:9200
```

```json
{
  "name" : "first-node",
  "cluster_name" : "first-cluster",
  "cluster_uuid" : "_na_",
  "version" : {
    "number" : "5.6.10",
    "build_hash" : "b727a60",
    "build_date" : "2018-06-06T15:48:34.860Z",
    "build_snapshot" : false,
    "lucene_version" : "6.6.1"
  },
  "tagline" : "You Know, for Search"
}
```

### 6、可视化工具-kibana

> elastic官网下载es对应版本的kibana
>
> 免安装解压
>
> 修改kibana.yml
>
> 验证：127.0.0.1:5601

```
# Kibana is served by a back end server. This setting specifies the port to use.
server.port: 5601

# Specifies the address to which the Kibana server will bind. IP addresses and host names are both valid values.
# The default is 'localhost', which usually means remote machines will not be able to connect.
# To allow connections from remote users, set this parameter to a non-loopback address.
server.host: "localhost"

# Enables you to specify a path to mount Kibana at if you are running behind a proxy.
# Use the `server.rewriteBasePath` setting to tell Kibana if it should remove the basePath
# from requests it receives, and to prevent a deprecation warning at startup.
# This setting cannot end in a slash.
#server.basePath: ""

# Specifies whether Kibana should rewrite requests that are prefixed with
# `server.basePath` or require that they are rewritten by your reverse proxy.
# This setting was effectively always `false` before Kibana 6.3 and will
# default to `true` starting in Kibana 7.0.
#server.rewriteBasePath: false

# The maximum payload size in bytes for incoming server requests.
#server.maxPayloadBytes: 1048576

# The Kibana server's name.  This is used for display purposes.
#server.name: "your-hostname"

# The URL of the Elasticsearch instance to use for all your queries.
elasticsearch.url: "http://localhost:9200"

# When this setting's value is true Kibana uses the hostname specified in the server.host
# setting. When the value of this setting is false, Kibana uses the hostname of the host
# that connects to this Kibana instance.
#elasticsearch.preserveHost: true

# Kibana uses an index in Elasticsearch to store saved searches, visualizations and
# dashboards. Kibana creates a new index if the index doesn't already exist.
#kibana.index: ".kibana"

# The default application to load.
#kibana.defaultAppId: "home"

# If your Elasticsearch is protected with basic authentication, these settings provide
# the username and password that the Kibana server uses to perform maintenance on the Kibana
# index at startup. Your Kibana users still need to authenticate with Elasticsearch, which
# is proxied through the Kibana server.
#elasticsearch.username: "user"
#elasticsearch.password: "pass"

# Enables SSL and paths to the PEM-format SSL certificate and SSL key files, respectively.
# These settings enable SSL for outgoing requests from the Kibana server to the browser.
#server.ssl.enabled: false
#server.ssl.certificate: /path/to/your/server.crt
#server.ssl.key: /path/to/your/server.key

# Optional settings that provide the paths to the PEM-format SSL certificate and key files.
# These files validate that your Elasticsearch backend uses the same key files.
#elasticsearch.ssl.certificate: /path/to/your/client.crt
#elasticsearch.ssl.key: /path/to/your/client.key

# Optional setting that enables you to specify a path to the PEM file for the certificate
# authority for your Elasticsearch instance.
#elasticsearch.ssl.certificateAuthorities: [ "/path/to/your/CA.pem" ]

# To disregard the validity of SSL certificates, change this setting's value to 'none'.
#elasticsearch.ssl.verificationMode: full

# Time in milliseconds to wait for Elasticsearch to respond to pings. Defaults to the value of
# the elasticsearch.requestTimeout setting.
#elasticsearch.pingTimeout: 1500

# Time in milliseconds to wait for responses from the back end or Elasticsearch. This value
# must be a positive integer.
#elasticsearch.requestTimeout: 30000

# List of Kibana client-side headers to send to Elasticsearch. To send *no* client-side
# headers, set this value to [] (an empty list).
#elasticsearch.requestHeadersWhitelist: [ authorization ]

# Header names and values that are sent to Elasticsearch. Any custom headers cannot be overwritten
# by client-side headers, regardless of the elasticsearch.requestHeadersWhitelist configuration.
#elasticsearch.customHeaders: {}

# Time in milliseconds for Elasticsearch to wait for responses from shards. Set to 0 to disable.
#elasticsearch.shardTimeout: 30000

# Time in milliseconds to wait for Elasticsearch at Kibana startup before retrying.
#elasticsearch.startupTimeout: 5000

# Logs queries sent to Elasticsearch. Requires logging.verbose set to true.
#elasticsearch.logQueries: false

# Specifies the path where Kibana creates the process ID file.
#pid.file: /var/run/kibana.pid

# Enables you specify a file where Kibana stores log output.
#logging.dest: stdout

# Set the value of this setting to true to suppress all logging output.
#logging.silent: false

# Set the value of this setting to true to suppress all logging output other than error messages.
#logging.quiet: false

# Set the value of this setting to true to log all events, including system usage information
# and all requests.
#logging.verbose: false

# Set the interval in milliseconds to sample system and process performance
# metrics. Minimum is 100ms. Defaults to 5000.
#ops.interval: 5000

# The default locale. This locale can be used in certain circumstances to substitute any missing
# translations.
#i18n.defaultLocale: "en"
```



## 三、基本概念

### 0、基本概念

1、单个 Elasticsearch 实例称为一个节点（node），一组节点构成一个集群（cluster）。

2、index

> 索引

3、document

> Index 里面单条的记录称为 Document（文档）

4、field

> https://www.elastic.co/guide/en/elasticsearch/reference/5.6/mapping-types.html

5、倒排索引

>倒排索引原理

6、term

> 被索引的精确值(术语)

7、type

> 不推荐使用type，原因    [点击跳转](#jump) 
>
> https://github.com/elastic/elasticsearch/pull/24317    Only allow one type on 6.0 indices

8、mappings

>

9、settings

>

10、群集概念

> 分片（shard）：数据拆分后的各个部分
>
> 副本（replica）：每个分片的复制

11、结果结构分析

> took：耗费了几毫秒
> timed_out：是否超时  true为超时  false为未超时
> _shards：数据拆成了5个分片，所以对于搜索请求，会打到所有的primary shard（或者是它的某个replica shard也可以）
> hits.total：查询结果的数量，1个document
> hits.max_score：score的含义，就是document对于一个search的相关度的匹配分数
> hits.hits：包含了匹配搜索的document的详细数据	
>
> _type

```json
{
  "took": 1,
  "timed_out": false,
  "_shards": {
    "total": 5,
    "successful": 5,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": 1,
    "max_score": 11.534002,
    "hits": [
      {
        "_index": "testuser",
        "_type": "userinfo",
        "_id": "31787",
        "_score": 11.534002,
        "_source": {
          "id": 31787,
          "userName": "李霭",
          "userAddr": "陕西西安",
          "sex": "女",
          "age": 11,
          "location": "40.088231,116.55389"
        },
        "highlight": {
          "userName": [
            "<em>李</em><em>霭</em>"
          ]
        }
      }
    ]
  }
}
```



### 1、数据结构

* 5.6.10   6.4.3

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

```
text : 
	被分词的，整个字符串根据一定规则分解成一个个小写的term
	
keyword:
	不被分词
	
字符串 put到es后，默认为text

默认分词器： standard analyzer

分析过程：
full_text: 指定类型为text，是会被分词
exact_value: 指定类型为keyword，不会被分词
full_text： 会被standard analyzer分词为如下terms [quick,foxes],存入倒排索引
exact_value： 只有[Quick Foxes!]这一个term会被存入倒排索引
```



### 2、分析器（analyzer）

> https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis.html
>
> https://www.elastic.co/guide/en/elasticsearch/reference/6.4/analysis.html

#### 2.1、三大构件

> Character filters  ->  Tokenizer   ->  Token filters 

##### 2.1.1、Character filters

> （字符过滤器）
>
> 一个分析器可能有0个或多个字符过滤器，它们按顺序应用
>
> 文本字符流 -> 字符流

| character filter            | logical name    | description                |                                                              |
| --------------------------- | --------------- | -------------------------- | ------------------------------------------------------------ |
| mapping char filter         | mapping         | 根据配置的映射关系替换字符 |                                                              |
| html strip char filter      | html_strip      | 去掉HTML元素               | ```"char_filter":{"my_char_filter": {"type": "html_strip","escaped_tags": ["b"] }} |
| pattern replace char filter | pattern_replace | 用正则表达式处理字符串     |                                                              |

##### 2.1.2、Tokenizer

> （分词器）
>
>一个分析器有且只能有一个分词器
>
>字符流 -> token流

| tokenizer                | logical name   | description                             |                                 |
| ------------------------ | -------------- | --------------------------------------- | ------------------------------- |
| standard tokenizer       | standard       |                                         | max_token_length                |
| edge ngram tokenizer     | edgeNGram      |                                         |                                 |
| keyword tokenizer        | keyword        | 不分词                                  |                                 |
| letter tokenizer         | letter         | 按单词分                                |                                 |
| lowercase tokenizer      | lowercase      | letter tokenizer, lower case filter     |                                 |
| ngram tokenizer          | ngram          |                                         | min_gram  max_gram  token_chars |
| edge ngram tokenizer     | edge_ngram     |                                         |                                 |
| whitespace tokenizer     | whitespace     | 以空格为分隔符拆分                      |                                 |
| pattern tokenizer        | pattern        | 定义分隔符的正则表达式                  |                                 |
| uax url email tokenizer  | uax_url_email  | 不拆分url和email                        | max_token_length                |
| path hierarchy tokenizer | path_hierarchy | 处理类似`/path/to/somthing`样式的字符串 |                                 |
| classic Tokenizer        | classic        |                                         | max_token_length                |

##### 2.1.3、Token filters 

> （token过滤器）
>
> 一个分析器可能有0个或多个token过滤器，它们按顺序应用
>
> token流 -> token流

| token filter                 | logical name                                      | description                                                  | demo                                                         |
| ---------------------------- | :------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| standard filter              | standard                                          |                                                              |                                                              |
| ascii folding filter         | asciifolding                                      |                                                              |                                                              |
| length filter                | length                                            | 过滤掉长度大于8和小于2的分词                                 | ```"filter": {"my_length":{"type":"length","max":8,"min":2}} |
| lowercase filter             | lowercase                                         | 转成小写                                                     |                                                              |
| uppercase filter             | uppercase                                         | 转成大写                                                     |                                                              |
| ngram filter                 | nGram                                             |                                                              |                                                              |
| edge ngram filter            | edgeNGram                                         |                                                              |                                                              |
| porter stem filter           | porter_stem                                       | 波特词干算法                                                 |                                                              |
| shingle filter               | shingle                                           | 定义分隔符的正则表达式                                       |                                                              |
| stop filter                  | stop                                              |                                                              |                                                              |
| word delimiter filter        | word_delimiter                                    | 将一个单词再拆成子分词                                       |                                                              |
| word  graph delimiter filter | word_delimiter_graph                              |                                                              |                                                              |
| stemmer token filter         | stemmer                                           |                                                              |                                                              |
| stemmer override filter      | stemmer_override                                  |                                                              |                                                              |
| keyword marker filter        | keyword_marker                                    |                                                              |                                                              |
| keyword repeat filter        | keyword_repeat                                    |                                                              |                                                              |
| kstem filter                 | kstem                                             |                                                              |                                                              |
| snowball filter              | snowball                                          |                                                              |                                                              |
| phonetic filter              | phonetic                                          | [插件](https://github.com/elasticsearch/elasticsearch-analysis-phonetic) |                                                              |
| synonym filter               | synonyms                                          | 处理同义词                                                   |                                                              |
| synonym graph filter         | synonym_graph                                     |                                                              |                                                              |
| compound word filter         | dictionary_decompounder, hyphenation_decompounder | 分解复合词                                                   |                                                              |
| reverse filter               | reverse                                           | 反转字符串                                                   |                                                              |
| elision filter               | elision                                           | 去掉缩略语                                                   |                                                              |
| truncate filter              | truncate                                          | 截断字符串                                                   |                                                              |
| unique filter                | unique                                            |                                                              |                                                              |
| pattern capture filter       | pattern_capture                                   |                                                              |                                                              |
| pattern replace filte        | pattern_replace                                   | 用正则表达式替换                                             |                                                              |
| trim filter                  | trim                                              | 去掉空格                                                     |                                                              |
| limit token count filter     | limit                                             | 限制token数量                                                |                                                              |
| hunspell filter              | hunspell                                          | 拼写检查                                                     |                                                              |
| common grams filter          | common_grams                                      |                                                              |                                                              |
| normalization filter         | arabic_normalization, persian_normalization       |                                                              |                                                              |

#### 2.2、内置分词器(TODO)

| analyzer           | logical name                                                 | description                                                  |
| ------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| standard analyzer  | standard                                                     | standard tokenizer, standard filter, lower case filter, stop filter |
| simple analyzer    | simple                                                       | lower case tokenizer                                         |
| stop analyzer      | stop                                                         | lower case tokenizer, stop filter                            |
| keyword analyzer   | keyword                                                      | 不分词，内容整体作为一个token(not_analyzed)                  |
| pattern analyzer   | whitespace                                                   | 正则表达式分词，默认匹配\W+                                  |
| language analyzers | [lang](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html) | 各种语言:[`arabic`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#arabic-analyzer), [`armenian`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#armenian-analyzer), [`basque`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#basque-analyzer), [`brazilian`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#brazilian-analyzer), [`bulgarian`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#bulgarian-analyzer), [`catalan`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#catalan-analyzer), [`cjk`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#cjk-analyzer), [`czech`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#czech-analyzer), [`danish`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#danish-analyzer), [`dutch`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#dutch-analyzer), [`english`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#english-analyzer),[`finnish`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#finnish-analyzer), [`french`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#french-analyzer), [`galician`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#galician-analyzer), [`german`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#german-analyzer), [`greek`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#greek-analyzer), [`hindi`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#hindi-analyzer), [`hungarian`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#hungarian-analyzer), [`indonesian`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#indonesian-analyzer), [`irish`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#irish-analyzer), [`italian`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#italian-analyzer), [`latvian`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#latvian-analyzer),[`lithuanian`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#lithuanian-analyzer), [`norwegian`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#norwegian-analyzer), [`persian`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#persian-analyzer), [`portuguese`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#portuguese-analyzer), [`romanian`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#romanian-analyzer), [`russian`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#russian-analyzer), [`sorani`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#sorani-analyzer), [`spanish`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#spanish-analyzer), [`swedish`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#swedish-analyzer),[`turkish`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#turkish-analyzer), [`thai`](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/analysis-lang-analyzer.html#thai-analyzer) |
| snowball analyzer  | snowball                                                     | standard tokenizer, standard filter, lower case filter, stop filter, snowball filter |
| custom analyzer    | custom                                                       | 一个Tokenizer, 零个或多个Token Filter, 零个或多个Char Filter |

* standard analyzer

  > standard tokenizer  -> standard token filter  -> lowercase token filter 

* keyword

* whitespace

* english analyzer

  > standard tokenizer -> english_possessive_stemmer token filter
  >
  > -> lowercase token filter -> english_stop token filter
  >
  > -> english_keyword token filter ->english_stemmer token filter

  >PUT _analyze
  >
  >
  >
  >{
  >​    "text":"working",
  >​    "analyzer":"english"
  >  }
  >
  >
  >
  >{
  >​    "tokens": [
  >​        {
  >​            "token": "work",
  >​            "start_offset": 0,
  >​            "end_offset": 7,
  >​            "type": "<ALPHANUM>",
  >​            "position": 0
  >​        }
  >​    ]
  >}

#### 2.3、分词阶段（划重点）

* 读时分词  Search time analysis

>ES对用户输入的关键词进行分词
>
>分词结果只存在内存中
>
>当查询结束时，分词结果也会消失
>
>从倒排索引中匹配读时分词结果，查找数据

* 写时分词    Index time analysis

>ES对写入的文档进行分词
>
>分词结果存入倒排索引，以文件形式存储于磁盘上

![1558945923974](D:\github\module-tower\monitor-es\1558946034710.png)



#### 2.4、其他分词器插件

> cmd   D:\Program Files\program\elasticsearch-5.6.10 下执行
>
> 安装ik分词器 
>
>  	./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v5.6.10/elasticsearch-analysis-ik-5.6.10.zip
>
> 安装拼音搜索
>
> ​       ./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-pinyin/releases/download/v5.6.10/elasticsearch-analysis-pinyin-5.6.10.zip

##### 2.4.1、分词器插件使用

> 在6.0版本之前, 添加新的分词器之前, 必须先关闭索引, 添加完成后, 要及时打开索引进行搜索等操作, 而6.0版本后无需此关闭/打开.
>
> 注意 my_pinyin 中的参数设置，会产生不同分词效果
>
> 1、关闭索引
>
> POST testuser/_close
>
> http://localhost:9200/testuser/_close
>
> 2、开启索引
>
> POST testuser/_open
>
> http://localhost:9200/testuser/_open
>
> 3、索引setting设置
>
> 索引未建立时
>
> ​	PUT  /testuser
>
> ​	http://localhost:9200/testuser
>
> 索引已建立时
>
> ​	PUT  /testuser/_setting
>
> ​	http://localhost:9200/testuser/_setting
>
> ```JSON
> {
>   "index": {
>     "analysis": {
>       "analyzer": {
>         "ik_pinyin_analyzer": {
>           "type": "custom",
>           "tokenizer": "ik_max_word",
>           "filter": [
>             "my_pinyin",
>             "my_stopwords"
>           ]
>         },
>         "ik_pinyin_analyzer_for_search": {
>           "type": "custom",
>           "tokenizer": "ik_smart",
>           "filter": [
>             "my_stopwords"
>           ]
>         }
>       },
>       "filter": {
>         "my_pinyin": {
>           "type": "pinyin",
>           "keep_separate_first_letter" : false,
>           "keep_full_pinyin": true,
>           "keep_original" : true
>         },
>         "my_stopwords": {
>           "type": "stop",
>           "stopwords": ["的","de","d"]
>         }
>       }
>     }
>   }
> }
> ```

```json
通过项目 userRepository.initNearBy()生成数据后，查看mapping

不同的filed，可以选择不同的analyzer

GET testuser/_mapping
http://localhost:9200/testuser/_mapping

当analyzer和searchAnalyzer一致时
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

当analyzer和searchAnalyzer不一致时
{
    "testuser": {
        "mappings": {
            "userinfo": {
                "properties": {
                    "id": {
                        "type": "long"
                    },
                    "userName": {
                        "type": "text",
                        "analyzer": "ik_pinyin_analyzer",
                        "search_analyzer": "ik_pinyin_analyzer_for_search"
                    },
                    "sex": {
                        "type": "text",
                        "fields": {
                            "keyword": {
                                "type": "keyword",
                                "ignore_above": 256
                            }
                        }
                    },
                    "age": {
                        "type": "long"
                    },
                    "location": {
                        "type": "text",
                        "fields": {
                            "keyword": {
                                "type": "keyword",
                                "ignore_above": 256
                            }
                        }
                    }
                }
            }
        }
    }
}
```

##### 2.4.2、ES 索引实体类

```java
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
    //用户住址
    @Field(type = FieldType.Text, analyzer = insert, searchAnalyzer = search)
    private String userAddr;
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

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
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
```

##### 2.4.3、ES DAO类

```java
//Long是ID类型

@Component
public interface UserRepository extends ElasticsearchRepository<UserInfo,Long>  {
}
```

##### 2.4.4、分器测试

> analyzer为索引setting设置的自定义分词器或者ES内置分词器
>
> 下面测试过程安装的是ik和pinyin插件

```cu
GET http://localhost:9200/testuser/_analyze
```

写入分词效果检测

```json
{
  "text": ["最好的我们"],
  "analyzer": "ik_pinyin_analyzer"
}
```

```json
{
  "tokens": [
    {
      "token": "zui",
      "start_offset": 0,
      "end_offset": 2,
      "type": "CN_WORD",
      "position": 0
    },
    {
      "token": "hao",
      "start_offset": 0,
      "end_offset": 2,
      "type": "CN_WORD",
      "position": 1
    },
    {
      "token": "最好",
      "start_offset": 0,
      "end_offset": 2,
      "type": "CN_WORD",
      "position": 1
    },
    {
      "token": "zh",
      "start_offset": 0,
      "end_offset": 2,
      "type": "CN_WORD",
      "position": 1
    },
    {
      "token": "wo",
      "start_offset": 3,
      "end_offset": 5,
      "type": "CN_WORD",
      "position": 3
    },
    {
      "token": "men",
      "start_offset": 3,
      "end_offset": 5,
      "type": "CN_WORD",
      "position": 4
    },
    {
      "token": "我们",
      "start_offset": 3,
      "end_offset": 5,
      "type": "CN_WORD",
      "position": 4
    },
    {
      "token": "wm",
      "start_offset": 3,
      "end_offset": 5,
      "type": "CN_WORD",
      "position": 4
    }
  ]
}
```

查询分词效果检测

```json
{
  "text": ["最好的我们"],
  "analyzer": "ik_pinyin_analyzer_for_search"
}
```

```json
{
  "tokens": [
    {
      "token": "最好",
      "start_offset": 0,
      "end_offset": 2,
      "type": "CN_WORD",
      "position": 0
    },
    {
      "token": "我们",
      "start_offset": 3,
      "end_offset": 5,
      "type": "CN_WORD",
      "position": 2
    }
  ]
}
```





#### 2.5、自定义分词器

```
验证自定义分词器
POST _analyze

{
  "char_filter": [], 
  "tokenizer": "standard",
  "filter": [
    "stop",
    "lowercase",
    "stemmer"
  ],
  "text": "women and men are all human"
}

验证效果完成后
```



### 3、搜索高亮	

> lucene支持三种高亮显示方式highlighter, fast-vector-highlighter， postings-highlighter
>
> 在ElasticSearch中，highlighter 高亮是缺省配置高亮方式
>
> 小字段采用highlighter
>
> 大字段采用fast-vector-highlighter
>
> 可自定义高亮

#### es api高亮

##### 单字段查询高亮

```
GET http://localhost:9200/testuser/_search
```

```
{
  "query": {
    "match_phrase": {
      "userName": "李霭"
    }
  },
  "highlight": {
    "fields": {
      "userName": {}
    }
  }
}
```

```json
{
  "took": 1,
  "timed_out": false,
  "_shards": {
    "total": 5,
    "successful": 5,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": 1,
    "max_score": 11.534002,
    "hits": [
      {
        "_index": "testuser",
        "_type": "userinfo",
        "_id": "31787",
        "_score": 11.534002,
        "_source": {
          "id": 31787,
          "userName": "李霭",
          "userAddr": "陕西西安",
          "sex": "女",
          "age": 11,
          "location": "40.088231,116.55389"
        },
        "highlight": {
          "userName": [
            "<em>李</em><em>霭</em>"
          ]
        }
      }
    ]
  }
}
```

##### 多字段查询高亮

```
GET http://localhost:9200/testuser/_search
```

```json
{
  "query": {
    "multi_match": {
        "query" : "段霞",
        "fields" : ["userName", "userAddr"],
        "minimum_should_match": "100%"
    }
  },
  "highlight": {
	"fields": {
		"userName": {},
		"userAddr":{}
	}
  }
}
```

```json
{
  "took": 12,
  "timed_out": false,
  "_shards": {
    "total": 5,
    "successful": 5,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": 3,
    "max_score": 11.044418,
    "hits": [
      {
        "_index": "testuser",
        "_type": "userinfo",
        "_id": "363",
        "_score": 11.044418,
        "_source": {
          "id": 363,
          "userName": "段霞",
          "userAddr": "新西兰",
          "sex": "女",
          "age": 16,
          "location": "39.948753,116.414412"
        },
        "highlight": {
          "userName": [
            "<em>段</em><em>霞</em>"
          ]
        }
      },
      {
        "_index": "testuser",
        "_type": "userinfo",
        "_id": "72063",
        "_score": 10.864601,
        "_source": {
          "id": 72063,
          "userName": "段霞",
          "userAddr": "浙江台州",
          "sex": "女",
          "age": 9,
          "location": "40.014167,116.479826"
        },
        "highlight": {
          "userName": [
            "<em>段</em><em>霞</em>"
          ]
        }
      },
      {
        "_index": "testuser",
        "_type": "userinfo",
        "_id": "16175",
        "_score": 8.953373,
        "_source": {
          "id": 16175,
          "userName": "段霞红",
          "userAddr": "湖北武汉",
          "sex": "女",
          "age": 33,
          "location": "40.792857,117.258516"
        },
        "highlight": {
          "userName": [
            "<em>段</em><em>霞</em>红"
          ]
        }
      }
    ]
  }
}
```

#### java api编码

```
参见monitor-es源代码 
https://github.com/qi90mufeng/module-tower/tree/master/monitor-es
```

### 4、_score(评分)



### 5、slop 

>以 "I like eating and swimming!" 的文档为例，想匹配 "I like swimming"，只需要将 "swimming" 词条向前移动两次，因此设置 `slop` 参数值为 2， 就可以匹配到

```json
{
  "query": {
    "match_phrase": {
      "message": {
        "query": "I like swimming",
        "slop": 2
      }
    }
  }
}
```

### 6、联想Suggester

- Term Suggester
- Phrase Suggester
- Completion Suggester
- Context Suggester



### 7、搜索方式

#### 7.1、多索引搜索

##### Es 搜索API

```
POST http://localhost:9200/_search
```

##### java 搜索API

```

```

#### 7.2、单索引搜索

#####  Es 搜索API

```
POST http://localhost:9200/testuser/_search
```

##### java 搜索API

```

```

#### 7.3、单字段搜索

##### 7.3.1、term query

> 查询的是倒排索引中确切的term

##### 7.3.2、match_all

```
GET http://localhost:9200/testuser/_search
```

```json
{
  "query": {
    "match_all": {}
  }
}
```

##### 7.3.3、match query

> 会对filed进行分词操作，然后再查询
>
> 默认10条数据

###### Es 搜索API

```
GET http://localhost:9200/testuser/_search
```

```json
{
  "query": {
    "match": {
      "userName": "李春"
    }
  }
}
```

```json
{
  "took": 10,
  "timed_out": false,
  "_shards": {
    "total": 5,
    "successful": 5,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": 687,
    "max_score": 6.3969073,
    "hits": [
      {
        "_index": "testuser",
        "_type": "userinfo",
        "_id": "6331",
        "_score": 6.3969073,
        "_source": {
          "id": 6331,
          "userName": "李裕",
          "userAddr": "浙江金华",
          "sex": "男",
          "age": 10,
          "location": "40.075164,116.540823"
        }
      },
      {
        "_index": "testuser",
        "_type": "userinfo",
        "_id": "14286",
        "_score": 6.3969073,
        "_source": {
          "id": 14286,
          "userName": "李先",
          "userAddr": "湖北武汉",
          "sex": "男",
          "age": 36,
          "location": "40.753247,117.218906"
        }
      },
      {
        "_index": "testuser",
        "_type": "userinfo",
        "_id": "31787",
        "_score": 6.3969073,
        "_source": {
          "id": 31787,
          "userName": "李霭",
          "userAddr": "陕西西安",
          "sex": "女",
          "age": 11,
          "location": "40.088231,116.55389"
        }
      },
      {
        "_index": "testuser",
        "_type": "userinfo",
        "_id": "32259",
        "_score": 6.3969073,
        "_source": {
          "id": 32259,
          "userName": "李军",
          "userAddr": "英国伦敦",
          "sex": "男",
          "age": 20,
          "location": "40.619743,117.085402"
        }
      },
      {
        "_index": "testuser",
        "_type": "userinfo",
        "_id": "39569",
        "_score": 6.3969073,
        "_source": {
          "id": 39569,
          "userName": "李茜",
          "userAddr": "浙江衢州常山",
          "sex": "女",
          "age": 29,
          "location": "40.447838,116.913497"
        }
      },
      {
        "_index": "testuser",
        "_type": "userinfo",
        "_id": "43912",
        "_score": 6.3969073,
        "_source": {
          "id": 43912,
          "userName": "李彬",
          "userAddr": "上海徐汇区",
          "sex": "男",
          "age": 12,
          "location": "40.696666,117.162325"
        }
      },
      {
        "_index": "testuser",
        "_type": "userinfo",
        "_id": "59522",
        "_score": 6.3969073,
        "_source": {
          "id": 59522,
          "userName": "李琳",
          "userAddr": "湖北武汉",
          "sex": "女",
          "age": 31,
          "location": "40.186421,116.65208"
        }
      },
      {
        "_index": "testuser",
        "_type": "userinfo",
        "_id": "66411",
        "_score": 6.3969073,
        "_source": {
          "id": 66411,
          "userName": "李康",
          "userAddr": "英国伦敦",
          "sex": "男",
          "age": 19,
          "location": "40.74274,117.208399"
        }
      },
      {
        "_index": "testuser",
        "_type": "userinfo",
        "_id": "69328",
        "_score": 6.3969073,
        "_source": {
          "id": 69328,
          "userName": "李咏",
          "userAddr": "浙江丽水",
          "sex": "女",
          "age": 7,
          "location": "40.333728,116.799387"
        }
      },
      {
        "_index": "testuser",
        "_type": "userinfo",
        "_id": "71323",
        "_score": 6.3969073,
        "_source": {
          "id": 71323,
          "userName": "李盛",
          "userAddr": "美国芝加哥",
          "sex": "男",
          "age": 34,
          "location": "40.309257,116.774916"
        }
      }
    ]
  }
}
```

###### java 搜索API

```
QueryBuilder queryBuilder = QueryBuilders.matchQuery("userName", "李春");
```

##### 7.3.4、match_phrase

>

##### 7.3.5、match_phrase_prefix

>  可以做搜索推荐
>
> 查询 `I like sw` 就能匹配到  `I like swimming and riding`



#### 7.4、多字段搜索

##### 7.4.1、terms query

##### 7.4.2、multi_match

> ```JAVA
> MultiMatchQueryBuilder.Type
> 
> public static enum Type implements Writeable {
> 	BEST_FIELDS(org.elasticsearch.index.search.MatchQuery.Type.BOOLEAN, 0.0F, new ParseField("best_fields", new String[]{"boolean"})),
> 	MOST_FIELDS(org.elasticsearch.index.search.MatchQuery.Type.BOOLEAN, 1.0F, new ParseField("most_fields", new String[0])),
> 	CROSS_FIELDS(org.elasticsearch.index.search.MatchQuery.Type.BOOLEAN, 0.0F, new ParseField("cross_fields", new String[0])),
>     PHRASE(org.elasticsearch.index.search.MatchQuery.Type.PHRASE, 0.0F, new ParseField("phrase", new String[0])),
>     PHRASE_PREFIX(org.elasticsearch.index.search.MatchQuery.Type.PHRASE_PREFIX, 0.0F, new ParseField("phrase_prefix", new String[0]));
>     ......    
> }
> ```
>
> best_fields
>
> most_fields
>
> cross_fields

###### Es 搜索API

```
GET http://localhost:9200/testuser/_search
```

```json
{
  "query": {
    "multi_match": {
        "query" : "西安",
        "fields" : ["userName", "userAddr"],
        "minimum_should_match": "100%"
    }
  },
  "highlight": {
	"fields": {
		"userName": {},
		"userAddr":{}
	}
  }
}
```

```
QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("西安",  
           "userName", "userAddr");
```



##### 7.9、fuzzy

模糊查询

##### 7.10、bool

>

* must

  > 返回的文档必须满足must子句的条件，并且参与计算分值

  ```
  
  ```

* filter

  > 返回的文档必须满足filter子句的条件。但是不会像Must一样，参与计算分值

* should

  > 应该满足他列出的条件,是或的关系

* must_nout

```json
{
    "bool" : {
        "must" : {
            "term" : { "user" : "kimchy" }
        },
        "filter": {
            "term" : { "tag" : "tech" }
        },
        "must_not" : {
            "range" : {
                "age" : { "from" : 10, "to" : 20 }
            }
        },
        "should" : [
            {
                "term" : { "tag" : "wow" }
            },
            {
                "term" : { "tag" : "elasticsearch" }
            }
        ],
        "minimum_should_match" : 1,
        "boost" : 1.0
    }
}
```


##### 7.11、range

```java
{
  "query":{
    "range":{
      "word_count":{
        "gte": 1000,
        "lte": 2000
      }
    }
  }
}
```



##### 7.12、wildcard

> 模糊查询，? 匹配单个字符        * 匹配多个字符



#### 7.5、分页(TODO)

###### Es 搜券API

```json
GET testuser/_search
{
  "from":0,
  "size":2,
  "query":{
    "match_all": {}
  }
}
```



#### 7.6、加权(TODO)

> boost  权重值，float 型

```java
{
  "query": {
    "bool": {
      "should": [
        {"wildcard": {
          "filmName": {
            "value": "*罗马*",
            "boost": "20.0f"
          }
        }},{"wildcard": {
          "filmDirector": {
            "value": "*罗马*",
            "boost": "10.0f"
          }
        }},{"wildcard": {
          "filmCast": {
            "value": "*罗马*",
            "boost": "2.0f"
          }
        }}
      ]
    }
  },
  "highlight": {
	"fields": {
		"filmName": {},
		"filmDirector":{},
		"filmCast":{}
	}
  }
}
```



#### 7.7、排序

* 默认排序规则

  > 按照_score降序排序的

* 定制排序规则

#### 7.7  聚合查询

>







dis_max

tie_breaker

geo_distance



  aggs

Scoll滚动搜索：scoll主要是用来一批一批检索数据，让系统进行处理的



#### 7.8、(TODO)

```json
GET /testuser/_validate/query?explain
{
  "query": {
    "math": {
      "test_field": "test"
    }
  }
}
```



#### 7.9、距离计算

##### java 搜索API

```java
 @GeoPointField
 private String location;
 
关键java语句，

GeoDistanceSortBuilder sort = SortBuilders.geoDistanceSort("location", Double.valueOf(lat) , Double.valueOf(lon)).unit(DistanceUnit.KILOMETERS).order(SortOrder.ASC)

    
SearchResponse response = elasticsearchTemplate.getClient().prepareSearch("testuser")
                .setQuery(boolQueryBuilderC)
                .addSort(sort)
                .setFrom(0)
                .setSize(100)
                .highlighter(highlightBuilder)
                .execute().actionGet();    
SearchHits searchHits = response.getHits();


for (SearchHit hit : searchHits) {
    //获取距离值，并保留两位小数点  geoDis就是距离，距离存储在sortvalues中
    //距离单位由sort中的uni决定
    BigDecimal geoDis = new BigDecimal((Double) hit.getSortValues()[0]).setScale(2, BigDecimal.ROUND_HALF_DOWN);
}
```



### 8、查询文档数量

```
GET /_cat/indices?v
```

| health | status |  index   |          uuid          | pri  | rep  | docs.count | docs.deleted | store.size | pri.store.size |
| :----: | :----: | :------: | :--------------------: | :--: | :--: | :--------: | :----------: | :--------: | :------------: |
| yellow |  open  |   cars   | -0p63yEhQ8OFBVMqF3QHwg |  5   |  1   |    331     |      0       |   913kb    |     913kb      |
| yellow |  open  | testuser | 5N-Z7DBVRIiHo0qmSq3zKQ |  5   |  1   |   12100    |      0       |   8.2mb    |     8.2mb      |

```
/_cat/count?v
```

| epoch      | timestamp | count |
| ---------- | --------- | ----- |
| 1559024134 | 14:15:34  | 12431 |

```
/_cat/count/testuser?v
```

| epoch      | timestamp | count |
| ---------- | --------- | ----- |
| 1559024254 | 14:17:34  | 12100 |



### 9、扩展字典

> 预先安装IK分词      
>
> 分词器扩展配置文件:  IKAnalyzer.cfg.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
<properties>
	<comment>IK Analyzer 扩展配置</comment>
	<!--用户可以在这里配置自己的扩展字典 -->
	<entry key="ext_dict"/>
	<!--用户可以在这里配置自己的扩展停止词字典-->
	<entry key="ext_stopwords"/>
    <!--用户可以在这里配置远程扩展字典 -->
    <!-- <entry key="remote_ext_dict">words_location</entry> -->
    <!--用户可以在这里配置远程扩展停止词字典-->
    <!-- <entry key="remote_ext_stopwords">words_location</entry> -->
</properties>
```



### 10、其他

#### 1、 <span id="jump">去除type原因</span>

> field的value值在底层的lucene中建立索引的时候，全部是opaque bytes类型，不区分类型的。lucene是没有type的概念的，在document中，实际上将type作为一个document的field来存储，即_type，es通过_type来进行type的过滤和筛选。一个index中的多个type，实际上是放在一起存储的，因此一个index下，不能有多个type重名但是类型或其他设置不同，因为那样是无法处理的。
>
> 转载自 https://www.cnblogs.com/liuqianli/p/8475477.html

```json
假设有如下一个index

   
PUT /goods
{
  "ecommerce": {
    "mappings": {
      "elactronic_goods": {
        "properties": {
          "name": {
            "type": "string"
          },
          "price": {
            "type": "double"
          },
          "service_period": {
            "type": "string"
          }
        }
      },
      "fresh_goods": {
        "properties": {
          "name": {
            "type": "string"
          },
          "price": {
            "type": "double"
          },
          "eat_period": {
            "type": "string"
          }
        }
      }
    }
  }
}

有如下两条数据
{
    "name": "geli kongtiao",
    "price": 1999.0,
    "service_period": "one year"
}
{
    "name": "aozhou dalongxia",
    "price": 199.0,
    "eat_period": "one week"
}

   
在底层的存储是这样子的：
{
  "ecommerce": {
    "mappings": {
      "_type": {
        "type": "string",
        "index": "not_analyzed"
      },
      "name": {
        "type": "string"
      },
      "price": {
        "type": "double"
      },
      "service_period": {
        "type": "string"
      },
      "eat_period": {
        "type": "string"
      }
    }
  }
} 

{
  "_type": "elactronic_goods",
  "name": "geli kongtiao",
  "price": 1999,
  "service_period": "one year",
  "eat_period": ""
}

{
  "_type": "fresh_goods",
  "name": "aozhou dalongxia",
  "price": 199,
  "service_period": "",
  "eat_period": "one week"
} 



可以看出，在es内部，会把所有field合并，对于一个type中没有的field就用空值替代。
所以，在一个index下不同type的同名field的类型必须一致，不然就会冲突。
最佳实践，将类似结构的type放在一个index下，这些type应该有多个field是相同的
因此，如果将两个type的field完全不同，放在一个index下，那么就每条数据都至少有一半的field在底层的lucene中是空值，会有严重的性能问题。
```



## 四、信息检索评价

##### 9.1、召回率

##### 9.2、准确率

##### 8.3、MAP

##### 8.4、nCDG



## 五、spring boot集成ES

参见monitor-es源代码 

https://github.com/qi90mufeng/module-tower/tree/master/monitor-es



## 六、实践出真知

> 为了保证索引时覆盖度和搜索时准确度：
>
> 写入分词可以尽量丰富点
>
> 读时分词，尽量少点，能够提高准确度
>
> ​	关键词保持原形，不做拼音分词，要不然拼音+中文分词，会搜到其他的数据



搜索优化

1、短语匹配

短语匹配用来解决准确匹配问题

2、提高精度

使用`"operator": "and"`提高精度或者 `"minimum_should_match": "90%"` 控制精度

3、提高权重

boost

4、高亮搜索

5、选择分词



字段内容为短语，如果需要拼音搜索，写入分词时，增加拼音分词，读时一般不分词，查询可以考虑用wildcard( *keyword*)

字段内容为长文，写入分词（一般会进行分词，比如中文分词，英文分词）、读时分词（需不需要分词看情况）





## 七、监控ES服务







 .\bin\elasticsearch-plugin.bat install https://github.com/NLPchina/elasticsearch-sql/releases/download/6.4.3.0/elasticsearch-sql-6.4.3.0.zip



单集群配置   多集群配置

http://localhost:9200/_cluster/allocation/explain

## 计划

1、怎样限定es查询返回数据最低分，低于XX分数的不要



2、距离计算 、字段查询   混合操作



3、手写分词器

https://www.jianshu.com/p/6812e4ce0f1c



3、IKAnalyzer.cfg.xml扩展字典



3、热修改mapping和分词

 

```
{
    "query":{
      "match":{
        "cityName":{
          "query": "大家",
          "analyzer": "ik_pinyin_analyzer"
        }
      }
    },
    "highlight": {
		"fields": {
			"cityName": {}
		}
    }
  }
```




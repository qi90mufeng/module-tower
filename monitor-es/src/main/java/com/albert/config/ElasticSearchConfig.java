package com.albert.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;

/**
 * @author fujin
 * @version v 0.1
 * @date 2019-05-08
 */
@Configuration
public class ElasticSearchConfig {

//    @Value("${spring.data.elasticsearch.cluster-name:first-cluster}")
    private String clusterName = "first-cluster";

//    @Value("${spring.data.elasticsearch.cluster-nodes:'127.0.0.1:9300'}")
    private String clusterNodes = "127.0.0.1:9300";

    @Value("${spring.data.elasticsearch.local:false}")
    private String local;

    @Value("${spring.data.elasticsearch.repositories.enable:true}")
    private String enable;

    private TransportClient client;

    @PostConstruct
    public void initialize() throws Exception {
        Settings esSettings = Settings.builder()
                .put("cluster.name", clusterName)
                //如果有配置xpack插件，需要配置登录
//                .put("xpack.security.user", "elastic:changeme")
                .put("client.transport.sniff", true).build();
        client = new PreBuiltTransportClient(esSettings);

        String[] esHostAndPorts = clusterNodes.trim().split(",");
        for (String esHostAndPort : esHostAndPorts) {
            String[] hostAndPort = esHostAndPort.split(":");
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostAndPort[0]),
                    Integer.valueOf(hostAndPort[1])));
        }
    }

    @Bean
    public Client client() {
        return client;
    }

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(client);
    }

    @PreDestroy
    public void destroy() {
        if (client != null) {
            client.close();
        }
    }

}

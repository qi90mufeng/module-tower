package com.albert.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;

/**
 * ////////////////////////////////////////////////////////////////////
 * //                          _ooOoo_                               //
 * //                         o8888888o                              //
 * //                         88" . "88                              //
 * //                         (| ^_^ |)                              //
 * //                         O\  =  /O                              //
 * //                      ____/`---'\____                           //
 * //                    .'  \\|     |//  `.                         //
 * //                   /  \\|||  :  |||//  \                        //
 * //                  /  _||||| -:- |||||-  \                       //
 * //                  |   | \\\  -  /// |   |                       //
 * //                  | \_|  ''\---/''  |   |                       //
 * //                  \  .-\__  `-`  ___/-. /                       //
 * //                ___`. .'  /--.--\  `. . ___                     //
 * //              ."" '<  `.___\_<|>_/___.'  >'"".                  //
 * //            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
 * //            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
 * //      ========`-.____`-.___\_____/___.-`____.-'========         //
 * //                           `=---='                              //
 * //      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
 * //         佛祖保佑       永无BUG     永不修改                      //
 * ////////////////////////////////////////////////////////////////////
 *
 * @author fujin
 * @version v 0.1
 * @date 2019-01-07
 */
@Configuration
public class EsConfig {

//    @Value("${elasticsearch.host:127.0.0.1}")
//    private String esHost;
//
//    @Value("${elasticsearch.port:9300}")
//    private int esPort;
//
//    @Value("${elasticsearch.clusterName:first_cluster}")
//    private String esClusterName;
//
//    private TransportClient client;
//
//    @PostConstruct
//    public void initialize() throws Exception {
//        Settings esSettings = Settings.builder()
//                .put("cluster.name", esClusterName)
//                .put("client.transport.sniff", true).build();
//        client = new PreBuiltTransportClient(esSettings);
//
//        String[] esHosts = esHost.trim().split(",");
//        for (String host : esHosts) {
//            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host),
//                    esPort));
//        }
//    }
//
//    @Bean
//    public Client client() {
//        return client;
//    }
//
//
//    @Bean
//    public ElasticsearchTemplate elasticsearchTemplate() throws Exception {
//        return new ElasticsearchTemplate(client);
//    }
//
//
//    @PreDestroy
//    public void destroy() {
//        if (client != null) {
//            client.close();
//        }
//    }

}

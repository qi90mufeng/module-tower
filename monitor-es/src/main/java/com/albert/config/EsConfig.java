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
import java.net.UnknownHostException;

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


    //@Value("${elasticsearch.pool}")
//    private int poolSize = 10;
//
//    @Bean
//    public TransportClient init() {
//        TransportClient transportClient = null;
//        try {
//            // 配置信息
//            Settings esSetting = Settings.builder()
//                    .put("cluster.name", "first-cluster")
//                    .put("client.transport.sniff", true)//增加嗅探机制，找到ES集群
//                    .put("thread_pool.search.size", poolSize)//增加线程池个数，暂时设为5
//                    .build();
//
//            transportClient = new PreBuiltTransportClient(esSetting);
//            InetSocketTransportAddress inetSocketTransportAddress = new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), Integer.valueOf(9300));
//            transportClient.addTransportAddresses(inetSocketTransportAddress);
//
//        } catch (Exception e) {
//            System.err.println("elasticsearch TransportClient create error!!!" + e);
//        }
//
//        return transportClient;
//    }
//    @Bean
//    public TransportClient client() throws UnknownHostException {
//        // 9300是es的tcp服务端口
//        InetSocketTransportAddress node = new InetSocketTransportAddress(
//                InetAddress.getByName("127.0.0.1"),
//                9300);
//
//        // 设置es节点的配置信息
//        Settings settings = Settings.builder()
//                .put("cluster.name", "first_cluster")
//                .build();
//
//        // 实例化es的客户端对象
//        TransportClient client = new PreBuiltTransportClient(settings);
//        client.addTransportAddress(node);
//
//        return client;
//    }
//    @Bean
//    public ElasticsearchTemplate elasticsearchTemplate() throws UnknownHostException {
//        // 9300是es的tcp服务端口
//        InetSocketTransportAddress node = new InetSocketTransportAddress(
//                InetAddress.getByName("127.0.0.1"),
//                9300);
//
//        // 设置es节点的配置信息
//        Settings settings = Settings.builder()
//                .put("cluster.name", "first_cluster")
//                .put("client.transport.ignore_cluster_name", false)
//                .put("node.client", true)
//                .put("client.transport.sniff", true)
//                .build();
//
//        // 实例化es的客户端对象
//        TransportClient client = new PreBuiltTransportClient(settings);
//        client.addTransportAddress(node);
//
//        return new ElasticsearchTemplate(client);
//    }

//    //@Value("${elasticsearch.host:127.0.0.1}")
//    private String esHost = "127.0.0.1";
//
//    //@Value("${elasticsearch.port:9300}")
//    private int esPort = 9300;
//
//    //@Value("${elasticsearch.clusterName:first_cluster}")
//    private String esClusterName = "first_cluster";
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

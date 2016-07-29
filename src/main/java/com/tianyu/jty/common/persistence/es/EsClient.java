package com.tianyu.jty.common.persistence.es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by LeslieLiu on 2016/7/25.
 */
public class EsClient {
    public static final String CLUSTER_NAME = "elacluster"; //实例名称
    private static final String[] CLUSTER_HOSTS = {"node1","node2","node3"};
    private static final int PORT = 9300;  //端口

    private static Settings settings = Settings
            .settingsBuilder()
            .put("cluster.name",CLUSTER_NAME)
            .put("client.transport.sniff", true)
            .build();
    private static TransportClient client;
    static {
        try {
            client = TransportClient.builder().settings(settings).build();
            for (int i = 0; i < CLUSTER_HOSTS.length; i++) {
                String host = CLUSTER_HOSTS[i];
               client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), PORT));
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    public static synchronized TransportClient getTransportClient(){
        return client;
    }
}

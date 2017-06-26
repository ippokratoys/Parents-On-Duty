package webapp;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.context.annotation.Configuration;
import webapp.database.Eventsgroup;
import webapp.database.elasticsearch.EventSearch;

import java.net.InetAddress;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * Created by thanasis on 21/6/2017.
 */

@Configuration
@EnableElasticsearchRepositories(basePackages = "webapp.database.elasticsearch")
public class ElasticSearchConfig {

//    @Value("${elasticsearch.host}")
//    private String EsHost;
//
//    @Value("${elasticsearch.port}")
//    private int EsPort;
//
//    @Value("${elasticsearch.clustername}")
//    private String EsClusterName;
//
//    @Bean
//    public Client client() throws Exception {
//
//        Settings esSettings = Settings.settingsBuilder()
//                .put("cluster.name", EsClusterName)
//                .build();
//
//        //https://www.elastic.co/guide/en/elasticsearch/guide/current/_transport_client_versus_node_client.html
//        return TransportClient.builder()
//                .settings(esSettings)
//                .build()
//                .addTransportAddress(
//                        new InetSocketTransportAddress(InetAddress.getByName(EsHost), EsPort));
//    }


    //Embedded Elasticsearch Server
    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        //create the elastic search
        System.out.println("I am here\n\n\n\n\n\nEEEE\n\n\n");
        ElasticsearchOperations elasticsearchOperations=new ElasticsearchTemplate(nodeBuilder().local(true).node().client());
        //initialize the index of elastic search
        elasticsearchOperations.deleteIndex(EventSearch.class);
        elasticsearchOperations.createIndex(EventSearch.class);
        elasticsearchOperations.putMapping(EventSearch.class);
        elasticsearchOperations.refresh(EventSearch.class);
        return elasticsearchOperations;
    }

}
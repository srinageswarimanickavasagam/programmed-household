package srinageswari.programmedhousehold.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

  @Value("${app.elasticsearch.host}")
  private String elasticsearchHost;

  @Value("${app.elasticsearch.port}")
  private int port;

  @Bean
  public RestClient getRestClient() {
    return RestClient.builder(new HttpHost(elasticsearchHost, port)).build();
  }

  public ElasticsearchClient getElasticSearchTransport() {
    JacksonJsonpMapper jsonMapper = new JacksonJsonpMapper();
    ElasticsearchTransport elasticsearchTransport =
        new RestClientTransport(getRestClient(), jsonMapper);
    return new ElasticsearchClient(elasticsearchTransport);
  }
}

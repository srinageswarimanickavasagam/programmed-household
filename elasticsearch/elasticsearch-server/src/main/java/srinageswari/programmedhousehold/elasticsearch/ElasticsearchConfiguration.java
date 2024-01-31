package srinageswari.programmedhousehold.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfiguration {

  @Bean
  public ElasticsearchClient elasticsearchClient(
      @Value("${app.elasticsearch.host}") String elasticsearchHost,
      @Value("${app.elasticsearch.port}") int port,
      @Value("${app.elasticsearch.username}") String username,
      @Value("${app.elasticsearch.password}") String password,
      @Value("${app.elasticsearch.max_conn_total}") int max_conn_total,
      @Value("${app.elasticsearch.max_conn_per_route}") int max_conn_per_route) {

    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    credentialsProvider.setCredentials(
        AuthScope.ANY, new UsernamePasswordCredentials(username, password));

    RestClient restClient =
        RestClient.builder(new HttpHost(elasticsearchHost, port, "http"))
            .setHttpClientConfigCallback(
                httpClientBuilder -> {
                  httpClientBuilder.setMaxConnTotal(max_conn_total);
                  httpClientBuilder.setMaxConnPerRoute(max_conn_per_route);
                  return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                })
            .build();

    ElasticsearchTransport transport =
        new RestClientTransport(restClient, new JacksonJsonpMapper());

    return new ElasticsearchClient(transport);
  }
}

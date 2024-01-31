package srinageswari.programmedhousehold.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ElasticsearchIndexInitializer implements ApplicationRunner {
  private static final Logger logger = LoggerFactory.getLogger(ElasticsearchIndexInitializer.class);
  private final ElasticsearchClient elasticsearchClient;

  @Autowired
  public ElasticsearchIndexInitializer(ElasticsearchClient elasticsearchClient) {
    this.elasticsearchClient = elasticsearchClient;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    if (!elasticsearchClient.indices().exists(ExistsRequest.of(e -> e.index("recipe"))).value())
      initializeIndex();
  }

  private void initializeIndex() {
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      InputStream inputStream = classLoader.getResourceAsStream("elasticsearch_mappings.json");

      CreateIndexRequest request =
          CreateIndexRequest.of(builder -> builder.index("recipe").withJson(inputStream));

      CreateIndexResponse createIndexResponse = elasticsearchClient.indices().create(request);

      if (createIndexResponse.acknowledged()) {
        System.out.println("Index created successfully.");
      } else {
        System.err.println("Failed to create the index.");
      }
    } catch (IOException e) {
      logger.error("An error occurred while initializing the Elasticsearch index", e);
    }
  }
}

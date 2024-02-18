package srinageswari.programmedhousehold.elasticsearch.common;

import static srinageswari.programmedhousehold.elasticsearch.Constants.RECIPE_INDEX_NAME;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
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
    if (!elasticsearchClient
        .indices()
        .exists(ExistsRequest.of(e -> e.index(RECIPE_INDEX_NAME)))
        .value()) initializeIndex();
  }

  public void initializeIndex() {
    try {
      Optional<InputStream> inputStreamOptional = loadIndexJsonStream();

      if (inputStreamOptional.isPresent()) {
        CreateIndexResponse createIndexResponse = createIndex(inputStreamOptional.get());

        if (createIndexResponse.acknowledged()) {
          logger.debug("Index created successfully.");
        } else {
          logger.error("Failed to create the index.");
        }
      } else {
        logger.error("Failed to load the index JSON file.");
      }
    } catch (IOException e) {
      logger.error("An error occurred while initializing the Elasticsearch index", e);
    }
  }

  private Optional<InputStream> loadIndexJsonStream() {
    return Optional.ofNullable(
        getClass().getClassLoader().getResourceAsStream("elasticsearch_mappings.json"));
  }

  private CreateIndexResponse createIndex(InputStream inputStream) throws IOException {
    CreateIndexRequest request =
        CreateIndexRequest.of(builder -> builder.index("recipe").withJson(inputStream));
    return elasticsearchClient.indices().create(request);
  }
}

package srinageswari.programmedhousehold.elasticsearch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import srinageswari.programmedhousehold.elasticsearch.dto.RecipeSearchDTO;

@Component
public class ElasticsearchClientService {

  private final WebClient webClient;

  public ElasticsearchClientService(WebClient webClient) {
    this.webClient = webClient;
  }

  protected final ParameterizedTypeReference<RecipeSearchDTO> restApiResponseParameterizedTypeRef =
      new ParameterizedTypeReference<RecipeSearchDTO>() {};

  @Value("${application.rest.protocol:http}")
  protected String APPLICATION_REST_PROTOCOL;

  protected String getBaseURL() {
    String SERVICE_NAME = "/api/elasticsearch/v1";
    return APPLICATION_REST_PROTOCOL + "://" + "elasticsearch" + ":" + "9000" + SERVICE_NAME;
  }

  public Mono<RecipeSearchDTO> createRecipeDocument(RecipeSearchDTO recipeDTO) {
    UriComponentsBuilder builder =
        UriComponentsBuilder.fromHttpUrl(getBaseURL() + "/createRecipeDocument");
    return webClient
        .post()
        .uri(uriBuilder -> builder.build().toUri())
        .body(BodyInserters.fromValue(recipeDTO))
        .retrieve()
        .bodyToMono(restApiResponseParameterizedTypeRef);
  }
}

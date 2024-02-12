package srinageswari.programmedhousehold.elasticsearch;

import static srinageswari.programmedhousehold.elasticsearch.Constants.RECIPE_INDEX_NAME;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.WildcardQuery;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.core.search.Hit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import srinageswari.programmedhousehold.elasticsearch.common.ElasticsearchIndexInitializer;
import srinageswari.programmedhousehold.elasticsearch.dto.BulkInsertResponseDTO;
import srinageswari.programmedhousehold.elasticsearch.dto.IndexResponseDTO;
import srinageswari.programmedhousehold.elasticsearch.dto.RecipeSearchDTO;
import srinageswari.programmedhousehold.elasticsearch.dto.ShardsDTO;

@Slf4j(topic = "ElasticsearchService")
@RequiredArgsConstructor
@Service
public class ElasticsearchService {

  private static final Logger logger = LoggerFactory.getLogger(ElasticsearchIndexInitializer.class);
  private final ElasticsearchClient elasticsearchClient;

  public RecipeSearchDTO saveToElasticsearch(RecipeSearchDTO recipe) throws IOException {
    IndexResponse indexResponse =
        elasticsearchClient.index(
            i ->
                i.index(RECIPE_INDEX_NAME)
                    .id(String.valueOf(recipe.getRecipeId()))
                    .document(recipe));

    return elasticsearchClient
        .get(
            s -> s.index(RECIPE_INDEX_NAME).id(String.valueOf(indexResponse.id())),
            RecipeSearchDTO.class)
        .source();
  }

  public BulkInsertResponseDTO bulkInsert(List<RecipeSearchDTO> recipeDTOS) throws IOException {
    BulkRequest.Builder br = new BulkRequest.Builder();

    for (RecipeSearchDTO recipeDTO : recipeDTOS) {
      br.operations(
          op ->
              op.create(
                  c ->
                      c.index(RECIPE_INDEX_NAME)
                          .id(String.valueOf(recipeDTO.getRecipeId()))
                          .document(recipeDTO)));
    }

    BulkResponse result = elasticsearchClient.bulk(br.build());
    BulkInsertResponseDTO bulkInsertResponseDTO = new BulkInsertResponseDTO();
    bulkInsertResponseDTO.setErrors(result.errors());
    List<IndexResponseDTO> indexResponseDTOS = new ArrayList<>();

    for (BulkResponseItem item : result.items()) {
      IndexResponseDTO indexResponseDTO = new IndexResponseDTO();
      indexResponseDTO.setId(item.id());
      if (item.error() != null) {
        indexResponseDTO.setError(item.error().reason());
        logger.error(item.error().reason());
      }
      indexResponseDTO.setIndex(item.index());
      indexResponseDTO.setPrimaryTerm(item.primaryTerm());
      indexResponseDTO.setSequenceNumber(item.seqNo());
      indexResponseDTO.setResult(item.result());
      indexResponseDTO.setVersion(item.version());
      if (item.shards() != null)
        indexResponseDTO.setShards(
            new ShardsDTO(
                item.shards().failed(), item.shards().successful(), item.shards().total()));
      indexResponseDTOS.add(indexResponseDTO);
    }
    bulkInsertResponseDTO.setItems(indexResponseDTOS);
    bulkInsertResponseDTO.setTook(result.took());
    return bulkInsertResponseDTO;
  }

  public void searchRecipe(String field, String searchText) throws IOException {
    SearchResponse<RecipeSearchDTO> searchResponse =
        this.elasticsearchClient.search(
            searchRequest ->
                searchRequest
                    .index(RECIPE_INDEX_NAME)
                    .query(
                        queryBuilder ->
                            queryBuilder.match(
                                matchQueryBuilder ->
                                    matchQueryBuilder.field(field).query(searchText))),
            RecipeSearchDTO.class);

    // Capture recipes
    List<RecipeSearchDTO> recipes = searchResponse.hits().hits().stream().map(Hit::source).toList();

    // Or print them to console
    searchResponse.hits().hits().forEach(System.out::println);
  }

  public List<RecipeSearchDTO> wildcardSearchOnField(String indexName, String wildcardStr)
      throws IOException {
    String fieldName = "title";
    // Construct the wildcard query
    Query wildcardQueryObj =
        WildcardQuery.of(q -> q.field(fieldName).wildcard("*" + wildcardStr + "*"))._toQuery();

    // Construct the search request
    SearchRequest searchRequest =
        new SearchRequest.Builder()
            .index(indexName)
            .query(q -> q.bool(b -> b.must(wildcardQueryObj)))
            .build();

    // Execute the search request
    SearchResponse<RecipeSearchDTO> response =
        elasticsearchClient.search(searchRequest, RecipeSearchDTO.class);
    List<RecipeSearchDTO> recipeDTOS = new ArrayList<>();
    List<Hit<RecipeSearchDTO>> searchHits = response.hits().hits();

    searchHits.forEach(
        hit -> {
          recipeDTOS.add(hit.source());
        });
    return recipeDTOS;
  }
}

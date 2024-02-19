package srinageswari.programmedhousehold.elasticsearch;

import static srinageswari.programmedhousehold.elasticsearch.Constants.RECIPE_INDEX_NAME;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srinageswari.programmedhousehold.coreservice.Constants;
import srinageswari.programmedhousehold.coreservice.dto.common.APIResponseDTO;
import srinageswari.programmedhousehold.elasticsearch.dto.BulkInsertResponseDTO;
import srinageswari.programmedhousehold.elasticsearch.dto.RecipeSearchDTO;

@RestController
@RequestMapping("/api/elasticsearch/v1")
@RequiredArgsConstructor
public class ElasticsearchController {
  private final ElasticsearchService elasticsearchService;

  @PostMapping("/createRecipeDocument")
  public ResponseEntity<APIResponseDTO<RecipeSearchDTO>> create(
      @RequestBody RecipeSearchDTO request) throws IOException {
    final RecipeSearchDTO response = elasticsearchService.saveToElasticsearch(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response, 1));
  }

  @PostMapping("/recipe/bulkInsert")
  public ResponseEntity<APIResponseDTO<BulkInsertResponseDTO>> bulkInsert(
      @RequestBody List<RecipeSearchDTO> requests) throws IOException {
    final BulkInsertResponseDTO response = elasticsearchService.bulkInsert(requests);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            new APIResponseDTO<>(
                LocalDateTime.now(), Constants.SUCCESS, response, response.getItems().size()));
  }

  @PostMapping("/recipe/search/{wildcard}")
  public ResponseEntity<APIResponseDTO<List<RecipeSearchDTO>>> wildcardSearch(
      @PathVariable String wildcard) throws IOException {
    final List<RecipeSearchDTO> response =
        elasticsearchService.wildcardSearchOnField(RECIPE_INDEX_NAME, wildcard);
    return ResponseEntity.status(HttpStatus.FOUND)
        .body(
            new APIResponseDTO<>(
                LocalDateTime.now(), Constants.SUCCESS, response, response.size()));
  }
}

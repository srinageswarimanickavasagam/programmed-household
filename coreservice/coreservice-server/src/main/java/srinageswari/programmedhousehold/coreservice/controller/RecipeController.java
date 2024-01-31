package srinageswari.programmedhousehold.coreservice.controller;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import srinageswari.programmedhousehold.coreservice.common.Constants;
import srinageswari.programmedhousehold.coreservice.dto.RecipeDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.APIResponseDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.SearchRequestDTO;
import srinageswari.programmedhousehold.coreservice.service.recipe.IRecipeService;
import srinageswari.programmedhousehold.coreservice.validator.ValidItem;

/**
 * @author smanickavasagam
 */
@Validated
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RecipeController {

  private final IRecipeService recipeService;

  /**
   * Fetches recipe by id
   *
   * @param id
   * @return A single recipe
   */
  @GetMapping("/recipe/{id}")
  public ResponseEntity<APIResponseDTO<RecipeDTO>> findById(@PathVariable long id) {
    final RecipeDTO response = recipeService.findById(id);
    return ResponseEntity.ok(
        new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response));
  }

  /**
   * Fetches all recipes based on the given recipe filter parameters
   *
   * @param request
   * @return Paginated recipe data
   */
  @GetMapping("/recipes")
  public ResponseEntity<APIResponseDTO<Page<RecipeDTO>>> findAll(
      @RequestBody SearchRequestDTO request) {
    final Page<RecipeDTO> response = recipeService.findAll(request);
    return ResponseEntity.ok(
        new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response));
  }

  /**
   * Creates a new recipe
   *
   * @return id of the created recipe
   */
  @PostMapping("/recipe")
  public ResponseEntity<APIResponseDTO<CommandResponseDTO>> create(
      @Valid @ValidItem(message = Constants.NOT_VALIDATED_ITEM) @RequestBody RecipeDTO request) {
    final CommandResponseDTO response = recipeService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response));
  }

  /**
   * Creates bulk recipes
   *
   * @return size of the created recipe
   */
  @PostMapping("/bulkinsert/recipes")
  public ResponseEntity<APIResponseDTO<CommandResponseDTO>> bulkInsert(
      @RequestBody List<RecipeDTO> recipeDTOS) {
    final CommandResponseDTO response = recipeService.bulkInsert(recipeDTOS);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response));
  }

  /**
   * Updates given recipe
   *
   * @return id of the updated recipe
   */
  @PutMapping("/recipe")
  public ResponseEntity<APIResponseDTO<CommandResponseDTO>> update(
      @Valid @ValidItem(message = Constants.NOT_VALIDATED_ITEM) @RequestBody RecipeDTO request) {
    final CommandResponseDTO response = recipeService.update(request);
    return ResponseEntity.ok(
        new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response));
  }

  /**
   * Deletes recipe by id
   *
   * @return id of the deleted recipe
   */
  @DeleteMapping("/recipe/{id}")
  public ResponseEntity<APIResponseDTO<Void>> deleteById(@PathVariable long id) {
    recipeService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @GetMapping("/recipes/category/{category_id}")
  public ResponseEntity<APIResponseDTO<List<RecipeDTO>>> search(@PathVariable long category_id) {
    final List<RecipeDTO> response = recipeService.getRecipeByCategoryId(category_id);
    return ResponseEntity.ok(
        new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response));
  }
}

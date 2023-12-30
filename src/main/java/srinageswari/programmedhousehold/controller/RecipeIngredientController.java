package srinageswari.programmedhousehold.controller;

import static srinageswari.programmedhousehold.common.Constants.SUCCESS;

import jakarta.validation.Valid;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srinageswari.programmedhousehold.common.dto.APIResponseDTO;
import srinageswari.programmedhousehold.common.dto.CommandResponseDTO;
import srinageswari.programmedhousehold.dto.recipeingredient.RecipeIngredientRequestDTO;
import srinageswari.programmedhousehold.service.recipeingredient.IRecipeIngredientService;

/**
 * @author smanickavasagam
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RecipeIngredientController {

  private final IRecipeIngredientService recipeIngredientService;

  /**
   * Adds ingredient to a recipe
   *
   * @return id of the recipe to which ingredient is added
   */
  @PostMapping("/recipeIngredients")
  public ResponseEntity<APIResponseDTO<CommandResponseDTO>> addIngredientToRecipe(
      @Valid @RequestBody RecipeIngredientRequestDTO request) {
    final CommandResponseDTO response = recipeIngredientService.addIngredientToRecipe(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new APIResponseDTO<>(Instant.now().toEpochMilli(), SUCCESS, response));
  }

  /**
   * Removes ingredient from a recipe by recipeId and ingredientId
   *
   * @return id of the recipe from which ingredient is removed
   */
  @DeleteMapping("/recipeIngredients/recipes/{recipeId}/ingredients/{ingredientId}")
  public ResponseEntity<APIResponseDTO<Void>> removeIngredientFromRecipe(
      @PathVariable long recipeId, @PathVariable long ingredientId) {
    recipeIngredientService.removeIngredientFromRecipe(recipeId, ingredientId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}

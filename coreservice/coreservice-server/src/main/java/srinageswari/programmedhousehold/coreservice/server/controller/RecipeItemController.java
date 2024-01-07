package srinageswari.programmedhousehold.coreservice.server.controller;

import static srinageswari.programmedhousehold.coreservice.server.common.Constants.SUCCESS;

import jakarta.validation.Valid;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srinageswari.programmedhousehold.coreservice.client.dto.RecipeItemDTO;
import srinageswari.programmedhousehold.coreservice.client.dto.common.APIResponseDTO;
import srinageswari.programmedhousehold.coreservice.client.dto.common.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.server.service.recipeitem.IRecipeItemService;

/**
 * @author smanickavasagam
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RecipeItemController {

  private final IRecipeItemService recipeItemService;

  /**
   * Adds item to a recipe
   *
   * @return id of the recipe to which item is added
   */
  @PostMapping("/recipeItems")
  public ResponseEntity<APIResponseDTO<CommandResponseDTO>> addItemToRecipe(
      @Valid @RequestBody RecipeItemDTO request) {
    final CommandResponseDTO response = recipeItemService.addItemToRecipe(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new APIResponseDTO<>(Instant.now().toEpochMilli(), SUCCESS, response));
  }

  /**
   * Removes item from a recipe by recipeId and itemId
   *
   * @return id of the recipe from which item is removed
   */
  @DeleteMapping("/recipeItems/recipes/{recipeId}/items/{itemId}")
  public ResponseEntity<APIResponseDTO<Void>> removeItemFromRecipe(
      @PathVariable long recipeId, @PathVariable long itemId) {
    recipeItemService.removeItemFromRecipe(recipeId, itemId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}

package srinageswari.programmedhousehold.coreservice.controller;

import static srinageswari.programmedhousehold.coreservice.common.Constants.SUCCESS;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srinageswari.programmedhousehold.coreservice.dto.RecipeItemDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.APIResponseDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.service.recipeitem.IRecipeItemService;

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
        .body(new APIResponseDTO<>(LocalDateTime.now(), SUCCESS, response));
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

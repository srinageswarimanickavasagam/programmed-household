package srinageswari.programmedhousehold.service.recipeingredient;

import srinageswari.programmedhousehold.common.dto.CommandResponseDTO;
import srinageswari.programmedhousehold.dto.recipeingredient.RecipeIngredientRequestDTO;

/**
 * @author smanickavasagam
 */
public interface IRecipeIngredientService {
  public CommandResponseDTO addIngredientToRecipe(RecipeIngredientRequestDTO request);

  public void removeIngredientFromRecipe(Long recipeId, Long ingredientId);
}

package srinageswari.programmedhousehold.service.recipeitem;

import srinageswari.programmedhousehold.common.dto.CommandResponseDTO;
import srinageswari.programmedhousehold.dto.recipeitem.RecipeItemRequestDTO;

/**
 * @author smanickavasagam
 */
public interface IRecipeItemService {
  public CommandResponseDTO addItemToRecipe(RecipeItemRequestDTO request);

  public void removeItemFromRecipe(Long recipeId, Long itemId);
}

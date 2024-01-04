package srinageswari.programmedhousehold.coreservice.service.recipeitem;

import srinageswari.programmedhousehold.coreservice.common.dto.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.dto.recipeitem.RecipeItemRequestDTO;

/**
 * @author smanickavasagam
 */
public interface IRecipeItemService {
  public CommandResponseDTO addItemToRecipe(RecipeItemRequestDTO request);

  public void removeItemFromRecipe(Long recipeId, Long itemId);
}

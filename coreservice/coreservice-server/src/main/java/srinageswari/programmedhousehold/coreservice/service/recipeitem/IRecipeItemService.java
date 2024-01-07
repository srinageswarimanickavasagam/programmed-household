package srinageswari.programmedhousehold.coreservice.service.recipeitem;

import srinageswari.programmedhousehold.coreservice.dto.RecipeItemDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.CommandResponseDTO;

/**
 * @author smanickavasagam
 */
public interface IRecipeItemService {
  public CommandResponseDTO addItemToRecipe(RecipeItemDTO recipeItemDTO);

  public void removeItemFromRecipe(Long recipeId, Long itemId);
}

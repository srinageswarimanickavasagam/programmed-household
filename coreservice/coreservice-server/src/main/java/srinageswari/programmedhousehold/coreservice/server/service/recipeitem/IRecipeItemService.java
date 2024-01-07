package srinageswari.programmedhousehold.coreservice.server.service.recipeitem;

import srinageswari.programmedhousehold.coreservice.client.dto.RecipeItemDTO;
import srinageswari.programmedhousehold.coreservice.client.dto.common.CommandResponseDTO;

/**
 * @author smanickavasagam
 */
public interface IRecipeItemService {
  public CommandResponseDTO addItemToRecipe(RecipeItemDTO recipeItemDTO);

  public void removeItemFromRecipe(Long recipeId, Long itemId);
}

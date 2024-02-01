package srinageswari.programmedhousehold.coreservice.service.recipeitem;

import srinageswari.programmedhousehold.coreservice.dto.RecipeItemDTO;

/**
 * @author smanickavasagam
 */
public interface IRecipeItemService {
  public RecipeItemDTO addItemToRecipe(RecipeItemDTO recipeItemDTO);

  public void removeItemFromRecipe(Long recipeId, Long itemId);
}

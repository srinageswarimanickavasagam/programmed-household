package srinageswari.programmedhousehold.backend.service.recipeitem;

import srinageswari.programmedhousehold.backend.dto.RecipeItemDTO;

/**
 * @author smanickavasagam
 */
public interface IRecipeItemService {
  public RecipeItemDTO addItemToRecipe(RecipeItemDTO recipeItemDTO);

  public void removeItemFromRecipe(Long recipeId, Long itemId);
}

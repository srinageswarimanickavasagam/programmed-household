package srinageswari.programmedhousehold.dto.recipeitem;

import lombok.Data;
import srinageswari.programmedhousehold.model.RecipeItemEntity;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for RecipeItem response
 */
@Data
public class RecipeItemResponseDTO {

  private String recipe;
  private String item;
  private String itemName;
  private int itemQty;
  private String unitName;
  private String type;

  public RecipeItemResponseDTO(RecipeItemEntity recipeItemEntity) {
    this.recipe = recipeItemEntity.getRecipe().getTitle();
    this.item = recipeItemEntity.getItem().getName();
    this.itemName = recipeItemEntity.getItem().getName();
    this.itemQty = recipeItemEntity.getItemQty();
    this.unitName = recipeItemEntity.getUnit().getLabel();
    this.type = recipeItemEntity.getItem().getItemtype().getType();
  }
}

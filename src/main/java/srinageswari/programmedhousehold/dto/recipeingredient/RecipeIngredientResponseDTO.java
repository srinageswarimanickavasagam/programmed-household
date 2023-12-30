package srinageswari.programmedhousehold.dto.recipeingredient;

import lombok.Data;
import srinageswari.programmedhousehold.model.RecipeIngredient;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for RecipeIngredient response
 */
@Data
public class RecipeIngredientResponseDTO {

  private Long id;
  private String ingredientName;
  private int ingredientQty;
  private String unitName;

  public RecipeIngredientResponseDTO(RecipeIngredient recipeIngredient) {
    this.id = recipeIngredient.getItem().getId();
    this.ingredientName = recipeIngredient.getItem().getName();
    this.ingredientQty = recipeIngredient.getIngredientQty();
    this.unitName = recipeIngredient.getUnit().getLabel();
  }
}

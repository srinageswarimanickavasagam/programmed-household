package srinageswari.programmedhousehold.dto.recipeingredient;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import srinageswari.programmedhousehold.common.enums.Unit;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for RecipeIngredient request
 */
@Data
@NoArgsConstructor
public class RecipeIngredientRequestDTO {

  private Long recipeId;

  private Long ingredientId;

  private String ingredientName;

  @NotNull private Unit unit;

  @NotNull private int ingredientQty;
}

package srinageswari.programmedhousehold.coreservice.dto.recipeitem;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import srinageswari.programmedhousehold.coreservice.model.Unit;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for RecipeItem request
 */
@Data
@NoArgsConstructor
public class RecipeItemRequestDTO {

  private Long recipeId;

  private Long itemId;

  private String itemName;

  @NotNull private Unit unit;

  @NotNull private int itemQty;

  @NotNull private String type;
}

package srinageswari.programmedhousehold.coreservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import srinageswari.programmedhousehold.coreservice.enums.Unit;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for RecipeItem
 */
@Data
@NoArgsConstructor
public class RecipeItemDTO {
  private RecipeDTO recipe;

  @NotNull private ItemDTO item;

  @NotNull private Unit unit;

  @NotNull private int itemQty;
}

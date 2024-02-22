package srinageswari.programmedhousehold.backend.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import srinageswari.programmedhousehold.backend.enums.CulinaryStep;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for RecipeItem
 */
@Data
@NoArgsConstructor
public class RecipeItemDTO {
  @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
  private RecipeDTO recipe;

  @NotNull private ItemDTO item;

  @NotNull private BigDecimal requiredQty;

  private CulinaryStep culinaryStep;
}

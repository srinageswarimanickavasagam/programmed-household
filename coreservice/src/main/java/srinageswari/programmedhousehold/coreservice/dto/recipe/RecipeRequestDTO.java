package srinageswari.programmedhousehold.coreservice.dto.recipe;

import jakarta.validation.constraints.*;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import srinageswari.programmedhousehold.coreservice.dto.recipeitem.RecipeItemRequestDTO;
import srinageswari.programmedhousehold.coreservice.model.AppUserEntity;
import srinageswari.programmedhousehold.coreservice.model.CategoryEntity;
import srinageswari.programmedhousehold.coreservice.model.Cuisine;
import srinageswari.programmedhousehold.coreservice.model.HealthLabel;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for Recipe request
 */
@Data
@NoArgsConstructor
public class RecipeRequestDTO {
  private Long id;

  @NotBlank
  @Size(min = 3, max = 50)
  private String title;

  private String description;

  private Integer prepTime;

  private Integer cookTime;

  @Min(1)
  @Max(12)
  private Integer servings;

  @NotBlank private String instructions;

  @NotNull private HealthLabel healthLabel;

  private Cuisine cuisine;

  @NotNull private CategoryEntity category;

  private boolean isActive;

  private AppUserEntity appUser;

  private List<RecipeItemRequestDTO> recipeItemRequests;
}

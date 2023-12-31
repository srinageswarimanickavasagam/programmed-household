package srinageswari.programmedhousehold.dto.recipe;

import jakarta.validation.constraints.*;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import srinageswari.programmedhousehold.dto.recipeitem.RecipeItemRequestDTO;
import srinageswari.programmedhousehold.model.AppUserEntity;
import srinageswari.programmedhousehold.model.CategoryEntity;
import srinageswari.programmedhousehold.model.Cuisine;
import srinageswari.programmedhousehold.model.HealthLabel;

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

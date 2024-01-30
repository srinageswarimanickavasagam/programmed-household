package srinageswari.programmedhousehold.coreservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import srinageswari.programmedhousehold.coreservice.enums.Cuisine;
import srinageswari.programmedhousehold.coreservice.enums.HealthLabel;

@Data
@NoArgsConstructor
public class RecipeDTO {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("title")
  @NotBlank
  @Size(min = 3, max = 50)
  private String title;

  @JsonProperty("reference")
  private String reference;

  @JsonProperty("prepTime")
  private Integer prepTime;

  @JsonProperty("cookTime")
  private Integer cookTime;

  @Min(1)
  @Max(12)
  @NotNull
  @JsonProperty("servings")
  private Integer servings;

  @JsonProperty("instructions")
  private String instructions;

  @JsonProperty("healthLabel")
  private HealthLabel healthLabel;

  @JsonProperty("cuisine")
  private Cuisine cuisine;

  @JsonProperty("category")
  @NotNull
  private CategoryDTO category;

  @JsonProperty("active")
  private boolean isActive;

  @JsonProperty("appUser")
  private AppUserDTO appUser;

  @JsonProperty("recipeItems")
  private List<RecipeItemDTO> recipeItems;

  @JsonProperty("scheduledAt")
  private Date scheduledAt;

  @JsonProperty("notes")
  private String notes;
}

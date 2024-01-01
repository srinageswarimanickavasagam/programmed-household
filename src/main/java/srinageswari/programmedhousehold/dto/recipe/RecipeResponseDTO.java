package srinageswari.programmedhousehold.dto.recipe;

import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import srinageswari.programmedhousehold.dto.recipeitem.RecipeItemResponseDTO;
import srinageswari.programmedhousehold.model.RecipeEntity;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for Recipe response
 */
@Data
@RequiredArgsConstructor
public class RecipeResponseDTO {

  private Long id;
  private String title;
  private String description;
  private Integer prepTime;
  private Integer cookTime;
  private Integer servings;
  private String instructions;
  private String healthLabel;
  private String userEmail;
  private String cuisine;
  private String category;
  private boolean isActive;
  private List<RecipeItemResponseDTO> recipeItemResponses;

  public RecipeResponseDTO(
      RecipeEntity recipeEntity, List<RecipeItemResponseDTO> recipeItemResponses) {
    this.id = recipeEntity.getId();
    this.title = recipeEntity.getTitle();
    this.description = recipeEntity.getDescription();
    this.prepTime = recipeEntity.getPrepTime();
    this.cookTime = recipeEntity.getCookTime();
    this.servings = recipeEntity.getServings();
    this.instructions = recipeEntity.getInstructions();
    this.healthLabel = recipeEntity.getHealthLabel().getLabel();
    this.recipeItemResponses = recipeItemResponses;
    this.userEmail = recipeEntity.getAppUser().getEmail();
    this.cuisine = recipeEntity.getCuisine().getLabel();
    this.category = recipeEntity.getCategory().getName();
    this.isActive = recipeEntity.isActive();
  }
}

package srinageswari.programmedhousehold.dto.recipe;

import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import srinageswari.programmedhousehold.dto.recipeingredient.RecipeIngredientResponseDTO;
import srinageswari.programmedhousehold.model.Recipe;

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
  private List<RecipeIngredientResponseDTO> ingredients;

  public RecipeResponseDTO(Recipe recipe, List<RecipeIngredientResponseDTO> ingredients) {
    this.id = recipe.getId();
    this.title = recipe.getTitle();
    this.description = recipe.getDescription();
    this.prepTime = recipe.getPrepTime();
    this.cookTime = recipe.getCookTime();
    this.servings = recipe.getServings();
    this.instructions = recipe.getInstructions();
    this.healthLabel = recipe.getHealthLabel().getLabel();
    this.ingredients = ingredients;
    this.userEmail = recipe.getAppUser().getEmail();
    this.cuisine = recipe.getCuisine().getLabel();
    this.category = recipe.getCategory().getName();
    this.isActive = recipe.isActive();
  }
}

package srinageswari.programmedhousehold.coreservice.client.dto;

import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class RecipeDocument {
  private int recipeId;
  private int userId;
  private String recipeName;
  private boolean sidedish;
  private List<IngredientAttributes> ingredientList;
  private Date scheduledDt;
  private boolean active;
  private CategoryAttributes categoryAttributes;
}

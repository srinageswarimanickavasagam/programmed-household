package srinageswari.programmedhousehold.elasticsearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class RecipeSearchDTO {
  private Long recipeId;
  private String title;
  private String reference;
  private Integer prepTime;
  private Integer cookTime;
  private String instructions;
  private String healthLabel;
  private String cuisine;
  private String category;
  private String meal;
  private String scheduleDay;
  private String difficulty;
  private Boolean sideDish;
  private String scheduledOn;
  private String notes;

  @JsonProperty("ingredients")
  Map<String, Map<String, String>> ingredientsCulinaryStepMap = new HashMap<>();
}

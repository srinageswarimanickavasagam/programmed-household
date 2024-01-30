package srinageswari.programmedhousehold.coreservice.service.recipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import srinageswari.programmedhousehold.coreservice.enums.CulinaryStep;
import srinageswari.programmedhousehold.coreservice.model.RecipeEntity;
import srinageswari.programmedhousehold.coreservice.model.RecipeItemEntity;

@Component
public class ElasticsearchService {

  public String saveToElasticsearch(RecipeEntity recipe) {
    return convertDataToJsonString(recipe);
  }

  private String convertDataToJsonString(RecipeEntity recipe) {
    ObjectNode jsonObject = new ObjectMapper().createObjectNode();
    jsonObject.put("RecipeId", recipe.getId());
    jsonObject.put("Title", recipe.getTitle());
    jsonObject.put("Reference", recipe.getReference());
    jsonObject.put("PrepTime", recipe.getPrepTime());
    jsonObject.put("CookTime", recipe.getCookTime());
    jsonObject.put("Instructions", recipe.getInstructions());
    jsonObject.put(
        "HealthLabel", recipe.getHealthLabel() != null ? recipe.getHealthLabel().getLabel() : null);
    jsonObject.put("Cuisine", recipe.getCuisine().getLabel());
    jsonObject.put("Category", recipe.getCategory().getName());
    jsonObject.put("Meal", recipe.getCategory().getMeal().getLabel());
    jsonObject.put(
        "ScheduleDay",
        recipe.getCategory().getDay() != null ? recipe.getCategory().getDay().getLabel() : null);
    jsonObject.put("Difficulty", recipe.getCategory().getDifficulty().getLabel());
    jsonObject.put("Side-dish", recipe.getCategory().isSidedish());
    jsonObject.put(
        "ScheduledOn", recipe.getScheduledDt() != null ? recipe.getScheduledDt().toString() : null);
    jsonObject.put("Notes", recipe.getNotes());

    ObjectNode allIngredients = JsonNodeFactory.instance.objectNode();

    Map<CulinaryStep, List<RecipeItemEntity>> groupedByCulinaryStep =
        recipe.getRecipeItems().stream()
            .collect(Collectors.groupingBy(RecipeItemEntity::getCulinaryStep));

    groupedByCulinaryStep.forEach(
        (culinaryStep, recipeItems) -> {
          ObjectNode recipeItemJsonObject = JsonNodeFactory.instance.objectNode();
          recipeItems.forEach(
              recipeItem -> {
                recipeItemJsonObject.put(
                    recipeItem.getItem().getName(),
                    recipeItem.getRequiredQty().toString() + recipeItem.getUnit());
              });
          allIngredients.set(culinaryStep.name(), recipeItemJsonObject);
        });
    jsonObject.set("Ingredients", allIngredients);
    return jsonObject.toString();
  }
}

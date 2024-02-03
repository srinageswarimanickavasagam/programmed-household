package srinageswari.programmedhousehold.coreservice.service.recipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import srinageswari.programmedhousehold.coreservice.enums.CulinaryStep;
import srinageswari.programmedhousehold.coreservice.model.RecipeEntity;
import srinageswari.programmedhousehold.coreservice.model.RecipeItemEntity;

@Service
public class ElasticsearchService {

  public String saveToElasticsearch(RecipeEntity recipe) {
    return convertDataToJsonString(recipe);
  }

  private String convertDataToJsonString(RecipeEntity recipe) {
    ObjectNode jsonObject = new ObjectMapper().createObjectNode();
    jsonObject.put("recipe_id", recipe.getId());
    jsonObject.put("title", recipe.getTitle());
    jsonObject.put("reference", recipe.getReference());
    jsonObject.put("prep_time", recipe.getPrepTime());
    jsonObject.put("cook_time", recipe.getCookTime());
    jsonObject.put("instructions", recipe.getInstructions());
    jsonObject.put(
        "health_label",
        recipe.getHealthLabel() != null ? recipe.getHealthLabel().getLabel() : null);
    jsonObject.put("cuisine", recipe.getCuisine().getLabel());
    jsonObject.put("category", recipe.getCategory().getName());
    jsonObject.put("meal", recipe.getCategory().getMeal().getLabel());
    jsonObject.put(
        "schedule_day",
        recipe.getCategory().getDay() != null ? recipe.getCategory().getDay().getLabel() : null);
    jsonObject.put("difficulty", recipe.getCategory().getDifficulty().getLabel());
    jsonObject.put("side_dish", recipe.getCategory().isSidedish());
    jsonObject.put(
        "scheduled_on",
        recipe.getScheduledDt() != null ? recipe.getScheduledDt().toString() : null);
    jsonObject.put("notes", recipe.getNotes());

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
                    recipeItem.getRequiredQty().toString() + recipeItem.getUnit().getLabel());
              });
          allIngredients.set(culinaryStep.getLabel(), recipeItemJsonObject);
        });
    jsonObject.set("ingredients", allIngredients);
    return jsonObject.toString();
  }
}

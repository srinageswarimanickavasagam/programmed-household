package srinageswari.programmedhousehold.elasticsearch.common;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import srinageswari.programmedhousehold.coreservice.dto.RecipeDTO;
import srinageswari.programmedhousehold.coreservice.dto.RecipeItemDTO;
import srinageswari.programmedhousehold.coreservice.enums.CulinaryStep;
import srinageswari.programmedhousehold.elasticsearch.dto.RecipeSearchDTO;

@Slf4j(topic = "ElasticsearchUtil")
@RequiredArgsConstructor
@Component
public class ElasticsearchUtil {
  public RecipeSearchDTO getRecipeSearchDocument(RecipeDTO recipe) {

    RecipeSearchDTO recipeSearchDTO = new RecipeSearchDTO();
    recipeSearchDTO.setRecipeId(recipe.getId());
    recipeSearchDTO.setTitle(recipe.getTitle());
    recipeSearchDTO.setReference(recipe.getReference());
    recipeSearchDTO.setPrepTime(recipe.getPrepTime());
    recipeSearchDTO.setCookTime(recipe.getCookTime());
    recipeSearchDTO.setInstructions(recipe.getInstructions());
    recipeSearchDTO.setHealthLabel(
        recipe.getHealthLabel() != null ? recipe.getHealthLabel().getLabel() : null);
    recipeSearchDTO.setCuisine(recipe.getCuisine().getLabel());
    recipeSearchDTO.setCategory(recipe.getCategory().getName());
    recipeSearchDTO.setMeal(recipe.getCategory().getMeal().getLabel());
    recipeSearchDTO.setScheduleDay(
        recipe.getCategory().getDay() != null ? recipe.getCategory().getDay().getLabel() : null);
    recipeSearchDTO.setDifficulty(recipe.getCategory().getDifficulty().getLabel());
    recipeSearchDTO.setSideDish(recipe.getCategory().isSidedish());
    recipeSearchDTO.setScheduledOn(
        recipe.getScheduledDt() != null ? recipe.getScheduledDt().toString() : null);
    recipeSearchDTO.setNotes(recipe.getNotes());

    Map<CulinaryStep, List<RecipeItemDTO>> groupedByCulinaryStep =
        recipe.getRecipeItems().stream()
            .collect(Collectors.groupingBy(RecipeItemDTO::getCulinaryStep));

    Map<String, Map<String, String>> ingredients = new HashMap<>();

    groupedByCulinaryStep.forEach(
        (culinaryStep, recipeItems) -> {
          Map<String, String> ingredientQtyMap = new HashMap<>();
          recipeItems.forEach(
              recipeItem -> {
                ingredientQtyMap.put(
                    recipeItem.getItem().getName(),
                    formatQty(recipeItem.getRequiredQty())
                        + recipeItem.getItem().getItemtype().getRecipeUnit().getLabel());
              });
          ingredients.put(culinaryStep.getLabel(), ingredientQtyMap);
        });
    recipeSearchDTO.setIngredientsCulinaryStepMap(ingredients);
    return recipeSearchDTO;
  }

  private String formatQty(BigDecimal qty) {
    return (qty.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0)
        ? String.valueOf(qty.intValue())
        : qty.stripTrailingZeros().toPlainString();
  }
}

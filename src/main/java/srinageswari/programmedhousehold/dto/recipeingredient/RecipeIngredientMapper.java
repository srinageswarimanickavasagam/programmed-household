package srinageswari.programmedhousehold.dto.recipeingredient;

import srinageswari.programmedhousehold.common.enums.Unit;
import srinageswari.programmedhousehold.model.Item;
import srinageswari.programmedhousehold.model.Recipe;
import srinageswari.programmedhousehold.model.RecipeIngredient;

/**
 * @author smanickavasagam
 *     <p>Mapper for RecipeIngredientRequest
 */
public class RecipeIngredientMapper {

  private RecipeIngredientMapper() {}

  /**
   * Maps RecipeIngredientRequest fields to a new RecipeIngredient
   *
   * @param recipe
   * @param item
   * @param unit
   * @param quantity
   * @return RecipeIngredient model
   */
  public static RecipeIngredient mapToEntity(Recipe recipe, Item item, Unit unit, int quantity) {
    return new RecipeIngredient(recipe, item, unit, quantity);
  }
}

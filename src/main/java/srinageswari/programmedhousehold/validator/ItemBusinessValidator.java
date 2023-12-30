package srinageswari.programmedhousehold.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import srinageswari.programmedhousehold.dto.recipe.RecipeRequestDTO;
import srinageswari.programmedhousehold.dto.recipeingredient.RecipeIngredientRequestDTO;

/**
 * @author smanickavasagam
 *     <p>Validates the items in the RecipeRequest. If the request contains duplicate items returns
 *     error
 */
@Slf4j(topic = "ItemBusinessValidator")
@RequiredArgsConstructor
public class ItemBusinessValidator implements ConstraintValidator<ValidItem, RecipeRequestDTO> {

  @Override
  public void initialize(ValidItem constraintAnnotation) {}

  @Override
  public boolean isValid(RecipeRequestDTO request, ConstraintValidatorContext context) {
    return areAllUnique(
        request.getRecipeIngredients().stream()
            .map(RecipeIngredientRequestDTO::getIngredientName)
            .toList());
  }

  /**
   * Checks if all the given list values are unique or not
   *
   * @param list
   * @param <T>
   * @return
   */
  public <T> boolean areAllUnique(List<T> list) {
    return list.stream().allMatch(new HashSet<>()::add);
  }
}

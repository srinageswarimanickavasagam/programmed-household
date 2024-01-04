package srinageswari.programmedhousehold.coreservice.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import srinageswari.programmedhousehold.coreservice.dto.recipe.RecipeRequestDTO;
import srinageswari.programmedhousehold.coreservice.dto.recipeitem.RecipeItemRequestDTO;

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
        request.getRecipeItemRequests().stream().map(RecipeItemRequestDTO::getItemName).toList());
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

package srinageswari.programmedhousehold.coreservice.service.recipe;

import static org.apache.commons.text.WordUtils.capitalizeFully;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import srinageswari.programmedhousehold.coreservice.common.Constants;
import srinageswari.programmedhousehold.coreservice.common.exception.helper.NoSuchElementFoundException;
import srinageswari.programmedhousehold.coreservice.common.search.SearchSpecification;
import srinageswari.programmedhousehold.coreservice.dto.RecipeDTO;
import srinageswari.programmedhousehold.coreservice.dto.RecipeResponseDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.SearchRequestDTO;
import srinageswari.programmedhousehold.coreservice.enums.CulinaryStep;
import srinageswari.programmedhousehold.coreservice.mapper.RecipeMapper;
import srinageswari.programmedhousehold.coreservice.model.*;
import srinageswari.programmedhousehold.coreservice.repository.RecipeRepository;
import srinageswari.programmedhousehold.coreservice.service.appuser.AppUserServiceImpl;
import srinageswari.programmedhousehold.coreservice.service.recipeitem.RecipeItemServiceImpl;

/**
 * @author smanickavasagam
 *     <p>Service used for adding, updating, removing and fetching recipes
 */
@Slf4j(topic = "RecipeServiceImpl")
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements IRecipeService {
  private final RecipeRepository recipeRepository;
  private final RecipeMapper recipeMapper;
  private final AppUserServiceImpl appUserServiceImpl;
  private final ElasticsearchService elasticsearchService;
  private final RecipeItemServiceImpl recipeItemServiceImpl;

  @Value("${app.security.enabled}")
  private boolean isSecurityEnabled;

  /**
   * Fetches a recipe by the given id
   *
   * @param id
   * @return
   */
  @Transactional(readOnly = true)
  public RecipeResponseDTO findById(Long id) {
    return recipeRepository
        .findById(id)
        .map(this::getRecipeResponseDTO)
        .orElseThrow(
            () -> {
              log.error(Constants.NOT_FOUND_RECIPE);
              return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
            });
  }

  /**
   * Fetches all recipes based on the given recipe filter parameters
   *
   * @param request
   * @return Paginated recipe data
   */
  @Transactional(readOnly = true)
  public Page<RecipeResponseDTO> findAll(SearchRequestDTO request) {
    final SearchSpecification<RecipeEntity> specification = new SearchSpecification<>(request);
    final Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
    final Page<RecipeResponseDTO> recipes =
        recipeRepository.findAll(specification, pageable).map(this::getRecipeResponseDTO);
    if (recipes.isEmpty()) {
      log.error(Constants.NOT_FOUND_RECORD);
      throw new NoSuchElementFoundException(Constants.NOT_FOUND_RECORD);
    }
    return recipes;
  }

  /**
   * Creates a new recipe and items belonging to the recipe using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  public RecipeResponseDTO create(RecipeDTO request) {
    RecipeEntity recipe = recipeRepository.save(constructRecipeEntity(request));
    return getRecipeResponseDTO(recipe);
  }

  @Transactional
  public List<RecipeResponseDTO> bulkInsert(List<RecipeDTO> recipeDTOList) {
    List<RecipeEntity> entities = recipeDTOList.stream().map(this::constructRecipeEntity).toList();
    List<RecipeEntity> response = recipeRepository.saveAll(entities);
    return response.stream().map(this::getRecipeResponseDTO).toList();
  }

  /**
   * Updates recipe using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  // for adding/removing items for a current recipe, use RecipeItemService methods
  public RecipeResponseDTO update(RecipeDTO request) {
    final RecipeEntity recipeEntity =
        recipeRepository
            .findById(request.getId())
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_RECIPE);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
                });
    recipeEntity.setTitle(capitalizeFully(request.getTitle()));
    recipeEntity.setReference(request.getReference());
    recipeEntity.setPrepTime(request.getPrepTime());
    recipeEntity.setCookTime(request.getCookTime());
    recipeEntity.setServings(request.getServings());
    recipeEntity.setInstructions(request.getInstructions());
    recipeEntity.setHealthLabel(request.getHealthLabel());
    return getRecipeResponseDTO(recipeRepository.save(recipeEntity));
  }

  /**
   * Deletes recipe by the given id
   *
   * @param id
   * @return
   */
  public void deleteById(Long id) {
    final RecipeEntity recipeEntity =
        recipeRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_RECIPE);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
                });
    recipeRepository.delete(recipeEntity);
  }

  public List<RecipeResponseDTO> getRecipeByCategoryId(Long id) {
    return recipeRepository.findRecipesByCategoryId(id).stream()
        .map(this::getRecipeResponseDTO)
        .toList();
  }

  public RecipeEntity constructRecipeEntity(RecipeDTO request) {
    final RecipeEntity recipeEntity = recipeMapper.toEntity(request);
    recipeEntity.getRecipeItems().clear();
    request
        .getRecipeItems()
        .forEach(
            recipeItemDTO ->
                recipeEntity.addRecipeItem(
                    new RecipeItemEntity(
                        recipeEntity,
                        recipeItemServiceImpl.processRecipeItem(recipeItemDTO),
                        recipeItemDTO.getRequiredQty(),
                        recipeItemDTO.getCulinaryStep())));
    recipeEntity.setAppUser(
        isSecurityEnabled ? appUserServiceImpl.getCurrentLoggedInUser() : new AppUserEntity(1L));
    recipeEntity.setId(uuidToLong(UUID.randomUUID()));
    return recipeEntity;
  }

  public static Long uuidToLong(UUID uuid) {
    long mostSigBits = uuid.getMostSignificantBits();
    long leastSigBits = uuid.getLeastSignificantBits();

    // Combine the most and least significant bits to create a long value
    return (mostSigBits & Long.MAX_VALUE) ^ (leastSigBits & Long.MAX_VALUE);
  }

  public RecipeResponseDTO getRecipeResponseDTO(RecipeEntity recipe) {

    RecipeResponseDTO recipeResponseDTO = new RecipeResponseDTO();
    recipeResponseDTO.setRecipeId(recipe.getId());
    recipeResponseDTO.setTitle(recipe.getTitle());
    recipeResponseDTO.setReference(recipe.getReference());
    recipeResponseDTO.setPrepTime(recipe.getPrepTime());
    recipeResponseDTO.setCookTime(recipe.getCookTime());
    recipeResponseDTO.setInstructions(recipe.getInstructions());
    recipeResponseDTO.setHealthLabel(
        recipe.getHealthLabel() != null ? recipe.getHealthLabel().getLabel() : null);
    recipeResponseDTO.setCuisine(recipe.getCuisine().getLabel());
    recipeResponseDTO.setCategory(recipe.getCategory().getName());
    recipeResponseDTO.setMeal(recipe.getCategory().getMeal().getLabel());
    recipeResponseDTO.setScheduleDay(
        recipe.getCategory().getDay() != null ? recipe.getCategory().getDay().getLabel() : null);
    recipeResponseDTO.setDifficulty(recipe.getCategory().getDifficulty().getLabel());
    recipeResponseDTO.setSideDish(recipe.getCategory().isSidedish());
    recipeResponseDTO.setScheduledOn(
        recipe.getScheduledDt() != null ? recipe.getScheduledDt().toString() : null);
    recipeResponseDTO.setNotes(recipe.getNotes());

    Map<CulinaryStep, List<RecipeItemEntity>> groupedByCulinaryStep =
        recipe.getRecipeItems().stream()
            .collect(Collectors.groupingBy(RecipeItemEntity::getCulinaryStep));

    Map<String, Map<String, String>> ingredients = new HashMap<>();

    groupedByCulinaryStep.forEach(
        (culinaryStep, recipeItems) -> {
          Map<String, String> ingredientQtyMap = new HashMap<>();
          recipeItems.forEach(
              recipeItem -> {
                ingredientQtyMap.put(
                    recipeItem.getItem().getName(),
                    formatQty(recipeItem.getRequiredQty())
                        + recipeItem.getItem().getRecipeUnit().getLabel());
              });
          ingredients.put(culinaryStep.getLabel(), ingredientQtyMap);
        });
    recipeResponseDTO.setIngredientsCulinaryStepMap(ingredients);
    return recipeResponseDTO;
  }

  public static String formatQty(BigDecimal qty) {
    return (qty.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0)
        ? String.valueOf(qty.intValue())
        : qty.stripTrailingZeros().toPlainString();
  }
}

package srinageswari.programmedhousehold.coreservice.service.recipe;

import static org.apache.commons.text.WordUtils.capitalizeFully;

import java.util.List;
import java.util.UUID;
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
import srinageswari.programmedhousehold.coreservice.dto.common.SearchRequestDTO;
import srinageswari.programmedhousehold.coreservice.mapper.RecipeMapper;
import srinageswari.programmedhousehold.coreservice.model.*;
import srinageswari.programmedhousehold.coreservice.repository.ItemRepository;
import srinageswari.programmedhousehold.coreservice.repository.ItemtypeRepository;
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
  private final ItemRepository itemRepository;
  private final RecipeMapper recipeMapper;
  private final AppUserServiceImpl appUserServiceImpl;
  private final ItemtypeRepository itemtypeRepository;
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
  public RecipeDTO findById(Long id) {
    return recipeRepository
        .findById(id)
        .map(recipeMapper::toDto)
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
  public Page<RecipeDTO> findAll(SearchRequestDTO request) {
    final SearchSpecification<RecipeEntity> specification = new SearchSpecification<>(request);
    final Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
    final Page<RecipeDTO> recipes =
        recipeRepository.findAll(specification, pageable).map(recipeMapper::toDto);
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
  public RecipeDTO create(RecipeDTO request) {
    RecipeEntity recipe = recipeRepository.save(constructRecipeEntity(request));
    return recipeMapper.toDto(recipe);
  }

  @Transactional
  public List<RecipeDTO> bulkInsert(List<RecipeDTO> recipeDTOList) {
    List<RecipeEntity> entities = recipeDTOList.stream().map(this::constructRecipeEntity).toList();
    List<RecipeEntity> response = recipeRepository.saveAll(entities);
    return response.stream().map(recipeMapper::toDto).toList();
  }

  /**
   * Updates recipe using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  // for adding/removing items for a current recipe, use RecipeItemService methods
  public RecipeDTO update(RecipeDTO request) {
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
    return recipeMapper.toDto(recipeRepository.save(recipeEntity));
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

  public List<RecipeDTO> getRecipeByCategoryId(Long id) {
    return recipeRepository.findrecipesByCategoryId(id).stream().map(recipeMapper::toDto).toList();
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
                        recipeItemDTO.getUnit(),
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
}

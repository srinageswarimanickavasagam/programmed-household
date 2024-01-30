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
import srinageswari.programmedhousehold.coreservice.common.exception.helper.ElementAlreadyExistsException;
import srinageswari.programmedhousehold.coreservice.common.exception.helper.NoSuchElementFoundException;
import srinageswari.programmedhousehold.coreservice.common.search.SearchSpecification;
import srinageswari.programmedhousehold.coreservice.dto.RecipeDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.SearchRequestDTO;
import srinageswari.programmedhousehold.coreservice.mapper.RecipeMapper;
import srinageswari.programmedhousehold.coreservice.model.*;
import srinageswari.programmedhousehold.coreservice.repository.ItemRepository;
import srinageswari.programmedhousehold.coreservice.repository.ItemtypeRepository;
import srinageswari.programmedhousehold.coreservice.repository.RecipeRepository;
import srinageswari.programmedhousehold.coreservice.service.appuser.AppUserServiceImpl;

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
  public CommandResponseDTO create(RecipeDTO request) {
    final RecipeEntity recipeEntity = recipeMapper.toEntity(request);
    recipeEntity.getRecipeItems().clear();
    request
        .getRecipeItems()
        .forEach(
            recipeItemRequestDTO -> {
              final ItemEntity itemEntity;
              if (recipeItemRequestDTO.getItem().getId() != 0) {
                itemEntity =
                    itemRepository
                        .findById(recipeItemRequestDTO.getItem().getId())
                        .orElseThrow(
                            () -> {
                              log.error(Constants.NOT_FOUND_ITEM);
                              return new NoSuchElementFoundException(Constants.NOT_FOUND_ITEM);
                            });
              } else {
                // check if the new item is already defined before
                if (itemRepository.existsByNameIgnoreCase(
                    recipeItemRequestDTO.getItem().getName())) {
                  log.error(
                      String.format(
                          Constants.ALREADY_EXISTS_ITEM, recipeItemRequestDTO.getItem().getId()));
                  throw new ElementAlreadyExistsException(
                      String.format(
                          Constants.ALREADY_EXISTS_ITEM, recipeItemRequestDTO.getItem().getId()));
                }
                ItemtypeEntity itemtype =
                    itemtypeRepository.findByType(
                        recipeItemRequestDTO.getItem().getItemtype().getType());
                if (null == itemtype) {
                  itemtype =
                      itemtypeRepository.save(
                          new ItemtypeEntity(
                              0L, recipeItemRequestDTO.getItem().getItemtype().getType()));
                }
                itemEntity =
                    itemRepository.save(
                        new ItemEntity(0L, recipeItemRequestDTO.getItem().getName(), itemtype));
              }
              recipeEntity.addRecipeItem(
                  new RecipeItemEntity(
                      recipeEntity,
                      itemEntity,
                      recipeItemRequestDTO.getUnit(),
                      recipeItemRequestDTO.getRequiredQty(),
                      recipeItemRequestDTO.getCulinaryStep()));
            });
    recipeEntity.setAppUser(
        isSecurityEnabled ? appUserServiceImpl.getCurrentLoggedInUser() : new AppUserEntity(1L));
    recipeEntity.setId(uuidToLong(UUID.randomUUID()));
    RecipeEntity recipe = recipeRepository.save(recipeEntity);
    String json = elasticsearchService.saveToElasticsearch(recipe);
    return CommandResponseDTO.builder().id(recipe.getId()).response(json).build();
  }

  /**
   * Updates recipe using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  // for adding/removing items for a current recipe, use RecipeItemService methods
  public CommandResponseDTO update(RecipeDTO request) {
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
    recipeRepository.save(recipeEntity);
    return CommandResponseDTO.builder().id(recipeEntity.getId()).build();
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

  public static Long uuidToLong(UUID uuid) {
    long mostSigBits = uuid.getMostSignificantBits();
    long leastSigBits = uuid.getLeastSignificantBits();

    // Combine the most and least significant bits to create a long value
    return (mostSigBits & Long.MAX_VALUE) ^ (leastSigBits & Long.MAX_VALUE);
  }
}

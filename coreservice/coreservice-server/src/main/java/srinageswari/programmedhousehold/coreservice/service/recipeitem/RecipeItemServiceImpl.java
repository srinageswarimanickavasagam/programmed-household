package srinageswari.programmedhousehold.coreservice.service.recipeitem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import srinageswari.programmedhousehold.coreservice.common.Constants;
import srinageswari.programmedhousehold.coreservice.common.exception.helper.ElementAlreadyExistsException;
import srinageswari.programmedhousehold.coreservice.common.exception.helper.NoSuchElementFoundException;
import srinageswari.programmedhousehold.coreservice.dto.RecipeItemDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.model.ItemEntity;
import srinageswari.programmedhousehold.coreservice.model.ItemtypeEntity;
import srinageswari.programmedhousehold.coreservice.model.RecipeEntity;
import srinageswari.programmedhousehold.coreservice.model.RecipeItemEntity;
import srinageswari.programmedhousehold.coreservice.repository.ItemRepository;
import srinageswari.programmedhousehold.coreservice.repository.ItemtypeRepository;
import srinageswari.programmedhousehold.coreservice.repository.RecipeItemRepository;

/**
 * @author smanickavasagam
 *     <p>Service used for adding and removing recipeItem
 */
@Slf4j(topic = "RecipeItemService")
@Service
@RequiredArgsConstructor
public class RecipeItemServiceImpl implements IRecipeItemService {

  private final RecipeItemRepository recipeItemRepository;
  private final ItemRepository itemRepository;
  private final ItemtypeRepository itemtypeRepository;

  /**
   * Adds item to the given recipe
   *
   * @param request
   * @return
   */
  @Transactional
  public CommandResponseDTO addItemToRecipe(RecipeItemDTO request) {
    final ItemEntity itemEntity;
    if (request.getItem().getId() != 0) {
      // check if the item is already defined for the recipe
      if (recipeItemRepository.existsByRecipeIdAndItemId(
          request.getRecipe().getId(), request.getItem().getId())) {
        log.error(Constants.ALREADY_EXISTS_ITEM);
        throw new ElementAlreadyExistsException(
            String.format(Constants.ALREADY_EXISTS_ITEM, request.getItem().getId()));
      }
      itemEntity = new ItemEntity(request.getItem().getId());
    } else {
      // check if the new item is already defined before
      if (itemRepository.existsByNameIgnoreCase(request.getItem().getName())) {
        log.error(Constants.ALREADY_EXISTS_ITEM);
        throw new ElementAlreadyExistsException(
            String.format(Constants.ALREADY_EXISTS_ITEM, request.getItem()));
      }
      ItemtypeEntity itemtype =
          itemtypeRepository.findByType(request.getItem().getItemtype().getType());
      if (null == itemtype) {
        itemtype =
            itemtypeRepository.save(
                new ItemtypeEntity(0L, request.getItem().getItemtype().getType()));
      }
      itemEntity = itemRepository.save(new ItemEntity(0L, request.getItem().getName(), itemtype));
    }
    // if needed, we can also check if recipe and unit values exists in db (we assumed recipe is
    // already defined and unit is selected from the list)
    final RecipeEntity recipeEntity = new RecipeEntity(request.getRecipe().getId());
    final RecipeItemEntity recipeItemEntity =
        new RecipeItemEntity(
            recipeEntity,
            itemEntity,
            request.getUnit(),
            request.getRequiredQty(),
            request.getCulinaryStep());

    recipeItemRepository.save(recipeItemEntity);
    return CommandResponseDTO.builder().id(recipeItemEntity.getRecipe().getId()).build();
  }

  /**
   * Removes item from the given recipe
   *
   * @param recipeId
   * @param itemId
   * @return
   */
  public void removeItemFromRecipe(Long recipeId, Long itemId) {
    final RecipeItemEntity recipeItemEntity =
        recipeItemRepository
            .findByRecipeIdAndItemId(recipeId, itemId)
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_ITEM);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_ITEM);
                });
    recipeItemRepository.delete(recipeItemEntity);
  }
}

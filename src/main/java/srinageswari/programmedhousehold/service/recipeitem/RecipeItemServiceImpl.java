package srinageswari.programmedhousehold.service.recipeitem;

import static srinageswari.programmedhousehold.common.Constants.ALREADY_EXISTS_ITEM;
import static srinageswari.programmedhousehold.common.Constants.NOT_FOUND_ITEM;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import srinageswari.programmedhousehold.common.dto.CommandResponseDTO;
import srinageswari.programmedhousehold.common.exception.helper.ElementAlreadyExistsException;
import srinageswari.programmedhousehold.common.exception.helper.NoSuchElementFoundException;
import srinageswari.programmedhousehold.dto.recipeitem.RecipeItemRequestDTO;
import srinageswari.programmedhousehold.model.ItemEntity;
import srinageswari.programmedhousehold.model.ItemtypeEntity;
import srinageswari.programmedhousehold.model.RecipeEntity;
import srinageswari.programmedhousehold.model.RecipeItemEntity;
import srinageswari.programmedhousehold.repository.ItemRepository;
import srinageswari.programmedhousehold.repository.ItemtypeRepository;
import srinageswari.programmedhousehold.repository.RecipeItemRepository;

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
  public CommandResponseDTO addItemToRecipe(RecipeItemRequestDTO request) {
    final ItemEntity itemEntity;
    if (request.getItemId() != 0) {
      // check if the item is already defined for the recipe
      if (recipeItemRepository.existsByRecipeIdAndItemId(
          request.getRecipeId(), request.getItemId())) {
        log.error(ALREADY_EXISTS_ITEM);
        throw new ElementAlreadyExistsException(
            String.format(ALREADY_EXISTS_ITEM, request.getItemId()));
      }
      itemEntity = new ItemEntity(request.getItemId());
    } else {
      // check if the new item is already defined before
      if (itemRepository.existsByNameIgnoreCase(request.getItemName())) {
        log.error(ALREADY_EXISTS_ITEM);
        throw new ElementAlreadyExistsException(
            String.format(ALREADY_EXISTS_ITEM, request.getItemId()));
      }
      ItemtypeEntity itemtype = itemtypeRepository.findByType(request.getType());
      if (null == itemtype) {
        itemtype = itemtypeRepository.save(new ItemtypeEntity(0L, request.getType()));
      }
      itemEntity = itemRepository.save(new ItemEntity(0L, request.getItemName(), itemtype));
    }
    // if needed, we can also check if recipe and unit values exists in db (we assumed recipe is
    // already defined and unit is selected from the list)
    final RecipeEntity recipeEntity = new RecipeEntity(request.getRecipeId());
    final RecipeItemEntity recipeItemEntity =
        new RecipeItemEntity(recipeEntity, itemEntity, request.getUnit(), request.getItemQty());

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
                  log.error(NOT_FOUND_ITEM);
                  return new NoSuchElementFoundException(NOT_FOUND_ITEM);
                });
    recipeItemRepository.delete(recipeItemEntity);
  }
}

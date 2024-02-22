package srinageswari.programmedhousehold.backend.service.recipeitem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import srinageswari.programmedhousehold.backend.common.Constants;
import srinageswari.programmedhousehold.backend.common.exception.helper.NoSuchElementFoundException;
import srinageswari.programmedhousehold.backend.dto.RecipeItemDTO;
import srinageswari.programmedhousehold.backend.mapper.RecipeItemMapper;
import srinageswari.programmedhousehold.backend.model.ItemEntity;
import srinageswari.programmedhousehold.backend.model.ItemtypeEntity;
import srinageswari.programmedhousehold.backend.model.RecipeEntity;
import srinageswari.programmedhousehold.backend.model.RecipeItemEntity;
import srinageswari.programmedhousehold.backend.repository.ItemRepository;
import srinageswari.programmedhousehold.backend.repository.ItemtypeRepository;
import srinageswari.programmedhousehold.backend.repository.RecipeItemRepository;

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
  private final RecipeItemMapper recipeItemMapper;

  /**
   * Adds item to the given recipe
   *
   * @param recipeItemDTO
   * @return
   */
  @Transactional
  public RecipeItemDTO addItemToRecipe(RecipeItemDTO recipeItemDTO) {
    final ItemEntity itemEntity = processRecipeItem(recipeItemDTO);
    final RecipeEntity recipeEntity = new RecipeEntity(recipeItemDTO.getRecipe().getId());
    final RecipeItemEntity recipeItemEntity =
        new RecipeItemEntity(
            recipeEntity,
            itemEntity,
            recipeItemDTO.getRequiredQty(),
            recipeItemDTO.getCulinaryStep());
    return recipeItemMapper.toDto(recipeItemRepository.save(recipeItemEntity));
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

  public ItemEntity processRecipeItem(RecipeItemDTO recipeItemDTO) {
    final ItemEntity itemEntity;
    itemEntity =
        itemRepository
            .findById(recipeItemDTO.getItem().getId())
            .orElseGet(
                () -> {
                  ItemtypeEntity itemtype =
                      itemtypeRepository.findByType(
                          recipeItemDTO.getItem().getItemtype().getType());
                  if (itemtype == null) {
                    itemtype =
                        itemtypeRepository.save(
                            new ItemtypeEntity(
                                0L, recipeItemDTO.getItem().getItemtype().getType()));
                  }
                  return itemRepository.save(
                      new ItemEntity(0L, recipeItemDTO.getItem().getName(), itemtype));
                });
    return itemEntity;
  }
}

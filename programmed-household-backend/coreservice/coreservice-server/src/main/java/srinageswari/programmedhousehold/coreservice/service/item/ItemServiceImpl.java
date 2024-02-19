package srinageswari.programmedhousehold.coreservice.service.item;

import static org.apache.commons.text.WordUtils.capitalizeFully;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import srinageswari.programmedhousehold.coreservice.common.Constants;
import srinageswari.programmedhousehold.coreservice.common.RecipeUtil;
import srinageswari.programmedhousehold.coreservice.common.exception.helper.ElementAlreadyExistsException;
import srinageswari.programmedhousehold.coreservice.common.exception.helper.NoSuchElementFoundException;
import srinageswari.programmedhousehold.coreservice.common.search.SearchSpecification;
import srinageswari.programmedhousehold.coreservice.dto.ItemDTO;
import srinageswari.programmedhousehold.coreservice.dto.PerishableItemsDTO;
import srinageswari.programmedhousehold.coreservice.dto.PerishableItemsResponseDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.SearchRequestDTO;
import srinageswari.programmedhousehold.coreservice.mapper.ItemMapper;
import srinageswari.programmedhousehold.coreservice.model.ItemEntity;
import srinageswari.programmedhousehold.coreservice.model.ItemtypeEntity;
import srinageswari.programmedhousehold.coreservice.repository.ItemRepository;
import srinageswari.programmedhousehold.coreservice.service.leftover.LeftoverService;

/**
 * @author smanickavasagam
 *     <p>Service used for adding, removing, updating item
 */
@Slf4j(topic = "ItemService")
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements IItemService {

  private final ItemRepository itemRepository;
  private final ItemMapper itemMapper;
  private final LeftoverService leftoverService;

  /**
   * Fetches an item by the given id
   *
   * @param id
   * @return
   */
  public ItemDTO findById(Long id) {
    return itemRepository
        .findById(id)
        .map(itemMapper::toDto)
        .orElseThrow(
            () -> {
              log.error(Constants.NOT_FOUND_ITEM);
              return new NoSuchElementFoundException(Constants.NOT_FOUND_ITEM);
            });
  }

  /**
   * Fetches all items based on the given filter parameters
   *
   * @param request
   * @return Paginated item data
   */
  @Transactional(readOnly = true)
  public Page<ItemDTO> findAll(SearchRequestDTO request) {
    final SearchSpecification<ItemEntity> specification = new SearchSpecification<>(request);
    final Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
    final Page<ItemDTO> items =
        itemRepository.findAll(specification, pageable).map(itemMapper::toDto);
    if (items.isEmpty()) {
      log.error(Constants.NOT_FOUND_RECORD);
      throw new NoSuchElementFoundException(Constants.NOT_FOUND_RECORD);
    }
    return items;
  }

  /**
   * Creates a new item using the given request parameters
   *
   * @param request
   * @return
   */
  public ItemDTO create(ItemDTO request) {
    if (itemRepository.existsByNameIgnoreCase(request.getName())) {
      log.error(Constants.ALREADY_EXISTS_ITEM);
      throw new ElementAlreadyExistsException(Constants.ALREADY_EXISTS_ITEM);
    }
    final ItemEntity itemEntity = itemMapper.toEntity(request);
    return itemMapper.toDto(itemRepository.save(itemEntity));
  }

  /**
   * Updates item using the given request parameters
   *
   * @param request
   * @return
   */
  public ItemDTO update(ItemDTO request) {
    final ItemEntity itemEntity =
        itemRepository
            .findById(request.getId())
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_ITEM);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_ITEM);
                });

    if (itemRepository.existsByNameIgnoreCase(request.getName())) {
      log.error(Constants.ALREADY_EXISTS_ITEM);
      throw new ElementAlreadyExistsException(Constants.ALREADY_EXISTS_ITEM);
    }
    itemEntity.setName(capitalizeFully(request.getName()));
    return itemMapper.toDto(itemRepository.save(itemEntity));
  }

  /**
   * Deletes item by the given id
   *
   * @param id
   * @return
   */
  public void deleteById(Long id) {
    final ItemEntity itemEntity =
        itemRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_ITEM);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_ITEM);
                });
    itemRepository.delete(itemEntity);
  }

  public PerishableItemsResponseDTO getPerishableItems() {
    List<ItemEntity> perishableItems = itemRepository.findItemsInStockAndInFridge();
    PerishableItemsResponseDTO perishableItemsResponseDTO = new PerishableItemsResponseDTO();
    Map<ItemtypeEntity, List<ItemEntity>> itemsByType =
        perishableItems.stream().collect(Collectors.groupingBy(ItemEntity::getItemtype));
    for (Map.Entry<ItemtypeEntity, List<ItemEntity>> entry : itemsByType.entrySet()) {
      List<PerishableItemsDTO> mappedItems =
          entry.getValue().stream()
              .map(this::mapItemToPerishableItemDTO)
              .collect(Collectors.toList());
      perishableItemsResponseDTO
          .getTypePerishableItemsMap()
          .put(entry.getKey().getType(), mappedItems);
    }
    perishableItemsResponseDTO
        .getTypePerishableItemsMap()
        .put("LeftOvers", new ArrayList<>(leftoverService.listLeftovers()));
    return perishableItemsResponseDTO;
  }

  public PerishableItemsDTO mapItemToPerishableItemDTO(ItemEntity itemEntity) {
    PerishableItemsDTO perishableItemsDTO = new PerishableItemsDTO();
    perishableItemsDTO.setId(itemEntity.getId());
    perishableItemsDTO.setName(itemEntity.getName());
    perishableItemsDTO.setQuantity(
        RecipeUtil.formatQty(BigDecimal.valueOf(itemEntity.getItemStockQty()))
            + itemEntity.getStockUnit().getLabel());
    if (itemEntity.getStockedDt() != null) {
      perishableItemsDTO.setStorageDt(itemEntity.getStockedDt());
      perishableItemsDTO.setUseBy(
          calculateUseByDt(itemEntity.getStockedDt(), itemEntity.getItemtype().getStorageLife()));
    }
    return perishableItemsDTO;
  }

  public static Date calculateUseByDt(Date date, int days) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DATE, days);
    return calendar.getTime();
  }
}

package srinageswari.programmedhousehold.coreservice.service.item;

import static org.apache.commons.text.WordUtils.capitalizeFully;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import srinageswari.programmedhousehold.coreservice.common.Constants;
import srinageswari.programmedhousehold.coreservice.common.exception.helper.ElementAlreadyExistsException;
import srinageswari.programmedhousehold.coreservice.common.exception.helper.NoSuchElementFoundException;
import srinageswari.programmedhousehold.coreservice.common.search.SearchSpecification;
import srinageswari.programmedhousehold.coreservice.dto.ItemDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.SearchRequestDTO;
import srinageswari.programmedhousehold.coreservice.mapper.ItemMapper;
import srinageswari.programmedhousehold.coreservice.model.ItemEntity;
import srinageswari.programmedhousehold.coreservice.repository.ItemRepository;

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
  public CommandResponseDTO create(ItemDTO request) {
    if (itemRepository.existsByNameIgnoreCase(request.getName())) {
      log.error(Constants.ALREADY_EXISTS_ITEM);
      throw new ElementAlreadyExistsException(Constants.ALREADY_EXISTS_ITEM);
    }
    final ItemEntity itemEntity = itemMapper.toEntity(request);
    itemRepository.save(itemEntity);
    return CommandResponseDTO.builder().id(itemEntity.getId()).build();
  }

  /**
   * Updates item using the given request parameters
   *
   * @param request
   * @return
   */
  public CommandResponseDTO update(ItemDTO request) {
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
    itemRepository.save(itemEntity);
    return CommandResponseDTO.builder().id(itemEntity.getId()).build();
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
}

package srinageswari.programmedhousehold.service.item;

import static org.apache.commons.text.WordUtils.capitalizeFully;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import srinageswari.programmedhousehold.common.Constants;
import srinageswari.programmedhousehold.common.dto.CommandResponseDTO;
import srinageswari.programmedhousehold.common.dto.SearchRequestDTO;
import srinageswari.programmedhousehold.common.exception.helper.ElementAlreadyExistsException;
import srinageswari.programmedhousehold.common.exception.helper.NoSuchElementFoundException;
import srinageswari.programmedhousehold.common.search.SearchSpecification;
import srinageswari.programmedhousehold.dto.item.ItemMapper;
import srinageswari.programmedhousehold.dto.item.ItemRequestDTO;
import srinageswari.programmedhousehold.dto.item.ItemResponseDTO;
import srinageswari.programmedhousehold.model.Item;
import srinageswari.programmedhousehold.repository.ItemRepository;

/**
 * @author smanickavasagam
 *     <p>Service used for adding, removing, updating item
 */
@Slf4j(topic = "ItemService")
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements IItemService {

  private final ItemRepository itemRepository;
  private final ItemMapper itemRequestMapper;

  /**
   * Fetches an item by the given id
   *
   * @param id
   * @return
   */
  public ItemResponseDTO findById(Long id) {
    return itemRepository
        .findById(id)
        .map(ItemResponseDTO::new)
        .orElseThrow(
            () -> {
              log.error(Constants.NOT_FOUND_INGREDIENT);
              return new NoSuchElementFoundException(Constants.NOT_FOUND_INGREDIENT);
            });
  }

  /**
   * Fetches all items based on the given filter parameters
   *
   * @param request
   * @return Paginated item data
   */
  @Transactional(readOnly = true)
  public Page<ItemResponseDTO> findAll(SearchRequestDTO request) {
    final SearchSpecification<Item> specification = new SearchSpecification<>(request);
    final Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
    final Page<ItemResponseDTO> items =
        itemRepository.findAll(specification, pageable).map(ItemResponseDTO::new);
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
  public CommandResponseDTO create(ItemRequestDTO request) {
    if (itemRepository.existsByNameIgnoreCase(request.getName())) {
      log.error(Constants.ALREADY_EXISTS_INGREDIENT);
      throw new ElementAlreadyExistsException(Constants.ALREADY_EXISTS_INGREDIENT);
    }
    final Item item = itemRequestMapper.toEntity(request);
    itemRepository.save(item);
    return CommandResponseDTO.builder().id(item.getId()).build();
  }

  /**
   * Updates item using the given request parameters
   *
   * @param request
   * @return
   */
  public CommandResponseDTO update(ItemRequestDTO request) {
    final Item item =
        itemRepository
            .findById(request.getId())
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_INGREDIENT);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_INGREDIENT);
                });

    if (itemRepository.existsByNameIgnoreCase(request.getName())) {
      log.error(Constants.ALREADY_EXISTS_INGREDIENT);
      throw new ElementAlreadyExistsException(Constants.ALREADY_EXISTS_INGREDIENT);
    }
    item.setName(capitalizeFully(request.getName()));
    itemRepository.save(item);
    return CommandResponseDTO.builder().id(item.getId()).build();
  }

  /**
   * Deletes item by the given id
   *
   * @param id
   * @return
   */
  public void deleteById(Long id) {
    final Item item =
        itemRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_INGREDIENT);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_INGREDIENT);
                });
    itemRepository.delete(item);
  }
}

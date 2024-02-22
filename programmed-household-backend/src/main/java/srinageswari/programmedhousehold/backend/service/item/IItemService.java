package srinageswari.programmedhousehold.backend.service.item;

import org.springframework.data.domain.Page;
import srinageswari.programmedhousehold.backend.dto.ItemDTO;
import srinageswari.programmedhousehold.backend.dto.PerishableItemsResponseDTO;
import srinageswari.programmedhousehold.backend.dto.common.SearchRequestDTO;

/**
 * @author smanickavasagam
 */
public interface IItemService {
  public ItemDTO findById(Long id);

  public Page<ItemDTO> findAll(SearchRequestDTO request);

  public ItemDTO create(ItemDTO request);

  public ItemDTO update(ItemDTO request);

  public void deleteById(Long id);

  public PerishableItemsResponseDTO getPerishableItems();
}

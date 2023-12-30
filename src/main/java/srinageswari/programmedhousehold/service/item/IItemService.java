package srinageswari.programmedhousehold.service.item;

import org.springframework.data.domain.Page;
import srinageswari.programmedhousehold.common.dto.CommandResponseDTO;
import srinageswari.programmedhousehold.common.dto.SearchRequestDTO;
import srinageswari.programmedhousehold.dto.item.ItemRequestDTO;
import srinageswari.programmedhousehold.dto.item.ItemResponseDTO;

/**
 * @author smanickavasagam
 */
public interface IItemService {
  public ItemResponseDTO findById(Long id);

  public Page<ItemResponseDTO> findAll(SearchRequestDTO request);

  public CommandResponseDTO create(ItemRequestDTO request);

  public CommandResponseDTO update(ItemRequestDTO request);

  public void deleteById(Long id);
}

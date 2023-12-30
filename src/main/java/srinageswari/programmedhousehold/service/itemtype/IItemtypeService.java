package srinageswari.programmedhousehold.service.itemtype;

import org.springframework.data.domain.Page;
import srinageswari.programmedhousehold.common.dto.CommandResponseDTO;
import srinageswari.programmedhousehold.common.dto.SearchRequestDTO;
import srinageswari.programmedhousehold.dto.itemtype.ItemtypeRequestDTO;
import srinageswari.programmedhousehold.dto.itemtype.ItemtypeResponseDTO;

/**
 * @author smanickavasagam
 */
public interface IItemtypeService {

  public ItemtypeResponseDTO findById(Long id);

  public Page<ItemtypeResponseDTO> findAll(SearchRequestDTO request);

  public CommandResponseDTO create(ItemtypeRequestDTO request);

  public CommandResponseDTO update(ItemtypeRequestDTO request);

  public void deleteById(Long id);
}

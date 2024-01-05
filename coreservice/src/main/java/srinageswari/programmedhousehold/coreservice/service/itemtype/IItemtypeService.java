package srinageswari.programmedhousehold.coreservice.service.itemtype;

import org.springframework.data.domain.Page;
import srinageswari.programmedhousehold.coreservice.common.dto.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.common.dto.SearchRequestDTO;
import srinageswari.programmedhousehold.coreservice.dto.itemtype.ItemtypeRequestDTO;
import srinageswari.programmedhousehold.coreservice.dto.itemtype.ItemtypeResponseDTO;

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

package srinageswari.programmedhousehold.coreservice.server.service.category;

import org.springframework.data.domain.Page;
import srinageswari.programmedhousehold.coreservice.client.dto.CategoryDTO;
import srinageswari.programmedhousehold.coreservice.client.dto.common.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.client.dto.common.SearchRequestDTO;

/**
 * @author smanickavasagam
 */
public interface ICategoryService {

  public CategoryDTO findById(Long id);

  public Page<CategoryDTO> findAll(SearchRequestDTO request);

  public CommandResponseDTO create(CategoryDTO request);

  public CommandResponseDTO update(CategoryDTO request);

  public void deleteById(Long id);
}

package srinageswari.programmedhousehold.service.category;

import org.springframework.data.domain.Page;
import srinageswari.programmedhousehold.common.dto.CommandResponseDTO;
import srinageswari.programmedhousehold.common.dto.SearchRequestDTO;
import srinageswari.programmedhousehold.dto.category.CategoryRequestDTO;
import srinageswari.programmedhousehold.dto.category.CategoryResponseDTO;

/**
 * @author smanickavasagam
 */
public interface ICategoryService {

  public CategoryResponseDTO findById(Long id);

  public Page<CategoryResponseDTO> findAll(SearchRequestDTO request);

  public CommandResponseDTO create(CategoryRequestDTO request);

  public CommandResponseDTO update(CategoryRequestDTO request);

  public void deleteById(Long id);
}

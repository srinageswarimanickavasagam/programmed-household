package srinageswari.programmedhousehold.backend.service.category;

import org.springframework.data.domain.Page;
import srinageswari.programmedhousehold.backend.dto.CategoryDTO;
import srinageswari.programmedhousehold.backend.dto.common.SearchRequestDTO;

/**
 * @author smanickavasagam
 */
public interface ICategoryService {

  public CategoryDTO findById(Long id);

  public Page<CategoryDTO> findAll(SearchRequestDTO request);

  public CategoryDTO create(CategoryDTO request);

  public CategoryDTO update(CategoryDTO request);

  public void deleteById(Long id);
}

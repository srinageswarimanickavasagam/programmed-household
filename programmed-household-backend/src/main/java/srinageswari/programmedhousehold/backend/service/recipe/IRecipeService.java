package srinageswari.programmedhousehold.backend.service.recipe;

import java.util.List;
import org.springframework.data.domain.Page;
import srinageswari.programmedhousehold.backend.dto.RecipeDTO;
import srinageswari.programmedhousehold.backend.dto.RecipeResponseDTO;
import srinageswari.programmedhousehold.backend.dto.common.SearchRequestDTO;

/**
 * @author smanickavasagam
 */
public interface IRecipeService {
  RecipeResponseDTO findById(Long id);

  Page<RecipeResponseDTO> findAll(SearchRequestDTO request);

  RecipeResponseDTO create(RecipeDTO request);

  List<RecipeResponseDTO> bulkInsert(List<RecipeDTO> recipeDTOList);

  RecipeResponseDTO update(RecipeDTO request);

  List<RecipeResponseDTO> getRecipeByCategoryId(Long id);

  void deleteById(Long id);
}

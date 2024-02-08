package srinageswari.programmedhousehold.coreservice.service.recipe;

import java.util.List;
import org.springframework.data.domain.Page;
import srinageswari.programmedhousehold.coreservice.dto.RecipeDTO;
import srinageswari.programmedhousehold.coreservice.dto.RecipeResponseDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.SearchRequestDTO;

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

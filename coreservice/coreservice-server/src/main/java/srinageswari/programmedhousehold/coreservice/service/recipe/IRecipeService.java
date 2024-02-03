package srinageswari.programmedhousehold.coreservice.service.recipe;

import java.util.List;
import org.springframework.data.domain.Page;
import srinageswari.programmedhousehold.coreservice.dto.RecipeDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.SearchRequestDTO;

/**
 * @author smanickavasagam
 */
public interface IRecipeService {
  RecipeDTO findById(Long id);

  Page<RecipeDTO> findAll(SearchRequestDTO request);

  RecipeDTO create(RecipeDTO request);

  List<RecipeDTO> bulkInsert(List<RecipeDTO> recipeDTOList);

  RecipeDTO update(RecipeDTO request);

  List<RecipeDTO> getRecipeByCategoryId(Long id);

  List<RecipeDTO> getTodaysRecipes();

  void deleteById(Long id);
}

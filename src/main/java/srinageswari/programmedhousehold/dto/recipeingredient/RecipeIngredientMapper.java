package srinageswari.programmedhousehold.dto.recipeingredient;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import srinageswari.programmedhousehold.model.RecipeIngredient;

/**
 * @author smanickavasagam
 *     <p>Mapper for RecipeIngredientRequest
 */
@Mapper(componentModel = "spring")
public interface RecipeIngredientMapper {

  RecipeIngredientMapper MAPPER = Mappers.getMapper(RecipeIngredientMapper.class);

  RecipeIngredient toEntity(RecipeIngredientRequestDTO dto);

  RecipeIngredientRequestDTO toDto(RecipeIngredient entity);
}

package srinageswari.programmedhousehold.dto.recipeitem;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import srinageswari.programmedhousehold.model.RecipeItemEntity;

/**
 * @author smanickavasagam
 *     <p>Mapper for RecipeItemRequest
 */
@Mapper(componentModel = "spring")
public interface RecipeItemMapper {

  RecipeItemMapper MAPPER = Mappers.getMapper(RecipeItemMapper.class);

  RecipeItemEntity toEntity(RecipeItemRequestDTO dto);

  RecipeItemRequestDTO toDto(RecipeItemEntity entity);
}

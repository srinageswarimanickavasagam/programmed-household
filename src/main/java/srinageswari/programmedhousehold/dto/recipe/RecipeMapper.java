package srinageswari.programmedhousehold.dto.recipe;

import org.apache.commons.text.WordUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import srinageswari.programmedhousehold.model.Recipe;

/**
 * @author smanickavasagam
 *     <p>Mapper for RecipeRequest
 */
@Mapper(componentModel = "spring")
public interface RecipeMapper {

  RecipeMapper MAPPER = Mappers.getMapper(RecipeMapper.class);

  Recipe toEntity(RecipeRequestDTO dto);

  RecipeRequestDTO toDto(Recipe entity);

  @AfterMapping
  default void getCapitalizedTitle(@MappingTarget Recipe entity, RecipeRequestDTO dto) {
    entity.setTitle(WordUtils.capitalizeFully(dto.getTitle()));
  }
}

package srinageswari.programmedhousehold.coreservice.dto.recipe;

import org.apache.commons.text.WordUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import srinageswari.programmedhousehold.coreservice.model.RecipeEntity;

/**
 * @author smanickavasagam
 *     <p>Mapper for RecipeRequest
 */
@Mapper(componentModel = "spring")
public interface RecipeMapper {

  RecipeMapper MAPPER = Mappers.getMapper(RecipeMapper.class);

  RecipeEntity toEntity(RecipeRequestDTO dto);

  RecipeRequestDTO toDto(RecipeEntity entity);

  @AfterMapping
  default void getCapitalizedTitle(@MappingTarget RecipeEntity entity, RecipeRequestDTO dto) {
    entity.setTitle(WordUtils.capitalizeFully(dto.getTitle()));
  }
}

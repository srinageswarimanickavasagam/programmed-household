package srinageswari.programmedhousehold.coreservice.server.mapper;

import org.apache.commons.text.WordUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;
import srinageswari.programmedhousehold.coreservice.client.dto.RecipeDTO;
import srinageswari.programmedhousehold.coreservice.server.model.RecipeEntity;

/**
 * @author smanickavasagam
 *     <p>Mapper for RecipeRequest
 */
@Mapper(
    componentModel = "spring",
    uses = {CategoryMapper.class, RecipeItemMapper.class})
@Component
public interface RecipeMapper {

  // RecipeMapper MAPPER = Mappers.getMapper(RecipeMapper.class);

  RecipeEntity toEntity(RecipeDTO dto);

  RecipeDTO toDto(RecipeEntity entity);

  @AfterMapping
  default void getCapitalizedTitle(@MappingTarget RecipeEntity entity, RecipeDTO dto) {
    entity.setTitle(WordUtils.capitalizeFully(dto.getTitle()));
  }
}

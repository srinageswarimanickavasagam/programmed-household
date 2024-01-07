package srinageswari.programmedhousehold.coreservice.mapper;

import org.apache.commons.text.WordUtils;
import org.mapstruct.*;
import org.springframework.stereotype.Component;
import srinageswari.programmedhousehold.coreservice.dto.RecipeDTO;
import srinageswari.programmedhousehold.coreservice.model.RecipeEntity;

/**
 * @author smanickavasagam
 *     <p>Mapper for RecipeRequest
 */
@Mapper(
    componentModel = "spring",
    uses = {CategoryMapper.class, RecipeItemMapper.class, AppUserMapper.class})
@Component
public interface RecipeMapper {
  RecipeEntity toEntity(RecipeDTO dto);

  @Mappings({@Mapping(target = "appUser", ignore = true)})
  RecipeDTO toDto(RecipeEntity entity);

  @AfterMapping
  default void getCapitalizedTitle(@MappingTarget RecipeEntity entity, RecipeDTO dto) {
    entity.setTitle(WordUtils.capitalizeFully(dto.getTitle()));
  }
}

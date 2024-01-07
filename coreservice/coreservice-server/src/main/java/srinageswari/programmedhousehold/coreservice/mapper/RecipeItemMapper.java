package srinageswari.programmedhousehold.coreservice.mapper;

import org.mapstruct.*;
import org.springframework.stereotype.Component;
import srinageswari.programmedhousehold.coreservice.dto.RecipeItemDTO;
import srinageswari.programmedhousehold.coreservice.model.RecipeItemEntity;

/**
 * @author smanickavasagam
 *     <p>Mapper for RecipeItemRequest
 */
@Mapper(
    componentModel = "spring",
    uses = {ItemMapper.class})
@Component
public interface RecipeItemMapper {
  RecipeItemEntity toEntity(RecipeItemDTO dto);

  @Mappings({@Mapping(target = "recipe", ignore = true)})
  RecipeItemDTO toDto(RecipeItemEntity entity);
}

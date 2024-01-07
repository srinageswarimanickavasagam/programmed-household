package srinageswari.programmedhousehold.coreservice.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import srinageswari.programmedhousehold.coreservice.model.RecipeItemEntity;
import srinageswari.programmedhousehold.coreservice.dto.RecipeItemDTO;

/**
 * @author smanickavasagam
 *     <p>Mapper for RecipeItemRequest
 */
@Mapper(
    componentModel = "spring",
    uses = {ItemMapper.class})
@Component
public interface RecipeItemMapper {

  // RecipeItemMapper MAPPER = Mappers.getMapper(RecipeItemMapper.class);

  RecipeItemEntity toEntity(RecipeItemDTO dto);

  RecipeItemDTO toDto(RecipeItemEntity entity);
}

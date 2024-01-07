package srinageswari.programmedhousehold.coreservice.server.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import srinageswari.programmedhousehold.coreservice.client.dto.RecipeItemDTO;
import srinageswari.programmedhousehold.coreservice.server.model.RecipeItemEntity;

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

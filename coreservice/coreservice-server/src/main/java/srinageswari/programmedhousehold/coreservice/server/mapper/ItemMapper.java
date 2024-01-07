package srinageswari.programmedhousehold.coreservice.server.mapper;

import org.apache.commons.text.WordUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;
import srinageswari.programmedhousehold.coreservice.client.dto.ItemDTO;
import srinageswari.programmedhousehold.coreservice.server.model.ItemEntity;

/**
 * @author smanickavasagam
 *     <p>Mapper for ItemRequest
 */
@Mapper(
    componentModel = "spring",
    uses = {ItemtypeMapper.class})
@Component
public interface ItemMapper {

  // ItemMapper MAPPER = Mappers.getMapper(ItemMapper.class);

  ItemEntity toEntity(ItemDTO dto);

  ItemDTO toDto(ItemEntity entity);

  @AfterMapping
  default void capitalizeFully(@MappingTarget ItemEntity entity, ItemDTO dto) {
    entity.setName(WordUtils.capitalizeFully(dto.getName()));
  }
}

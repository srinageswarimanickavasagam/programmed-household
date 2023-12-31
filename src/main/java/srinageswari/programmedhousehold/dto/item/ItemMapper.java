package srinageswari.programmedhousehold.dto.item;

import org.apache.commons.text.WordUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import srinageswari.programmedhousehold.model.ItemEntity;

/**
 * @author smanickavasagam
 *     <p>Mapper for ItemRequest
 */
@Mapper(componentModel = "spring")
public interface ItemMapper {

  ItemMapper MAPPER = Mappers.getMapper(ItemMapper.class);

  ItemEntity toEntity(ItemRequestDTO dto);

  ItemRequestDTO toDto(ItemEntity entity);

  @AfterMapping
  default void capitalizeFully(@MappingTarget ItemEntity entity, ItemRequestDTO dto) {
    entity.setName(WordUtils.capitalizeFully(dto.getName()));
  }
}

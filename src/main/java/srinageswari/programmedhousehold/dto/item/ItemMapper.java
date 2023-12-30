package srinageswari.programmedhousehold.dto.item;

import org.apache.commons.text.WordUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import srinageswari.programmedhousehold.model.Item;

/**
 * @author smanickavasagam
 *     <p>Mapper for ItemRequest
 */
@Mapper(componentModel = "spring")
public interface ItemMapper {

  ItemMapper MAPPER = Mappers.getMapper(ItemMapper.class);

  Item toEntity(ItemRequestDTO dto);

  ItemRequestDTO toDto(Item entity);

  @AfterMapping
  default void capitalizeFully(@MappingTarget Item entity, ItemRequestDTO dto) {
    entity.setName(WordUtils.capitalizeFully(dto.getName()));
  }
}

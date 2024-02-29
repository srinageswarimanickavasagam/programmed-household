package srinageswari.programmedhousehold.backend.mapper;

import org.apache.commons.text.WordUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;
import srinageswari.programmedhousehold.backend.dto.ItemDTO;
import srinageswari.programmedhousehold.backend.model.ItemEntity;

/**
 * @author smanickavasagam
 *     <p>Mapper for ItemRequest
 */
@Mapper(
    componentModel = "spring",
    uses = {ItemtypeMapper.class})
@Component
public interface ItemMapper {
  ItemEntity toEntity(ItemDTO dto);

  ItemDTO toDto(ItemEntity entity);

  @AfterMapping
  default void capitalizeFully(@MappingTarget ItemEntity entity, ItemDTO dto) {
    entity.setName(WordUtils.capitalizeFully(dto.getName()));
  }
}

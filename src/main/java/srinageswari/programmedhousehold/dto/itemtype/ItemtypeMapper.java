package srinageswari.programmedhousehold.dto.itemtype;

import org.apache.commons.text.WordUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import srinageswari.programmedhousehold.model.ItemtypeEntity;

/**
 * @author smanickavasagam ItemtypeMapper Mapper for ItemtypeRequest
 */
@Mapper(componentModel = "spring")
public interface ItemtypeMapper {

  ItemtypeMapper MAPPER = Mappers.getMapper(ItemtypeMapper.class);

  ItemtypeEntity toEntity(ItemtypeRequestDTO dto);

  ItemtypeRequestDTO toDto(ItemtypeEntity entity);

  @AfterMapping
  default void capitalizeFully(@MappingTarget ItemtypeEntity entity, ItemtypeRequestDTO dto) {
    entity.setType(WordUtils.capitalizeFully(dto.getType()));
  }
}

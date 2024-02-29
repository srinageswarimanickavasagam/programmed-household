package srinageswari.programmedhousehold.backend.mapper;

import org.apache.commons.text.WordUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;
import srinageswari.programmedhousehold.backend.dto.ItemtypeDTO;
import srinageswari.programmedhousehold.backend.model.ItemtypeEntity;

/**
 * @author smanickavasagam ItemtypeMapper Mapper for ItemtypeRequest
 */
@Mapper(componentModel = "spring")
@Component
public interface ItemtypeMapper {
  ItemtypeEntity toEntity(ItemtypeDTO dto);

  ItemtypeDTO toDto(ItemtypeEntity entity);

  @AfterMapping
  default void capitalizeFully(@MappingTarget ItemtypeEntity entity, ItemtypeDTO dto) {
    entity.setType(WordUtils.capitalizeFully(dto.getType()));
  }
}

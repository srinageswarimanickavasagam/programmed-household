package srinageswari.programmedhousehold.coreservice.mapper;

import org.apache.commons.text.WordUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;
import srinageswari.programmedhousehold.coreservice.model.ItemtypeEntity;
import srinageswari.programmedhousehold.coreservice.dto.ItemtypeDTO;

/**
 * @author smanickavasagam ItemtypeMapper Mapper for ItemtypeRequest
 */
@Mapper(componentModel = "spring")
@Component
public interface ItemtypeMapper {

  // ItemtypeMapper MAPPER = Mappers.getMapper(ItemtypeMapper.class);

  ItemtypeEntity toEntity(ItemtypeDTO dto);

  ItemtypeDTO toDto(ItemtypeEntity entity);

  @AfterMapping
  default void capitalizeFully(@MappingTarget ItemtypeEntity entity, ItemtypeDTO dto) {
    entity.setType(WordUtils.capitalizeFully(dto.getType()));
  }
}

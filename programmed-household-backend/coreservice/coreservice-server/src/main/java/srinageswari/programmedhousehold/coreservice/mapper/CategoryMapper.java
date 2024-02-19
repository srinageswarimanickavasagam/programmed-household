package srinageswari.programmedhousehold.coreservice.mapper;

import org.apache.commons.text.WordUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;
import srinageswari.programmedhousehold.coreservice.dto.CategoryDTO;
import srinageswari.programmedhousehold.coreservice.model.CategoryEntity;

/**
 * @author smanickavasagam
 *     <p>Mapper for CategoryRequest
 */
@Mapper(componentModel = "spring")
@Component
public interface CategoryMapper {
  CategoryEntity toEntity(CategoryDTO dto);

  CategoryDTO toDto(CategoryEntity entity);

  @AfterMapping
  default void capitalizeFully(@MappingTarget CategoryEntity entity, CategoryDTO dto) {
    entity.setName(WordUtils.capitalizeFully(dto.getName()));
  }
}

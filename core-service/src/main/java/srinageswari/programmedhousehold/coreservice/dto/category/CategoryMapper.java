package srinageswari.programmedhousehold.coreservice.dto.category;

import org.apache.commons.text.WordUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import srinageswari.programmedhousehold.coreservice.model.CategoryEntity;

/**
 * @author smanickavasagam
 *     <p>Mapper for CategoryRequest
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {

  CategoryMapper MAPPER = Mappers.getMapper(CategoryMapper.class);

  CategoryEntity toEntity(CategoryRequestDTO dto);

  CategoryRequestDTO toDto(CategoryEntity entity);

  @AfterMapping
  default void capitalizeFully(@MappingTarget CategoryEntity entity, CategoryRequestDTO dto) {
    entity.setName(WordUtils.capitalizeFully(dto.getName()));
  }
}
